package com.enatcpromanager.com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.enatcpromanager.com.dto.ProjectCountData;
import com.enatcpromanager.com.dto.StatusChartData;
import com.enatcpromanager.com.dto.TimeChartData;
import com.enatcpromanager.com.entities.Project;

public interface IProjectRepository extends CrudRepository<Project, Long> {

  List<Project> findAll();

  @Query(nativeQuery = true, value = "SELECT STATUS as label, COUNT(STATUS) AS value FROM PROJECT GROUP BY STATUS")
  List<StatusChartData> getProjectStatus();

  Project findByProjectId(long id);

  @Query(nativeQuery = true, value="SELECT name as projectName, start_date as startDate, end_date as endDate FROM project")
  List<TimeChartData> getTimeData();

  @Query(nativeQuery = true, value="select count(DISTINCT e.employee_id) as label, count( distinct ee.employee_id)-count(DISTINCT e.employee_id) as value " +
  		"from employee e left join project_employee pe ON pe.employee_id = e.employee_id " +
  		"right join employee ee on pe.employee_id = ee.employee_id")
  List<ProjectCountData> getProjectCount();


}
