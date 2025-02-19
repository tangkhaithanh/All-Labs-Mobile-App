package org.example.lab2_practice_api.controllers;

import org.example.lab2_practice_api.entities.Product;
import org.example.lab2_practice_api.repositories.ProductRepository;
import org.example.lab2_practice_api.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private IProductService productService;
    @GetMapping("/top-selling")
    public ResponseEntity<List<Product>> getTopSellingProducts()
    {
        List<Product> products = productService.getTopSellingProduct();
        if(products.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @GetMapping("/product-in-seven-days")
    public ResponseEntity<List<Product>> getProductsInSevenDays()
    {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysAgo = now.minus(7, ChronoUnit.DAYS);
        List<Product>products = productService.getProductInWeek(sevenDaysAgo);
        if(products.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(products, HttpStatus.OK);
    }
}

