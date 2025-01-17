package com.example.QuanLiDonHang.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuanLiDonHang.DTO.ChiTietDonHangDTO;
import com.example.QuanLiDonHang.DTO.DonHangRequest;
import com.example.QuanLiDonHang.entity.DonHang;
import com.example.QuanLiDonHang.service.DonHangService;

@RestController
@RequestMapping("/api/donhang")
@CrossOrigin(origins = "*")
public class DonHangController2 {

	@Autowired
	private DonHangService donHangService;

	// Lấy danh sách chi tiết đơn hàng
	@GetMapping("/list-api")
	public ResponseEntity<List<ChiTietDonHangDTO>> getDanhSachChiTietDonHang() {
		List<ChiTietDonHangDTO> danhSachChiTiet = donHangService.getDanhSachChiTietDonHang();
		return ResponseEntity.ok(danhSachChiTiet);
	}

	// Thêm đơn hàng mới
	@PostMapping("/add-api")
    public ResponseEntity<String> addDonHang(@RequestBody DonHangRequest donHangRequest) throws ParseException {
        donHangService.saveDonHang(donHangRequest.getMaSP(), donHangRequest.getSoLuong(), donHangRequest.getMaKH());
        return ResponseEntity.ok("Thêm đơn hàng thành công");
    }

}
