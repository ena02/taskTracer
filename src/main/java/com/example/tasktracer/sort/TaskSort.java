package com.example.tasktracer.sort;


import com.example.tasktracer.model.Status;
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
    private Status status;
    private String description;
    private Integer priority;
}
