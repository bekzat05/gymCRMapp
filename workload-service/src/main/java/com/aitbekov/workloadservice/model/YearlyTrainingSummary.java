package com.aitbekov.workloadservice.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashMap;
import java.util.Map;

@Data
public class YearlyTrainingSummary {
    @Field
    private Integer trainingYear;

    @Field
    private Map<Integer, Integer> monthlySummary = new HashMap<>();
}
