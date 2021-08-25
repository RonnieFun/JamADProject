package sg.edu.iss.jam.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.LazyContextVariable;

import sg.edu.iss.jam.model.Category;
import sg.edu.iss.jam.model.OrderDetails;
import sg.edu.iss.jam.model.Orders;
import sg.edu.iss.jam.model.Payment;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.security.MyUserDetails;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.UserInterface;

@Controller
public class ProductController {
	@Autowired
	ArtistInterface aservice;

	@Autowired
	UserInterface uservice;

	@GetMapping("/carthometab/{artistid}")
	public String shoppingCartHome(Model model, @PathVariable long artistid,
			@AuthenticationPrincipal MyUserDetails userDetails) {
		// long userID = (long) 1;
		User artist = null;
		artist = aservice.getArtistByID(artistid);
		// userId need to replace
		Long count = uservice.getItemCountByUserID(userDetails.getUserId());
		model.addAttribute("status", "allProducts");
		model.addAttribute("user", artist);
		model.addAttribute("count", count);
		model.addAttribute("profileUrl", artist.getProfileUrl());
		model.addAttribute("productList", aservice.getProductListByArtistID(artistid));
		return "carthometab";
	}

	@GetMapping("/carthometab/{artistid}/{category}")
	public String getProductByCategory(Model model, @PathVariable long artistid, @PathVariable Category category,
			@AuthenticationPrincipal MyUserDetails userDetails) {
		// long userID = (long) 1;
		User artist = null;
		artist = aservice.getArtistByID(artistid);
		// userId need to replace
		Long count = uservice.getItemCountByUserID(userDetails.getUserId());

		model.addAttribute("status", category.toString());
		model.addAttribute("user", artist);
		model.addAttribute("count", count);
		model.addAttribute("profileUrl", artist.getProfileUrl());
		model.addAttribute("productList", aservice.getPopularProductByCategory(artistid, category));
		return "carthometab";
	}

//	@GetMapping("/carthometab/{artistid}/{category}")
//	public String getPopularProductByCategory(Model model, @PathVariable long artistid, @PathVariable Category category) {
//		// long userID = (long) 1;
//		User artist = null;
//		artist = aservice.getArtistByID(artistid);
//		//userId need to replace
//		Long count  =  uservice.getItemCountByUserID(artistid);
//		model.addAttribute("count", count);
//		model.addAttribute("artistId", artist.getUserID());
//		model.addAttribute("artistName", artist.getDisplayName());
//		model.addAttribute("profileUrl", artist.getProfileUrl());
//		//model.addAttribute("productList", aservice.getProductListByArtistIDAndCategory(artistid, category));
//		return "carthometab";
//	}

	@RequestMapping(value = "/product/details", method = RequestMethod.POST)
	@ResponseBody
	public String productDetail(@RequestParam(value = "productId") Long productId) throws Exception {
		String Result = "";
		Product product = aservice.getProductByID(productId);
		System.out.println(product);
		if (product != null) {
			Result = "{\"Name\":";
			Result += "\"" + product.getProductName() + "\",";
			Result += "\"Description\":\"" + product.getProductDes() + "\",";
			Result += "\"Price\":\"" + product.getProductPrice() + "\",";
			Result += "\"Url\":\"" + product.getProductUrl() + "\"";
			Result += "}";
		} else {
			Result = "Invalid";
		}
		return Result;
	}

//	@GetMapping("/cartothertab")
//	public String shoppingCartOther(Model model) {
//		return "product/orderconfrim";
//	}

	// ajax call
	@RequestMapping(value = "/getAllProduct", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestParam(value = "artistId") Long artistId) throws Exception {

		List<Product> productList = aservice.getProductListByArtistID(artistId);
		System.out.println(productList.size());
		String jsSon = null;
//		ObjectMapper mapper = new ObjectMapper();
//		if (!productList.isEmpty()) {
//			try {
//				jsSon = mapper.writeValueAsString(productList);
//			} catch (JsonGenerationException e) {
//				e.printStackTrace();
//			}
//		}
		return "carthometab";
	}

	@GetMapping("/shop")
	public String shopLandingPage(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = uservice.findUserByUserId(userDetails.getUserId());

		List<Object[]> topAllProductsBySale = uservice.getTopAllProductsInPastWeekByOrderDetailsQuantity(8);
		List<Object[]> topMusicCollectionProductsBySale = uservice
				.getTopMusicCollectionProductsInPastWeekByOrderDetailsQuantity(4);
		List<Object[]> topMerchandiseProductsBySale = uservice
				.getTopMerchandiseProductsInPastWeekByOrderDetailsQuantity(4);
		List<Object[]> topClothingProductsBySale = uservice.getTopClothingProductsInPastWeekByOrderDetailsQuantity(4);

		Map<Product, Long> allProductsAndCountShop = new LinkedHashMap<Product, Long>(16, 0.75F, true);
		Map<Product, Long> musicCollectionProductsAndCountShop = new LinkedHashMap<Product, Long>(16, 0.75F, true);
		Map<Product, Long> merchandiseProductsAndCountShop = new LinkedHashMap<Product, Long>(16, 0.75F, true);
		Map<Product, Long> clothingProductsAndCountShop = new LinkedHashMap<Product, Long>(16, 0.75F, true);
		for (Object[] object : topAllProductsBySale) {
			Product product = (Product) object[0];
			if (product.getProductQty() > 0) {
				allProductsAndCountShop.put((Product) object[0], (Long) object[1]);
			}
		}
		for (Object[] object : topMusicCollectionProductsBySale) {
			Product product = (Product) object[0];
			if (product.getProductQty() > 0) {
				musicCollectionProductsAndCountShop.put((Product) object[0], (Long) object[1]);
			}
		}
		for (Object[] object : topMerchandiseProductsBySale) {
			Product product = (Product) object[0];
			if (product.getProductQty() > 0) {
				merchandiseProductsAndCountShop.put((Product) object[0], (Long) object[1]);
			}
		}
		for (Object[] object : topClothingProductsBySale) {
			Product product = (Product) object[0];
			if (product.getProductQty() > 0) {
				clothingProductsAndCountShop.put((Product) object[0], (Long) object[1]);
			}
		}

		Long count = uservice.getItemCountByUserID(userDetails.getUserId());

		model.addAttribute("allProductsAndCountShop", allProductsAndCountShop);
		model.addAttribute("musicCollectionProductsAndCountShop", musicCollectionProductsAndCountShop);
		model.addAttribute("merchandiseProductsAndCountShop", merchandiseProductsAndCountShop);
		model.addAttribute("clothingProductsAndCountShop", clothingProductsAndCountShop);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("count", count);
		model.addAttribute("user", user);

		return "shoplandingpage";
	}

	// TODO awaiting sessions and userid
	@GetMapping("/shop/allproducts")
	public String allProductsLandingPage(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
		User user = uservice.findUserByUserId(userDetails.getUserId());
		List<Object[]> topAllProductsBySale = uservice.getAllProducts();

		Long count = uservice.getItemCountByUserID(userDetails.getUserId());

		model.addAttribute("allProductsAndCountShop", new LazyContextVariable<Map<Product, Long>>() {
			@Override
			protected Map<Product, Long> loadValue() {
				Map<Product, Long> allProductsAndCountShop = new LinkedHashMap<Product, Long>(16, 0.75F, true);
				for (Object[] object : topAllProductsBySale) {
					Product product = (Product) object[0];
					if (product.getProductQty() > 0) {
						allProductsAndCountShop.put(product, (Long) object[1]);
					}
				}
				return allProductsAndCountShop;
			}
		});
		model.addAttribute("category", "allProducts");
		model.addAttribute("count", count);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("user", user);

		return "shopcategorylandingpage";
	}

	// TODO awaiting sessions and userid
	@GetMapping("/shop/musiccollections")
	public String musicCollectionsLandingPage(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
		User user = uservice.findUserByUserId(userDetails.getUserId());
		List<Object[]> topAllProductsBySale = uservice.getAllMusicCollections();

		Map<Product, Long> allProductsAndCountShop = new LinkedHashMap<Product, Long>(16, 0.75F, true);
		for (Object[] object : topAllProductsBySale) {
			Product product = (Product) object[0];
			if (product.getProductQty() > 0) {
				allProductsAndCountShop.put((Product) object[0], (Long) object[1]);
			}
		}
		Long count = uservice.getItemCountByUserID(userDetails.getUserId());

		model.addAttribute("allProductsAndCountShop", allProductsAndCountShop);
		model.addAttribute("category", "musicCollection");
		model.addAttribute("count", count);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("user", user);

		return "shopcategorylandingpage";
	}

	// TODO awaiting sessions and userid
	@GetMapping("/shop/merchandise")
	public String merchandiseLandingPage(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
		User user = uservice.findUserByUserId(userDetails.getUserId());
		List<Object[]> topAllProductsBySale = uservice.getAllMerchandise();

		Map<Product, Long> allProductsAndCountShop = new LinkedHashMap<Product, Long>(16, 0.75F, true);
		for (Object[] object : topAllProductsBySale) {
			Product product = (Product) object[0];
			if (product.getProductQty() > 0) {
				allProductsAndCountShop.put((Product) object[0], (Long) object[1]);
			}
		}
		Long count = uservice.getItemCountByUserID(userDetails.getUserId());

		model.addAttribute("allProductsAndCountShop", allProductsAndCountShop);
		model.addAttribute("category", "merchandise");
		model.addAttribute("count", count);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("user", user);

		return "shopcategorylandingpage";
	}

	// TODO awaiting sessions and userid
	@GetMapping("/shop/clothing")
	public String clothingLandingPage(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
		User user = uservice.findUserByUserId(userDetails.getUserId());
		List<Object[]> topAllProductsBySale = uservice.getAllClothing();

		Map<Product, Long> allProductsAndCountShop = new LinkedHashMap<Product, Long>(16, 0.75F, true);
		for (Object[] object : topAllProductsBySale) {
			Product product = (Product) object[0];
			if (product.getProductQty() > 0) {
				allProductsAndCountShop.put((Product) object[0], (Long) object[1]);
			}
		}
		Long count = uservice.getItemCountByUserID(userDetails.getUserId());

		model.addAttribute("allProductsAndCountShop", allProductsAndCountShop);
		model.addAttribute("category", "clothing");
		model.addAttribute("count", count);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("user", user);

		return "shopcategorylandingpage";
	}

	// TODO awaiting sessions and userid
	@GetMapping("/purchasehistory")
	public String purchaseHistory(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
		User user = uservice.findUserByUserId(userDetails.getUserId());
		Long count = uservice.getItemCountByUserID(userDetails.getUserId());
		List<Orders> purchaseHistoriesraw = uservice.getPurchaseHistoryByUserId(user.getUserID());
		Map<Orders, Double> purchaseHistories = new LinkedHashMap<Orders, Double>();
		for (Orders purchaseHistory : purchaseHistoriesraw) {
			double totalPrice = 0;
			for (OrderDetails purchaseHistoryOrderDetails : purchaseHistory.getOrderDetails()) {
				totalPrice = totalPrice + purchaseHistoryOrderDetails.getQuantity()
						* purchaseHistoryOrderDetails.getProduct().getProductPrice();
			}
			purchaseHistories.put(purchaseHistory, totalPrice);
		}
		model.addAttribute("purchaseHistories", purchaseHistories);
		model.addAttribute("count", count);
		model.addAttribute("user", user);
		model.addAttribute("profileUrl", user.getProfileUrl());

		return "product/purchasehistory";
	}
}
