package camt.se.pigit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PigitApplication {

    public static void main(String[] args) {
        System.out.println("Project start");
        SpringApplication.run(PigitApplication.class, args);
    }
}
