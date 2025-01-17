package com.example.QuanLiSanPham.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.QuanLiSanPham.DTO.SanPhamDTO;
import com.example.QuanLiSanPham.entity.SanPham;
import com.example.QuanLiSanPham.repository.SanPhamRepository;

import jakarta.persistence.criteria.Predicate;

@Service
public class SanPhamService {
	@Autowired
	private SanPhamRepository sanPhamRepository;

	public SanPham addSanPham(SanPham sanPham) {
		return sanPhamRepository.save(sanPham);
	}

	public List<SanPham> getAllSanPhamsNotDeleted() {
		return sanPhamRepository.findByNgayXoaIsNull();
	}

	public SanPham getSanPhamById(Long id) {
		return sanPhamRepository.findById(id).orElse(null);
	}

	public SanPham updateSanPham(SanPham sanPham) {
		return sanPhamRepository.save(sanPham);
	}

	public SanPham deleteSanPham(Long maSP) throws ParseException {
		SanPham sanPham = sanPhamRepository.findById(maSP)
				.orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = sdf.format(new Date());
		Date date = sdf.parse(formattedDate);
		// Cập nhật trường ngayXoa thành ngày hiện tại
		sanPham.setNgayXoa(date);
		return sanPhamRepository.save(sanPham);
	}

	public List<SanPham> searchSanPham(String tenSP, Long maLoaiSP, Double giaBanFrom, Double giaBanTo,
			Date ngayNhapFrom, Date ngayNhapTo) {
		Specification<SanPham> spec = (root, query, cb) -> {
			// Lưu trữ các điều kiện của truy vấn
			Predicate p = cb.conjunction(); // Khởi tạo với điều kiện true

			// Kiểm tra trường ngayXoa là null
			p = cb.and(p, cb.isNull(root.get("ngayXoa")));

			if (tenSP != null && !tenSP.isEmpty()) {
				p = cb.and(p, cb.like(root.get("tenSP"), "%" + tenSP + "%"));
			}

			if (maLoaiSP != null) {
				p = cb.and(p, cb.equal(root.get("loaiSanPham").get("maLoaiSP"), maLoaiSP));
			}

			if (giaBanFrom != null && giaBanTo != null) {
				p = cb.and(p, cb.between(root.get("giaBan"), giaBanFrom, giaBanTo));
			} else if (giaBanFrom != null) {
				p = cb.and(p, cb.greaterThanOrEqualTo(root.get("giaBan"), giaBanFrom));
			} else if (giaBanTo != null) {
				p = cb.and(p, cb.lessThanOrEqualTo(root.get("giaBan"), giaBanTo));
			}

			if (ngayNhapFrom != null && ngayNhapTo != null) {
				p = cb.and(p, cb.between(root.get("ngayNhap"), ngayNhapFrom, ngayNhapTo));
			} else if (ngayNhapFrom != null) {
				p = cb.and(p, cb.greaterThanOrEqualTo(root.get("ngayNhap"), ngayNhapFrom));
			} else if (ngayNhapTo != null) {
				p = cb.and(p, cb.lessThanOrEqualTo(root.get("ngayNhap"), ngayNhapTo));
			}

			return p;
		};

		return sanPhamRepository.findAll(spec);
	}

	public List<SanPhamDTO> convertToDTO(List<SanPham> sanPhams) {
		List<SanPhamDTO> sanPhamDTOs = new ArrayList<>();

		for (SanPham sanPham : sanPhams) {
			SanPhamDTO dto = new SanPhamDTO();
			Date someDate = sanPham.getNgayNhap();
			dto.setMaSP(sanPham.getMaSP());
			dto.setTenSP(sanPham.getTenSP());
			dto.setGiaBan(sanPham.getGiaBan());
			dto.setSoLuong(sanPham.getSoLuong());
			dto.setLoaiSanPham(sanPham.getLoaiSanPham().getTenLoaiSP());
			dto.setMaLoaiSP(sanPham.getLoaiSanPham().getMaLoaiSP());
			dto.setNgayNhap(someDate);
			sanPhamDTOs.add(dto);
		}
		return sanPhamDTOs;
	}

	public SanPhamDTO convertToDTO(SanPham sanPham) {
		SanPhamDTO dto = new SanPhamDTO();
		Date someDate = sanPham.getNgayNhap();
		dto.setMaSP(sanPham.getMaSP());
		dto.setTenSP(sanPham.getTenSP());
		dto.setGiaBan(sanPham.getGiaBan());
		dto.setSoLuong(sanPham.getSoLuong());
		dto.setLoaiSanPham(sanPham.getLoaiSanPham().getTenLoaiSP());
		dto.setMaLoaiSP(sanPham.getLoaiSanPham().getMaLoaiSP());
		dto.setNgayNhap(someDate);
		return dto;
	}

}
