package com.example.tasktracer.controller;

import com.example.tasktracer.model.Project;
import com.example.tasktracer.model.Task;
import com.example.tasktracer.model.Task;
import com.example.tasktracer.service.TaskService;
import com.example.tasktracer.sort.ProjectSortValues;
import com.example.tasktracer.sort.TaskSortValues;
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
@RequestMapping("/task")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public List<Task> findAll() {
        return taskService.findAll();
    }


    @PostMapping("/create")
    public ResponseEntity<Task> create(@RequestBody Task task) {

        if (task.getId() != null && task.getId() != 0) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(taskService.create(task));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Task task) {


        if (task.getId() == null && task.getId() == 0) {
            return new ResponseEntity("id can not be null", HttpStatus.NOT_ACCEPTABLE);
        }


        taskService.update(task);

        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {


        Task task = null;

        try {
            task = taskService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity(String.format("id=%d not found", id), HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {


        try {
            taskService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity(String.format("task with id = %d not found", id), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping("/sort")
    public ResponseEntity<List<Task>> sort(@RequestBody TaskSortValues taskSortValues) {

        Integer pageNumber = taskSortValues.getPageNumber() != null ? taskSortValues.getPageNumber() : null;
        Integer pageSize = taskSortValues.getPageSize() != null ? taskSortValues.getPageSize() : null;

        String sortColumn = taskSortValues.getSortColumn() != null ? taskSortValues.getSortColumn() : null;
        String sortDirection = taskSortValues.getSortDirection() != null ? taskSortValues.getSortDirection() : null;

        Sort.Direction direction = sortDirection == null || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort sort = Sort.by(direction, sortColumn);

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page result = taskService.sortedSearch(pageRequest);

        return ResponseEntity.ok(result.getContent());
    }
    
}
