package com.example.QuanLiSanPham.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuanLiSanPham.DTO.SanPhamDTO;
import com.example.QuanLiSanPham.entity.LoaiSanPham;
import com.example.QuanLiSanPham.entity.SanPham;
import com.example.QuanLiSanPham.service.LoaiSanPhamService;
import com.example.QuanLiSanPham.service.SanPhamService;

@RestController
@RequestMapping("/api/sanpham1")
@CrossOrigin(origins = "*")
public class SanPhamController2 {

    @Autowired
    private SanPhamService sanPhamService;
    
    @Autowired
    private LoaiSanPhamService loaiSanPhamService;

    @GetMapping("/list-api")
    @ResponseBody
    public ResponseEntity<List<SanPhamDTO>> listSanPhams() {
        List<SanPham> sanPhams = sanPhamService.getAllSanPhamsNotDeleted();
        List<SanPhamDTO> sanPhamDTOs = sanPhamService.convertToDTO(sanPhams);
        return ResponseEntity.ok(sanPhamDTOs);
    }

    @GetMapping("/list-api/{id}")
    @ResponseBody
    public ResponseEntity<SanPhamDTO> getSanPhamById(@PathVariable("id") Long id) {
        SanPham sanPham = sanPhamService.getSanPhamById(id);
        if (sanPham != null) {
            SanPhamDTO sanPhamDTO = sanPhamService.convertToDTO(sanPham);
            return ResponseEntity.ok(sanPhamDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Thêm sản phẩm
    @PostMapping("/add-api")
    public ResponseEntity<String> addSanPham(@RequestBody SanPhamDTO sanPhamDTO) throws ParseException {
        // Tìm LoaiSanPham từ maLoaiSP
        LoaiSanPham loaiSanPham = loaiSanPhamService.getLoaiSanPhamById(sanPhamDTO.getMaLoaiSP());
        if (loaiSanPham == null) {
            return ResponseEntity.badRequest().body("Loại sản phẩm không tồn tại");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(new Date());
        Date date = sdf.parse(formattedDate);
        // Tạo SanPham từ DTO
        SanPham sanPham = new SanPham();
        sanPham.setTenSP(sanPhamDTO.getTenSP());
        sanPham.setLoaiSanPham(loaiSanPham);
        sanPham.setSoLuong(sanPhamDTO.getSoLuong());
        sanPham.setNgayNhap(date);
        sanPham.setGiaBan(sanPhamDTO.getGiaBan());

        sanPhamService.addSanPham(sanPham);
        return ResponseEntity.ok("Thêm sản phẩm thành công");
    }

    // Cập nhật sản phẩm
    @PutMapping("/edit-api/{id}")
    public ResponseEntity<String> updateSanPham(@PathVariable("id") Long id, @RequestBody SanPhamDTO sanPhamDTO) {
        SanPham sanPham = sanPhamService.getSanPhamById(id);
        if (sanPham == null) {
            return ResponseEntity.notFound().build();
        }

        LoaiSanPham loaiSanPham = loaiSanPhamService.getLoaiSanPhamById(sanPhamDTO.getMaLoaiSP());
        if (loaiSanPham == null) {
            return ResponseEntity.badRequest().body("Loại sản phẩm không tồn tại");
        }

        sanPham.setTenSP(sanPhamDTO.getTenSP());
        sanPham.setLoaiSanPham(loaiSanPham);
        sanPham.setSoLuong(sanPhamDTO.getSoLuong());
        sanPham.setGiaBan(sanPhamDTO.getGiaBan());

        sanPhamService.updateSanPham(sanPham);
        return ResponseEntity.ok("Cập nhật sản phẩm thành công");
    }
}
