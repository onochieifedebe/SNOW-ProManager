package com.enatcpromanager.com.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enatcpromanager.com.dao.IProjectRepository;
import com.enatcpromanager.com.dto.ProjectCountData;
import com.enatcpromanager.com.dto.StatusChartData;
import com.enatcpromanager.com.dto.TimeChartData;
import com.enatcpromanager.com.entities.Project;

@Service
public class ProjectService {

    @Autowired
    IProjectRepository proRepo;

    public Project save(Project project){
        return proRepo.save(project);
    }

    public List<Project> getAll(){
        return proRepo.findAll();
    }

    public List<StatusChartData> getProjectStatus(){
        return proRepo.getProjectStatus();
    }

    public Project findByProjectId(long id) {
        return proRepo.findByProjectId(id);
    }

    public void delete(Project project) {
        proRepo.delete(project);
    }

    public List<TimeChartData> getTimeData(){
        return proRepo.getTimeData();
    }

    public List<ProjectCountData> getProjectCount(){
    	return proRepo.getProjectCount();
    }

}
