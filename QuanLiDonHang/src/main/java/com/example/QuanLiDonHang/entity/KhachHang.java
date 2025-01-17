package com.example.QuanLiDonHang.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "khachhang")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maKH")
    private Long maKH;

    @Column(name = "tenKH")
    private String tenKH;

    @Column(name = "ngaySinh")
    private Date ngaySinh;

    @Column(name = "email")
    private String email;

    @Column(name = "diaChi")
    private String diaChi;

    public KhachHang(Long maKH) {
        this.maKH = maKH;
    }

    public KhachHang() {
    }
    
    // Getters and Setters
    public Long getMaKH() {
        return maKH;
    }

    public void setMaKH(Long maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
