package com.example.tasktracer.service;

import com.example.tasktracer.model.Project;
import com.example.tasktracer.model.Task;
import com.example.tasktracer.repo.ProjectRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project create(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findById(Long id) {
        return projectRepository.findById(id).get();
    }

    public Project update(Project project) {
        return projectRepository.save(project);
    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    public List<Task> findAllTasksByProjectId(Long id) {
        return projectRepository.findAllTasksByProjectId(id);
    }
}
