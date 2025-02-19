package org.example.lab2_practice_api.controllers;

import org.example.lab2_practice_api.entities.Category;
import org.example.lab2_practice_api.entities.Product;
import org.example.lab2_practice_api.repositories.CategoryRepository;
import org.example.lab2_practice_api.services.ICategoryService;
import org.example.lab2_practice_api.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    // API hiển thị toàn bộ danh mục:
    @GetMapping
    public ResponseEntity<?>getAllCategories()
    {
        List<Category> categories = categoryService.getAllCategories();
        if(categories.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
        {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
    }

    @GetMapping("/products/{categoryID}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryID)
    {
        List<Product> products = productService.getProductByCategory(categoryID);
        if(products == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
            return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
