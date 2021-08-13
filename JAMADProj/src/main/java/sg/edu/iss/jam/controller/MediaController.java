package sg.edu.iss.jam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sg.edu.iss.jam.service.UserInterface;

@Controller
public class MediaController {

	@Autowired
	UserInterface uservice;
	
	@GetMapping("/watchvideos")
	public String watchvideos(Model model) {
		return "userwatchvideo";
	}
	
	
}
