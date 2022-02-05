package com.enatcpromanager.com.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enatcpromanager.com.dao.ICertificationRepository;
import com.enatcpromanager.com.dto.EmpCertInOrder;
import com.enatcpromanager.com.entities.EpicCertification;

@Service
public class CertificationService {

    @Autowired
    ICertificationRepository certRepo;

    public List<EpicCertification> getAll(){
        return certRepo.findAll();
    }

    public EpicCertification save(EpicCertification certification) {
        return certRepo.save(certification);
    }

    public List<EmpCertInOrder> empCertInOrder(){
    	return certRepo.empCertInOrder();
    }

}
