package com.enatcpromanager.com.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_generator")
  @SequenceGenerator(name = "employee_generator", sequenceName = "employee_seq", allocationSize = 1)
  private long employeeId;

  private String firstName;

  private String lastName;

  private String active;

  private String managementLevel;

  private Date availability;

  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
  @JoinTable(name = "cert_employee", joinColumns = {@JoinColumn(name = "employee_id")}, inverseJoinColumns = {@JoinColumn(name = "snowCert_id")})
  private List<SnowCertification> certifications;

  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
  @JoinTable(name = "project_employee", joinColumns = {@JoinColumn(name = "employee_id")}, inverseJoinColumns = {@JoinColumn(name = "project_id")})
  private List<Project> projects;

  public Employee(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }


  public Date getAvailability() {
	return availability;
  }


  public void setAvailability(Date availability) {
	  this.availability = availability;
  }


  public Employee() {}

  public String getManagementLevel() {
    return this.managementLevel;
  }

  public void setManagementLevel(String managementLevel) {
    this.managementLevel = managementLevel;
  }

  public List<Project> getProjects() {
    return this.projects;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }

  public List<SnowCertification> getCertifications() {
    return this.certifications;
  }

  public void setCertifications(List<SnowCertification> certifications) {
    this.certifications = certifications;
  }

  public long getEmployeeId() {
    return this.employeeId;
  }

  public void setEmployeeId(long employeeId) {
    this.employeeId = employeeId;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getActive() {
    return active;
  }

  public void setActive(String active) {
    this.active = active;
  }
}
