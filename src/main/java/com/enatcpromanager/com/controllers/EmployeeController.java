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

import com.enatcpromanager.com.dto.EmpCertInOrder;
import com.enatcpromanager.com.dto.ResourceCertification;
import com.enatcpromanager.com.entities.Employee;
import com.enatcpromanager.com.entities.EpicCertification;
import com.enatcpromanager.com.services.CertificationService;
import com.enatcpromanager.com.services.EmployeeService;
import com.enatcpromanager.com.utilities.CertResExporter;
import com.enatcpromanager.com.utilities.empPdfExporter;
import com.lowagie.text.DocumentException;

@Controller
@RequestMapping({"/employees"})
public class EmployeeController {

  @Autowired
  EmployeeService empService;

  @Autowired
  CertificationService certService;


  @GetMapping({"/new"})
  public String displayEmployeeForm(Model model) {
    Employee employee = new Employee();
    model.addAttribute("employee", employee);

    List<EmpCertInOrder> empCertInOrder = certService.empCertInOrder();
    model.addAttribute("empCertInOrder", empCertInOrder);

// ***Kept in case EmpCertInOrder fails. Revert to this & and update the View.***
//    List<EpicCertification> certifications = certService.getAll();
//    model.addAttribute("allCertifications", certifications);

    return "Employees/new-employee";
  }

  @PostMapping({"/save"})
  public String addEmployee(Employee employee, Model model) {
    empService.save(employee);
    return "redirect:/employees/new";
  }

  @GetMapping({""})
  public String displayAllEmployees(Model model) {

    List<Employee> employees = empService.getAll();
    model.addAttribute("employees", employees);

    List<ResourceCertification> resourceCertificationList = empService.resourceCertifications();
    model.addAttribute("resourceCertificationList", resourceCertificationList);

    return "Employees/list-employees";
  }

  @GetMapping("/update")
  public String displayEmployeeUpdateForm(@RequestParam("id") long id, Model model){

    Employee employee = empService.findByEmployeeId(id);
    model.addAttribute("employee", employee);

    List<EpicCertification> certifications = certService.getAll();
    model.addAttribute("allCertifications", certifications);

    return "Employees/update-employees";
  }

  @GetMapping("/delete")
  public String deleteEmployee(@RequestParam("id") long id, Model model){
    Employee employee = empService.findByEmployeeId(id);
    empService.delete(employee);
    return "redirect:/employees";
  }

  @GetMapping("/exportemployees")
  public void exportPDF(HttpServletResponse response) throws DocumentException, IOException {

	response.setContentType("application/pdf");
  	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
  	String currentDateTime = dateFormatter.format(new Date());

  	String headerKey = "Content-Disposition";
	String headerValue = "attachment; filename=enatcemployees_" + currentDateTime + ".pdf";
	response.setHeader(headerKey, headerValue);

	List<Employee> employees = empService.getAll();

	empPdfExporter exporter = new empPdfExporter(employees);
	exporter.export(response);

  }

  @GetMapping("/exportcsvemployees")
  public void exportCSV(HttpServletResponse response) throws IOException {

	response.setContentType("text/csv");
  	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
  	String currentDateTime = dateFormatter.format(new Date());

  	String headerKey = "Content-Disposition";
	String headerValue = "attachment; filename=enatcemployees_" + currentDateTime + ".csv";
	response.setHeader(headerKey, headerValue);

	List<Employee> employees = empService.getAll();

	ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
	String[] csvHeader = {"First Name", "Last Name", "Management Level", "Availability"};
	String[] nameMapping = {"firstName", "lastName", "managementLevel", "availability"};

	csvWriter.writeHeader(csvHeader);

	for (Employee employee : employees) {
		csvWriter.write(employee, nameMapping);
	}

	csvWriter.close();

  }

  @GetMapping("/certifiedresources")
  public void exportCertResPDF(HttpServletResponse response) throws DocumentException, IOException {

	response.setContentType("application/pdf");
  	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
  	String currentDateTime = dateFormatter.format(new Date());

  	String headerKey = "Content-Disposition";
  	String headerValue = "attachment; filename=certifiedresources_" + currentDateTime + ".pdf";
  	response.setHeader(headerKey, headerValue);

  	List<ResourceCertification> resourceCertificationList = empService.resourceCertifications();

  	CertResExporter exporter = new CertResExporter(resourceCertificationList);
  	exporter.export(response);

  }

  @GetMapping("/csvcertifiedresources")
  public void CertRescCSV(HttpServletResponse response) throws IOException {

	response.setContentType("text/csv");
  	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
  	String currentDateTime = dateFormatter.format(new Date());

  	String headerKey = "Content-Disposition";
	String headerValue = "attachment; filename=certifiedresources_" + currentDateTime + ".csv";
	response.setHeader(headerKey, headerValue);

	List<ResourceCertification> resourceCerts = empService.resourceCertifications();

	ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
	String[] csvHeader = {"Certification", "First Name", "Last Name", "Management Level", "Availability Date"};
	String[] nameMapping = {"certName", "firstName", "lastName", "ClLevel", "availability"};

	csvWriter.writeHeader(csvHeader);

	for (ResourceCertification resourceCert : resourceCerts) {
		csvWriter.write(resourceCert, nameMapping);
	}

	csvWriter.close();

  }
}
