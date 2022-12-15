package com.shoponline.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shoponline.model.Category;
import com.shoponline.model.Menu;
import com.shoponline.model.Product;
import com.shoponline.model.ShippingAddress;
import com.shoponline.model.User;
import com.shoponline.service.CategoryService;
import com.shoponline.service.HomeService;
import com.shoponline.service.MenuService;
import com.shoponline.service.ProductService;
import com.shoponline.service.UserService;

/**
 * this controller handles the operations of the home page which any User
 * landing on the page can access like Home-Page, categories, products, user
 * registration
 * 
 * @author yaman
 *
 */
@Controller
public class HomeController {

	private Logger log = LoggerFactory.getLogger(HomeController.class.getName());

	/**
	 * this service class handle operations linked to Home Page settings
	 */
	@Autowired
	private HomeService homeService;

	/**
	 * this service class handle operations related to Menus
	 */
	@Autowired
	private MenuService menuService;

	/**
	 * this service class handle operations related to Categories
	 */
	@Autowired
	private CategoryService categoryService;

	/**
	 * this service class handle operations related to product
	 */
	@Autowired
	private ProductService productService;

	/**
	 * this service class handle operations related to User services
	 */
	@Autowired
	private UserService userService;

	/**
	 * This api when called returns view of Home Page.
	 * 
	 * @return
	 */
	@GetMapping({ "/", "/home" })
	public ModelAndView home() {
		log.info("Execution of Execution of home api Started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("home.html");
		modelView.addObject("title", "Home - Online Shopping");
		List<Menu> menu = menuService.getAllMenu();
		modelView.addObject("menu", menu);
		return modelView;
	}

	/**
	 * This api when called returns view of about web page.
	 * 
	 * @return
	 */
	@GetMapping("/about")
	public ModelAndView about() {
		log.info("Execution of about api Started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("about.html");
		modelView.addObject("title", "About - Online Shopping");
		return modelView;
	}

	/**
	 * This api when called returns view Menu page.
	 * 
	 * @return
	 */
	@GetMapping("/men/{menuId}")
	public ModelAndView men(@PathVariable int menuId) {
		log.info("Execution of men api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("category.html");
		Menu menu = menuService.getMenuById(menuId);
		List<Category> category = menu.getCategories();
		modelAndView.addObject("categories", category);
		return modelAndView;
	}

	/**
	 * This api when called returns view for women category Page.
	 * 
	 * @param menuId
	 * @return
	 */
	@GetMapping("/women/{menuId}")
	public ModelAndView women(@PathVariable int menuId) {
		log.info("Execution of women api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("category.html");
		Menu menu = menuService.getMenuById(menuId);
		List<Category> category = menu.getCategories();
		modelAndView.addObject("categories", category);
		log.info("Execution of women: api Stopped");
		return modelAndView;
	}

	/**
	 * This api when called returns view for kids category Page.
	 * 
	 * @param menuId
	 * @return
	 */
	@GetMapping("/kids/{menuId}")
	public ModelAndView kids(@PathVariable int menuId) {
		log.info("Execution of kids api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("category.html");
		Menu menu = menuService.getMenuById(menuId);
		List<Category> category = menu.getCategories();
		modelAndView.addObject("categories", category);
		return modelAndView;
	}

	/**
	 * This api when called returns view for homeLiving category Page.
	 * 
	 * @param menuId
	 * @return
	 */
	@GetMapping("/home&living/{menuId}")
	public ModelAndView homeliving(@PathVariable int menuId) {
		log.info("Execution of homeLiving api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("category.html");
		Menu menu = menuService.getMenuById(menuId);
		List<Category> category = menu.getCategories();
		modelAndView.addObject("categories", category);
		return modelAndView;
	}

	/**
	 * This api when called returns view for selected product's page.
	 * 
	 * @param productId
	 * @return
	 */
	@GetMapping("/product/{productId}")
	public ModelAndView getProductById(@PathVariable int productId) {
		log.info("Execution of getProductById api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("product_detail.html");
		Product product = productService.getProductByProductId(productId);
		modelAndView.addObject("products", product);
		return modelAndView;
	}

	/**
	 * This api when called returns view for Selected category's page.
	 * 
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/category/{categoryId}")
	public ModelAndView getCategoryById(@PathVariable int categoryId) {
		log.info("Execution of getCategoryById api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("products.html");
		Category category = categoryService.getCategoryById(categoryId);
		List<Product> prodList = category.getProducts();
		modelAndView.addObject("categories", category);
		modelAndView.addObject("products", prodList);
		return modelAndView;
	}

	/**
	 * This api when called returns view for cart page.
	 * 
	 * @return
	 */
	@GetMapping("/cart")
	public ModelAndView viewCart() {
		log.info("Execution of viewCart api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cart.html");
		return modelAndView;
	}

	/**
	 * This api when called returns view for place order page.
	 * 
	 * @param principal
	 * @return
	 */
	@PreAuthorize("hasRole('User')")
	@GetMapping("/placeorder")
	public ModelAndView placeorder(Principal principal) {

		log.info("Execution of placeOrder api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("placeorder.html");
		String username = principal.getName();
		System.out.println("username " + username);
		User user = userService.findUserByUserName(username);

		modelAndView.addObject("users", user);
		ShippingAddress shippingAddress = new ShippingAddress();
		modelAndView.addObject("shippingAddress", shippingAddress);
		return modelAndView;
	}

	/**
	 * This api when called register user's address to db. and redirect user to
	 * payment page
	 * 
	 * @param shippingAddress
	 * @param principal
	 * @return
	 */
	@PreAuthorize("hasRole('User')")
	@PostMapping("/address")
	public String address(@ModelAttribute("shippingAddress") ShippingAddress shippingAddress, Principal principal) {

		log.info("Execution of address api Started");
		homeService.saveUserAddress(shippingAddress, principal.getName());
		return "redirect:/payment";

	}

	/**
	 * This api when called returns view for payment page
	 * 
	 * @param principal
	 * @return
	 */
	@PreAuthorize("hasRole('User')")
	@GetMapping("/payment")
	public ModelAndView payment(Principal principal) {
		log.info("Execution of payment api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("payment.html");
		ShippingAddress shippingAddress = homeService.getUserShippingAddress(principal.getName());
		modelAndView.addObject("shipAddress", shippingAddress);
		return modelAndView;
	}
	/**
	 * This api when called returnrs view for Order Confirmation Page
	 * 
	 * @return
	 */
	@GetMapping("/confirmorder")
	public ModelAndView confirmOrder() {
		log.info("Execution of confirmOrder api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("confirm_order.html");
		return modelAndView;
	}

}
