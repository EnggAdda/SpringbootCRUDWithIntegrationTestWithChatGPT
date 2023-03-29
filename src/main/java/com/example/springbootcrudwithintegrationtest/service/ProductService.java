package com.example.springbootcrudwithintegrationtest.service;


import com.example.springbootcrudwithintegrationtest.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product saveProduct(Product product);

    void deleteProductById(Long id);

    void deleteAllProducts();
}

