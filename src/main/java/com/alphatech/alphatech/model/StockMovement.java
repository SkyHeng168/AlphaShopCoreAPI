package com.alphatech.alphatech.model;

import com.alphatech.alphatech.enums.MovementType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "stock_Movement_tbl")
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_id",  nullable = false)
    @JsonBackReference
    private Inventory inventory;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type",  nullable = false, length = 20)
    private MovementType movementType;

    @Column(nullable = false)
    private int quantity;

    private String note;
    private LocalDateTime stockMovementDate;
}
