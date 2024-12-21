package com.example.bth3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bth3.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}

