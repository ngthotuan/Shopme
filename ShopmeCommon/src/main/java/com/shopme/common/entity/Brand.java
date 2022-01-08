package com.shopme.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 128, nullable = false, unique = true)
    private String name;
    @Column(length = 128)
    private String logo;

    public Brand(String name) {
        this.name = name;
    }

    public Brand(String name, String logo) {
        this.name = name;
        this.logo = logo;
    }

    public Brand(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @ManyToMany
    @JoinTable(
            name = "brands_categories",
            joinColumns = @JoinColumn(name = "brand_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @ToString.Exclude
    private Set<Category> categories = new HashSet<>();

    @Transient
    public String getLogoPath() {
        if (id == null || logo == null) {
            return "/images/image-thumbnail.png";
        }
        return String.format("/brand-images/%d/%s", id, logo);
    }

    @Override
    public String toString() {
        return name;
    }
}
