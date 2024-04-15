package com.ecobank.productservice.controller;


import com.ecobank.productservice.dto.ProductRequest;
import com.ecobank.productservice.dto.ProductResponse;
import com.ecobank.productservice.model.Product;
import com.ecobank.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String createProduct(@RequestBody ProductRequest productRequest) {

        productService.createProduct(productRequest);
        return "Product successfully created";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
}


