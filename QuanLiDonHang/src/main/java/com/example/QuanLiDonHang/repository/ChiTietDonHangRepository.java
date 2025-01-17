package com.example.QuanLiDonHang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.QuanLiDonHang.entity.ChiTietDonHang;

@Repository
public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHang, Long>{
	List<ChiTietDonHang> findByNgayXoaIsNull();
}
