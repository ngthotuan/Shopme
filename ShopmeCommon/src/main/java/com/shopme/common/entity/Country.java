package com.shopme.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 45, nullable = false)
    private String name;
    @Column(length = 5, nullable = false)
    private String code;
    @OneToMany(mappedBy = "country")
    @ToString.Exclude
    @JsonIgnore
    private Set<State> states;

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Country(long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Country(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
