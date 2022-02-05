package com.enatcpromanager.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enatcpromanager.com.dto.EmpInOrder;
import com.enatcpromanager.com.dto.EmployeeCertification;
import com.enatcpromanager.com.entities.EpicCertification;
import com.enatcpromanager.com.services.CertificationService;
import com.enatcpromanager.com.services.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping({"/certifications"})
public class CertificationController {

  @Autowired
  CertificationService certService;

  @Autowired
  EmployeeService empService;

  @GetMapping({"/new"})
  public String displayCertificationForm(Model model) {
    EpicCertification certification = new EpicCertification();
    model.addAttribute("epicCert", certification);

    List<EmpInOrder> empInOrder = empService.empInOrder();
    model.addAttribute("allEmployees", empInOrder);

//    List<Employee> employees = empService.getAll();
//    model.addAttribute("allEmployees", employees);

    return "Certifications/new-certification";
  }

  @PostMapping({"/save"})
  public String addCertification(EpicCertification certification, Model model) {
    certService.save(certification);
    return "redirect:/certifications/new";
  }

  @GetMapping({""})
  public String displayAllCertifications(Model model) throws JsonProcessingException {
    List<EpicCertification> certifications = certService.getAll();
    model.addAttribute("certifications", certifications);


    List<EmployeeCertification> certificationEmployeeList = empService.employeeCertifications();
    model.addAttribute("certificationEmployeeList", certificationEmployeeList);



    return "Certifications/list-certifications";
  }
}