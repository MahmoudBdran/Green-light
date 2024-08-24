package com.erp.greenlight.greenlightWorker.controllers;

import com.erp.greenlight.greenlightWorker.dto.ProjectFinancialReportDTO;
import com.erp.greenlight.greenlightWorker.models.Expenses;
import com.erp.greenlight.greenlightWorker.models.Project;
import com.erp.greenlight.greenlightWorker.service.ProjectService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = {"http://localhost", "https://animated-sprinkles-d16b69.netlify.app"})
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping()
    public ResponseEntity<Object> createWorker(@RequestBody Project project) {
        return AppResponse.generateResponse("تم حفظ المشروع بنجاح", HttpStatus.OK,projectService.saveProject(project) , true);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllProjects(@RequestParam int pageIndex,
                                                 @RequestParam int pageSize) {
        return AppResponse.generateResponse("all_data", HttpStatus.OK,  projectService.findAllProjects(pageIndex, pageSize) , true);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProjectById(@PathVariable Long id) {
        return AppResponse.generateResponse("all_data", HttpStatus.OK, projectService.findProjectById(id) , true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProject(@PathVariable Long id) {
        projectService.deleteProjectById(id);
        return AppResponse.generateResponse("تم حذف العامل بنجاح", HttpStatus.OK, null , true);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProject(@PathVariable Long id, @RequestBody Project project) {
        project.setId(id);
        Project updatedProject = projectService.updateProject(project);
        return AppResponse.generateResponse("تم تحديث العامل بنجاح", HttpStatus.OK,   updatedProject, true);
    }
    @GetMapping("/{id}/expenses")
    public ResponseEntity<List<Expenses>> getProjectExpenses(@PathVariable Long id) {
        List<Expenses> expenses = projectService.getProjectExpenses(id);
        return ResponseEntity.ok(expenses);

    }
    @GetMapping("/{projectId}/financial-status")
    public ResponseEntity<Object> getProjectFinancialStatus(@PathVariable Long projectId) {
        return AppResponse.generateResponse("financial-status", HttpStatus.OK, projectService.getProjectFinancialReport(projectId) , true);
    }


}

