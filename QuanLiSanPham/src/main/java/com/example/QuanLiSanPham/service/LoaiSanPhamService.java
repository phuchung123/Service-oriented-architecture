package com.example.QuanLiSanPham.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.QuanLiSanPham.entity.LoaiSanPham;
import com.example.QuanLiSanPham.repository.LoaiSanPhamRepository;

@Service
public class LoaiSanPhamService {

    @Autowired
    private LoaiSanPhamRepository loaiSanPhamRepository;

    public List<LoaiSanPham> getAllLoaiSanPhams() {
        return loaiSanPhamRepository.findAll();
    }
    
    public LoaiSanPham getLoaiSanPhamById(Long id) {
        return loaiSanPhamRepository.findById(id).orElse(null);
    }

}
