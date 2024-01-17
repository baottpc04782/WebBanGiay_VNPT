package com.poly.ControllerAdmin;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

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
import org.springframework.web.multipart.MultipartFile;

import com.poly.dao.CategoriesDao;
import com.poly.dao.ManufactorDao;
import com.poly.entity.Color;
import com.poly.entity.Products;
import com.poly.entity.ProductsDetail;
import com.poly.entity.Size;
import com.poly.services.ColorService;
import com.poly.services.ProductDetailService;
import com.poly.services.ProductService;
import com.poly.services.SizeServies;

@Controller
@MultipartConfig
@RequestMapping("/admin")
public class ProductAdminController {
	@Autowired
	ProductService productService;

	@Autowired
	SizeServies sizeService;

	@Autowired
	ColorService colorService;

	@Autowired
	ProductDetailService productDetailService;

	@Autowired
	CategoriesDao categoryDAO;

	@Autowired
	HttpServletRequest request;

	@Autowired
	ManufactorDao manufactorDao;

	private static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/assets/images/products/";

	// Hiển thị trang quản lý sản phẩm
	@GetMapping("/product")
	public String index(Products product, Model model, @PathParam("p") Optional<Integer> p) {
	    // Xác định trang và số lượng sản phẩm trên mỗi trang
	    Pageable pageable = PageRequest.of(p.orElse(0), 5);
	    Page<Products> page = productService.findAllByPage(pageable);

	    // Thêm các thuộc tính vào Model để truyền thông tin đến giao diện người dùng
	    model.addAttribute("list", page);
	    model.addAttribute("currentPage", p.orElse(0));
	    model.addAttribute("categoryList", categoryDAO.findAll());
	    model.addAttribute("colors", colorService.findAll());
	    model.addAttribute("sizes", sizeService.findAll());
	    model.addAttribute("manufactorLst", manufactorDao.findAll());
	    model.addAttribute("product", product);

	    // Chuyển hướng đến trang quản lý sản phẩm
	    return "views/admin/product";
	}

	// Xử lý lưu thông tin sản phẩm
	@PostMapping("/saveproduct")
	public String SaveProduct(@Validated @ModelAttribute Products products, BindingResult result,
	                          @RequestParam(value = "selectedSize", required = false) Integer sizeId,
	                          @RequestParam(value = "selectedColor", required = false) Integer colorId,
	                          @RequestParam(value = "uploadImage", required = false) MultipartFile image,
	                          @PathParam("p") Optional<Integer> p, Model model) {
	    // Xác định trang và số lượng sản phẩm trên mỗi trang
	    Pageable pageable = PageRequest.of(p.orElse(0), 5);
	    Page<Products> page = productService.findAllByPage(pageable);

	    Optional<Size> sizeOptional = Optional.empty();
	    Optional<Color> colorOptional = Optional.empty();

	    if (image != null && !image.isEmpty()) {
	        // Xử lý lưu hình ảnh sản phẩm
	        try {
	            String fileName = image.getOriginalFilename();
	            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, fileName);
	            Files.write(fileNameAndPath, image.getBytes());
	            products.setImage(fileName);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        productService.save(products);
	    } else {
	        // Bắt lỗi khi hình ảnh trống
	        if (products.getProductId() == null) {
	            model.addAttribute("product", products); // Tạo một sản phẩm mới để điền vào form
	            model.addAttribute("list", page);
	            model.addAttribute("categoryList", categoryDAO.findAll());
	            model.addAttribute("colors", colorService.findAll());
	            model.addAttribute("sizes", sizeService.findAll());
	            model.addAttribute("manufactorLst", manufactorDao.findAll());
	            model.addAttribute("photo_message", "Không bỏ trống hình");
	            return "views/admin/product";
	        }
	    }

	    // Xử lý lưu thông tin size và color của sản phẩm
	    if (sizeOptional.isPresent() && colorOptional.isPresent()) {
	        Size size = sizeOptional.get();
	        Color color = colorOptional.get();

	        // Tạo ProductDetail và liên kết với Product, Size và Color đã lưu
	        ProductsDetail detail = new ProductsDetail();
	        detail.setProducts(products);
	        detail.setSize(size);
	        detail.setColor(color);

	        // Lưu ProductDetail
	        productDetailService.save(detail);
	    }

	    // Lưu thông tin sản phẩm
	    productService.save(products);

	    // Thêm các thuộc tính vào Model để truyền thông tin đến giao diện người dùng
	    model.addAttribute("list", productService.findAll());
	    model.addAttribute("categoryList", categoryDAO.findAll());
	    model.addAttribute("product", new Products());
	    model.addAttribute("colors", colorService.findAll());
	    model.addAttribute("sizes", sizeService.findAll());

	    // Chuyển hướng đến trang quản lý sản phẩm
	    return "redirect:/admin/product";
	}

	// Chỉnh sửa thông tin sản phẩm
	@GetMapping("editproduct/{id}")
	public String EditProduct(@PathVariable("id") Integer id, Products products, @PathParam("p") Optional<Integer> p,
	                          Model model) {
	    // Xác định trang và số lượng sản phẩm trên mỗi trang
	    Pageable pageable = PageRequest.of(p.orElse(0), 5);
	    Page<Products> page = productService.findAllByPage(pageable);

	    // Thêm các thuộc tính vào Model để truyền thông tin đến giao diện người dùng
	    model.addAttribute("product", productService.getOne(id));
	    model.addAttribute("colors", colorService.findAll());
	    model.addAttribute("sizes", sizeService.findAll());
	    model.addAttribute("list", page);
	    model.addAttribute("categoryList", categoryDAO.findAll());
	    model.addAttribute("manufactorLst", manufactorDao.findAll());

	    // Chuyển hướng đến trang quản lý sản phẩm
	    return "/views/admin/product";
	}

	// Tìm kiếm sản phẩm
	@PostMapping("/product")
	public String ProductSearch(Model model, HttpServletRequest request, @RequestParam("p") Optional<Integer> p) {
	    // Lấy từ khóa tìm kiếm từ request
	    String keywords = request.getParameter("keywords");
	    // Xác định trang và số lượng sản phẩm trên mỗi trang
	    Pageable pageable = PageRequest.of(p.orElse(0), 5);
	    Page<Products> pages;

	    // Kiểm tra từ khóa tìm kiếm
	    if (keywords != null && !keywords.isEmpty()) {
	        model.addAttribute("categoryList", categoryDAO.findAll());
	        pages = productService.findProductNameLike(keywords, pageable);
	    } else {
	        model.addAttribute("categoryList", categoryDAO.findAll());
	        pages = productService.findAllByPage(pageable);
	    }

	    // Thêm các thuộc tính vào Model để truyền thông tin đến giao diện người dùng
	    model.addAttribute("list", pages);
	    model.addAttribute("currentPage", p.orElse(0));
	    model.addAttribute("product", new Products());
	    model.addAttribute("colors", colorService.findAll());
	    model.addAttribute("sizes", sizeService.findAll());

	    // Chuyển hướng đến trang quản lý sản phẩm
	    return "views/admin/product";
	}

	// Sắp xếp sản phẩm theo tên
	@GetMapping("/product/sort")
	public String ProductSort(Model model, HttpServletRequest request, @PathParam("p") Optional<Integer> p) {
	    // Xác định trang và số lượng sản phẩm trên mỗi trang
	    Pageable pageable = PageRequest.of(p.orElse(0), 5);
	    Page<Products> pages;

	    // Sắp xếp sản phẩm theo tên
	    pages = productService.SortByProductName(pageable);

	    // Thêm các thuộc tính vào Model để truyền thông tin đến giao diện người dùng
	    model.addAttribute("list", pages);
	    model.addAttribute("currentPage", p.orElse(0));
	    model.addAttribute("categoryList", categoryDAO.findAll());
	    model.addAttribute("product", new Products());
	    model.addAttribute("colors", colorService.findAll());
	    model.addAttribute("sizes", sizeService.findAll());

	    // Chuyển hướng đến trang quản lý sản phẩm
	    return "views/admin/product";
	}

	// Hiển thị trang phân trang sản phẩm
	@GetMapping("/product/pagination")
	public String ProductPagination(Model model, @PathParam("p") Optional<Integer> p) {
	    // Xác định trang và số lượng sản phẩm trên mỗi trang
	    Pageable pageable = PageRequest.of(p.orElse(0), 5);
	    Page<Products> page = productService.findAllByPage(pageable);

	    // Thêm các thuộc tính vào Model để truyền thông tin đến giao diện người dùng
	    model.addAttribute("list", page);
	    model.addAttribute("categoryList", categoryDAO.findAll());
	    model.addAttribute("product", new Products());

	    // Chuyển hướng đến trang quản lý sản phẩm
	    return "views/admin/product";
	}

	// Xóa sản phẩm
	@GetMapping("/deleteproduct/{id}")
	public String DeleteProduct(@PathVariable("id") Integer id) {
	    productService.deleteById(id);
	    return "redirect:/admin/product";
	}

	// Hiển thị trang quản lý chi tiết sản phẩm
	@GetMapping("/productDetail")
	public String getProductDetail(Model model, @PathParam("p") Optional<Integer> p, @RequestParam(value="id", required = false) Integer id,
	        @RequestParam("productId") Integer productId) {
	    // Tạo đối tượng ProductsDetail để lưu chi tiết sản phẩm
	    ProductsDetail productDetail = new ProductsDetail();
	    // Xác định trang và số lượng chi tiết sản phẩm trên mỗi trang
	    Pageable pageable = PageRequest.of(p.orElse(0), 5);
	    Page<ProductsDetail> page = productDetailService.findByProductIdAndPage(productId, pageable);

	    // Nếu có ID, lấy chi tiết sản phẩm theo ID
	    if (id != null) {
	        productDetail = productDetailService.findById(id);
	    }

	    // Thêm các thuộc tính vào Model để truyền thông tin đến giao diện người dùng
	    model.addAttribute("list", page);
	    model.addAttribute("currentPage", p.orElse(0));
	    model.addAttribute("colorList", colorService.findAll());
	    model.addAttribute("sizeList", sizeService.findAll());
	    model.addAttribute("productDetail", productDetail);
	    model.addAttribute("productIdH", productId);

	    // Chuyển hướng đến trang quản lý chi tiết sản phẩm
	    return "views/admin/productDetail.html";
	}

	// Lưu thông tin chi tiết sản phẩm
	@PostMapping("/saveProductDetail")
	public String SaveProductDetail(ProductsDetail productDetail, BindingResult result, Model model,
	        @RequestParam("productIdH") Integer productIdH) {
	    // Liên kết chi tiết sản phẩm với sản phẩm theo ID
	    productDetail.setProducts(new Products(productIdH));
	    // Lưu chi tiết sản phẩm
	    productDetailService.save(productDetail);

	    // Chuyển hướng đến trang quản lý chi tiết sản phẩm của sản phẩm đó
	    return "redirect:/admin/productDetail?productId=" + productIdH;
	}

	// Xóa chi tiết sản phẩm
	@GetMapping("/deleteProductDetail")
	public String DeleteProductDetail(@RequestParam("id") Integer id, @RequestParam("productIdH") Integer productIdH) {
	    // Xóa chi tiết sản phẩm theo ID
	    productDetailService.deleteById(id);
	    // Chuyển hướng đến trang quản lý chi tiết sản phẩm của sản phẩm đó
	    return "redirect:/admin/productDetail?productId=" + productIdH;
	}

	/*
	 * @Autowired ProductService productService;
	 * 
	 * @Autowired SizeServies sizeService;
	 * 
	 * @Autowired ColorService colorService;
	 * 
	 * @Autowired ProductDetailService productDetailService;
	 * 
	 * @Autowired CategoriesDao categoryDAO;
	 * 
	 * @Autowired HttpServletRequest request;
	 * 
	 * @Autowired ManufactorDao manufactorDao;
	 * 
	 * private static String UPLOAD_DIRECTORY = System.getProperty("user.dir") +
	 * "/src/main/resources/static/assets/images/products/";
	 * 
	 * @GetMapping("/product") public String index(Products product, Model
	 * model, @PathParam("p") Optional<Integer> p) { Pageable pageable =
	 * PageRequest.of(p.orElse(0), 5); Page<Products> page =
	 * productService.findAllByPage(pageable); model.addAttribute("list", page);
	 * model.addAttribute("currentPage", p.orElse(0));
	 * model.addAttribute("categoryList", categoryDAO.findAll());
	 * model.addAttribute("colors", colorService.findAll());
	 * model.addAttribute("sizes", sizeService.findAll());
	 * model.addAttribute("manufactorLst", manufactorDao.findAll());
	 * model.addAttribute("product", product); return "views/admin/product"; }
	 * 
	 * @PostMapping("/saveproduct") public String
	 * SaveProduct(@Validated @ModelAttribute Products products, BindingResult
	 * result,
	 * 
	 * @RequestParam(value = "selectedSize", required = false) Integer
	 * sizeId, @RequestParam(value = "selectedColor" , required = false) Integer
	 * colorId,
	 * 
	 * @RequestParam(value = "uploadImage", required = false) MultipartFile
	 * image, @PathParam("p") Optional<Integer> p, Model model) {
	 * 
	 * Pageable pageable = PageRequest.of(p.orElse(0), 5); Page<Products> page =
	 * productService.findAllByPage(pageable); // Optional<Size> sizeOptional =
	 * sizeService.findById(sizeId); // Optional<Color> colorOptional =
	 * colorService.findById(colorId); Optional<Size> sizeOptional =
	 * Optional.empty(); Optional<Color> colorOptional = Optional.empty();
	 * 
	 * if (image != null && !image.isEmpty()) { try { String fileName =
	 * image.getOriginalFilename(); Path fileNameAndPath =
	 * Paths.get(UPLOAD_DIRECTORY, fileName); Files.write(fileNameAndPath,
	 * image.getBytes()); products.setImage(fileName); } catch (IOException e) {
	 * e.printStackTrace(); } productService.save(products);
	 * 
	 * } else { // bắt lỗi cho hình if (products.getProductId() == null) {
	 * model.addAttribute("product", products); // Create a new product for the form
	 * model.addAttribute("list", page); model.addAttribute("categoryList",
	 * categoryDAO.findAll()); model.addAttribute("colors", colorService.findAll());
	 * model.addAttribute("sizes", sizeService.findAll());
	 * model.addAttribute("manufactorLst", manufactorDao.findAll());
	 * 
	 * model.addAttribute("photo_message", "Không bỏ trống hình"); return
	 * "views/admin/product"; }
	 * 
	 * }
	 * 
	 * if (sizeOptional.isPresent() && colorOptional.isPresent()) { Size size =
	 * sizeOptional.get(); Color color = colorOptional.get();
	 * 
	 * // Create ProductDetail and associate it with the saved Product, Size, and
	 * Color ProductsDetail detail = new ProductsDetail();
	 * detail.setProducts(products); detail.setSize(size); detail.setColor(color);
	 * 
	 * // Save the ProductDetail productDetailService.save(detail); }
	 * 
	 * productService.save(products);
	 * 
	 * model.addAttribute("list", productService.findAll());
	 * model.addAttribute("categoryList", categoryDAO.findAll());
	 * model.addAttribute("product", new Products()); model.addAttribute("colors",
	 * colorService.findAll()); model.addAttribute("sizes", sizeService.findAll());
	 * return "redirect:/admin/product"; }
	 * 
	 * @GetMapping("editproduct/{id}") public String EditProduct(@PathVariable("id")
	 * Integer id, Products products, @PathParam("p") Optional<Integer> p, Model
	 * model) { Pageable pageable = PageRequest.of(p.orElse(0), 5); Page<Products>
	 * page = productService.findAllByPage(pageable);
	 * 
	 * model.addAttribute("product", productService.getOne(id));
	 * model.addAttribute("colors", colorService.findAll());
	 * model.addAttribute("sizes", sizeService.findAll());
	 * model.addAttribute("list", page); model.addAttribute("categoryList",
	 * categoryDAO.findAll()); model.addAttribute("manufactorLst",
	 * manufactorDao.findAll());
	 * 
	 * return "/views/admin/product"; }
	 * 
	 * // Tìm kiếm
	 * 
	 * @PostMapping("/product") public String ProductSearch(Model model,
	 * HttpServletRequest request, @RequestParam("p") Optional<Integer> p) { String
	 * keywords = request.getParameter("keywords"); Pageable pageable =
	 * PageRequest.of(p.orElse(0), 5); Page<Products> pages;
	 * 
	 * if (keywords != null && !keywords.isEmpty()) {
	 * model.addAttribute("categoryList", categoryDAO.findAll()); pages =
	 * productService.findProductNameLike(keywords, pageable); } else {
	 * model.addAttribute("categoryList", categoryDAO.findAll()); pages =
	 * productService.findAllByPage(pageable); }
	 * 
	 * model.addAttribute("list", pages); model.addAttribute("currentPage",
	 * p.orElse(0)); model.addAttribute("product", new Products());
	 * model.addAttribute("colors", colorService.findAll());
	 * model.addAttribute("sizes", sizeService.findAll()); return
	 * "views/admin/product"; }
	 * 
	 * @GetMapping("/product/sort") public String ProductSort(Model model,
	 * HttpServletRequest request, @PathParam("p") Optional<Integer> p) { Pageable
	 * pageable = PageRequest.of(p.orElse(0), 5); Page<Products> pages;
	 * 
	 * pages = productService.SortByProductName(pageable);
	 * 
	 * model.addAttribute("list", pages); model.addAttribute("currentPage",
	 * p.orElse(0)); model.addAttribute("categoryList", categoryDAO.findAll());
	 * model.addAttribute("product", new Products()); model.addAttribute("colors",
	 * colorService.findAll()); model.addAttribute("sizes", sizeService.findAll());
	 * return "views/admin/product"; }
	 * 
	 * @GetMapping("/product/pagination") public String ProductPagination(Model
	 * model, @PathParam("p") Optional<Integer> p) {
	 * 
	 * Pageable pageable = PageRequest.of(p.orElse(0), 5); Page<Products> page =
	 * productService.findAllByPage(pageable);
	 * 
	 * model.addAttribute("list", page); // model.addAttribute("list",
	 * productService.findAll()); model.addAttribute("categoryList",
	 * categoryDAO.findAll()); model.addAttribute("product", new Products());
	 * 
	 * return "views/admin/product"; }
	 * 
	 * @GetMapping("/deleteproduct/{id}") public String
	 * DeleteProduct(@PathVariable("id") Integer id) {
	 * productService.deleteById(id); return "redirect:/admin/product"; }
	 * 
	 * @GetMapping("/productDetail") public String getProductDetail(Model
	 * model, @PathParam("p") Optional<Integer> p, @RequestParam(value="id",
	 * required = false) Integer id,
	 * 
	 * @RequestParam("productId") Integer productId) {
	 * 
	 * ProductsDetail productDetail = new ProductsDetail(); Pageable pageable =
	 * PageRequest.of(p.orElse(0), 5); Page<ProductsDetail> page =
	 * productDetailService.findByProductIdAndPage(productId, pageable);
	 * 
	 * if (id != null) { productDetail = productDetailService.findById(id); }
	 * 
	 * model.addAttribute("list", page); model.addAttribute("currentPage",
	 * p.orElse(0)); model.addAttribute("colorList", colorService.findAll());
	 * model.addAttribute("sizeList", sizeService.findAll());
	 * model.addAttribute("productDetail", productDetail);
	 * model.addAttribute("productIdH", productId); return
	 * "views/admin/productDetail.html"; }
	 * 
	 * @PostMapping("/saveProductDetail") public String
	 * SaveProductDetail(ProductsDetail productDetail, BindingResult result, Model
	 * model,
	 * 
	 * @RequestParam("productIdH") Integer productIdH) {
	 * 
	 * productDetail.setProducts(new Products(productIdH));
	 * productDetailService.save(productDetail);
	 * 
	 * return "redirect:/admin/productDetail?productId=" + productIdH; }
	 * 
	 * @GetMapping("/deleteProductDetail") public String
	 * DeleteProductDetail(@RequestParam("id") Integer
	 * id, @RequestParam("productIdH") Integer productIdH) {
	 * productDetailService.deleteById(id); return
	 * "redirect:/admin/productDetail?productId=" + productIdH; }
	 */

}
