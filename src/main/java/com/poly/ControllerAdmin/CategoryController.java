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


import com.poly.entity.Categories;
import com.poly.entity.Color;
import com.poly.services.CategoryService;



@Controller
@RequestMapping("/admin")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	// field du lieu len table
	@GetMapping("/category")
	public String showCategoryList(Model model, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Categories> pages = categoryService.findAll(pageable);
		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", p.orElse(0));
		model.addAttribute("category", new Categories()); // Thêm một category mới cho form
		return "/views/admin/category";
	}
	// chuc nang insert
		@PostMapping("/savecategory")
		public String createCategory(@ModelAttribute @Validated Categories categories, BindingResult bindingResult, Model model) {
			if (bindingResult.hasErrors()) {
				// Thêm category vào model để hiển thị lại dữ liệu đã nhập
				model.addAttribute("categories", categories);
				// Thêm danh sách danh mục (pages) vào model để giữ nguyên dữ liệu dưới bảng
				Page<Categories> pages = categoryService.findAll(PageRequest.of(0, 5));
				model.addAttribute("pages", pages);
				model.addAttribute("currentPage", 0);
				return "/views/admin/category";
			}
			   // Xử lý logic khi không có lỗi
			   categoryService.create(categories);

			return "redirect:/admin/category";
		}

		@GetMapping("/edit/{categoryId}")
		public String showUpdateForm(@PathVariable("categoryId") Integer categoryId, Model model) {
			// Lấy category cần sửa
			Categories categories = categoryService.findById(categoryId)
			.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + categoryId));
			// Lấy danh sách categories để giữ nguyên dữ liệu dưới bảng
			Page<Categories> pages = categoryService.findAll(PageRequest.of(0, 5));
			// Thêm category và danh sách categories vào model
			model.addAttribute("category", categories);
			model.addAttribute("pages", pages);
			model.addAttribute("currentPage", 0);

			return "/views/admin/category";
		}
		// chuc nang delete
		@GetMapping("/delete/{categoryId}")
		public String deleteCategory(@PathVariable("categoryId") Integer categoryId, Model model) {
			Categories categories = categoryService.findById(categoryId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + categoryId));
			categoryService.delete(categories);
			return "redirect:/admin/category";
		}
		@GetMapping("/searchCate")
		public String searchCategories(@RequestParam(value = "keyword", required = false) String keyword,
		                               @RequestParam("p") Optional<Integer> p, Model model) {
		    try {
		        // Xác định trang và số lượng phần tử trên mỗi trang
		        Pageable pageable = PageRequest.of(p.orElse(0), 5);

		        // Biến để lưu trữ kết quả tìm kiếm
		        Page<Categories> pages;

		        // Kiểm tra xem có từ khóa tìm kiếm không
		        if (keyword != null && !keyword.isEmpty()) {
		            // Nếu có từ khóa, thực hiện tìm kiếm dựa trên từ khóa
		            pages = categoryService.search(keyword, pageable);
		        } else {
		            // Nếu không có từ khóa, lấy tất cả các danh mục
		            pages = categoryService.findAll(pageable);
		        }

		        // Thêm các thuộc tính vào Model để truyền thông tin đến giao diện người dùng
		        model.addAttribute("pages", pages);
		        model.addAttribute("currentPage", p.orElse(0));
		        model.addAttribute("category", new Categories());  // Thêm đối tượng Categories mới cho form
		        model.addAttribute("keyword", keyword);

		        // Trả về tên của trang để render kết quả
		        return "/views/admin/category";
		    } catch (Exception e) {
		        // Nếu có lỗi, thêm thông báo lỗi vào Model để hiển thị trên giao diện người dùng
		        model.addAttribute("error", "Error occurred during category search: " + e.getMessage());

		        // Trả về trang lỗi để render thông báo lỗi
		        return "/views/admin/error";
		    }
		}
}
