package camt.se.pigit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@ComponentScan({"camt.se.pigit.dao","camt.se.pigit.controller", "camt.se.pigit.service", "camt.se.pigit.config"})
@SpringBootApplication
@EnableConfigurationProperties
public class PigitApplication {

    public static void main(String[] args) {
        System.out.println("Project start");
        SpringApplication.run(PigitApplication.class, args);
    }
}
