package com.trafimchuk.veranika.crud.controller;

import com.trafimchuk.veranika.crud.entity.Product;
import com.trafimchuk.veranika.crud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;


    @PostMapping("/addProduct")
    public ResponseEntity<HttpStatus> addProduct(@RequestBody Product product) {
        service.saveProduct(product);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/addProducts")
    public ResponseEntity<HttpStatus> addProducts(@RequestBody List<Product> products) {
        service.saveProducts(products);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAllProducts() {
        List<Product> list = service.getProducts();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/productById/{id}")
    public ResponseEntity<Product>  findProductById(@PathVariable int id) {
        Product product = service.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/product/{name}")
    public ResponseEntity<Product> findProductByName(@PathVariable String name) {
        Product product = service.getProductByName(name);
        return ResponseEntity.ok(product);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        String deleteProduct = service.deleteProduct(id);
        return ResponseEntity.ok(deleteProduct);
    }
}
