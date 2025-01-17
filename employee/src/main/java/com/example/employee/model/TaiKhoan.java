package com.example.employee.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "taikhoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maTK;

    private String tenTK;
    private String matKhau;
    private String vaiTro;

    @OneToMany(mappedBy = "taiKhoan")
    private List<NhanVien> nhanViens;

	public Long getMaTK() {
		return maTK;
	}

	public void setMaTK(Long maTK) {
		this.maTK = maTK;
	}

	public String getTenTK() {
		return tenTK;
	}

	public void setTenTK(String tenTK) {
		this.tenTK = tenTK;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public String getVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(String vaiTro) {
		this.vaiTro = vaiTro;
	}

	public List<NhanVien> getNhanViens() {
		return nhanViens;
	}

	public void setNhanViens(List<NhanVien> nhanViens) {
		this.nhanViens = nhanViens;
	}
}