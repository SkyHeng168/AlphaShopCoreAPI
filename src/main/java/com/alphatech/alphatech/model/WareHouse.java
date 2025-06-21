package com.alphatech.alphatech.model;

import com.alphatech.alphatech.enums.WareHouseStatus;
import com.alphatech.alphatech.enums.WarehouseCapacityStatus;
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
@Table(name = "wareHouse_tbl")
public class WareHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 50)
    private String warehouseName;
    @Column(nullable = false, length = 50, unique = true)
    private String warehouseCode;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String contactPhone;
    @Column(nullable = false, length = 50)
    private String  managerName;
    @Column(nullable = false, length = 50)
    private String  managerEmail;
    @Column(nullable = false)
    private int capacity;

    @Enumerated(EnumType.STRING)
    private WarehouseCapacityStatus warehouseCapacityStatus = WarehouseCapacityStatus.NOT_FULL;

    @Enumerated(EnumType.STRING)
    private WareHouseStatus status;
    @Lob
    private String note;

    @OneToMany(mappedBy = "wareHouse", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    public List<Inventory> inventories = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
