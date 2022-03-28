package com.example.tasktracer.model;

import java.io.Serializable;


public enum ProjectStatus implements Serializable {
    NotStarted("NotStarted"),
    Active("Active"),
    Completed("Completed");

    private String projectStatus;

    ProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectStatus() {
        return projectStatus;
    }
}
