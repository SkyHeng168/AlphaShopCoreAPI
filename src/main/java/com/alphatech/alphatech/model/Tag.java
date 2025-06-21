package com.alphatech.alphatech.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "tags_tbl")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 100, nullable = false)
    private String tagName;
    @Column(length = 100, nullable = false)
    private String slug;
    @ManyToMany(mappedBy = "tags", cascade = CascadeType.PERSIST)
    private Set<Product> products = new HashSet<>();
}
