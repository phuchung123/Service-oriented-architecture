package com.example.QuanLiSanPham.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLiSanPham.entity.LoaiSanPham;

public interface LoaiSanPhamRepository extends JpaRepository<LoaiSanPham, Long> {
	
}
