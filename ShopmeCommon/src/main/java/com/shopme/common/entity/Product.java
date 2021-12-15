package com.shopme.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.*;

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
    private Long id;

    @Column(nullable = false, length = 256)
    private String name;
    @Column(nullable = false, length = 256)
    private String alias;
    @Column(columnDefinition = "text", nullable = false, name = "short_description")
    private String shortDescription;
    @Column(columnDefinition = "text", nullable = false, name = "full_description")
    private String fullDescription;

    @Column(name = "main_image", nullable = false, length = 256)
    private String mainImage;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImage> images = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_PRODUCT_CATEGORY"))
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id", foreignKey = @ForeignKey(name = "FK_PRODUCT_BRAND"))
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDetail> details = new ArrayList<>();

    public void addExtraImage(String imageName) {
        ProductImage productImage = new ProductImage();
        productImage.setProduct(this);
        productImage.setName(imageName);
        images.add(productImage);
    }

    @Transient
    public String getMainImagePath() {
        if (id == null || mainImage == null) {
            return "/images/image-thumbnail.png";
        }
        return String.format("/product-images/%d/%s", id, mainImage);
    }

    public void addDetail(String name, String value) {
        ProductDetail productDetail = new ProductDetail(name, value, this);
        details.add(productDetail);
    }

    public void addDetail(Long id, String name, String value) {
        ProductDetail productDetail = new ProductDetail(id, name, value, this);
        details.add(productDetail);
    }

    public boolean containsImageName(String imageName) {
        return images.stream()
                .anyMatch(image -> image.getName().equals(imageName));
    }
}
