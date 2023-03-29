package com.example.springbootcrudwithintegrationtest;

import com.example.springbootcrudwithintegrationtest.entity.Product;
import com.example.springbootcrudwithintegrationtest.repository.ProductRepository;
import com.example.springbootcrudwithintegrationtest.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateProduct() throws Exception {
        Product product = new Product(1L, "Test Product", "Test Description", 10.99);
        //when(mock().saveProduct(product)).thenReturn(product);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.price").value("10.99"));
    }

    @Test
    public void testGetProductById() throws Exception {
        Product product = new Product(1L,"Test Product", "Test Description", 10.99);
        product = productService.saveProduct(product);

        mockMvc.perform(get("/api/v1/products/{id}", product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.price").value("10.99"));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Product product1 = new Product(1L,"Test Product 1", "Test Description 1", 10.99);
        productService.saveProduct(product1);

        Product product2 = new Product(2L,"Test Product 2", "Test Description 2", 20.99);
        productService.saveProduct(product2);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Test Product 1"))
                .andExpect(jsonPath("$[0].description").value("Test Description 1"))
                .andExpect(jsonPath("$[0].price").value("10.99"))
                .andExpect(jsonPath("$[1].name").value("Test Product 2"))
                .andExpect(jsonPath("$[1].description").value("Test Description 2"))
                .andExpect(jsonPath("$[1].price").value("20.99"));
    }

 /*@Test
    public void testUpdateProduct() throws Exception {
       Product product= new Product(1L,"Test Product 1", "Test Description 1", 10.99);
        product = productService.saveProduct(product);

        Product product1 = new Product(1L,"Updated Product", "Updated Description", 20.99);

        mockMvc.perform(put("/products/{id}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.price").value("20.99"));

        Optional<Product> updatedProductOptional = productRepository.findById(product.getId());
        assertTrue(updatedProductOptional.isPresent());
        Product updatedProduct = updatedProductOptional.get();
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals("Updated Description", updatedProduct.getDescription());
        assertEquals(new BigDecimal("20"));
    }*/
    @Test
    public void testDeleteProduct() throws Exception {
        Product product = new Product(1L,"Test Product", "Test Description", 10.99);
        product = productService.saveProduct(product);

        mockMvc.perform(delete("/api/v1/products/{id}", product.getId()))
                .andExpect(status().isOk());


    }
}



