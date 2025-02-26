package com.aitbekov.gym.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "trainings")
public class Training implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "duration")
    private int duration;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    @ToString.Exclude
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    @ToString.Exclude
    private Trainer trainer;

    @Enumerated(EnumType.STRING)
    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;
}
