package org.fasttrackit.countriesservice.repository;

import org.fasttrackit.countriesservice.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    List<Country> findByContinent(String continent);

    @Query("select c from Country c where c.continent=:conti")
    List<Country> findByContinentQuery(@Param("conti") String continent);

    @Query("select c from Country c where (c.continent=:continent or :continent is null ) " +
            "and (c.population>= :population or :population is null)")
    List<Country> findFilterAll(String continent, Integer population);
}
