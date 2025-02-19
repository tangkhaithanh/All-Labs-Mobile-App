    package org.example.lab2_practice_api.entities;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import jakarta.persistence.*;
    import lombok.*;
    import org.springframework.stereotype.Component;

    import java.time.LocalDateTime;

    @Table(name="products")
    @Entity
    @NoArgsConstructor
    @AllArgsConstructor
    public class Product
    {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name="product_name",nullable = false,columnDefinition = "nvarchar(255)")
        private String productName;

        @Column(name="quantity_sold", nullable = false)
        private Long quantitySold;

        @Column(name="created_date", nullable = false)
        private LocalDateTime createdDate;

        @ManyToOne
        @JoinColumn(name="category_id")
        @JsonBackReference
        private Category category;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Long getQuantitySold() {
            return quantitySold;
        }

        public void setQuantitySold(Long quantitySold) {
            this.quantitySold = quantitySold;
        }

        public LocalDateTime getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }
    }
