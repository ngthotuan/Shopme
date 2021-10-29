package com.shopme.common.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 128, nullable = false, unique = true)
    private String name;
    @Column(length = 64, nullable = false, unique = true)
    private String alias;
    private String image;
    private boolean enabled;

    public static Category copyIdAndName(Category category) {
        Category copy = new Category();
        copy.setId(category.getId());
        copy.setName(category.getName());
        return copy;
    }

    public static Category copyIdAndName(Long id, String name) {
        Category copy = new Category();
        copy.setId(id);
        copy.setName(name);
        return copy;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private Set<Category> children = new HashSet<>();

    @Transient
    public String getImagePath() {
        if (id == null || image == null) {
            return "/images/image-thumbnail.png";
        }
        return String.format("/category-image/%d/%s", id, image);
    }


}
