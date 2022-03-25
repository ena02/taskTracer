package com.example.tasktracer.repo;

import com.example.tasktracer.model.Project;
import com.example.tasktracer.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT t FROM Task t WHERE" +
            " t.project.id = :id")
    List<Task> findAllTasksByProjectId(@Param("id") Long id);
}
