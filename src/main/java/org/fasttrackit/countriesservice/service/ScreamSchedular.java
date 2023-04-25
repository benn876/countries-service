package org.fasttrackit.countriesservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ScreamSchedular {

    //    @Scheduled(cron = "7 * * * * *")
    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void scream() {
        log.info("AAAAAAAAAAAAAAAAAA");
    }
}
