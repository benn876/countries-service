package org.fasttrackit.countriesservice.service;

import org.fasttrackit.countriesservice.exceptions.ResourceNotFoundException;
import org.fasttrackit.countriesservice.model.City;
import org.fasttrackit.countriesservice.model.Country;
import org.fasttrackit.countriesservice.model.filter.CountryFilter;
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

    public List<Country> getAllCountries(CountryFilter filter) {
        if (filter == null) {
            return repository.findAll();
        }
        return repository.findFilterAll(filter.continent(), filter.population());
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
                .capital(City.builder()
                        .name(replaceCountry.getCapital().getName())
                        .id(foundCountry.getCapital().getId())
                        .build())
                .area(replaceCountry.getArea())
                .population(replaceCountry.getPopulation())
                .neighbours(replaceCountry.getNeighbours())
                .build();
      return repository.save(updatedCountry);
    }

    public Country addCityToCountry(String id, City city) {
        Country country = getById(id);
        country.getCities().stream().count();
        country.getCities().add(city);
        return repository.save(country);
    }

    public Country addNeighbourToCountry(String id, String neighbourId) {
        Country country = getById(id);
        Country neighbour = getById(neighbourId);
        country.getNeighboursCountries().add(neighbour);
        neighbour.getNeighboursCountries().add(country);
        return repository.save(country);
    }
}
