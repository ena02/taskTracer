package com.example.tasktracer.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "project")
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
public class Project implements Serializable {
    private Long id;
    private String name;
    private Date startDate;
    private Date completedDate;
    private Integer currentStatus;
    private Integer priority;


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
    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    @Basic
    @Column(name = "completed_date")
    public Date getCompletedDate() {
        return completedDate;
    }

    @Basic
    @Column(name = "current_status")
    public Integer getCurrentStatus() {
        return currentStatus;
    }

    @Basic
    @Column(name = "priority")
    public Integer getPriority() {
        return priority;
    }

}
