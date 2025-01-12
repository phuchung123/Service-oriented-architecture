package com.example.fruits.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fruits.model.Fruit;
import com.example.fruits.service.FruitService;

@RestController
@RequestMapping("/api/fruits")
public class FruitController {

    @Autowired
    private FruitService fruitService;

//    @GetMapping
//    public List<Fruit> getAllFruits() {
//        return fruitService.getAllFruits();
//    }

    @PostMapping("/detail")	
    public ResponseEntity<List<Fruit>> getAllFruits() {
        List<Fruit> fruits = fruitService.getAllFruits();
        return ResponseEntity.ok(fruits); // Trả về danh sách trái cây với delete_time = null
    }
    
    @PostMapping("/{id}")	
    public ResponseEntity<Fruit> getFruitById(@PathVariable Long id) {
        return fruitService.getFruitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Fruit createFruit(@RequestBody Fruit fruit) {
        return fruitService.saveFruit(fruit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fruit> updateFruit(@PathVariable Long id, @RequestBody Fruit fruitDetails) {
        return fruitService.getFruitById(id)
                .map(fruit -> {
                    fruit.setName(fruitDetails.getName());
                    fruit.setPrice(fruitDetails.getPrice());
                    Fruit updatedFruit = fruitService.saveFruit(fruit);
                    return ResponseEntity.ok(updatedFruit);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Fruit> deleteFruit(@PathVariable Long id, @RequestBody Fruit fruitDetails) {
        return fruitService.getFruitById(id)
                .map(fruit -> {
                    fruit.setDeleteTime(new Date().toString());
                    Fruit updatedFruit = fruitService.saveFruit(fruit);
                    return ResponseEntity.ok(updatedFruit);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteFruit(@PathVariable Long id) {
//        fruitService.deleteFruit(id);
//        return ResponseEntity.noContent().build();
//    }
    
    @GetMapping("/search/name")
    public List<Fruit> getFruitsByName(@RequestParam String name) {
        return fruitService.findByName(name);
    }

    @GetMapping("/search/price")
    public List<Fruit> getFruitsByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        return fruitService.findByPriceRange(minPrice, maxPrice);
    }
}