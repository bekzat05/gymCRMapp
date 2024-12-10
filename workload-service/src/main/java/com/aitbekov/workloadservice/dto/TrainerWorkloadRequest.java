package com.aitbekov.workloadservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerWorkloadRequest {
    private String username;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private LocalDate trainingDate;
    private int trainingDuration;

    private ActionType actionType;

    public enum ActionType {
        ADD,
        DELETE
    }
}
