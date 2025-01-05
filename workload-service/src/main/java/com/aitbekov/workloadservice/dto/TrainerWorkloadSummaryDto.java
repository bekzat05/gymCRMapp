package com.aitbekov.workloadservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class TrainerWorkloadSummaryDto {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private List<YearlyTrainingSummaryDto> yearlySummaries;
}
