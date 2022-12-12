package com.example.projectbluehair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ProjectBlueHairApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectBlueHairApplication.class, args);
    }
}
