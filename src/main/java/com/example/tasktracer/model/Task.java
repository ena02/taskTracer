package com.example.tasktracer.model;

import com.example.tasktracer.service.TaskService;
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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Basic
    @Column(name = "priority")
    private Integer priority;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @Getter(AccessLevel.NONE)
    private Project project;


}
