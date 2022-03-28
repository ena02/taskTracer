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
public class TaskSortValues {
    
    private Integer pageNumber;
    private Integer pageSize;

    private String sortColumn;
    private String sortDirection;
}
