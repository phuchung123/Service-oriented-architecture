package com.example.QuanLiDonHang.repository;

import com.example.QuanLiDonHang.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {
    // Có thể thêm các phương thức tìm kiếm tùy chỉnh nếu cần
}
