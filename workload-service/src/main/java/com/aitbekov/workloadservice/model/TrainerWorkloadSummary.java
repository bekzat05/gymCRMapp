package com.aitbekov.workloadservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "trainer_workload_summary")
public class TrainerWorkloadSummary {

    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    @Indexed
    private String firstName;
    @Indexed
    private String lastName;
    private Boolean isActive;
    private List<YearlyTrainingSummary> yearlySummaries;
}
