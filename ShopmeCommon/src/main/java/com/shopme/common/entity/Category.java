package com.shopme.common.entity;

import lombok.*;
import org.springframework.beans.BeanUtils;

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

    public static Category from(Category category, String newName) {
        Category copy = from(category);
        copy.setName(newName);
        return copy;
    }

    public static Category from(Category category) {
        Category copy = new Category();
        BeanUtils.copyProperties(category, copy);
        return copy;
    }

    public Category(Long id, String name, String alias) {
        this.id = id;
        this.name = name;
        this.alias = alias;
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
        return String.format("/category-images/%d/%s", id, image);
    }


}
