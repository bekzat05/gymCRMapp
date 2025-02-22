package com.aitbekov.workloadservice.integration.service;

import com.aitbekov.workloadservice.dto.TrainerWorkloadRequest;
import com.aitbekov.workloadservice.integration.IntegrationTestBase;
import com.aitbekov.workloadservice.integration.annotation.IT;
import com.aitbekov.workloadservice.model.TrainerWorkloadSummary;
import com.aitbekov.workloadservice.repository.TrainerWorkloadRepository;
import com.aitbekov.workloadservice.service.TrainerWorkloadServiceMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IT
public class TrainerWorkloadServiceIntegrationTest extends IntegrationTestBase {

    @Autowired
    private TrainerWorkloadServiceMap trainerWorkloadService;

    @Autowired
    private TrainerWorkloadRepository trainerWorkloadRepository;

    private TrainerWorkloadRequest workloadRequest;

    @BeforeEach
    public void setup() {
        workloadRequest = new TrainerWorkloadRequest(
                "transaction123",
                "john.doe",
                "John",
                "Doe",
                true,
                LocalDate.now(),
                60,
                TrainerWorkloadRequest.ActionType.ADD
        );
    }

    @Test
    public void testUpdateWorkload_trainerSavedInDatabase() {
        trainerWorkloadService.updateWorkload(workloadRequest);

        TrainerWorkloadSummary workload = trainerWorkloadService.getWorkload("john.doe", LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        assertNotNull(workload);
        assertEquals("John", workload.getFirstName());
        assertEquals("Doe", workload.getLastName());
    }

    @Test
    public void testGetWorkload_success() {
        trainerWorkloadService.updateWorkload(workloadRequest);
        TrainerWorkloadSummary workloadSummary = trainerWorkloadService.getWorkload("john.doe", LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        assertNotNull(workloadSummary);
        assertEquals("john.doe", workloadSummary.getUsername());
    }
}
