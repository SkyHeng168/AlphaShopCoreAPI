package com.alphatech.alphatech.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "documentContract_tbl")
public class DocumentContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 150)
    private String documentName;
    @Column(nullable = false, length = 250)
    private String file;
    @Column(nullable = false, length = 250)
    private String fileType;
    private LocalDateTime uploadDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    @JsonBackReference
    private Suppliers supplier;

    @Column(length = 2500)
    private String description;
    /*private String uploadBy;*/
}
