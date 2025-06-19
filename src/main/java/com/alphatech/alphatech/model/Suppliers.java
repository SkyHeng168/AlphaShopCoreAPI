package com.alphatech.alphatech.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "suppliers_tbl")
public class Suppliers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String supplierName;
    private String imageSuppliers;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false, length = 15)
    private String phoneNumber;
    private String address;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String contactPerson;
    private LocalDateTime contractDate;

    @ManyToOne
    @JoinColumn(name = "country_code", referencedColumnName = "countryCode")
    private CountryTaxInfo countryTaxInfo;

    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
