package com.aitbekov.gym.controller;

import com.aitbekov.gym.dto.*;
import com.aitbekov.gym.service.AuthenticationService;
import com.aitbekov.gym.service.TraineeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/trainees")
@AllArgsConstructor
public class TraineeController {

    private final TraineeService traineeService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerTrainee(@RequestBody TraineeRegistrationDto registrationDto) {
        return ResponseEntity.ok(authenticationService.registerTrainee(registrationDto));
    }

    @GetMapping()
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/{username}")
    public ResponseEntity<TraineeProfileReadDto> getTraineeProfile(@PathVariable String username) {
        TraineeProfileReadDto profile = traineeService.findTraineeByUsername(username);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<TraineeProfileReadDto> updateTraineeProfile(@PathVariable String username, @RequestBody TraineeCreateAndUpdateDto dto) {
        TraineeProfileReadDto updatedProfile = traineeService.update(username, dto);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteTraineeProfile(@PathVariable String username) {
        traineeService.delete(username);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{username}")
    public ResponseEntity<Void> changeTraineeActiveStatus(@PathVariable String username, @RequestBody StatusUpdateDto statusDto) {
        traineeService.changeActiveStatus(username, statusDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{username}/trainers")
    public ResponseEntity<TraineeProfileReadDto> updateTraineeTrainersList(@PathVariable String username, @RequestParam List<String> trainers) {
        TraineeProfileReadDto traineeProfileReadDto = traineeService.updateTraineeTrainers(username, trainers);
        return new ResponseEntity<>(traineeProfileReadDto, HttpStatus.OK);
    }

    @GetMapping("/{username}/trainings")
    public ResponseEntity<Set<TrainingReadDto>> getTraineeTrainings(@PathVariable String username) {
        Set<TrainingReadDto> trainings = traineeService.getTraineeTrainings(username);
        return ResponseEntity.ok(trainings);
    }
}
