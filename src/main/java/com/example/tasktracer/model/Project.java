package com.example.tasktracer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "project")
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
public class Project implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "start_date")
    private Date startDate;

    @Basic
    @Column(name = "completed_date")
    private Date completedDate;

    @Basic
    @Column(name = "current_status")
    @Enumerated(EnumType.STRING)
    private ProjectStatus currentStatus;

    @Basic
    @Column(name = "priority")
    private Integer priority;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private List<Task> tasks;


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", completedDate=" + completedDate +
                ", currentStatus=" + currentStatus +
                ", priority=" + priority +
                '}';
    }
}
