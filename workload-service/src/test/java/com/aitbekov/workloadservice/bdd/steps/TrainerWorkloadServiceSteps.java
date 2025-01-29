package com.aitbekov.workloadservice.bdd.steps;

import com.aitbekov.workloadservice.dto.TrainerWorkloadRequest;
import com.aitbekov.workloadservice.exceptions.TrainerWorkloadNotFoundException;
import com.aitbekov.workloadservice.model.TrainerWorkloadSummary;
import com.aitbekov.workloadservice.model.YearlyTrainingSummary;
import com.aitbekov.workloadservice.repository.TrainerWorkloadRepository;
import com.aitbekov.workloadservice.service.TrainerWorkloadServiceMap;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@CucumberContextConfiguration
public class TrainerWorkloadServiceSteps {
    @Mock
    private TrainerWorkloadRepository repository;
    @InjectMocks
    private TrainerWorkloadServiceMap service;

    private TrainerWorkloadRequest request;
    private TrainerWorkloadSummary summary;
    private YearlyTrainingSummary yearlySummary;

    public TrainerWorkloadServiceSteps() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("a trainer workload request with username {string} and training duration {int}")
    public void aTrainerWorkloadRequest(String username, int duration) {
        request = new TrainerWorkloadRequest();
        request.setTransactionId("txn123");
        request.setUsername(username);
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setIsActive(true);
        request.setTrainingDate(LocalDate.of(2023, 1, 15));
        request.setTrainingDuration(duration);
        request.setActionType(TrainerWorkloadRequest.ActionType.ADD);

        summary = new TrainerWorkloadSummary();
        summary.setUsername(request.getUsername());
        summary.setFirstName(request.getFirstName());
        summary.setLastName(request.getLastName());
        summary.setIsActive(request.getIsActive());
        summary.setYearlySummaries(new ArrayList<>());

        yearlySummary = new YearlyTrainingSummary();
        yearlySummary.setTrainingYear(2023);
        yearlySummary.setMonthlySummary(new HashMap<>());
        yearlySummary.getMonthlySummary().put(1, duration);
        summary.getYearlySummaries().add(yearlySummary);

        Mockito.when(repository.findByUsername(username)).thenReturn(Optional.of(summary));
    }

    @Given("a non-existent trainer with username {string}")
    public void aNonExistentTrainer(String username) {
        Mockito.when(repository.findByUsername(username)).thenReturn(Optional.empty());
    }
    @Given("a workload summary for trainer {string} for year {int}")
    public void aWorkloadSummary(String username, int year) {
        summary = new TrainerWorkloadSummary();
        summary.setUsername(username);
        summary.setFirstName("John");
        summary.setLastName("Doe");
        summary.setIsActive(true);

        yearlySummary = new YearlyTrainingSummary();
        yearlySummary.setTrainingYear(year);
        yearlySummary.setMonthlySummary(new HashMap<>());
        yearlySummary.getMonthlySummary().put(1, 120);
        summary.setYearlySummaries(Collections.singletonList(yearlySummary));

        Mockito.when(repository.findByUsername(username)).thenReturn(Optional.of(summary));
    }

    @When("I retrieve the yearly workload for trainer {string} for year {int}")
    public void retrieveYearlyWorkload(String username, int year) {
        Mockito.when(repository.findByUsername(username)).thenReturn(Optional.of(summary));
        service.getWorkload(username, year, 0);
    }

    @When("I retrieve the workload for trainer {string} for year {int}")
    public void retrieveWorkloadForTrainer(String username, int year) {
        Mockito.when(repository.findByUsername(username)).thenReturn(Optional.empty());
        service.getWorkload(username, year, 0);
    }

    @When("the workload is updated")
    public void updateWorkload() {
        Mockito.when(repository.findByUsername(request.getUsername())).thenReturn(Optional.of(summary));
        service.updateWorkload(request);
        verify(repository).save(summary);
    }

    @Then("the workload should be saved")
    public void verifyWorkloadSaved() {
        verify(repository).save(summary);
    }

    @Then("an error should be thrown for missing trainer")
    public void verifyTrainerNotFoundError() {
        assertThrows(TrainerWorkloadNotFoundException.class, () -> service.getWorkload(request.getUsername(), 2023, 0));
    }

    @Then("the yearly workload should be returned")
    public void verifyYearlyWorkload() {
        verify(repository).findByUsername(summary.getUsername());
    }
}