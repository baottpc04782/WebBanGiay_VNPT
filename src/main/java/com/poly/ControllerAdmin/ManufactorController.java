package com.poly.ControllerAdmin;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.entity.Manufactor;
import com.poly.services.ManufactorService;

@Controller
@RequestMapping("/admin")
public class ManufactorController {

	@Autowired
	ManufactorService manufactorService;
	
	
	
	
	// field du lieu len table
			@GetMapping("/manufactor")
			public String showList(Model model, @RequestParam("p") Optional<Integer> p) {
				Pageable pageable = PageRequest.of(p.orElse(0), 5);
				Page<Manufactor> pages = manufactorService.findAll(pageable);
				model.addAttribute("pages", pages);
				model.addAttribute("currentPage", p.orElse(0));
				model.addAttribute("manufactor", new Manufactor()); // Thêm một manufactor mới cho form
				return "views/admin/manufactor";
			}

			// chuc nang insert
			@PostMapping("/savemanufactor")
			public String createManufactor(@ModelAttribute @Validated Manufactor manufactor, BindingResult bindingResult, Model model) {
				if (bindingResult.hasErrors()) {
					// Thêm category vào model để hiển thị lại dữ liệu đã nhập
					model.addAttribute("manufactor", manufactor);
					// Thêm danh sách danh mục (pages) vào model để giữ nguyên dữ liệu dưới bảng
					Page<Manufactor> pages = manufactorService.findAll(PageRequest.of(0, 5));
					model.addAttribute("pages", pages);
					model.addAttribute("currentPage", 0);
					return "/views/admin/manufactor";
				}
				   // Xử lý logic khi không có lỗi
				manufactorService.create(manufactor);

				return "redirect:/admin/manufactor";
			}
			@GetMapping("/editManufactor/{manufactorId}")
			public String showUpdateForm(@PathVariable("manufactorId") Integer manufactorId, Model model) {
				// Lấy category cần sửa
				Manufactor manufactor = manufactorService.findById(manufactorId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid manufactor Id:" + manufactorId));
				// Lấy danh sách categories để giữ nguyên dữ liệu dưới bảng
				Page<Manufactor> pages = manufactorService.findAll(PageRequest.of(0, 5));
				// Thêm category và danh sách categories vào model
				model.addAttribute("manufactor", manufactor);
				model.addAttribute("pages", pages);
				model.addAttribute("currentPage", 0);

				return "/views/admin/manufactor";
			}
			// chuc nang delete
			@GetMapping("/deleteManufactor/{manufactorId}")
			public String deleteManufactor(@PathVariable("manufactorId") Integer manufactorId, Model model) {
				Manufactor manufactor = manufactorService.findById(manufactorId)
						.orElseThrow(() -> new IllegalArgumentException("Invalid manufactor Id:" + manufactorId));
				manufactorService.delete(manufactor);
				return "redirect:/admin/manufactor";
			}
			
			@GetMapping("/searchManufactor")
			public String searchManufactor(@RequestParam(value = "keyword", required = false) String keyword,
			                               @RequestParam("p") Optional<Integer> p, Model model) {
			    try {
			        // Xác định trang và số lượng phần tử trên mỗi trang
			        Pageable pageable = PageRequest.of(p.orElse(0), 5);
			        
			        // Biến để lưu trữ kết quả tìm kiếm
			        Page<Manufactor> pages;

			        // Kiểm tra xem có từ khóa tìm kiếm không
			        if (keyword != null && !keyword.isEmpty()) {
			            // Nếu có từ khóa, thực hiện tìm kiếm dựa trên từ khóa
			            pages = manufactorService.search(keyword, pageable);
			        } else {
			            // Nếu không có từ khóa, lấy tất cả các nhà sản xuất
			            pages = manufactorService.findAll(pageable);
			        }

			        // Thêm các thuộc tính vào Model để truyền thông tin đến giao diện người dùng
			        model.addAttribute("pages", pages);
			        model.addAttribute("currentPage", p.orElse(0));
			        model.addAttribute("manufactor", new Manufactor());
			        model.addAttribute("keyword", keyword);

			        // Trả về tên của trang để render kết quả
			        return "/views/admin/manufactor";
			    } catch (Exception e) {
			        // Nếu có lỗi, thêm thông báo lỗi vào Model để hiển thị trên giao diện người dùng
			        model.addAttribute("error", "Error occurred during  search: " + e.getMessage());
			        
			        // Trả về tên của trang để render thông báo lỗi
			        return "/views/admin/manufactor";
			    }
			}


	
}
