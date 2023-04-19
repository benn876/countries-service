package org.fasttrackit.countriesservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @Column
    private String capital;
    @Column
    private Integer population;
    @Column
    private Integer area;
    @Column
    private String continent;
    @Transient
    private List<String> neighbours;
}
