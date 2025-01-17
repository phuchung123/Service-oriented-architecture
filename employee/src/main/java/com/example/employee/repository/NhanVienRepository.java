package com.example.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.employee.model.NhanVien;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {
    List<NhanVien> findByTenNVContaining(String keyword);
    boolean existsByCanCuoc(String canCuoc);
    boolean existsBySoDT(String soDT);
    boolean existsByEmail(String email);
}

