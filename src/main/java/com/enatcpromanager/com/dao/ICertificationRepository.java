package com.enatcpromanager.com.dao;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.enatcpromanager.com.dto.EmpCertInOrder;
import com.enatcpromanager.com.entities.SnowCertification;
import org.springframework.stereotype.Repository;


public interface ICertificationRepository extends CrudRepository<SnowCertification, Long> {

  List<SnowCertification> findAll();

  @Query(nativeQuery = true, value = "SELECT snow_cert_id as certId, cert_name as certName FROM snow_certification ORDER BY cert_name asc")
  List<EmpCertInOrder> empCertInOrder();

}
