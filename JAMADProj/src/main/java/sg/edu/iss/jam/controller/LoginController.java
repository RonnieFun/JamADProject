package sg.edu.iss.jam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.service.UserInterface;

@Controller
@RequestMapping("/login")
public class LoginController {
		
		@Autowired
		UserInterface userService;
		
		@RequestMapping("/")
		public String login(Model model) {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
				return"index";
			}
			return "redirect:/";
		}
		
		@RequestMapping("/signup")
		public String signup(Model model) {
			User user = new User();
			model.addAttribute("user", user);
			return "signupForm";
		}
		
		@RequestMapping("/save")
		public String saveUserForm(@ModelAttribute("user") User user, BindingResult bindingResult) {
			userService.updateUser(user);
			return"signup2";
		}
		


}