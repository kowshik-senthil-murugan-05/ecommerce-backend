package com.ecommerce.app.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepo extends JpaRepository<Product, Long>
{
    Product findByCategoryIdAndProductName(long categoryId, String productName);

    Page<Product> findAllByCategoryId(long categoryId, Pageable pageable);

//    @Query("SELECT p FROM Product p WHERE " +
//            "(:name IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%'))) AND ")
//            "(:categoryName IS NULL OR LOWER(p.category.categoryName) LIKE LOWER(CONCAT('%', :categoryName, '%')))")

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :productName, '%'))")
    Page<Product> searchByProductName(@Param("productName") String productName, Pageable pageable);

    Page<Product> findAllBySellerId(long sellerId, Pageable pageable);
}
