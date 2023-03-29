package com.example.springbootcrudwithintegrationtest.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "producttable")
public class Product {
    @Id

    private Long id;

    private String name;

    private String description;

    private Double price;


}

