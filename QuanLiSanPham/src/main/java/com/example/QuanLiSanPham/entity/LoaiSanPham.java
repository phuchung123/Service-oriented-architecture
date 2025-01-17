package com.example.QuanLiSanPham.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "loaisanpham")
public class LoaiSanPham {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "maLoaiSP")
	private Long maLoaiSP; // Mã loại sản phẩm

	@Column(name = "tenLoaiSP")
	private String tenLoaiSP; // Tên loại sản phẩm

	@OneToMany(mappedBy = "loaiSanPham", cascade = CascadeType.ALL)
	private List<SanPham> sanPhams; // Danh sách sản phẩm thuộc loại này

	// Getters and Setters
	public Long getMaLoaiSP() {
		return maLoaiSP;
	}

	public void setMaLoaiSP(Long maLoaiSP) {
		this.maLoaiSP = maLoaiSP;
	}

	public String getTenLoaiSP() {
		return tenLoaiSP;
	}

	public void setTenLoaiSP(String tenLoaiSP) {
		this.tenLoaiSP = tenLoaiSP;
	}

	public List<SanPham> getSanPhams() {
		return sanPhams;
	}

	public void setSanPhams(List<SanPham> sanPhams) {
		this.sanPhams = sanPhams;
	}
}