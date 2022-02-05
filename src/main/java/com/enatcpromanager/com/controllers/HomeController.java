package com.enatcpromanager.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.enatcpromanager.com.dto.EmployeeProject;
import com.enatcpromanager.com.dto.ProjectCountData;
import com.enatcpromanager.com.dto.StatusChartData;
import com.enatcpromanager.com.dto.TimeChartData;
import com.enatcpromanager.com.entities.Project;
import com.enatcpromanager.com.services.EmployeeService;
import com.enatcpromanager.com.services.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {

  @Autowired
  ProjectService proService;

  @Autowired
  EmployeeService empService;

  @GetMapping({"/"})
  public String displayHome(Model model) throws JsonProcessingException {


    List<EmployeeProject> employeeList = empService.employeeProjects();
    model.addAttribute("employeeList", employeeList);

    List<Project> projects = proService.getAll();
    model.addAttribute("projects", projects);

    List<StatusChartData> statusData = proService.getProjectStatus();
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonString = objectMapper.writeValueAsString(statusData);
    model.addAttribute("statusCount", jsonString);

    List<TimeChartData> timelineData = proService.getTimeData();
    ObjectMapper objectMapper1 = new ObjectMapper();
    String jsonTimelineString = objectMapper1.writeValueAsString(timelineData);
    model.addAttribute("projectTimeList", jsonTimelineString);

    List<ProjectCountData> projCountData = proService.getProjectCount();
    ObjectMapper objectMapper2 = new ObjectMapper();
    String jsonProjCountString = objectMapper2.writeValueAsString(projCountData);
    model.addAttribute("projCount", jsonProjCountString);

    return "Main/home";
  }


}

