package sg.edu.iss.jam.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.jam.model.OrderDetails;
import sg.edu.iss.jam.model.Orders;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.ShoppingCart;
import sg.edu.iss.jam.model.ShoppingCartDetails;
import sg.edu.iss.jam.model.User;
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
	    cartInfo.updateQuantity(cartForm);
	 
	    return "redirect:shoppingcart";
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
	         uservice.removeCartDetails(product.getProductID(),cartInfo.getShoppingCartID());
	 
	      }
	 
	      return "redirect:shoppingcart";
	   }
	
	@GetMapping("/checkout")
	public String checkOut(Model model) {
		long userID = (long) 1;
		model.addAttribute("cartForm", uservice.getShoppingCartByUserID(userID));
		//model.addAttribute("cartForm", uservice.getShoppingCartByUserID(userID));
		//System.out.println(uservice.getShoppingCartByUserID(userID));
		return "product/checkout";
	}
	
	@GetMapping("/placeorder")
	public String placeOrder(Model model) {
		long userID = (long) 1;
		User user = null;
		user = uservice.findUserByUserId(1L);
		ShoppingCart cart = uservice.getShoppingCartByUserID(userID);
		if(cart.getCartDetails()!=null) {
			Orders neworder = new Orders(LocalDate.now(), null, user,null);
			uservice.saveOrder(neworder);
			
			List<OrderDetails> orderDetailList = new ArrayList<>();
			for (ShoppingCartDetails cardetail : cart.getCartDetails()) {
				OrderDetails newOrderDetail= new OrderDetails(cardetail.getQuantity(), cardetail.getProduct(), neworder);
				orderDetailList.add(newOrderDetail);
			}
			uservice.saveOrderDetailsList(orderDetailList);
		}
		return "product/orderconfirm";
	}
}
