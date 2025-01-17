package com.example.QuanLiSanPham.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.QuanLiSanPham.DTO.SanPhamDTO;
import com.example.QuanLiSanPham.entity.LoaiSanPham;
import com.example.QuanLiSanPham.entity.SanPham;
import com.example.QuanLiSanPham.service.LoaiSanPhamService;
import com.example.QuanLiSanPham.service.SanPhamService;

@Controller
@RequestMapping("/api/sanpham")
public class SanPhamController {
    @Autowired
    private SanPhamService sanPhamService;
    
    @Autowired
    private LoaiSanPhamService loaiSanPhamService;
    
    @Value("${donhang.api.url}")
    private String sanPhamApiUrl;
    
    @PostMapping("/add")
    public String addSanPham(@ModelAttribute SanPhamDTO sanPhamDTO) throws ParseException {
        // Tìm LoaiSanPham từ maLoaiSP
        LoaiSanPham loaiSanPham = loaiSanPhamService.getLoaiSanPhamById(sanPhamDTO.getMaLoaiSP());
        if (loaiSanPham == null) {
            throw new IllegalArgumentException("Loại sản phẩm không tồn tại");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(new Date());
        Date date = sdf.parse(formattedDate);
        // Tạo SanPham từ DTO
        SanPham sanPham = new SanPham();
        sanPham.setTenSP(sanPhamDTO.getTenSP());
        sanPham.setLoaiSanPham(loaiSanPham);
        sanPham.setSoLuong(sanPhamDTO.getSoLuong());
        sanPham.setNgayNhap(date);
        sanPham.setGiaBan(sanPhamDTO.getGiaBan());

        sanPhamService.addSanPham(sanPham);
        return "ThanhCong";
    }


    @GetMapping("/add-view")
    public String showCreateSanPhamForm(Model model) {
        model.addAttribute("loaiSanPhams", loaiSanPhamService.getAllLoaiSanPhams());
        return "ThemSanPham";
    }
    
    @GetMapping("/list")
    public String listSanPhams(Model model) {
        List<SanPham> sanPhams = sanPhamService.getAllSanPhamsNotDeleted();
        model.addAttribute("sanPhams", sanPhams);
        return "DanhSachSanPham";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        SanPham sanPham = sanPhamService.getSanPhamById(id);
        if (sanPham == null) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại");
        }
        
        model.addAttribute("sanPham", sanPham);
        model.addAttribute("loaiSanPhams", loaiSanPhamService.getAllLoaiSanPhams());
        return "ChinhSuaSanPham";
    }

    @PostMapping("/edit/{id}")
    public String updateSanPham(@PathVariable("id") Long id, @ModelAttribute SanPhamDTO sanPhamDTO) {
        SanPham sanPham = sanPhamService.getSanPhamById(id);
        if (sanPham == null) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại");
        }

        LoaiSanPham loaiSanPham = loaiSanPhamService.getLoaiSanPhamById(sanPhamDTO.getMaLoaiSP());
        if (loaiSanPham == null) {
            throw new IllegalArgumentException("Loại sản phẩm không tồn tại");
        }
        
        sanPham.setTenSP(sanPhamDTO.getTenSP());
        sanPham.setLoaiSanPham(loaiSanPham);
        sanPham.setSoLuong(sanPhamDTO.getSoLuong());
        sanPham.setGiaBan(sanPhamDTO.getGiaBan());

        sanPhamService.updateSanPham(sanPham);
        return "redirect:/api/sanpham/list";
    }

    @PostMapping("/delete")
    public String deleteSanPham(@RequestParam("maSP") Long maSP) throws ParseException {
        sanPhamService.deleteSanPham(maSP);
        return "redirect:/api/sanpham/list"; // Điều hướng về trang danh sách sau khi xóa
    }

    @GetMapping("/search")
    public String searchSanPham(@RequestParam(value = "tenSP", required = false) String tenSP,
                                 @RequestParam(value = "maLoaiSP", required = false) Long maLoaiSP,
                                 @RequestParam(value = "giaBanFrom", required = false) Double giaBanFrom,
                                 @RequestParam(value = "giaBanTo", required = false) Double giaBanTo,
                                 @RequestParam(value = "ngayNhapFrom", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayNhapFrom,
                                 @RequestParam(value = "ngayNhapTo", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayNhapTo,
                                 Model model) {
        List<SanPham> sanPhams = sanPhamService.searchSanPham(tenSP, maLoaiSP, giaBanFrom, giaBanTo, ngayNhapFrom, ngayNhapTo);
        
        model.addAttribute("sanPhams", sanPhams);
        model.addAttribute("loaiSanPhams", loaiSanPhamService.getAllLoaiSanPhams());
        return "TimKiemSanPham"; 
    }

    @GetMapping("/search-view")
    public String showSearchForm(Model model) {
        model.addAttribute("loaiSanPhams", loaiSanPhamService.getAllLoaiSanPhams());
        return "TimKiemSanPham"; 
    }
}
