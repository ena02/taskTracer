package com.example.tasktracer.controller;

import com.example.tasktracer.model.Project;
import com.example.tasktracer.model.Task;
import com.example.tasktracer.service.ProjectService;
import com.example.tasktracer.service.TaskService;
import com.example.tasktracer.sort.ProjectSortValues;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private ProjectService projectService;
    private TaskService taskService;

    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
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

        System.out.println(project);

        return ResponseEntity.ok(projectService.create(project));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Project project) {


        if (project.getId() == null && project.getId() == 0) {
            return new ResponseEntity("id can not be null", HttpStatus.NOT_ACCEPTABLE);
        }


        projectService.update(project);

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

    @GetMapping("/allTasks/{id}")
    public List<Task> findAllTasks(@PathVariable Long id) {
        return projectService.findAllTasksByProjectId(id);
    }


    @PostMapping("/addTask/{id}")
    public ResponseEntity<List<Task>> addTask(@PathVariable Long id, @RequestBody Task task) {

        if (task.getId() != null && task.getId() != 0) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        task.setProject(projectService.findById(id));
        taskService.create(task);

        return ResponseEntity.ok(taskService.findAll());
    }


    @DeleteMapping("/deleteTask/{taskId}")
    public ResponseEntity<String> deleteTaskById(@PathVariable Long taskId) {

        try {
            taskService.deleteById(taskId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity(String.format("Category with id = %d not found", taskId), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/sort")
    public ResponseEntity<List<Project>> sort(@RequestBody ProjectSortValues projectSortValues) {

        Integer pageNumber = projectSortValues.getPageNumber() != null ? projectSortValues.getPageNumber() : null;
        Integer pageSize = projectSortValues.getPageSize() != null ? projectSortValues.getPageSize() : null;

        String sortColumn = projectSortValues.getSortColumn() != null ? projectSortValues.getSortColumn() : null;
        String sortDirection = projectSortValues.getSortDirection() != null ? projectSortValues.getSortDirection() : null;

        Sort.Direction direction = sortDirection == null || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort sort = Sort.by(direction, sortColumn);

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page result = projectService.sortedSearch(pageRequest);

        return ResponseEntity.ok(result.getContent());
    }
}
