package com.example.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee.model.NhanVien;
import com.example.employee.model.TaiKhoan;
import com.example.employee.repository.NhanVienRepository;
import com.example.employee.repository.TaiKhoanRepository;

@Service
public class NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;
    
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    
    public void saveNhanVien(NhanVien nhanVien) {
        // Save TaiKhoan first if it's new
        TaiKhoan taiKhoan = nhanVien.getTaiKhoan();
        if (taiKhoan != null && taiKhoan.getMaTK() == null) {
            taiKhoanRepository.save(taiKhoan);
        }
        nhanVienRepository.save(nhanVien);
    }
    
    public boolean existsByCanCuoc(String canCuoc) {
        return nhanVienRepository.existsByCanCuoc(canCuoc);
    }

    public boolean existsBySoDT(String soDT) {
        return nhanVienRepository.existsBySoDT(soDT);
    }
    
    public boolean existsByEmail(String email) {
        return nhanVienRepository.existsByEmail(email);
    }

    public List<NhanVien> getAllNhanViens() {
        return nhanVienRepository.findAll();
    }

    public NhanVien getNhanVienById(Long id) {
        return nhanVienRepository.findById(id).orElse(null);
    }
    
    public List<NhanVien> searchNhanVienByName(String keyword) {
        return nhanVienRepository.findByTenNVContaining(keyword);
    }

//    public void deleteNhanVien(Long id) {
//        nhanVienRepository.deleteById(id);
//    }
    
    public void deleteNhanVien(Long id) {
        NhanVien nhanVien = nhanVienRepository.findById(id).orElse(null);
        if (nhanVien != null) {
            TaiKhoan taiKhoan = nhanVien.getTaiKhoan();
            nhanVienRepository.deleteById(id);
            if (taiKhoan != null) {
                taiKhoanRepository.delete(taiKhoan);
            }
        }
    }
}