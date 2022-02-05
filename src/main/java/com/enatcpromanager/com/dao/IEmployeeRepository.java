package com.enatcpromanager.com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.enatcpromanager.com.dto.EmpInOrder;
import com.enatcpromanager.com.dto.EmployeeCertification;
import com.enatcpromanager.com.dto.EmployeeProject;
import com.enatcpromanager.com.dto.MlEmpCount;
import com.enatcpromanager.com.dto.ProjectEmployee;
import com.enatcpromanager.com.dto.ResourceCertification;
import com.enatcpromanager.com.entities.Employee;

public interface IEmployeeRepository extends CrudRepository<Employee, Long> {
  List<Employee> findAll();

  @Query(nativeQuery = true, value = "SELECT e.first_name as FirstName , e.last_name as LastName , e.management_level as ManagementLevel,  COUNT(pe.employee_id) AS NumberOfProjects FROM employee e LEFT JOIN project_employee pe ON pe.employee_id = e.employee_id GROUP BY e.management_level, e.first_name, e.last_name ORDER BY NumberOfProjects DESC")
  List<EmployeeProject> employeeProjects();

  @Query(nativeQuery = true, value = "SELECT  ec.cert_name as Certification, count(employee_id) as ResourceCapacity FROM epic_certification ec inner join CERT_EMPLOYEE ce ON ce.epic_cert_id = ec.epic_cert_id group by ec.cert_name, ec.epic_cert_id order by ResourceCapacity desc")
  List<EmployeeCertification> employeeCertifications();


  @Query(nativeQuery = true, value = "SELECT e.management_level as ClLevel, e.first_name as FirstName, e.last_name as LastName, p.start_date as StartDate, p.end_date as EndDate, p.name as ClientName, p.status "
  		+ "FROM employee e right join project_employee pe on pe.employee_id = e.employee_id "
  		+ "Right Join project p on pe.project_id = p.project_id "
  		+ "order by p.status desc;")
  List<ProjectEmployee> projectEmployees();

  @Query(nativeQuery = true, value = "SELECT e.first_name as FirstName, e.last_name as LastName, e.management_level as ClLevel, ec.cert_name as CertName, e.availability " +
          "FROM employee e right join cert_employee ce on ce.employee_id = e.employee_id " +
          "Inner Join epic_certification ec on ce.epic_cert_id = ec.epic_cert_id " +
          "ORDER BY e.availability")
  List<ResourceCertification> resourceCertifications();

  @Query(nativeQuery = true, value = "SELECT employee_id as employeeId, first_name as firstName, last_name as lastName FROM employee ORDER BY lastName asc")
  List<EmpInOrder> empInOrder();

  Employee findByEmployeeId(long id);


  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(nativeQuery = true, value = "DELETE FROM project_employee WHERE project_id= (SELECT p.project_id " +
  		"FROM project p INNER JOIN project_employee pe ON p.project_id = pe.project_id " +
  		"WHERE p.status = 'COMPLETED') ")
  void deleteEmployees();
  
  @Query(nativeQuery = true, value="SELECT e.management_level as label, count(DISTINCT e.employee_id) - count(DISTINCT pe.employee_id) AS value, count(DISTINCT pe.employee_id) AS onproject "
  		+ "FROM employee e LEFT join project_employee pe on pe.employee_id = e.employee_id "
  		+ "left Join project p on pe.project_id = p.project_id "
  		+ "GROUP BY e.management_level "
  		+ "order by e.management_level desc")
  List<MlEmpCount> getMlEmpCount();


}
