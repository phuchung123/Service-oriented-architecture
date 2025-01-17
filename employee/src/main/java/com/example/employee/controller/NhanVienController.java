package com.example.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.employee.model.NhanVien;
import com.example.employee.model.TaiKhoan;
import com.example.employee.service.NhanVienService;
import com.example.employee.service.TaiKhoanService;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;
    
    @Autowired
    private TaiKhoanService taiKhoanService;

    @GetMapping
    public String viewHomePage(Model model) {
        List<NhanVien> listNhanVien = nhanVienService.getAllNhanViens();
        model.addAttribute("listNhanVien", listNhanVien);
        return "index";
    }
    
    @GetMapping("/search")
    public String searchNhanVien(@RequestParam("keyword") String keyword, Model model) {
        List<NhanVien> listNhanVien = nhanVienService.searchNhanVienByName(keyword);
        model.addAttribute("listNhanVien", listNhanVien);
        return "index";
    }
    
    @GetMapping("/showNewNhanVienForm")
    public String showNewNhanVienForm(Model model) {
        NhanVien nhanVien = new NhanVien();
        model.addAttribute("nhanVien", nhanVien);
        return "new_nhanvien";
    }

    @PostMapping("/saveNhanVien")
    public String saveNhanVien(@ModelAttribute("nhanVien") NhanVien nhanVien, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "new_nhanvien";
        }

        boolean hasErrors = false;

        // Kiểm tra căn cước và số điện thoại
        if (nhanVienService.existsByCanCuoc(nhanVien.getCanCuoc())) {
            model.addAttribute("canCuocError", "Căn cước đã tồn tại");
            hasErrors = true;
        }
        if (nhanVienService.existsBySoDT(nhanVien.getSoDT())) {
            model.addAttribute("soDTError", "Số điện thoại đã tồn tại");
            hasErrors = true;
        }
        if (nhanVienService.existsByEmail(nhanVien.getEmail())) {
            model.addAttribute("emailError", "Email đã tồn tại");
            hasErrors = true;
        }

        if (hasErrors) {
            return "new_nhanvien";
        }
        
        try {

            // Tạo tên tài khoản từ số điện thoại
            String username = nhanVien.getSoDT();

            // Tạo mật khẩu từ ngày sinh (chỉ lấy các số yyyyMMdd)
            String dateStr = nhanVien.getNgaySinh().toString();
            String year = dateStr.substring(0, 4);
            String month = dateStr.substring(5, 7);
            String day = dateStr.substring(8, 10);
            String password = year + month + day;

            // Tạo đối tượng TaiKhoan và lưu vào cơ sở dữ liệu
            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setTenTK(username);
            taiKhoan.setMatKhau(password);
            taiKhoan.setVaiTro("USER");
            taiKhoanService.saveTaiKhoan(taiKhoan);

            // Gán tài khoản cho nhân viên
            nhanVien.setTaiKhoan(taiKhoan);

            // Lưu nhân viên
            nhanVienService.saveNhanVien(nhanVien);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "new_nhanvien";
        }
        return "redirect:/nhanvien";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        NhanVien nhanVien = nhanVienService.getNhanVienById(id);
        model.addAttribute("nhanVien", nhanVien);
        return "update_nhanvien";
    }
    
    @PostMapping("/updateNhanVien")
    public String updateNhanVien(@ModelAttribute("nhanVien") NhanVien nhanVien, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "update_nhanvien";
        }

        try {
            // Kiểm tra sự tồn tại của nhân viên
            NhanVien existingNhanVien = nhanVienService.getNhanVienById(nhanVien.getMaNV());
            if (existingNhanVien == null) {
                model.addAttribute("errorMessage", "Nhân viên không tồn tại");
                return "update_nhanvien";
            }

            // Kiểm tra cập nhật số điện thoại và ngày sinh
            boolean phoneChanged = !existingNhanVien.getSoDT().equals(nhanVien.getSoDT());
            boolean birthDateChanged = !existingNhanVien.getNgaySinh().equals(nhanVien.getNgaySinh());

            // Cập nhật thông tin nhân viên
            existingNhanVien.setTenNV(nhanVien.getTenNV());
            existingNhanVien.setNgaySinh(nhanVien.getNgaySinh());
            existingNhanVien.setGioiTinh(nhanVien.getGioiTinh());
            existingNhanVien.setEmail(nhanVien.getEmail());
            existingNhanVien.setDiaChi(nhanVien.getDiaChi());
            existingNhanVien.setCanCuoc(nhanVien.getCanCuoc());
            existingNhanVien.setSoDT(nhanVien.getSoDT());

            // Cập nhật tên tài khoản và mật khẩu nếu số điện thoại hoặc ngày sinh thay đổi
            if (phoneChanged || birthDateChanged) {
                String username = nhanVien.getSoDT();
                String dateStr = nhanVien.getNgaySinh().toString();
                String year = dateStr.substring(0, 4);
                String month = dateStr.substring(5, 7);
                String day = dateStr.substring(8, 10);
                String password = year + month + day;

                TaiKhoan taiKhoan = existingNhanVien.getTaiKhoan();
                taiKhoan.setTenTK(username);
                taiKhoan.setMatKhau(password);
                taiKhoanService.saveTaiKhoan(taiKhoan);
            }

            // Lưu nhân viên đã cập nhật
            nhanVienService.saveNhanVien(existingNhanVien);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "update_nhanvien";
        }
        return "redirect:/nhanvien";
    }

    @GetMapping("/deleteNhanVien/{id}")
    public String deleteNhanVien(@PathVariable(value = "id") long id) {
        nhanVienService.deleteNhanVien(id);
        return "redirect:/nhanvien";
    }
}