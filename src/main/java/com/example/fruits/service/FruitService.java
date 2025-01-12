package com.example.fruits.service;

import com.example.fruits.model.Fruit;
import com.example.fruits.repository.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FruitService {
    @Autowired
    private FruitRepository fruitRepository;

//    public List<Fruit> getAllFruits() {
//        return fruitRepository.findAll();
//    }

    public List<Fruit> getAllFruits() {
        return fruitRepository.findByDeleteTimeIsNull(); // Gọi phương thức mới
    }
    
    public Optional<Fruit> getFruitById(Long id) {
        return fruitRepository.findById(id);
    }

    public Fruit saveFruit(Fruit fruit) {
        return fruitRepository.save(fruit);
    }

    public void deleteFruit(Long id) {
        fruitRepository.deleteById(id);
    }

    public List<Fruit> findByName(String name) {
        return fruitRepository.findByNameContaining(name);
    }

    public List<Fruit> findByPriceRange(double minPrice, double maxPrice) {
        return fruitRepository.findByPriceBetween(minPrice, maxPrice);
    }
}