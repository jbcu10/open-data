package dev.jbcu10.opendata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableWebFlux
public class OpenDataApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(OpenDataApplication.class, args);
    }
}
