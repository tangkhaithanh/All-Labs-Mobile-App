package org.example.lab2_practice_api.repositories;

import org.example.lab2_practice_api.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    // lấy 10 sản phẩm bán chạy nhất
    List<Product> findTop10ByOrderByQuantitySoldDesc();
    // lấy 10 sản phẩm có ngay tạo <=7
    List<Product> findByCreatedDateAfter(LocalDateTime createdDate);

    List<Product> findByCategory_Id(Long categoryId);
}
