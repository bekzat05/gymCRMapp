package com.bekzat.gym.controller;

import com.bekzat.gym.dto.TrainingCreateDto;
import com.bekzat.gym.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainings")
@AllArgsConstructor
public class TrainingController {
    @Autowired
    private TrainingService trainingService;

    @PostMapping
    public ResponseEntity<Void> registerTrainee(@RequestBody TrainingCreateDto createDto) {
        trainingService.create(createDto);
        return ResponseEntity.ok().build();
    }
}
