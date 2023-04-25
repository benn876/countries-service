package org.fasttrackit.countriesservice.model.filter;

import lombok.Builder;

@Builder
public record CountryFilter(
        String continent,
        Integer population
) {
}
