package dev.jbcu10.opendata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class OpenDataApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(OpenDataApplication.class, args);
    }
}
