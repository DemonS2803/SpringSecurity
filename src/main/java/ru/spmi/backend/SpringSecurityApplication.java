package ru.spmi.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.spmi.backend.security.RsaProperties;

import java.util.Collections;

@SpringBootApplication
@EnableConfigurationProperties(RsaProperties.class)
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringSecurityApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", 9000));
        app.run();
    }

}
