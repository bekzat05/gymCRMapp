package com.bekzat.gym;

import com.bekzat.gym.config.AppConfig;
import com.bekzat.gym.model.Trainee;
import com.bekzat.gym.model.Trainer;
import com.bekzat.gym.model.Training;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        String[] beans = ctx.getBeanDefinitionNames();
        for (String bean : beans) {
            log.info("Bean Name: " + bean);
        }

    }
}
