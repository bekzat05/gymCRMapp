package com.aitbekov.gym.integration;

import com.aitbekov.gym.integration.annotation.IT;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;

@IT
@Sql({
        "classpath:sql/schema-test.sql",
        "classpath:sql/data-test.sql"

})
public abstract class IntegrationTestBase {

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:5432/gym");
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "Algabas77");
    }

}