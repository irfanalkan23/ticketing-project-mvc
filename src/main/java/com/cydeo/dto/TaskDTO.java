package com.cydeo.dto;

import com.cydeo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
//@AllArgsConstructor  // PostgreSQL will generate primary key, so I don't need this
@Data
public class TaskDTO {

    private Long id;    //not in the form or the table, but I need a unique specifier
    @NotNull
    private ProjectDTO project;
    @NotNull
    private UserDTO assignedEmployee;
    @NotBlank
    private String taskSubject;
    @NotBlank
    private String taskDetail;

    private Status taskStatus;
    private LocalDate assignedDate;

    //this is how we create constructor when working with database
    public TaskDTO(ProjectDTO project, UserDTO assignedEmployee, String taskSubject, String taskDetail, Status taskStatus, LocalDate assignedDate) {
        this.project = project;
        this.assignedEmployee = assignedEmployee;
        this.taskSubject = taskSubject;
        this.taskDetail = taskDetail;
        this.taskStatus = taskStatus;
        this.assignedDate = assignedDate;
        this.id = UUID.randomUUID().getMostSignificantBits();
    }

}
