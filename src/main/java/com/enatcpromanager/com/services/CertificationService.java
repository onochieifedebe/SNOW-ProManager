package com.enatcpromanager.com.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enatcpromanager.com.dao.ICertificationRepository;
import com.enatcpromanager.com.dto.EmpCertInOrder;
import com.enatcpromanager.com.entities.SnowCertification;

@Service
public class CertificationService {
    @Autowired
    ICertificationRepository certRepo;

    public List<SnowCertification> getAll(){
        return certRepo.findAll();
    }

    public SnowCertification save(SnowCertification certification) {
        return certRepo.save(certification);
    }

    public List<EmpCertInOrder> empCertInOrder(){
    	return certRepo.empCertInOrder();
    }

}
