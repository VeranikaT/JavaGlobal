package com.trafimchuk.veranika.crud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trafimchuk.veranika.crud.entity.Product;
import com.trafimchuk.veranika.crud.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @Test
    void addProduct() throws Exception {
        Product product = new Product(1, "Milk", 1, 1.00);

        mvc.perform(post("/addProduct")
                        .content(asJsonString(product))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addProducts() throws Exception {
        Product product1 = new Product(1, "Milk", 1, 1.00);
        Product product2 = new Product(2, "Coca-cola", 1, 1.00);
        List<Product> products = new ArrayList<Product>();
        products.add(product1);
        products.add(product2);

        mvc.perform(post("/addProducts")
                        .content(asJsonString(products))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findAllProducts() throws Exception {
        Product product1 = new Product(1, "Milk", 1, 1.00);
        Product product2 = new Product(2, "Coca-cola", 1, 1.00);
        List<Product> products = new ArrayList<Product>();
        products.add(product1);
        products.add(product2);

        mvc.perform(get("/products"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    void findProductById() throws Exception {
        Product product = new Product(1, "Milk", 1, 1.00);

        mvc.perform(get("/productById/{id}", product.getId()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    void findProductByName() throws Exception {
        Product product = new Product(1, "Milk", 1, 1.00);

        mvc.perform(get("/product/{name}", product.getName()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    void deleteProduct() throws Exception {
        Product product = new Product(1, "Milk", 1, 1.00);

        mvc.perform(delete("/delete/{id}", product.getId()))
                .andExpect(status().isOk())
                .andReturn();
    }

    public static String asJsonString(Product product) {
        try {
            return new ObjectMapper().writeValueAsString(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String asJsonString(List<Product> list) {
        try {
            return new ObjectMapper().writeValueAsString(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}