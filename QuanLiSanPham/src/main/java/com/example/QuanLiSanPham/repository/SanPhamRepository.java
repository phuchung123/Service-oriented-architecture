package com.example.QuanLiSanPham.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.QuanLiSanPham.entity.SanPham;

public interface SanPhamRepository extends JpaRepository<SanPham, Long>, JpaSpecificationExecutor<SanPham> {
    List<SanPham> findAll();
    List<SanPham> findByNgayXoaIsNull();
}
