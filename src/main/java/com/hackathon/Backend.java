package com.hackathon;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.jpa.repository.config.*;

@SpringBootApplication
@EnableJpaAuditing(modifyOnCreate = false)
@EnableJpaRepositories(considerNestedRepositories = true)
public class Backend {

    public static void main(String[] args) {
        SpringApplication.run(Backend.class, args);
    }

}
