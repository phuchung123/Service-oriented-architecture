package com.example.QuanLiDonHang.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.QuanLiDonHang.DTO.ChiTietDonHangDTO;
import com.example.QuanLiDonHang.DTO.SanPhamDTO;
import com.example.QuanLiDonHang.entity.DonHang;
import com.example.QuanLiDonHang.entity.KhachHang;
import com.example.QuanLiDonHang.service.DonHangService;

@Controller
@CrossOrigin(origins = "*")
public class DonHangController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${sanpham.api.url}")
    private String sanPhamApiUrl;
    
    @Autowired
    private DonHangService donHangService;
    
    @GetMapping("/donhang/add")
    public String showAddDonHangForm(Model model) {
        try {
            // Gọi API để lấy danh sách sản phẩm
            List<SanPhamDTO> sanPhams = restTemplate.getForObject(sanPhamApiUrl, List.class);
            List<KhachHang> khachHangs = donHangService.getAllKhachHangs();
            model.addAttribute("sanPhams", sanPhams);
            model.addAttribute("khachHangs", khachHangs);
        } catch (RestClientException e) {
            model.addAttribute("error", "Không thể lấy dữ liệu từ API sản phẩm.");
            e.printStackTrace();
        }
        return "addDonHang";
    }

    @PostMapping("/donhang/save")
    public String saveDonHang(@RequestParam Long maSP, @RequestParam Integer soLuong,
			@RequestParam Long maKH, Model model) throws ParseException {
    	donHangService.saveDonHang(maSP, soLuong, maKH);
        return "ThanhCong";
    }
    
    @GetMapping("/donhang/danhsach")
    public String hienThiDanhSachChiTietDonHang(Model model) {
        List<ChiTietDonHangDTO> danhSachChiTiet = donHangService.getDanhSachChiTietDonHang();
        model.addAttribute("danhSachChiTiet", danhSachChiTiet);
        return "ChiTietDonHang";
    }

    @PostMapping("/donhang/update-trang-thai")
    public String updateTrangThaiDonHang(@RequestParam Long maDonHang, @RequestParam String trangThaiMoi, RedirectAttributes redirectAttributes) {
        try {
            donHangService.updateTrangThaiDonHang(maDonHang, trangThaiMoi);
            // Sử dụng flash attribute để thêm thông báo thành công
            redirectAttributes.addFlashAttribute("message", "Cập nhật trạng thái đơn hàng thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            // Thêm thông báo lỗi vào flash attribute
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi cập nhật trạng thái đơn hàng.");
        }
        return "redirect:/donhang/danhsach"; // redirect lại để hiển thị thông báo
    }

    @PostMapping("/donhang/xoa-chi-tiet/{maChiTiet}")
    public String xoaChiTietDonHang(@PathVariable Long maChiTiet, Model model, RedirectAttributes redirectAttributes) {
        try {
            donHangService.xoaChiTietDonHang(maChiTiet);
            redirectAttributes.addFlashAttribute("message", "Cập nhật trạng thái đơn hàng thành công!");
        } catch (Exception e) {
        	redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi cập nhật trạng thái đơn hàng.");
            e.printStackTrace();
        }
        return "redirect:/donhang/danhsach";
    }
    
    @GetMapping("/donhang/tim-kiem")
    public String timKiemChiTietDonHang(@RequestParam(required = false) Long maDH,
                                         @RequestParam(required = false) String tenKH,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayTaoFrom,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayTaoTo,
                                         Model model) {

        // Gọi phương thức tìm kiếm trong service để lấy danh sách chi tiết đơn hàng
        List<DonHang> donHangs = donHangService.searchDonHangs(maDH, tenKH, ngayTaoFrom, ngayTaoTo);

        // Thêm kết quả vào model để gửi đến view
        model.addAttribute("donHangs", donHangs);

        // Nếu không tìm thấy kết quả, thông báo cho người dùng
        if (donHangs.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy đơn hàng nào khớp với tiêu chí tìm kiếm.");
        }

        return "TimKiemDonHang"; // Trả về view kết quả tìm kiếm
    }


	@GetMapping("/donhang/view-tim-kiem")
	public String showSearchForm() {
		return "TimKiemDonHang";
	}
}
