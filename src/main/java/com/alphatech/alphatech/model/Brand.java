package com.alphatech.alphatech.model;

import com.alphatech.alphatech.enums.BrandStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brand_tbl")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    private String brandName;
    private String logoUrl;
    @Column(length = 1000)
    private String description;
    private String websiteUrl;
    private String socialMediaUrl;
    @Column(nullable = false, length = 50)
    private String country;
    @Enumerated(EnumType.STRING)
    private BrandStatus brandStatus;

    @Column(updatable = false)
    private LocalDateTime createdDate =  LocalDateTime.now();
    private LocalDateTime updatedDate;
}


