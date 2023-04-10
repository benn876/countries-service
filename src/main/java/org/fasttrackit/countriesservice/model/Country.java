package org.fasttrackit.countriesservice.model;

import lombok.Builder;
import lombok.With;

import java.util.List;

@Builder(toBuilder = true)
@With
public record Country(
        String id,
        String name,
        String capital,
        Integer population,
        Integer area,
        String continent,
        List<String> neighbours
) {
}
