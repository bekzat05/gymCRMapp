package com.aitbekov.gym.controller;

import com.aitbekov.gym.service.TrainingTypeService;
import com.aitbekov.gym.model.TrainingType;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/training_types")
@AllArgsConstructor
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;

    @GetMapping
    public ResponseEntity<List<TrainingType>> getTrainerProfile() {
        List<TrainingType> types = trainingTypeService.findAll();
        return ResponseEntity.ok(types);
    }
}
