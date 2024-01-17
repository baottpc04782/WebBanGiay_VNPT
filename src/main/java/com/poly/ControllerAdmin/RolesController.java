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

import com.poly.entity.Roles;
import com.poly.services.RoleService;




@Controller
@RequestMapping("/admin")
public class RolesController {

	
	@Autowired
	RoleService roleService;
	
	@GetMapping("/role")
	public String showAccount(Model model, @RequestParam("p") Optional<Integer> p) {
		Roles roles = new Roles();
        Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Roles> pages = roleService.findAll(pageable);
		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", p.orElse(0));
		model.addAttribute("roles",roles);
	    return "views/admin/role";
	}
	
	

	@GetMapping("/editRole/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {

		Roles roles = roleService.findById(id)
		.orElseThrow(() -> new IllegalArgumentException("Invalid account Id:" + id));

		Page<Roles> pages = roleService.findAll(PageRequest.of(0, 5));

		model.addAttribute("role", roles);
		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", 0);

		return "views/admin/role";
	}
	
	// chuc nang insert
			@PostMapping("/saverole")
			public String createrole(@ModelAttribute @Validated Roles roles, BindingResult bindingResult, Model model) {
				if (bindingResult.hasErrors()) {
					model.addAttribute("roles", roles);
					Page<Roles> pages = roleService.findAll(PageRequest.of(0, 5));
					model.addAttribute("pages", pages);
					model.addAttribute("currentPage", 0);
					return "/views/admin/role";
				}
				   // Xử lý logic khi không có lỗi
				roleService.create(roles);

				return "redirect:/admin/role";
			}
			
			@GetMapping("/deleteRole/{id}")
			public String deleterole(@PathVariable("id") String id, Model model) {
				Roles roles = roleService.findById(id)
						.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
				roleService.delete(roles);
				return "redirect:/admin/role";
			}
			
			/*
			 * // search
			 * 
			 * @GetMapping("/searchRL") public String searchrole(@RequestParam(value =
			 * "keyword", required = false) String keyword,
			 * 
			 * @RequestParam("p") Optional<Integer> p, Model model) { Pageable pageable =
			 * PageRequest.of(p.orElse(0), 5); Page<Roles> pages;
			 * 
			 * if (keyword != null && !keyword.isEmpty()) { pages =
			 * roleService.search(keyword, pageable); } else { pages =
			 * roleService.findAll(pageable); }
			 * 
			 * model.addAttribute("pages", pages); model.addAttribute("currentPage",
			 * p.orElse(0)); model.addAttribute("role", new Role());
			 * 
			 * return "/views/admin/role"; }
			 */
	
}
