package com.example.tasktracer.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task implements Serializable {

    private Long id;
    private String name;
    private String description;
    private Integer status;
    private Integer priority;
    private Project project;



    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    @Basic
    @Column(name = "priority")
    public Integer getPriority() {
        return priority;
    }


    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    public Project getProject() {
        return project;
    }
}
