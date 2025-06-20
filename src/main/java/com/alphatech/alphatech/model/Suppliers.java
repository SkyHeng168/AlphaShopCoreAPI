package com.alphatech.alphatech.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)  // Match the field name
    @JsonManagedReference  // This should be on the "parent" side (Suppliers)
    private List<DocumentContract> documentContracts = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
