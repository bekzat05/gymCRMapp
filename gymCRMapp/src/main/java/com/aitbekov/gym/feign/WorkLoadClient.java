package com.aitbekov.gym.feign;

import com.aitbekov.gym.dto.TrainerWorkloadRequest;
import com.aitbekov.gym.dto.TrainerWorkloadSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "work-load-service")
public interface WorkLoadClient {

    @GetMapping("/workload/{username}")
    ResponseEntity<TrainerWorkloadSummary> getWorkload(@PathVariable("username") String username,
                                                       @RequestParam int year,
                                                       @RequestParam int month);

    @PostMapping("/workload")
    ResponseEntity<Void> updateWorkload(@RequestBody TrainerWorkloadRequest request);

}
