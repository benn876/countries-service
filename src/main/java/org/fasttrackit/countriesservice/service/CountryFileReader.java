package org.fasttrackit.countriesservice.service;

import org.fasttrackit.countriesservice.model.Country;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.lang.Integer.valueOf;
import static java.util.stream.Collectors.toList;

@Repository
public class CountryFileReader implements FileReader {
    @Value("${file.countries}")
    private String fileCountriesPath;

    @Override
    public List<Country> populateWithDataFromFile() {
        try {
            return Files.lines(Path.of(fileCountriesPath))
                    .map(this::lineToCountry)
                    .collect(toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Country lineToCountry(String line) {
        String[] countryParts = line.split("\\|");
        return Country.builder()
                .name(countryParts[0])
                .capital(countryParts[1])
                .population(valueOf(countryParts[2]))
                .area(valueOf(countryParts[3]))
                .continent(countryParts[4])
                .neighbours(countryParts.length > 5 ? parseNeighbours(countryParts[5]) : List.of())
                .build();
    }

    private List<String> parseNeighbours(String neighboursString) {
        return Arrays.stream(neighboursString.split("~")).toList();
    }
}
