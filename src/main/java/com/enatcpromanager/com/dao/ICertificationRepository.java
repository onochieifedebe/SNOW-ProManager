package com.enatcpromanager.com.dao;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.enatcpromanager.com.dto.EmpCertInOrder;
import com.enatcpromanager.com.entities.EpicCertification;

public interface ICertificationRepository extends CrudRepository<EpicCertification, Long> {

  List<EpicCertification> findAll();

  @Query(nativeQuery = true, value = "SELECT epic_cert_id as certId, cert_name as certName FROM epic_certification ORDER BY cert_name asc")
  List<EmpCertInOrder> empCertInOrder();

}
