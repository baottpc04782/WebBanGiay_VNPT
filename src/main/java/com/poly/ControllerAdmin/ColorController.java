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

import com.poly.entity.Color;
import com.poly.services.ColorService;




@Controller
@RequestMapping("/admin")
public class ColorController {
	@Autowired
	ColorService colorService;
	
	
	
	
	// field du lieu len table
			@GetMapping("/color")
			public String showColorList(Model model, @RequestParam("p") Optional<Integer> p) {
				Pageable pageable = PageRequest.of(p.orElse(0), 5);
				Page<Color> pages = colorService.findAll(pageable);
				model.addAttribute("pages", pages);
				model.addAttribute("currentPage", p.orElse(0));
				model.addAttribute("color", new Color()); // Thêm một category mới cho form
				return "/views/admin/color";
			}

			// chuc nang insert
			@PostMapping("/savecolor")
			public String createColor(@ModelAttribute @Validated Color color, BindingResult bindingResult, Model model) {
				if (bindingResult.hasErrors()) {
					// Thêm category vào model để hiển thị lại dữ liệu đã nhập
					model.addAttribute("color", color);
					// Thêm danh sách danh mục (pages) vào model để giữ nguyên dữ liệu dưới bảng
					Page<Color> pages = colorService.findAll(PageRequest.of(0, 5));
					model.addAttribute("pages", pages);
					model.addAttribute("currentPage", 0);
					return "/views/admin/color";
				}
				   // Xử lý logic khi không có lỗi
				colorService.create(color);

				return "redirect:/admin/color";
			}
			@GetMapping("/editt/{colorId}")
			public String showUpdateForm(@PathVariable("colorId") Integer colorId, Model model) {
				// Lấy category cần sửa
				Color color = colorService.findById(colorId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid color Id:" + colorId));
				// Lấy danh sách categories để giữ nguyên dữ liệu dưới bảng
				Page<Color> pages = colorService.findAll(PageRequest.of(0, 5));
				// Thêm category và danh sách categories vào model
				model.addAttribute("color", color);
				model.addAttribute("pages", pages);
				model.addAttribute("currentPage", 0);

				return "/views/admin/color";
			}
			// chuc nang delete
			@GetMapping("/deleteColor/{colorId}")
			public String deleteColor(@PathVariable("colorId") Integer colorId, Model model) {
				Color color = colorService.findById(colorId)
						.orElseThrow(() -> new IllegalArgumentException("Invalid color Id:" + colorId));
				colorService.delete(color);
				return "redirect:/admin/color";
			}
			
			@GetMapping("/searchColor")
			public String searchColors(@RequestParam(value = "keyword", required = false) String keyword,
			                           @RequestParam("p") Optional<Integer> p, Model model) {
			    try {
			        // Xác định trang và số lượng phần tử trên mỗi trang
			        Pageable pageable = PageRequest.of(p.orElse(0), 5);
			        
			        // Biến để lưu trữ kết quả tìm kiếm
			        Page<Color> pages;

			        // Kiểm tra xem có từ khóa tìm kiếm không
			        if (keyword != null && !keyword.isEmpty()) {
			            // Nếu có từ khóa, thực hiện tìm kiếm dựa trên từ khóa
			            pages = colorService.search(keyword, pageable);
			        } else {
			            // Nếu không có từ khóa, lấy tất cả các màu sắc
			            pages = colorService.findAll(pageable);
			        }

			        // Thêm các thuộc tính vào Model để truyền thông tin đến giao diện người dùng
			        model.addAttribute("pages", pages);
			        model.addAttribute("currentPage", p.orElse(0));
			        model.addAttribute("color", new Color());
			        model.addAttribute("keyword", keyword);

			        // Trả về tên của trang để render kết quả
			        return "/views/admin/color";
			    } catch (Exception e) {
			        // Nếu có lỗi, thêm thông báo lỗi vào Model để hiển thị trên giao diện người dùng
			        model.addAttribute("error", "Error occurred during color search: " + e.getMessage());
			        
			        // Trả về trang lỗi để render thông báo lỗi
			        return "/views/admin/error";
			    }
			}


			
//			// search
//			@GetMapping("/searchColor")
//			public String searchCategories(@RequestParam(value = "keyword", required = false) String keyword,
//					@RequestParam("p") Optional<Integer> p, Model model) {
//				Pageable pageable = PageRequest.of(p.orElse(0), 5);
//				Page<Color> pages;
//				
//				if (keyword != null && !keyword.isEmpty()) {
//					pages = colorService.search(keyword, pageable);
//				} else {
//					pages = colorService.findAll(pageable);
//				}
//				
//				model.addAttribute("pages", pages);
//				model.addAttribute("currentPage", p.orElse(0));
//				model.addAttribute("color", new Color());
//
//				return "/views/admin/color";
//			}
	
}
