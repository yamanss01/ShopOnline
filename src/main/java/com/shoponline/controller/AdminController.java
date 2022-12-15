package com.shoponline.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.shoponline.dto.CategoryDTO;
import com.shoponline.dto.ProductDTO;
import com.shoponline.service.CategoryService;
import com.shoponline.service.MenuService;
import com.shoponline.service.ProductService;

/**
 * this controller handles the operation which only Admin users can
 * access like Adding Product, Adding Categories, Adding Menu
 * 
 * @author yaman
 *
 */
@Controller
public class AdminController {

	private Logger log = LoggerFactory.getLogger(AdminController.class.getName());
	/**
	 * this service class handle operation linked to Products
	 */
	@Autowired
	ProductService productService;
	/**
	 * this service class handle operation linked to Categories
	 */
	@Autowired
	CategoryService categoryService;
	/**
	 * this service class handle operation linked to Menus
	 */
	@Autowired
	MenuService menuService;
	/**
	 * this method will generate some menu on application startup so Admin User can
	 * get views having some Menu's
	 */
	@PostConstruct
	public void initMenu() {
		menuService.initMenu();
	}
	/**
	 * this api when called returns view for Admin Home Panel
	 * 
	 * @return view
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin")
	public ModelAndView adminHome() {
		log.info("Execution of adminHome: api started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("admin/adminHome");
		return modelView;
	}

	/**
	 * This api when called return view for Menus
	 * 
	 * @return view
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/menus")
	public ModelAndView getMenus() {
		log.info("Execution of getMenus: api started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("/admin/menus.html");
		modelView.addObject("menus", menuService.getAllMenu());
		return modelView;
	}
	/**
	 * This api when called return view for Categories
	 * 
	 * @return
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/categories")
	public ModelAndView getCategories() {
		log.info("Execution of getCategories: api started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("/admin/categories.html");
		modelView.addObject("categories", categoryService.getAllCategory());
		log.info("Execution of getCategories: api stopped");
		return modelView;
	}

	/**
	 * This api when called returns view for adding category page in admin panel.
	 * 
	 * @return view
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/categories/add")
	public ModelAndView getAddCategory() {
		log.info("Execution of getAddCategory: api started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("/admin/categoriesAdd.html");
		modelView.addObject("categoryDTO", new CategoryDTO());
		modelView.addObject("menus", menuService.getAllMenu());
		return modelView;
	}

	/**
	 * This api when called register an Category and redirects admin to categories
	 * Page.
	 * 
	 * @param categoryDTO
	 * @return view
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@PostMapping("/admin/categories/add")
	public String postAddCategory(@ModelAttribute("categoryDTO") CategoryDTO categoryDTO,
			@RequestParam("categoryImage") MultipartFile file, @RequestParam("imgName") String imgName)
			throws Exception {
		log.info("Execution of postAddCategory: api started");
		categoryService.addCategory(categoryDTO, file, imgName);
		return "redirect:/admin/categories";
	}
	/**
	 * This api when called deleted the category & redirects admin to category page
	 * 
	 * @param categoryId
	 * @return view
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/categories/delete/{categoryId}")
	public String deleteCategory(@PathVariable int categoryId) {
		log.info("Execution of deleteCategory: api started");
		categoryService.removeCategoryById(categoryId);
		return "redirect:/admin/categories";
	}

	/**
	 * This api when called updates the category details & redirects admin
	 * updateCategory Page.
	 * 
	 * @param categoryId
	 * @return
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/categories/update/{categoryId}")
	public ModelAndView updateCategory(@PathVariable int categoryId, Model model) {
		log.info("Execution of updateCategory api started");
		ModelAndView modelView = new ModelAndView();
		CategoryDTO categoryDTO = categoryService.updateCategory(categoryId);
		modelView.setViewName("/admin/categoriesAdd.html");
		modelView.addObject("categoryDTO", categoryDTO);
		modelView.addObject("menus", menuService.getAllMenu());
		return modelView;
	}

	// Product Section

	/**
	 * This api when called returns view of products page to admin.
	 * 
	 * @return
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/products")
	public ModelAndView getProducts() {
		log.info("Execution of getProducts api Started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("/admin/products.html");
		modelView.addObject("products", productService.getAllProduct());
		return modelView;
	}

	/**
	 * This api when called returns view for AddProduct page for admin
	 * 
	 * @return
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/products/add")
	public ModelAndView getAddProduct() {
		log.info("Execution of getAddProduct: api Started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("/admin/productAdd.html");
		modelView.addObject("productDTO", new ProductDTO());
		modelView.addObject("categories", categoryService.getAllCategory());
		return modelView;
	}

	/**
	 * This api when called registers product to db & redirects admin to add
	 * 
	 * @param productDTO
	 * @param file
	 * @param imgName
	 * @return
	 * @throws IOException
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@PostMapping("/admin/products/add")
	public String postAddProduct(@ModelAttribute("productDTO") ProductDTO productDTO,
			@RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imgName)
			throws Exception {
		log.info("Execution of postAddProduct: api Started");
		productService.addProduct(productDTO, file, imgName);
		return "redirect:/admin/products";
	}

	/**
	 * This api when called deletes the product & redirects admin to Products page
	 * after deleting the product.
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable int id) {
		log.info("Execution of deleteProduct: api Started");
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}

	/**
	 * This api when called return view of update product page
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/product/update/{id}")
	public ModelAndView getUpdateProduct(@PathVariable int id) {
		log.info("Execution of getUpdateProduct: api Started");
		ModelAndView modelView = new ModelAndView();
		ProductDTO productDTO = productService.updateProduct(id);

		modelView.setViewName("/admin/productAdd.html");
		modelView.addObject("productDTO", productDTO);
		modelView.addObject("categories", categoryService.getAllCategory());
		return modelView;
	}
}
