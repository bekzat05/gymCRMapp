package com.bekzat.gym.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "training_types")
@Data
public class TrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private Type name;

    public enum Type {
        CARDIO,
        STRENGTH_TRAINING
    }

}
