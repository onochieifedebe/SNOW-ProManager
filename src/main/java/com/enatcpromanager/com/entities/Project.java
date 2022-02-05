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
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_generator")
  @SequenceGenerator(name = "project_generator", sequenceName = "project_seq", allocationSize = 1)
  private long projectId;
  
  private String company_size;
  
  private String company_industry;
  
  private String name;
  
  private String status;
  
  private Date startDate;
  
  private Date endDate;
  
  private int resourcesAssigned;
  
  private int resourcesNeeded;
  
  private String description;
  
  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
  @JoinTable(name = "project_employee", joinColumns = {@JoinColumn(name = "project_id")}, inverseJoinColumns = {@JoinColumn(name = "employee_id")})
  private List<Employee> employees;
  
  public Project() {}
  
  public Project(String name, String status, Date startDate, Date endDate) {
    this.name = name;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
  }
  
  public List<Employee> getEmployees() {
    return this.employees;
  }
  
  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }
  
  public long getProjectId() {
    return this.projectId;
  }
  
  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public Date getStartDate() {
    return this.startDate;
  }
  
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  
  public Date getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

public int getResourcesAssigned() {
	return resourcesAssigned;
}

public void setResourcesAssigned(int resourcesAssigned) {
	this.resourcesAssigned = resourcesAssigned;
}

public int getResourcesNeeded() {
	return resourcesNeeded;
}

public void setResourcesNeeded(int resourcesNeeded) {
	this.resourcesNeeded = resourcesNeeded;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getCompany_size() {
	return company_size;
}

public void setCompany_size(String company_size) {
	this.company_size = company_size;
}

public String getCompany_industry() {
	return company_industry;
}

public void setCompany_industry(String company_industry) {
	this.company_industry = company_industry;
}
  
  
}


