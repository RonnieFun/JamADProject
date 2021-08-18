package sg.edu.iss.jam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/home/artist")
	public String goToArtistHome(Model model) {
		return "homeArtist";
	}
	
	@GetMapping("/home/user")
	public String goToUserHome(Model model) {
		return "homeUser";
	}

}
