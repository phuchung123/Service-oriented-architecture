package com.example.QuanLiSanPham.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sanpham")
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maSP")
    private Long maSP; 

    @Column(name = "tenSP")
    private String tenSP;

    @ManyToOne
    @JoinColumn(name = "maLoaiSP", referencedColumnName = "maLoaiSP")
    private LoaiSanPham loaiSanPham;

    @Column(name = "soLuong")
    private Integer soLuong;

    @Column(name = "giaBan")
    private Double giaBan;

    @Column(name = "ngayNhap")
    private Date ngayNhap;

    @Column(name = "ngayXoa")
    private Date ngayXoa;
    
    public Long getMaSP() {
        return maSP;
    }

    public void setMaSP(Long maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public LoaiSanPham getLoaiSanPham() {
        return loaiSanPham;
    }

    public void setLoaiSanPham(LoaiSanPham loaiSanPham) {
        this.loaiSanPham = loaiSanPham;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Double giaBan) {
        this.giaBan = giaBan;
    }

	public Date getNgayXoa() {
		return ngayXoa;
	}

	public void setNgayXoa(Date ngayXoa) {
		this.ngayXoa = ngayXoa;
	}

	public Date getNgayNhap() {
		return ngayNhap;
	}

	public void setNgayNhap(Date ngayNhap) {
		this.ngayNhap = ngayNhap;
	}
}
