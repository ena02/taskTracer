package com.example.tasktracer.controller;

import com.example.tasktracer.model.Project;
import com.example.tasktracer.model.Task;
import com.example.tasktracer.service.ProjectService;
import com.example.tasktracer.service.TaskService;
import com.example.tasktracer.sort.ProjectSortValues;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("Project controller")
public class ProjectController {

    // access to BD data
    private ProjectService projectService;
    private TaskService taskService;


    // automatic insertion of an instance of a class through the constructor
    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }


    @GetMapping("/all")
    @ApiOperation("Getting a project list")
    public List<Project> findAll() {
        return projectService.findAll();
    }


    @PostMapping("/add")
    @ApiOperation("Add a new project")
    public ResponseEntity<Project> add(@RequestBody Project project) {

        // check for mandatory parameters
        if (project.getId() != null && project.getId() != 0) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        System.out.println(project);

        return ResponseEntity.ok(projectService.create(project));
    }

    @PutMapping("/update")
    @ApiOperation("Upgrade an existing project")
    public ResponseEntity update(@RequestBody Project project) {

        // check for mandatory parameters
        if (project.getId() == null && project.getId() == 0) {
            return new ResponseEntity("id can not be null", HttpStatus.NOT_ACCEPTABLE);
        }


        projectService.update(project);

        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/findById/{id}")
    @ApiOperation("Get project by id")
    public ResponseEntity<Project> findById(@PathVariable Long id) {


        Project project  = null;

        try {
            project = projectService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity(String.format("id=%d not found", id), HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("Delete project by id")
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
    @ApiOperation("Get tasks by project id")
    public List<Task> findAllTasks(@PathVariable Long id) {
        return projectService.findAllTasksByProjectId(id);
    }


    @PostMapping("/addTask/{id}")
    @ApiOperation("Add task to project")
    public ResponseEntity<List<Task>> addTask(@PathVariable Long id, @RequestBody Task task) {

        if (task.getId() != null && task.getId() != 0) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        task.setProject(projectService.findById(id));
        taskService.create(task);

        return ResponseEntity.ok(taskService.findAll());
    }


    @DeleteMapping("/deleteTask/{taskId}")
    @ApiOperation("Delete task by task id in project")
    public ResponseEntity<String> deleteTaskById(@PathVariable Long taskId) {

        try {
            taskService.deleteById(taskId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity(String.format("Category with id = %d not found", taskId), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    // sorting by any parameters
    // ProjectSortValues contains all possible search options
    @PostMapping("/sort")
    @ApiOperation("Get sorted project list")
    public ResponseEntity<List<Project>> sort(@RequestBody ProjectSortValues projectSortValues) {

        Integer pageNumber = projectSortValues.getPageNumber() != null ? projectSortValues.getPageNumber() : null;
        Integer pageSize = projectSortValues.getPageSize() != null ? projectSortValues.getPageSize() : null;

        String sortColumn = projectSortValues.getSortColumn() != null ? projectSortValues.getSortColumn() : null;
        String sortDirection = projectSortValues.getSortDirection() != null ? projectSortValues.getSortDirection() : null;

        Sort.Direction direction = sortDirection == null || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;


        // substitute all values

        // sort object
        Sort sort = Sort.by(direction, sortColumn);

        // pagination object
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        // the result of the query with the page-by-page output
        Page result = projectService.sortedSearch(pageRequest);

        // query result
        return ResponseEntity.ok(result.getContent());
    }
}
