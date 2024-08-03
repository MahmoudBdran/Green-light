package com.erp.greenlight.greenlightWorker.service;


import com.erp.greenlight.greenlightWorker.models.Expenses;
import com.erp.greenlight.greenlightWorker.models.Project;
import com.erp.greenlight.greenlightWorker.respository.ExpensesRepository;
import com.erp.greenlight.greenlightWorker.respository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ExpensesRepository expensesRepository;
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> findProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
    }

    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }



    public List<Expenses> getProjectExpenses(Long projectId) {
        return expensesRepository.findByProjectId(projectId);
    }
}

