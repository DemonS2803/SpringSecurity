package ru.spmi.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringSecurityApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", 9000));
        app.run();
    }

}
