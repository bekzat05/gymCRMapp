package com.bekzat.gym;

import com.bekzat.gym.config.AppConfig;
import com.bekzat.gym.model.Trainee;
import com.bekzat.gym.model.Trainer;
import com.bekzat.gym.model.Training;
import com.bekzat.gym.storage.Storage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        String[] beans = context.getBeanDefinitionNames();
        for (String bean : beans) {
            System.out.println("Bean Name: "+ bean);
        }
        context.getApplicationName();

        Storage<Trainee> traineeStorage = context.getBean("traineeStorage", Storage.class);
        Storage<Trainer> trainerStorage = context.getBean("trainerStorage", Storage.class);
        Storage<Training> trainingStorage = context.getBean("trainingStorage", Storage.class);

        System.out.println("Trainees:");
        for (Trainee trainee : traineeStorage.findAll()) {
            System.out.println(trainee);
        }

        System.out.println("\nTrainers:");
        for (Trainer trainer : trainerStorage.findAll()) {
            System.out.println(trainer);
        }

        System.out.println("\nTrainings:");
        for (Training training : trainingStorage.findAll()) {
            System.out.println(training);
        }
    }
}
