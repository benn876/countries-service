package org.fasttrackit.countriesservice.model.dto;

import lombok.Builder;
import org.fasttrackit.countriesservice.model.City;
import org.fasttrackit.countriesservice.model.Country;

import java.util.List;

@Builder
public record CountryDTO(
        Long id,
        String name,
        City capital,
        List<City> cities,
        List<Country> countryNeighbours,
        Integer population,
        String continent,
        List<String> neighbours
) {
}
