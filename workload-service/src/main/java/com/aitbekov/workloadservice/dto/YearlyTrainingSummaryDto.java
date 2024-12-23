package com.aitbekov.workloadservice.dto;

import lombok.Data;

import java.util.Map;

@Data
public class YearlyTrainingSummaryDto {
    private Integer trainingYear;
    private Map<Integer, Integer> monthlySummary;
}
