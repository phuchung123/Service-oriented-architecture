package com.example.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.model.TaiKhoan;
import com.example.employee.service.TaiKhoanService;

@RestController
@RequestMapping("/taikhoan")
public class TaiKhoanController {
    @Autowired
    private TaiKhoanService taiKhoanService;

    @GetMapping
    public List<TaiKhoan> getAllTaiKhoans() {
        return taiKhoanService.getAllTaiKhoans();
    }

    @GetMapping("/{maTK}")
    public TaiKhoan getTaiKhoanById(@PathVariable Long maTK) {
        return taiKhoanService.getTaiKhoanById(maTK);
    }

    @PostMapping
    public TaiKhoan createTaiKhoan(@RequestBody TaiKhoan taiKhoan) {
        return taiKhoanService.saveTaiKhoan(taiKhoan);
    }

    @PutMapping("/{maTK}")
    public TaiKhoan updateTaiKhoan(@PathVariable Long maTK, @RequestBody TaiKhoan taiKhoan) {
        TaiKhoan existingTaiKhoan = taiKhoanService.getTaiKhoanById(maTK);
        if (existingTaiKhoan != null) {
            taiKhoan.setMaTK(maTK);
            return taiKhoanService.saveTaiKhoan(taiKhoan);
        }
        return null;
    }

    @DeleteMapping("/{maTK}")
    public void deleteTaiKhoan(@PathVariable Long maTK) {
        taiKhoanService.deleteTaiKhoan(maTK);
    }
}