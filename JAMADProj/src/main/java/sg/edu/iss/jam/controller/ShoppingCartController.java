package sg.edu.iss.jam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
