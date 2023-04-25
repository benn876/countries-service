package org.fasttrackit.countriesservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {
    public City(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @OneToOne(mappedBy = "capital")
    @JsonIgnore
    private Country capitalOf;

    @ManyToOne
    @JsonIgnore
    private Country country;
}
