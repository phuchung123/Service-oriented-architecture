package com.example.QuanLiDonHang.DTO;

public class ChiTietDonHangDTO {
	private Long maDonHang;
	private String tenKhachHang;
	private Long maSanPham;
	private Long maChiTietDonHang;
	private String tenSanPham;
	private Integer soLuong;
	private Double tongGia;
	private String trangThai;

	// Getters and Setters
	public Long getMaDonHang() {
		return maDonHang;
	}

	public void setMaDonHang(Long maDonHang) {
		this.maDonHang = maDonHang;
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public Long getMaChiTietDonHang() {
		return maChiTietDonHang;
	}

	public void setMaChiTietDonHang(Long maChiTietDonHang) {
		this.maChiTietDonHang = maChiTietDonHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}

	public Long getMaSanPham() {
		return maSanPham;
	}

	public void setMaSanPham(Long maSanPham) {
		this.maSanPham = maSanPham;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public Integer getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(Integer soLuong) {
		this.soLuong = soLuong;
	}

	public Double getTongGia() {
		return tongGia;
	}

	public void setTongGia(Double tongGia) {
		this.tongGia = tongGia;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
}
