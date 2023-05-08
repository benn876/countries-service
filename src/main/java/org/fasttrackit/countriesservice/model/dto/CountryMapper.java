package org.fasttrackit.countriesservice.model.dto;

import org.fasttrackit.countriesservice.model.City;
import org.fasttrackit.countriesservice.model.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    public static CountryDTO toDto(Country entity) {
        return CountryDTO.builder()
                .id(entity.getId())
                .population(entity.getPopulation())
                .capital(entity.getCapital())
                .cities(entity.getCities())
                .countryNeighbours(entity.getNeighboursCountries())
                .continent(entity.getContinent())
                .name(entity.getName())
                .neighbours(entity.getNeighbours())
                .build();
    }

    public static Country toEntity(CountryDTO countryDTO) {
        return Country.builder()
                .population(countryDTO.population())
                .continent(countryDTO.continent())
                .capital(City.builder()
                        .name(countryDTO.capital().getName())
                        .build())
                .name(countryDTO.name())
                .neighbours(countryDTO.neighbours())
                .build();
    }
}
