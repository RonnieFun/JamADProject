package sg.edu.iss.jam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.security.MyUserDetails;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.UserInterface;



@Controller
@RequestMapping("/home")
@PreAuthorize("hasRole('Customer')")
public class HomeController {
	
	@Autowired 
	UserInterface uService;
	
	@Autowired
	ArtistInterface adService;
	
	@GetMapping("/")
	public String goToHome(Model model,@AuthenticationPrincipal MyUserDetails userDetails) {
		
		User user = null;
		user = uService.findUserByUserId(userDetails.getUserId());
		
		model.addAttribute("user", uService.findUserByUserId(userDetails.getUserId()));
		model.addAttribute("profileUrl", user.getProfileUrl());
		Long count = uService.getItemCountByUserID(userDetails.getUserId());
		model.addAttribute("count", count);
		
		return "home";
	}
	

}
