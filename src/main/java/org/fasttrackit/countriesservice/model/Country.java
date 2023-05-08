package org.fasttrackit.countriesservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Builder(toBuilder = true)
@With
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Country {


    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JsonIgnore
    private City capital;

    @OneToMany(mappedBy = "country", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = LAZY)
    @JsonIgnore
    private List<City> cities;

    @Column
    private Integer population;

    @Column
    private Integer area;

    @Column
    private String continent;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Country> neighboursCountries;

    @Transient
    private List<String> neighbours;
}
