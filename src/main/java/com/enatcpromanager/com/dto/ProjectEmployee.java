package com.enatcpromanager.com.dto;

import java.sql.Date;

public interface ProjectEmployee {
  String getClLevel();
  
  String getFirstName();
  
  String getLastName();
  
  Date getStartDate();
  
  Date getEndDate();
  
  String getClientName();
  
  String getStatus();
}
