package com.example.employee.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "nhanvien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maNV;

    @ManyToOne
    @JoinColumn(name = "maTK")
    private TaiKhoan taiKhoan;

    private String tenNV;
    private String canCuoc;
    private String soDT;
    private String ngaySinh;
    private String gioiTinh;
    private String email;
    private String diaChi;
    
	public Long getMaNV() {
		return maNV;
	}
	public void setMaNV(Long maNV) {
		this.maNV = maNV;
	}
	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}
	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
	public String getTenNV() {
		return tenNV;
	}
	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}
	public String getCanCuoc() {
		return canCuoc;
	}
	public void setCanCuoc(String canCuoc) {
		this.canCuoc = canCuoc;
	}
	public String getSoDT() {
		return soDT;
	}
	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}
	public String getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(String ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
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