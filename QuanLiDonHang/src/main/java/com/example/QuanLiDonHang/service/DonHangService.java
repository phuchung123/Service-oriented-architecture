package com.example.QuanLiDonHang.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.QuanLiDonHang.DTO.ChiTietDonHangDTO;
import com.example.QuanLiDonHang.DTO.SanPhamDTO;
import com.example.QuanLiDonHang.entity.ChiTietDonHang;
import com.example.QuanLiDonHang.entity.DonHang;
import com.example.QuanLiDonHang.entity.KhachHang;
import com.example.QuanLiDonHang.repository.ChiTietDonHangRepository;
import com.example.QuanLiDonHang.repository.DonHangRepository;
import com.example.QuanLiDonHang.repository.KhachHangRepository;

import jakarta.persistence.criteria.Predicate;

@Service
public class DonHangService {

	@Autowired
	private DonHangRepository donHangRepository;

	@Autowired
	private KhachHangRepository khachHangRepository;

	@Autowired
	private ChiTietDonHangRepository chiTietDonHangRepository;

	@Autowired
	private RestTemplate restTemplate; // Thêm RestTemplate để gọi API sản phẩm

	@Value("${sanpham.api.url}") // Đọc URL API sản phẩm từ application.properties
	private String sanPhamApiUrl;

	public void saveDonHang(Long sanPhamId, Integer soLuong, Long khachHangId) throws ParseException {
		Optional<KhachHang> khachHangOptional = khachHangRepository.findById(khachHangId);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = sdf.format(new Date());
		Date date = sdf.parse(formattedDate);

		// Tạo mới đơn hàng
		DonHang donHang = new DonHang();
		donHang.setNgayTao(date);
		donHang.setTrangThaiDonHang("Chờ xác nhận");
		donHang.setKhachHang(khachHangOptional.get());
		donHangRepository.save(donHang);

		// Lấy thông tin sản phẩm từ API
		SanPhamDTO sanPham = restTemplate.getForObject(sanPhamApiUrl + "/" + sanPhamId, SanPhamDTO.class);

		// Tính toán tổng giá
		Double tongGia = soLuong * sanPham.getGiaBan();

		// Tạo chi tiết đơn hàng
		ChiTietDonHang chiTietDonHang = new ChiTietDonHang();
		chiTietDonHang.setDonHang(donHang);
		chiTietDonHang.setMaSanPham(sanPhamId);
		chiTietDonHang.setSoLuong(soLuong);
		chiTietDonHang.setTongGia(tongGia);
		chiTietDonHangRepository.save(chiTietDonHang);

	}

	public List<KhachHang> getAllKhachHangs() {
		return khachHangRepository.findAll();
	}

	public List<ChiTietDonHang> getActiveChiTietDonHangs() {
		return chiTietDonHangRepository.findByNgayXoaIsNull();
	}

	public List<ChiTietDonHangDTO> getDanhSachChiTietDonHang() {
		List<ChiTietDonHang> chiTietDonHangs = chiTietDonHangRepository.findByNgayXoaIsNull();

		List<ChiTietDonHangDTO> chiTietDonHangDTOs = new ArrayList<>();
		for (ChiTietDonHang chiTiet : chiTietDonHangs) {
			// Gọi API để lấy thông tin sản phẩm
			SanPhamDTO sanPham = restTemplate.getForObject(sanPhamApiUrl + "/" + chiTiet.getMaSanPham(),
					SanPhamDTO.class);

			// Map dữ liệu vào DTO
			ChiTietDonHangDTO dto = new ChiTietDonHangDTO();
			dto.setMaDonHang(chiTiet.getDonHang().getMaDH());
			dto.setTenKhachHang(chiTiet.getDonHang().getKhachHang().getTenKH());
			dto.setMaSanPham(chiTiet.getMaSanPham());
			dto.setTenSanPham(sanPham.getTenSP());
			dto.setSoLuong(chiTiet.getSoLuong());
			dto.setTongGia(chiTiet.getTongGia());
			dto.setTrangThai(chiTiet.getDonHang().getTrangThaiDonHang());
			dto.setMaChiTietDonHang(chiTiet.getMaCTDH());
			chiTietDonHangDTOs.add(dto);
		}
		return chiTietDonHangDTOs;
	}

	public List<DonHang> getAllDonHangs() {
		return donHangRepository.findAll();
	}

	public void updateTrangThaiDonHang(Long maDonHang, String trangThaiMoi) {
		Optional<DonHang> donHangOptional = donHangRepository.findById(maDonHang);
		if (donHangOptional.isPresent()) {
			DonHang donHang = donHangOptional.get();
			donHang.setTrangThaiDonHang(trangThaiMoi);
			donHangRepository.save(donHang);
		} else {
			throw new RuntimeException("Không tìm thấy đơn hàng với mã: " + maDonHang);
		}
	}

	public void xoaChiTietDonHang(Long maChiTiet) throws ParseException {
		Optional<ChiTietDonHang> chiTietOptional = chiTietDonHangRepository.findById(maChiTiet);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = sdf.format(new Date());
		Date date = sdf.parse(formattedDate);
		if (chiTietOptional.isPresent()) {
			ChiTietDonHang chiTiet = chiTietOptional.get();
			chiTiet.setNgayXoa(date);
			chiTietDonHangRepository.save(chiTiet);
		} else {
			throw new RuntimeException("Không tìm thấy chi tiết đơn hàng với mã: " + maChiTiet);
		}
	}

	
	public List<DonHang> searchDonHangs(Long maDH, String tenKH, Date ngayTaoFrom, Date ngayTaoTo) {
	    Specification<DonHang> spec = (root, query, cb) -> {
	        Predicate p = cb.conjunction();
	        
	        // Điều kiện tìm kiếm mã đơn hàng
	        if (maDH != null) {
	            p = cb.and(p, cb.equal(root.get("maDH"), maDH));
	        }
	        
	        // Điều kiện tìm kiếm tên khách hàng
	        if (tenKH != null && !tenKH.isEmpty()) {
	            p = cb.and(p, cb.like(root.get("khachHang").get("tenKH"), "%" + tenKH + "%"));
	        }

	        // Điều kiện tìm kiếm ngày tạo từ và đến
	        if (ngayTaoFrom != null && ngayTaoTo != null) {
	            p = cb.and(p, cb.between(root.get("ngayTao"), ngayTaoFrom, ngayTaoTo));
	        } else if (ngayTaoFrom != null) {
	            p = cb.and(p, cb.greaterThanOrEqualTo(root.get("ngayTao"), ngayTaoFrom));
	        } else if (ngayTaoTo != null) {
	            p = cb.and(p, cb.lessThanOrEqualTo(root.get("ngayTao"), ngayTaoTo));
	        }
	        
	        return p;
	    };

	    return donHangRepository.findAll(spec);
	}

}
