package com.enatcpromanager.com.entities;

import java.util.ArrayList;
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
public class SnowCertification {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "snow_certification_generator")
  @SequenceGenerator(name = "snow_certification_generator", sequenceName = "snow_certification_seq", allocationSize = 1)
  private long snow_cert_id;
  
  private String certName;
  
  private String certDescription;
  
  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
  @JoinTable(name = "cert_employee", joinColumns = {@JoinColumn(name = "snowCert_id")}, inverseJoinColumns = {@JoinColumn(name = "employee_id")})
  private List<Employee> employees;
  
  public SnowCertification() {}
  
  public SnowCertification(String certName, String certDescription) {
    this.certName = certName;
    this.certDescription = certDescription;
  }
  
  public List<Employee> getEmployees() {
    return this.employees;
  }
  
  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }  

  
  public long getSnow_cert_id() {
	return snow_cert_id;
}

public void setSnow_cert_id(long snow_cert_id) {
	this.snow_cert_id = snow_cert_id;
}

public String getCertName() {
    return this.certName;
  }
  
  public void setCertName(String certName) {
    this.certName = certName;
  }
  
  public String getCertDescription() {
    return this.certDescription;
  }
  
  public void setCertDescription(String certDescription) {
    this.certDescription = certDescription;
  }
  
  public void addEmployee(Employee emp) {
    if (this.employees == null)
      this.employees = new ArrayList<>(); 
    this.employees.add(emp);
  }
}


