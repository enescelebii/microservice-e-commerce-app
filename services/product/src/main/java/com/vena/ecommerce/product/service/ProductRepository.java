package com.vena.ecommerce.product.service;

import com.vena.ecommerce.product.entity.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {


    List<Product> findAllByIdInOrderById(List<@NotNull(message = "Product ID is Mandatory") Integer> productIds);
}
