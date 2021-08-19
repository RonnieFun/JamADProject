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
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.service.ArtistInterface;

@Controller
public class ProductController {
	@Autowired
	ArtistInterface aservice;

	@GetMapping("/carthometab/{artistid}")
	public String shoppingCartHome(Model model, @PathVariable long artistid) {
		// long userID = (long) 1;
		User artist = null;
		artist = aservice.getArtistByID(artistid);
		model.addAttribute("status", "home");
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
		model.addAttribute("artistId", artist.getUserID());
		model.addAttribute("artistName", artist.getDisplayName());
		model.addAttribute("profileUrl", artist.getProfileUrl());
		model.addAttribute("productList", aservice.getProductListByArtistIDAndCategory(artistid, category));
		return "carthometab";
	}

	@GetMapping("/product/details/{productid}")
	public String productDetail(Model model, @PathVariable long productid) {
		model.addAttribute("product", aservice.getProductByID(productid));
		return "product/productdetail";
	}

	@GetMapping("/cartothertab")
	public String shoppingCartOther(Model model) {
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

	@GetMapping("/shop")
	public String shopLandingPage(Model model) {
		List<Object[]> topAllProductsBySale = aservice.getTopAllProductsInPastWeekByOrderDetailsQuantity(8);
//		List<Object[]> topMusicCollectionProductsBySale = aservice.getTopAllProductsInPastWeekByOrderDetailsQuantity(4);
//		List<Object[]> topMerchandiseProductsBySale = aservice.getTopAllProductsInPastWeekByOrderDetailsQuantity(4);
//		List<Object[]> topClothingProductsBySale = aservice.getTopAllProductsInPastWeekByOrderDetailsQuantity(4);
		Map<Product, Long> productsAndCountShop = new HashMap<Product, Long>();
		for (Object[] object : topAllProductsBySale) {
			productsAndCountShop.put((Product) object[0], (Long) object[1]);
		}
		model.addAttribute("productsAndCountShop", productsAndCountShop);

		return "shoplandingpage";
	}
}
