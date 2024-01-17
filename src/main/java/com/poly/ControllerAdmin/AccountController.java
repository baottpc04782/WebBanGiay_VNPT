package com.poly.ControllerAdmin;

import java.util.HashMap;
import java.util.Map;
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

import com.poly.entity.Accounts;
import com.poly.services.AccountService;



@Controller
@RequestMapping("/admin")
public class AccountController {
	@Autowired
	AccountService accountService;
	// Hiển thị trang quản lý tài khoản.
	@GetMapping("/account")
	public String showAccount(Accounts accounts, Model model, @RequestParam("p") Optional<Integer> p) {
	    // Tạo đối tượng Pageable để xác định trang và kích thước trang.
	    Pageable pageable = PageRequest.of(p.orElse(0), 5);

	    // Gọi phương thức findAll từ accountService để lấy dữ liệu tài khoản theo trang.
	    Page<Accounts> pages = accountService.findAll(pageable);

	    // Thêm dữ liệu vào Model để truyền đến View.
	    model.addAttribute("pages", pages);
	    model.addAttribute("currentPage", p.orElse(0));
	    model.addAttribute("accounts", accounts);

	    // Trả về tên trang view.
	    return "views/admin/account";
	}
	
	@GetMapping("/editaccount/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
		// Lấy category cần sửa
		Accounts account = accountService.findById(id);
		// Lấy danh sách categories để giữ nguyên dữ liệu dưới bảng
		Page<Accounts> pages = accountService.findAll(PageRequest.of(0, 5));
		// Thêm category và danh sách categories vào model
		model.addAttribute("accounts", account);
		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", 0);

		return "views/admin/account";
	}
	/*
	 * // chuc nang delete
	 * 
	 * @GetMapping("/deleteaccount/{username}") public String
	 * deleteAccount(@PathVariable("username") String username, Model model) { //
	 * Tìm kiếm một tài khoản theo username Accounts account =
	 * accountService.findByUsername(username) .orElseThrow(() -> new
	 * IllegalArgumentException("Invalid account Id:" + username));
	 * 
	 * // Xóa tài khoản được tìm thấy accountService.delete(account);
	 * 
	 * 
	 * 
	 * // Chuyển hướng về trang quản trị tài khoản return "redirect:/admin/account";
	 * }
	 */

	/*
	 * @ModelAttribute("genders") public Map<Boolean, String> getGenders(){
	 * Map<Boolean, String> map = new HashMap<>(); map.put(true,"Male");
	 * map.put(false,"Female"); return map; }
	 */
	@ModelAttribute("roles")
	public Map<Boolean, String> getRole() {
	    // Khởi tạo một đối tượng Map để chứa các giá trị boolean và string
	    Map<Boolean, String> map = new HashMap<>();

	    // Đặt giá trị true với mô tả "Hoạt động" vào Map
	    map.put(true, "Hoạt động");

	    // Đặt giá trị false với mô tả "Không hoạt động" vào Map
	    map.put(false, "Không hoạt động");

	    // Trả về Map đã được điền với các giá trị
	    return map;
	}


	// chuc nang insert
	
	 @PostMapping("/saveaccount")
		public String createAccount(@ModelAttribute @Validated Accounts accounts, BindingResult bindingResult, Model model) {
			if (bindingResult.hasErrors()) {
				// Thêm category vào model để hiển thị lại dữ liệu đã nhập
				
				model.addAttribute("accounts", accounts);
				// Thêm danh sách danh mục (pages) vào model để giữ nguyên dữ liệu dưới bảng
				Page<Accounts> pages = accountService.findAll(PageRequest.of(0, 5));
				model.addAttribute("pages", pages);
				model.addAttribute("currentPage", 0);
				return "/views/admin/account";
			}
			   // Xử lý logic khi không có lỗi
			accountService.create(accounts);

			return "redirect:/admin/account";
		}
	 //chuc nang delete
	 @GetMapping("/deleteaccount/{id}") 
	 public String deleteaccount(@PathVariable("id") String id, Model model) { 
		 Accounts account = accountService.findById(id); 
		 accountService.delete(account);
		 return "redirect:/admin/account"; 
	}
	 
	// chuc nang delete
	
		/*
		 * // chuc nang delete
		 * 
		 * @GetMapping("/deleteaccount/{id}") public String
		 * deleteaccount(@PathVariable("id") String id, Model model) { Accounts account
		 * = accountService.findById(id); accountService.deleteReferencingRecords(id);
		 * accountService.deleteReferenceOrders(id); accountService.delete(account);
		 * return "redirect:/admin/account"; }
		 */
		/*
		 * @GetMapping("/searchAC") public String searchaacount(@RequestParam(value =
		 * "keyword", required = false) String keyword,
		 * 
		 * @RequestParam("p") Optional<Integer> p, Model model) { Pageable pageable =
		 * PageRequest.of(p.orElse(0), 5); Page<Accounts> pages;
		 * 
		 * if (keyword != null && !keyword.isEmpty()) { pages =
		 * accountService.search(keyword, pageable); } else { pages =
		 * accountService.findAll(pageable); }
		 * 
		 * model.addAttribute("pages", pages); model.addAttribute("currentPage",
		 * p.orElse(0)); model.addAttribute("account", new Account());
		 * 
		 * return "/views/admin/account"; }
		 */
}
