package sg.edu.iss.jam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.edu.iss.jam.model.Category;
import sg.edu.iss.jam.model.Payment;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.UserInterface;

@Controller
public class ProductController {
	@Autowired
	ArtistInterface aservice;

	@Autowired
	UserInterface uservice;

	@GetMapping("/carthometab/{artistid}")
	public String shoppingCartHome(Model model, @PathVariable long artistid) {
		// long userID = (long) 1;
		User artist = null;
		artist = aservice.getArtistByID(artistid);
		// userId need to replace
		Long count = uservice.getItemCountByUserID(artistid);
		model.addAttribute("status", "home");
		model.addAttribute("count", count);
		model.addAttribute("artistId", artist.getUserID());
		model.addAttribute("artistName", artist.getDisplayName());
		model.addAttribute("profileUrl", artist.getProfileUrl());
		model.addAttribute("productList", aservice.getProductListByArtistID(artistid));
		return "carthometab";
	}

	@GetMapping("/carthometab/{artistid}/{category}")
	public String getProductByCategory(Model model, @PathVariable long artistid, @PathVariable Category category) {
		// long userID = (long) 1;
		User artist = null;
		artist = aservice.getArtistByID(artistid);
		// userId need to replace
		Long count = uservice.getItemCountByUserID(artistid);

		model.addAttribute("status", category);
		model.addAttribute("count", count);
		model.addAttribute("artistId", artist.getUserID());
		model.addAttribute("artistName", artist.getDisplayName());
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
		String Result ="";
		Product product =  aservice.getProductByID(productId);
		System.out.println(product);
		if (product!=null) {  
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


	@GetMapping("/cartothertab")
	public String shoppingCartOther(Model model) {
		Payment payment = new Payment();
		model.addAttribute("newPayment", payment);
		return "product/checkout";
	}

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

	// TODO awaiting sessions and userid
	@GetMapping("/shop")
	public String shopLandingPage(Model model) {
		User user = uservice.findUserByUserId(2L);

		List<Object[]> topAllProductsBySale = uservice.getTopAllProductsInPastWeekByOrderDetailsQuantity(8);
		List<Object[]> topMusicCollectionProductsBySale = uservice
				.getTopMusicCollectionProductsInPastWeekByOrderDetailsQuantity(4);
		List<Object[]> topMerchandiseProductsBySale = uservice
				.getTopMerchandiseProductsInPastWeekByOrderDetailsQuantity(4);
		List<Object[]> topClothingProductsBySale = uservice.getTopClothingProductsInPastWeekByOrderDetailsQuantity(4);

		Map<Product, Long> allProductsAndCountShop = new HashMap<Product, Long>();
		Map<Product, Long> musicCollectionProductsAndCountShop = new HashMap<Product, Long>();
		Map<Product, Long> merchandiseProductsAndCountShop = new HashMap<Product, Long>();
		Map<Product, Long> clothingProductsAndCountShop = new HashMap<Product, Long>();
		for (Object[] object : topAllProductsBySale) {
			allProductsAndCountShop.put((Product) object[0], (Long) object[1]);
		}
		for (Object[] object : topMusicCollectionProductsBySale) {
			musicCollectionProductsAndCountShop.put((Product) object[0], (Long) object[1]);
		}
		for (Object[] object : topMerchandiseProductsBySale) {
			merchandiseProductsAndCountShop.put((Product) object[0], (Long) object[1]);
		}
		for (Object[] object : topClothingProductsBySale) {
			clothingProductsAndCountShop.put((Product) object[0], (Long) object[1]);
		}
		Long count = uservice.getItemCountByUserID(2L);

		model.addAttribute("allProductsAndCountShop", allProductsAndCountShop);
		model.addAttribute("musicCollectionProductsAndCountShop", musicCollectionProductsAndCountShop);
		model.addAttribute("merchandiseProductsAndCountShop", merchandiseProductsAndCountShop);
		model.addAttribute("clothingProductsAndCountShop", clothingProductsAndCountShop);
		model.addAttribute("count", count);
		model.addAttribute("user", user);

		return "shoplandingpage";
	}

	// TODO awaiting sessions and userid
	@GetMapping("/shop/allproducts")
	public String allProductsLandingPage(Model model) {
		User user = uservice.findUserByUserId(2L);
		List<Object[]> topAllProductsBySale = uservice.getAllProducts();

		Map<Product, Long> allProductsAndCountShop = new HashMap<Product, Long>();
		for (Object[] object : topAllProductsBySale) {
			allProductsAndCountShop.put((Product) object[0], (Long) object[1]);
		}
		Long count = uservice.getItemCountByUserID(2L);

		model.addAttribute("allProductsAndCountShop", allProductsAndCountShop);
		model.addAttribute("category", "allProducts");
		model.addAttribute("count", count);

		model.addAttribute("user", user);

		return "shopcategorylandingpage";
	}

	// TODO awaiting sessions and userid
	@GetMapping("/shop/musiccollections")
	public String musicCollectionsLandingPage(Model model) {
		User user = uservice.findUserByUserId(2L);
		List<Object[]> topAllProductsBySale = uservice.getAllMusicCollections();

		Map<Product, Long> allProductsAndCountShop = new HashMap<Product, Long>();
		for (Object[] object : topAllProductsBySale) {
			allProductsAndCountShop.put((Product) object[0], (Long) object[1]);
		}
		Long count = uservice.getItemCountByUserID(2L);

		model.addAttribute("allProductsAndCountShop", allProductsAndCountShop);
		model.addAttribute("category", "musicCollection");
		model.addAttribute("count", count);

		model.addAttribute("user", user);

		return "shopcategorylandingpage";
	}

	// TODO awaiting sessions and userid
	@GetMapping("/shop/merchandise")
	public String merchandiseLandingPage(Model model) {
		User user = uservice.findUserByUserId(2L);
		List<Object[]> topAllProductsBySale = uservice.getAllMerchandise();

		Map<Product, Long> allProductsAndCountShop = new HashMap<Product, Long>();
		for (Object[] object : topAllProductsBySale) {
			allProductsAndCountShop.put((Product) object[0], (Long) object[1]);
		}
		Long count = uservice.getItemCountByUserID(2L);

		model.addAttribute("allProductsAndCountShop", allProductsAndCountShop);
		model.addAttribute("category", "merchandise");
		model.addAttribute("count", count);

		model.addAttribute("user", user);

		return "shopcategorylandingpage";
	}

	// TODO awaiting sessions and userid
	@GetMapping("/shop/clothing")
	public String clothingLandingPage(Model model) {
		User user = uservice.findUserByUserId(2L);
		List<Object[]> topAllProductsBySale = uservice.getAllClothing();

		Map<Product, Long> allProductsAndCountShop = new HashMap<Product, Long>();
		for (Object[] object : topAllProductsBySale) {
			allProductsAndCountShop.put((Product) object[0], (Long) object[1]);
		}
		Long count = uservice.getItemCountByUserID(2L);

		model.addAttribute("allProductsAndCountShop", allProductsAndCountShop);
		model.addAttribute("category", "clothing");
		model.addAttribute("count", count);

		model.addAttribute("user", user);

		return "shopcategorylandingpage";
	}
}
