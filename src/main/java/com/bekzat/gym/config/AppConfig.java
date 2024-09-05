package com.bekzat.gym.config;

import com.bekzat.gym.model.Trainee;
import com.bekzat.gym.model.Trainer;
import com.bekzat.gym.model.Training;
import com.bekzat.gym.storage.Storage;
import com.bekzat.gym.storage.StorageImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("com.bekzat.gym")
public class AppConfig {
    @Bean
    public Storage<Trainee> traineeStorage() {
        return new StorageImpl<>(Trainee.class);
    }

    @Bean
    public Storage<Trainer> trainerStorage() {
        return new StorageImpl<>(Trainer.class);
    }

    @Bean
    public Storage<Training> trainingStorage() {
        return new StorageImpl<>(Training.class);
    }
}
