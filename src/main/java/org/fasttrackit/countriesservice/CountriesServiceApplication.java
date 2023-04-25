package org.fasttrackit.countriesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CountriesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountriesServiceApplication.class, args);
    }

}
