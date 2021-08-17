package sg.edu.iss.jam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import sg.edu.iss.jam.model.Category;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.service.ArtistInterface;

@Controller
public class ProductController {
	@Autowired
	ArtistInterface aservice;
	
	@GetMapping("/carthometab/{artistid}")
	public String shoppingCartHome(Model model, @PathVariable long artistid) {
		//long userID = (long) 1;
		User artist =null;
		artist =  aservice.getArtistByID(artistid);
		model.addAttribute("artistId", artist.getUserID());
		model.addAttribute("artistName", artist.getDisplayName());
		model.addAttribute("profileUrl", artist.getProfileUrl());
		model.addAttribute("productList", aservice.getProductListByArtistID(artistid));
		return "carthometab";
	}
	
	@GetMapping("/carthometab/{artistid}/{category}")
	public String getProductByCategory(Model model, @PathVariable long artistid ,@PathVariable Category category) {
		//long userID = (long) 1;
		User artist =null;
		artist =  aservice.getArtistByID(artistid);
		model.addAttribute("artistId", artist.getUserID());
		model.addAttribute("artistName", artist.getDisplayName());
		model.addAttribute("profileUrl", artist.getProfileUrl());
		model.addAttribute("productList", aservice.getProductListByArtistIDAndCategory(artistid,category));
		return "carthometab";
	}
	
	@GetMapping("/product/details/{productid}")
	public String productDetail(Model model, @PathVariable long productid) {
		model.addAttribute("product", aservice.getProductByID(productid));
		return "product/productdetail";
	}
	
	@GetMapping("/cartothertab")
	public String shoppingCartOther(Model model) {
		return "cartothertab";
	}

}
