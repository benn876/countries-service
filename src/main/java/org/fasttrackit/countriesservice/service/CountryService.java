package org.fasttrackit.countriesservice.service;

import org.fasttrackit.countriesservice.exceptions.ResourceNotFoundException;
import org.fasttrackit.countriesservice.model.Country;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CountryService {
    private final CountryFileReader countryFileReader;
    private List<Country> countries;

    public CountryService(CountryFileReader countryFileReader) {
        this.countryFileReader = countryFileReader;
        countries = countryFileReader.populateWithDataFromFile();
    }

    public List<Country> getAllCountries() {
        return countries;
    }

    public List<Country> getCountriesInContinentWithPopulationBiggerThan(String continent, Integer population) {
        if (continent == null || population == null) {
            throw new RuntimeException();
        }
        return countries.stream()
                .filter(country -> continent.equals(country.continent()))
                .filter(country -> country.population() > population)
                .toList();
    }

    public List<Country> getCountriesByNeighbours(String includedNeighbour, String excludedNeighbour) {
        return countries.stream()
                .filter(country -> country.neighbours().size() > 1)
                .filter(country -> country.neighbours().contains(includedNeighbour) && !country.neighbours().contains(excludedNeighbour))
                .toList();
    }

    public List<Country> getCountriesByContinent(String continent) {
        if (continent == null) {
            throw new RuntimeException();
        }
        return countries.stream()
                .filter(country -> continent.equals(country.continent()))
                .toList();
    }

    public Country getById(String id) {
        return countries.stream()
                .filter(country -> id.equals(country.id()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Country with id %s was not found".formatted(id)));
    }

    public Country deleteById(String id) {
        Country countryToBeDeleted = getById(id);
        countries.remove(countryToBeDeleted);
        return countryToBeDeleted;
    }

    public Country createNewCountry(Country newCountry) {
        String newID = UUID.randomUUID().toString();
        countries.add(newCountry.withId(newID));
        return getById(newID);
    }

    public Country replaceCountry(String id, Country replaceCountry) {
        Country foundCountry = getById(id);
        deleteById(id);
        Country updatedCountry = foundCountry.toBuilder()
                .id(foundCountry.id())
                .name(replaceCountry.name())
                .capital(replaceCountry.capital())
                .area(replaceCountry.area())
                .population(replaceCountry.population())
                .neighbours(replaceCountry.neighbours())
                .build();
        countries.add(updatedCountry);
        return updatedCountry;
    }
}
