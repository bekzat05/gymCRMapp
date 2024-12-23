package com.aitbekov.workloadservice.controller;

import com.aitbekov.workloadservice.dto.TrainerWorkloadRequest;
import com.aitbekov.workloadservice.dto.TrainerWorkloadSummaryDto;
import com.aitbekov.workloadservice.mapstruct.TrainerWorkloadSummaryMapper;
import com.aitbekov.workloadservice.model.TrainerWorkloadSummary;
import com.aitbekov.workloadservice.service.TrainerWorkloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workload")
@Slf4j
@Validated
public class TrainerWorkloadController {

    @Autowired
    private TrainerWorkloadService workloadService;

    @PostMapping
    public ResponseEntity<Void> updateWorkload(@RequestBody TrainerWorkloadRequest request) {
        log.info("TransactionId: {} - Received workload update request for trainer: {}", request.getTransactionId(), request.getUsername());
        workloadService.updateWorkload(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<TrainerWorkloadSummaryDto> getWorkload(@PathVariable String username,
                                                              @RequestParam int year,
                                                              @RequestParam int month) {
        TrainerWorkloadSummary summary = workloadService.getWorkload(username, year, month);
        log.info("WorkLoad Summary  - {}", summary);
        TrainerWorkloadSummaryDto summaryDto = TrainerWorkloadSummaryMapper.INSTANCE.toDTO(summary);
        log.info("Summary  - {}", summaryDto);

        return ResponseEntity.ok(summaryDto);
    }
}
