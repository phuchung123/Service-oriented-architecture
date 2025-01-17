package com.example.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee.model.TaiKhoan;
import com.example.employee.repository.TaiKhoanRepository;

@Service
public class TaiKhoanService {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    public List<TaiKhoan> getAllTaiKhoans() {
        return taiKhoanRepository.findAll();
    }

    public TaiKhoan getTaiKhoanById(Long maTK) {
        return taiKhoanRepository.findById(maTK).orElse(null);
    }

    public TaiKhoan saveTaiKhoan(TaiKhoan taiKhoan) {
        return taiKhoanRepository.save(taiKhoan);
    }

    public void deleteTaiKhoan(Long maTK) {
        taiKhoanRepository.deleteById(maTK);
    }
}
