package com.example.tasktracer.controller;

import com.example.tasktracer.model.Project;
import com.example.tasktracer.model.Task;
import com.example.tasktracer.service.ProjectService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/all")
    public List<Project> findAll() {
        return projectService.findAll();
    }


    @PostMapping("/create")
    public ResponseEntity<Project> create(@RequestBody Project project) {

        if (project.getId() != null && project.getId() != 0) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(projectService.create(project));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Project category) {


        if (category.getId() == null && category.getId() == 0) {
            return new ResponseEntity("id can not be null", HttpStatus.NOT_ACCEPTABLE);
        }


        projectService.update(category);

        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<Project> findById(@PathVariable Long id) {


        Project category = null;

        try {
            category = projectService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity(String.format("id=%d not found", id), HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {


        try {
            projectService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity(String.format("Category with id = %d not found", id), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}")
    public List<Task> findAllTask(@PathVariable Long id) {
        return projectService.findAllTasksByProjectId(id);
    }
}
