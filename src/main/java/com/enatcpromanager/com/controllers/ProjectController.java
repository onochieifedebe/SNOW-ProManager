package com.enatcpromanager.com.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.enatcpromanager.com.dto.EmpInOrder;
import com.enatcpromanager.com.dto.MlEmpCount;
import com.enatcpromanager.com.dto.ProjectEmployee;
import com.enatcpromanager.com.entities.Employee;
import com.enatcpromanager.com.entities.Project;
import com.enatcpromanager.com.services.EmployeeService;
import com.enatcpromanager.com.services.ProjectService;
import com.enatcpromanager.com.utilities.EmpOnProjExporter;
import com.enatcpromanager.com.utilities.projPdfExporter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;

@Controller
@RequestMapping({"/projects"})
public class ProjectController {;

	 @Autowired
	 ProjectService proService;

	 @Autowired
	 EmployeeService empService;

  @GetMapping({"/new"})
  public String displayProjectForm(Model model) {
//    List<Employee> employees = empService.getAll();
//    model.addAttribute("allEmployees", employees);

	  	empService.deleteEmpOnComplete();

	  	Project project = new Project();
    	model.addAttribute("project", project);

		List<EmpInOrder> empInOrder = empService.empInOrder();
		model.addAttribute("allEmployees", empInOrder);

	    return "Projects/new-project";
	 }

	  @PostMapping({"/save"})
	  public String createProject(Project project, Model model) {

	    proService.save(project);
	    return "redirect:/projects/new";
	  }

	  @GetMapping({""})
	  public String displayAllProjects(Model model) throws JsonProcessingException {

	    List<Project> projects = proService.getAll();
	    model.addAttribute("projects", projects);

		List<ProjectEmployee> projectemployeeList = empService.projectEmployees();
		model.addAttribute("projectemployeeList", projectemployeeList);
		
		List<MlEmpCount> mlEmpCountData = empService.getMlEmpCount();
		ObjectMapper objectMapper3 = new ObjectMapper();
		String jsonMlEmpString = objectMapper3.writeValueAsString(mlEmpCountData);
	    model.addAttribute("mlEmpList", jsonMlEmpString);

		return "Projects/list-projects";
	  }

	 @GetMapping("/update")
	  public String displayProjectUpdateForm(@RequestParam("id") long id, Model model){

		  Project project = proService.findByProjectId(id);
		  model.addAttribute("project", project);

		  List<Employee> employees = empService.getAll();
		  model.addAttribute("allEmployees", employees);

		  return "Projects/update-projects";
	 }

	@GetMapping("/delete")
		public String deleteProject(@RequestParam("id") long id, Model model){
		Project project = proService.findByProjectId(id);
		proService.delete(project);
		return "redirect:/projects";
	}

    @GetMapping("/exportprojects")
    public void exportPDF(HttpServletResponse response) throws DocumentException, IOException {

    	response.setContentType("application/pdf");
    	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    	String currentDateTime = dateFormatter.format(new Date());

    	String headerKey = "Content-Disposition";
    	String headerValue = "attachment; filename=enatcprojects_" + currentDateTime + ".pdf";
    	response.setHeader(headerKey, headerValue);

    	List<Project> projects = proService.getAll();

    	projPdfExporter exporter = new projPdfExporter(projects);
    	exporter.export(response);
    }

    @GetMapping("/exportempsonproj")
    public void exportEmpOnProjPDF(HttpServletResponse response) throws DocumentException, IOException {

    	response.setContentType("application/pdf");
    	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    	String currentDateTime = dateFormatter.format(new Date());

    	String headerKey = "Content-Disposition";
    	String headerValue = "attachment; filename=enatcempsonproj_" + currentDateTime + ".pdf";
    	response.setHeader(headerKey, headerValue);

    	List<ProjectEmployee> projectemployeeList = empService.projectEmployees();

    	EmpOnProjExporter exporter = new EmpOnProjExporter(projectemployeeList);
    	exporter.export(response);
    }

    @GetMapping("/exportcsvprojects")
	    public void exportCSV(HttpServletResponse response) throws IOException {

	  	response.setContentType("text/csv");
	    	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	    	String currentDateTime = dateFormatter.format(new Date());

	    	String headerKey = "Content-Disposition";
	  	String headerValue = "attachment; filename=enatcprojects_" + currentDateTime + ".csv";
	  	response.setHeader(headerKey, headerValue);

	  	List<Project> projects = proService.getAll();

	  	ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
	  	String[] csvHeader = {"Project Name", "Start Date", "End Date", "Rsc Needed", "Rsc Assigned", "Client Needs", "Status"};
	  	String[] nameMapping = {"name", "startDate", "endDate", "resourcesNeeded", "resourcesAssigned", "description", "status"};

	  	csvWriter.writeHeader(csvHeader);

	  	for (Project project : projects) {
	  		csvWriter.write(project, nameMapping);
	  	}

	  	csvWriter.close();

    }

    @GetMapping("/csvexportempsonproj")
    public void CertRescCSV(HttpServletResponse response) throws IOException {

	  	response.setContentType("text/csv");
	    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	    String currentDateTime = dateFormatter.format(new Date());

	    String headerKey = "Content-Disposition";
	  	String headerValue = "attachment; filename=enatcempsonproj_" + currentDateTime + ".csv";
	  	response.setHeader(headerKey, headerValue);

	  	List<ProjectEmployee> projectEmps = empService.projectEmployees();

	  	ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
	  	String[] csvHeader = {"Project Name", "First Name", "Last Name", "CL Level", "Project Status"};
	  	String[] nameMapping = {"clientName", "firstName", "lastName", "ClLevel", "status"};

	  	csvWriter.writeHeader(csvHeader);

	  	for (ProjectEmployee projectEmp : projectEmps) {
	  		csvWriter.write(projectEmp, nameMapping);
	  	}

	  	csvWriter.close();

    }
}

