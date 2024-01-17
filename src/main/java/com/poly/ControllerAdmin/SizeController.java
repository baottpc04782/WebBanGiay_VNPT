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
import com.poly.entity.Size;
import com.poly.services.SizeServies;




@Controller
@RequestMapping("/admin")
public class SizeController {
	@Autowired
	SizeServies sizeService;
			
	// field du lieu len table
			@GetMapping("/size")
			public String showSizeList(Model model, @RequestParam("p") Optional<Integer> p) {
				Pageable pageable = PageRequest.of(p.orElse(0), 5);
				Page<Size> pages = sizeService.findAll(pageable);
				model.addAttribute("pages", pages);
				model.addAttribute("currentPage", p.orElse(0));
				model.addAttribute("size", new Size()); // Thêm một size mới cho form
				return "/views/admin/size";
			}

			// chuc nang insert
						@PostMapping("/savesize")
						public String createSize(@ModelAttribute @Validated Size size, BindingResult bindingResult, Model model) {
							if (bindingResult.hasErrors()) {
								// Thêm category vào model để hiển thị lại dữ liệu đã nhập
								model.addAttribute("size", size);
								// Thêm danh sách danh mục (pages) vào model để giữ nguyên dữ liệu dưới bảng
								Page<Size> pages = sizeService.findAll(PageRequest.of(0, 5));
								model.addAttribute("pages", pages);
								model.addAttribute("currentPage", 0);
								return "/views/admin/size";
							}
							   // Xử lý logic khi không có lỗi
							sizeService.create(size);
							return "redirect:/admin/size";
						}
						
						@GetMapping("/editsize/{sizeId}")
						public String showUpdateForm(@PathVariable("sizeId") Integer sizeId, Model model) {
							// Lấy category cần sửa
							Size size = sizeService.findById(sizeId)
							.orElseThrow(() -> new IllegalArgumentException("Invalid color Id:" + sizeId));
							// Lấy danh sách categories để giữ nguyên dữ liệu dưới bảng
							Page<Size> pages = sizeService.findAll(PageRequest.of(0, 5));
							// Thêm category và danh sách categories vào model
							model.addAttribute("size", size);
							model.addAttribute("pages", pages);
							model.addAttribute("currentPage", 0);

							return "/views/admin/size";
						}
						
						// chuc nang delete
						@GetMapping("/deleteSize/{sizeId}")
						public String deleteSize(@PathVariable("sizeId") Integer sizeId, Model model) {
							Size size = sizeService.findById(sizeId)
									.orElseThrow(() -> new IllegalArgumentException("Invalid size Id:" + sizeId));
							sizeService.delete(size);
							return "redirect:/admin/size";
						}

						// Tìm kiếm kích thước (Size)
						@GetMapping("/searchSize")
						public String searchCategories(@RequestParam(value = "keyword", required = false) String keyword,
						        @RequestParam("p") Optional<Integer> p, Model model) {
						    // Xác định trang và số lượng phần tử trên mỗi trang
						    Pageable pageable = PageRequest.of(p.orElse(0), 5);
						    Page<Size> pages;

						    try {
						        // Kiểm tra xem có từ khóa tìm kiếm không
						        if (keyword != null && !keyword.isEmpty()) {
						            // Thử chuyển từ khóa thành số nguyên (nếu là số)
						            Integer name = Integer.parseInt(keyword);
						            // Thực hiện tìm kiếm theo số nguyên
						            pages = sizeService.search(name, pageable);
						        } else {
						            // Nếu không có từ khóa, lấy tất cả các kích thước
						            pages = sizeService.findAll(pageable);
						        }

						        // Thêm các thuộc tính vào Model để truyền thông tin đến giao diện người dùng
						        model.addAttribute("pages", pages);
						        model.addAttribute("currentPage", p.orElse(0));
						        model.addAttribute("size", new Size());

						        // Trả về tên trang để render kết quả
						        return "views/admin/size";
						    } catch (NumberFormatException e) {
						        // Xử lý khi không thể chuyển từ khóa thành số nguyên
						        // Không chuyển hướng đến trang lỗi, chỉ thông báo lỗi trong log hoặc nơi khác
						        e.printStackTrace();
						        return "views/admin/size";
						    }
						}



						
}
