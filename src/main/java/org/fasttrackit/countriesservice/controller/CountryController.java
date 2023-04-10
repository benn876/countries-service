package org.fasttrackit.countriesservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fasttrackit.countriesservice.model.Country;
import org.fasttrackit.countriesservice.service.CountryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("countries") // http://localhost:8080/countries
@RequiredArgsConstructor
@Slf4j
public class CountryController {
    private final CountryService service;

    @GetMapping// http://localhost:8080/countries
    public List<Country> getAllCountries(@RequestParam(required = false) String continent,
                                         @RequestParam(required = false) Integer population) {
        if (continent != null) {
            if (population != null) {
                log.info("GET all countries by continent and population was called");
                return service.getCountriesInContinentWithPopulationBiggerThan(continent, population);
            }
            log.info("GET all countries by continent was called");
            return service.getCountriesByContinent(continent);
        } else {
            log.info("GET all countries was called");
            return service.getAllCountries();
        }
    }

    @GetMapping("{id}") // http://localhost:8080/countries/some-id
    public Country getById(@PathVariable String id) {
        log.info("GET country by id");
        return service.getById(id);
    }

    @DeleteMapping("{id}") // http://localhost:8080/countries/some-id
    public Country deleteById(@PathVariable String id) {
        log.info("DELETE country by id");
        return service.deleteById(id);
    }

    @PostMapping // http://localhost:8080/countries
    public Country createNewCountry(@RequestBody Country newCountry) {
        log.info("POST create new country");
        return service.createNewCountry(newCountry);
    }

    @PutMapping("{id}")
    public Country replaceCountry(@PathVariable String id, @RequestBody Country replaceCountry){
        return service.replaceCountry(id, replaceCountry);
    }
}
