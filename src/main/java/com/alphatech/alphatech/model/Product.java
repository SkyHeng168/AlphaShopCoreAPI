package com.alphatech.alphatech.model;

import com.alphatech.alphatech.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "product_tbl")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false, unique = true)
    private String productName;
    @Column(length = 1000)
    private String description;
    private BigDecimal basePrice;
    private BigDecimal currentPrice;
    @Column(length = 10, nullable = false)
    private String sku;
    private String imageUrl;
    private boolean isFeature;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    @JsonBackReference
    private WareHouse wareHouse;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    @JsonBackReference
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    @JsonBackReference
    private Suppliers suppliers;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Inventory> inventories = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "product_tags",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
