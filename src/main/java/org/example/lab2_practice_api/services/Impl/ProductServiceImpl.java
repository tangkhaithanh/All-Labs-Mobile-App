package org.example.lab2_practice_api.services.Impl;

import com.zaxxer.hikari.pool.ProxyDatabaseMetaData;
import org.example.lab2_practice_api.entities.Product;
import org.example.lab2_practice_api.repositories.ProductRepository;
import org.example.lab2_practice_api.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Product> getTopSellingProduct() {
        return productRepository.findTop10ByOrderByQuantitySoldDesc();
    }

    @Override
    public List<Product> getProductInWeek(LocalDateTime date) {
        return productRepository.findByCreatedDateAfter(date);
    }

    @Override
    public List<Product> getProductByCategory(Long categoryID) {
        return productRepository.findByCategory_Id(categoryID);
    }
}

