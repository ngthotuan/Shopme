package com.shopme.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "unique_name"),
        @UniqueConstraint(columnNames = "alias", name = "unique_alias")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 256)
    private String name;
    @Column(nullable = false, length = 256)
    private String alias;
    @Column(length = 512, nullable = false, name = "short_description")
    private String shortDescription;
    @Column(length = 4096, nullable = false, name = "full_description")
    private String fullDescription;

    @Column(name = "created_time")
    private Date createdTime;
    @Column(name = "updated_time")
    private Date updatedTime;

    @ColumnDefault("true")
    private boolean enabled;
    @Column(name = "in_stock")
    @ColumnDefault("true")
    private boolean inStock;

    private float cost;

    private float price;
    @Column(name = "discount_percent")
    @ColumnDefault("0")
    private float discountPercent;


    @ColumnDefault("0")
    private float length;
    @ColumnDefault("0")
    private float width;
    @ColumnDefault("0")
    private float height;
    @ColumnDefault("0")
    private float weight;

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_PRODUCT_CATEGORY"))
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id", foreignKey = @ForeignKey(name = "FK_PRODUCT_BRAND"))
    private Brand brand;
}
