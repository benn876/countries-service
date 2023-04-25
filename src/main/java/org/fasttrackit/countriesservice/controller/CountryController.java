package org.fasttrackit.countriesservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fasttrackit.countriesservice.model.City;
import org.fasttrackit.countriesservice.model.dto.CountryDTO;
import org.fasttrackit.countriesservice.model.dto.CountryMapper;
import org.fasttrackit.countriesservice.model.filter.CountryFilter;
import org.fasttrackit.countriesservice.service.CountryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.fasttrackit.countriesservice.model.dto.CountryMapper.toDto;
import static org.fasttrackit.countriesservice.model.dto.CountryMapper.toEntity;

@RestController
@RequestMapping("countries") // http://localhost:8080/countries
@RequiredArgsConstructor
@Slf4j
public class CountryController {
    private final CountryService service;
    private final CountryMapper mapper;

    @GetMapping// http://localhost:8080/countries
    public List<CountryDTO> getAllCountries(CountryFilter filter) {
        log.info("GET all countries was called");
        return service.getAllCountries(filter).stream()
                .map(CountryMapper::toDto)
                .toList();
    }

    @GetMapping("{id}") // http://localhost:8080/countries/some-id
    public CountryDTO getById(@PathVariable String id) {
        log.info("GET country by id");
        return toDto(service.getById(id));
    }

    @DeleteMapping("{id}") // http://localhost:8080/countries/some-id
    public CountryDTO deleteById(@PathVariable String id) {
        log.info("DELETE country by id");
        return toDto(service.deleteById(id));
    }

    @PostMapping // http://localhost:8080/countries
    public CountryDTO createNewCountry(@RequestBody CountryDTO newCountry) {
        log.info("POST create new country");
        return toDto(service.createNewCountry(toEntity(newCountry)));
    }

    @PutMapping("{id}")
    public CountryDTO replaceCountry(@PathVariable String id, @RequestBody CountryDTO replaceCountry) {
        return toDto(service.replaceCountry(id, toEntity(replaceCountry)));
    }

    @PostMapping("{id}/cities") // http://localhost:8080/countries/{id}/cities
    public CountryDTO createNewCountry(@PathVariable String id, @RequestBody City city) {
        log.info("POST create new city and add it to country");
        return toDto(service.addCityToCountry(id, city));
    }

    @PostMapping("{id}/neighbours/{neighbourId}") // http://localhost:8080/countries/{id}/cities
    public CountryDTO createNewCountry(@PathVariable String id, @PathVariable String neighbourId) {
        log.info("POST create new city and add it to country");
        return toDto(service.addNeighbourToCountry(id, neighbourId));
    }
}
