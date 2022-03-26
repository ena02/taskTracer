package com.example.tasktracer.model;

import org.hibernate.annotations.Type;

import java.io.Serializable;


public enum ProjectStatus implements Serializable {
    NotStarted,
    Active,
    Completed;
}
