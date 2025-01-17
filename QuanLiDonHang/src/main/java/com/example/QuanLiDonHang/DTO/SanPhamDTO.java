package com.example.QuanLiDonHang.DTO;

import java.util.Date;

public class SanPhamDTO {
    private Long maSP; // Id của sản phẩm
    private String tenSP; // Tên sản phẩm
    private Long maLoaiSP; // Id của loại sản phẩm
    private Integer soLuong; // Số lượng
    private Double giaBan; // Giá bán
    private Date ngayNhap; // Ngày nhập
    private String loaiSanPham; 

    // Getter và Setter cho maSP (Id)
    public Long getMaSP() {
        return maSP;
    }

    public void setMaSP(Long maSP) {
        this.maSP = maSP;
    }

    // Getter và Setter cho tenSP
    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    // Getter và Setter cho maLoaiSP
    public Long getMaLoaiSP() {
        return maLoaiSP;
    }

    public void setMaLoaiSP(Long maLoaiSP) {
        this.maLoaiSP = maLoaiSP;
    }

    // Getter và Setter cho soLuong
    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    // Getter và Setter cho giaBan
    public Double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Double giaBan) {
        this.giaBan = giaBan;
    }

    // Getter và Setter cho ngayNhap
    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    // Getter và Setter cho loaiSanPham
    public String getLoaiSanPham() {
        return loaiSanPham;
    }

    public void setLoaiSanPham(String loaiSanPham) {
        this.loaiSanPham = loaiSanPham;
    }
}
