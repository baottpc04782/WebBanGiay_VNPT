package com.poly.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.SizeDao;
import com.poly.entity.Color;
import com.poly.entity.Products;
import com.poly.entity.ProductsDetail;
import com.poly.entity.Size;
import com.poly.services.ProductService;

@Controller
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	SizeDao sizeDao;

	@Autowired
	HttpServletRequest req;

	@RequestMapping("/product/list")
	public String home(Model model) {
		// if (cid.isPresent()) {
		// List<Products> list = productService.findByCategoryId(cid.get());
		// model.addAttribute("items", list);
		// } else {
		// List<Products> list = productService.findAll();
		// model.addAttribute("items", list);
		// }
		List<Products> list = productService.findAll();
		model.addAttribute("items", list);
		return "product/list.html";
	}

	@RequestMapping("/product/detail/{id}")
	public String detail_product(Model model, @PathVariable("id") Integer id) {
		Products item = productService.findById(id);

		// ProductsDetail product = productsDetailService.findById(id);
		// int quantity = Integer.parseInt(req.getParameter("quantity"));
		// if(quantity <= product.getQuantity()){

		// }
		List<Size> listSize = new ArrayList<Size>();
		List<Color> listColor = new ArrayList<Color>();
		List<ProductsDetail> proDts = item.getProductsDetail();

		for (ProductsDetail proDt : proDts) {
			Size size = proDt.getSize();
			listSize.add(size);
			Color color = proDt.getColor();
			listColor.add(color);
		}
		model.addAttribute("item", item);
		model.addAttribute("listSize", listSize);
		model.addAttribute("listColor", listColor);
		return "product/detail.html";
	}

	@RequestMapping("/allproduct")
	public String allProduct(Model model, @RequestParam("cid") Optional<Integer> cid) {
		if (cid.isPresent()) {
			List<Products> list = productService.findByCategoryId(cid.get());
			model.addAttribute("items", list);
		} else {
			List<Products> list = productService.findAll();
			model.addAttribute("items", list);
		}
		return "product/allproduct.html";
	}

}
