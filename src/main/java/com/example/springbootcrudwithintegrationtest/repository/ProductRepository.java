package com.example.springbootcrudwithintegrationtest.repository;


import com.example.springbootcrudwithintegrationtest.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

