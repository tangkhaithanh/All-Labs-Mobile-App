package org.example.lab2_practice_api.services;

import org.example.lab2_practice_api.entities.Product;

import java.time.LocalDateTime;
import java.util.List;

public interface IProductService
{
    List<Product> getTopSellingProduct();
    List<Product> getProductInWeek(LocalDateTime date);
    List<Product> getProductByCategory(Long categoryID);
}
