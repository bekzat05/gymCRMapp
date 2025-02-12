package com.aitbekov.gym.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class TrainerWorkloadSummary {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private List<YearlyTrainingSummary> yearlySummaries;
}
