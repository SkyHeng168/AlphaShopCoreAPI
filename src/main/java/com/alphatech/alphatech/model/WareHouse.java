package com.alphatech.alphatech.model;

import com.alphatech.alphatech.enums.WareHouseStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    @Column(nullable = false, length = 50)
    private int capacity;
    @Enumerated(EnumType.STRING)
    private WareHouseStatus status;
    @Lob
    private String note;

    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
