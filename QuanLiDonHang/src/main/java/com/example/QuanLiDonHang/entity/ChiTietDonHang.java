package com.example.QuanLiDonHang.entity;

import jakarta.persistence.*;
import java.util.Date;


@Entity
@Table(name = "chitietdonhang")
public class ChiTietDonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maCTDH")
    private Long maCTDH;

    @ManyToOne
    @JoinColumn(name = "maDH", referencedColumnName = "maDH")
    private DonHang donHang;

	private Long maSanPham; 
	
    @Column(name = "soLuong")
    private Integer soLuong;

    @Column(name = "tongGia")
    private Double tongGia;

    @Column(name = "ngayXoa")
    private Date ngayXoa;

    // Getters and Setters
    public Long getMaCTDH() {
        return maCTDH;
    }

    public void setMaCTDH(Long maCTDH) {
        this.maCTDH = maCTDH;
    }

    public DonHang getDonHang() {
        return donHang;
    }

    public void setDonHang(DonHang donHang) {
        this.donHang = donHang;
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

    public Date getNgayXoa() {
        return ngayXoa;
    }

    public void setNgayXoa(Date ngayXoa) {
        this.ngayXoa = ngayXoa;
    }

	public Long getMaSanPham() {
		return maSanPham;
	}

	public void setMaSanPham(Long maSanPham) {
		this.maSanPham = maSanPham;
	}
    
}
