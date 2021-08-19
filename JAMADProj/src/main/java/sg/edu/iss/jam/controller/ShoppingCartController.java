package sg.edu.iss.jam.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.ShoppingCart;
import sg.edu.iss.jam.service.UserInterface;
@Controller
public class ShoppingCartController {

	@Autowired
	UserInterface uservice;
	
	@GetMapping("/shoppingcart")
	public String shoppingCart(Model model) {
		long userID = (long) 1;
		model.addAttribute("cartForm", uservice.getShoppingCartByUserID(userID));
		System.out.println(uservice.getShoppingCartByUserID(userID));
		return "product/shoppingCart";
	}
	
	// POST: Update quantity for product in cart
	@RequestMapping(value = { "/shoppingcart" }, method = RequestMethod.POST)
	public String shoppingCartUpdateQty(HttpServletRequest request, Model model, //
	         @ModelAttribute("cartForm") ShoppingCart cartForm) {
		long userID = (long) 1;
		ShoppingCart cartInfo = uservice.getShoppingCartByUserID(userID);
	    //cartInfo.updateQuantity(cartForm);
	 
	    return "redirect:product/shoppingCart";
	}
	
	@RequestMapping({ "/shoppingCartRemoveProduct" })
	   public String removeProductHandler(HttpServletRequest request, Model model, //
	         @RequestParam(value = "id", defaultValue = "") Long id) {
	      Product product = null;
	      if (id != null) {
	         product = uservice.findProduct(id);
	      }
	      if (product != null) {
	    	  
	    	 long userID = (long) 1;
	 
	         ShoppingCart cartInfo = uservice.getShoppingCartByUserID(userID);
	 
	         //ProductInfo productInfo = new ProductInfo(product);
	 
	         cartInfo.removeProduct(product);
	 
	      }
	 
	      return "redirect:product/shoppingCart";
	   }
}
