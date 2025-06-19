package com.alphatech.alphatech.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "countryTaxInfo_tbl")
public class CountryTaxInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 3)
    private String countryCode;
    @Column(unique = true ,nullable = false, length = 50)
    private String countryName;
    @Column(nullable = false, length = 15)
    private String taxIdPrefix;
    @Column(nullable = false)
    private String taxIdRegex;
    @Column(nullable = false)
    private boolean requiresTaxId;
    private String taxIdExample;
}
