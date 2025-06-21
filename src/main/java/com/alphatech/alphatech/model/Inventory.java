package com.alphatech.alphatech.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "inventory_tbl")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    @JsonBackReference
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    @JsonBackReference
    private WareHouse warehouse;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<StockMovement> movements = new ArrayList<>();

    @Column(nullable = false)
    private Integer quantity = 0;
}
