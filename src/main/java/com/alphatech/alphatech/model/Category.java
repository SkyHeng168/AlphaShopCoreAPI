package com.alphatech.alphatech.model;

import com.alphatech.alphatech.enums.CategoryStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "categories_tbl")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false,  unique = true)
    private String categoryName;
    @Column(columnDefinition = "TEXT")
    private String categoryLogo;
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "sub_category")
    @JsonBackReference
    private Category subCategory;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Category> childCategories = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private CategoryStatus categoryStatus;

    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
