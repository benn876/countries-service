package org.fasttrackit.countriesservice.service;

import org.fasttrackit.countriesservice.exceptions.ResourceNotFoundException;
import org.fasttrackit.countriesservice.model.Country;
import org.fasttrackit.countriesservice.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    private final CountryRepository repository;

    public CountryService(CountryFileReader countryFileReader, CountryRepository repository) {
        this.repository = repository;
        repository.saveAll(countryFileReader.populateWithDataFromFile());
    }

    public List<Country> getAllCountries() {
        return repository.findAll();
    }

    public List<Country> getCountriesInContinentWithPopulationBiggerThan(String continent, Integer population) {
        if (continent == null || population == null) {
            throw new RuntimeException();
        }
        return repository.findAll().stream()
                .filter(country -> continent.equals(country.getContinent()))
                .filter(country -> country.getPopulation() > population)
                .toList();
    }

    public List<Country> getCountriesByContinent(String continent) {
        return repository.findByContinentQuery(continent);
    }

    public List<Country> getCountriesByNeighbours(String includedNeighbour, String excludedNeighbour) {
        return repository.findAll().stream()
                .filter(country -> country.getNeighbours().size() > 1)
                .filter(country -> country.getNeighbours().contains(includedNeighbour) && !country.getNeighbours().contains(excludedNeighbour))
                .toList();
    }

    public Country getById(String id) {
        return repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Country with id %s was not found".formatted(id)));
    }

    public Country deleteById(String id) {
        Country countryToBeDeleted = getById(id);
        repository.deleteById(Long.valueOf(id));
        return countryToBeDeleted;
    }

    public Country createNewCountry(Country newCountry) {
        return repository.save(newCountry);
    }

    public Country replaceCountry(String id, Country replaceCountry) {
        Country foundCountry = getById(id);
        deleteById(id);
        Country updatedCountry = foundCountry.toBuilder()
                .id(foundCountry.getId())
                .name(replaceCountry.getName())
                .capital(replaceCountry.getCapital())
                .area(replaceCountry.getArea())
                .population(replaceCountry.getPopulation())
                .neighbours(replaceCountry.getNeighbours())
                .build();
        repository.save(updatedCountry);
        return updatedCountry;
    }
}
