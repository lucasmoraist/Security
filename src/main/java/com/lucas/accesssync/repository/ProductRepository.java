package com.lucas.accesssync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.accesssync.domain.product.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
}
