package com.example.QuanLiDonHang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.QuanLiDonHang.entity.DonHang;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Long>,  JpaSpecificationExecutor<DonHang>  {

}
