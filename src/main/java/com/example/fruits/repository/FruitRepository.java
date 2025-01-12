package com.example.fruits.repository;

import com.example.fruits.model.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FruitRepository extends JpaRepository<Fruit, Long> {
    List<Fruit> findByNameContaining(String name);
    List<Fruit> findByPriceBetween(double minPrice, double maxPrice);
    List<Fruit> findByDeleteTimeIsNull();
}