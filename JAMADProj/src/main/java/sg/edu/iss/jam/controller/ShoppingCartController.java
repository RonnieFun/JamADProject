package sg.edu.iss.jam.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.edu.iss.jam.model.OrderDetails;
import sg.edu.iss.jam.model.Orders;
import sg.edu.iss.jam.model.Payment;
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
		Payment payment = new Payment();
		model.addAttribute("newPayment", payment);
		model.addAttribute("cartForm", uservice.getShoppingCartByUserID(userID));
		return "product/checkout";
	}
	
	@PostMapping("/placeorder")
	public String placeOrder(Model model,@Valid @ModelAttribute("payment") Payment payment , BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "product/checkout";
		}
		long userID = (long) 1;
		User user = null;
		user = uservice.findUserByUserId(1L);
		//insert order depending on shoppingcart
		ShoppingCart cart = uservice.getShoppingCartByUserID(userID);
		if(cart.getCartDetails()!=null) {
			Orders neworder = new Orders(LocalDate.now(), payment.getAddress(), user,null);
			uservice.saveOrder(neworder);
			
			List<OrderDetails> orderDetailList = new ArrayList<>();
			for (ShoppingCartDetails cardetail : cart.getCartDetails()) {
				OrderDetails newOrderDetail= new OrderDetails(cardetail.getQuantity(), cardetail.getProduct(), neworder);
				orderDetailList.add(newOrderDetail);
				//delete cart detail
				uservice.deleteCartDetails(cardetail);
			}
			uservice.saveOrderDetailsList(orderDetailList);
		}
		//save payment
		payment.setUser(user);
		uservice.savePayement(payment);
		return "product/orderconfirm";
	}
	
	// ajax call
	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
	@ResponseBody
	public Long add(@RequestParam(value = "productId") Long productId) throws Exception {

		long userID = (long) 1;
		ShoppingCartDetails carddetail = null;
	      if (productId != null) {
	    	 ShoppingCart cart = uservice.getShoppingCartByUserID(userID);
	    	 carddetail = uservice.getCartDetailByProductID(productId, cart.getShoppingCartID());
	    	 if(carddetail!=null) {
	    		 carddetail.setQuantity(carddetail.getQuantity()+1);
	    		 uservice.saveCartDetails(carddetail);
	    	 }
	    	 else {
	    		 carddetail = new ShoppingCartDetails(1,uservice.findProduct(productId), uservice.getShoppingCartByUserID(userID));
	    		 uservice.saveCartDetails(carddetail);
			}
	      }
		Long count  = uservice.getItemCountByUserID(userID);
		return count;
	}
	
	@RequestMapping(value="/updatecartitemqty", method=RequestMethod.PUT)
	@ResponseBody
	public void updateItem(@RequestParam(value = "productId") Long productId ,@RequestParam(value = "quantity") Integer quantity) throws Exception {
		long userID = (long) 1;
		ShoppingCart cart = uservice.getShoppingCartByUserID(userID);
		if(quantity<=0) {
			uservice.removeCartDetails(productId, cart.getShoppingCartID());
		}
		else {
			ShoppingCartDetails carddetail = uservice.getCartDetailByProductID(productId,cart.getShoppingCartID());
			carddetail.setQuantity(quantity);
			uservice.saveCartDetails(carddetail);
		}
		
	}
	
}
