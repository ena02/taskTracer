package com.example.tasktracer.sort;


import com.example.tasktracer.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskSort {
    private String name;
    private TaskStatus status;
    private String description;
    private Integer priority;
}
