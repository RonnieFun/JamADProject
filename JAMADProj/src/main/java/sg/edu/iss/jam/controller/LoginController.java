package sg.edu.iss.jam.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.jam.model.Role;
import sg.edu.iss.jam.model.Roles;
import sg.edu.iss.jam.model.ShoppingCart;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.RolesRepository;
import sg.edu.iss.jam.repo.ShoppingCartRepository;
import sg.edu.iss.jam.service.UserInterface;

@Controller
@RequestMapping("/login")
public class LoginController {
		
		@Autowired
		UserInterface userService;
		
		@Autowired
		RolesRepository rrepo;
		
		@Autowired
		ShoppingCartRepository screpo;
		
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
			model.addAttribute("isArtist", user.isArtist());
			return "signupForm";
		}
		
		@RequestMapping("/save")
		public String saveUserForm(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
			Collection<Roles> roles = new ArrayList<Roles>();
			
			String rawPassword = user.getPassword();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword = encoder.encode(rawPassword);
			user.setPassword(encodedPassword);
			user.setEnabled(true);
			user.setBannerUrl("/images/JAM_LOGO.png");
			
			if(user.getProfileUrl()==null) {
				user.setProfileUrl("/images/default_user.jpg");
			}
			
			if(user.isArtist()==true) {
				user.setRoles(roles);
				Roles a = new Roles(Role.Artist, user);
				ShoppingCart s = new ShoppingCart(user,null);
				userService.updateUser(user);
				rrepo.save(a);
				screpo.save(s);
				model.addAttribute("user", userService.findUserByUserId(user.getUserID()));
				model.addAttribute("profileUrl", user.getProfileUrl());
				System.out.println(user.getUserID());
				return"signup2";
			}
			
			else {
				user.setRoles(roles);
				Roles b = new Roles(Role.Customer, user);
				ShoppingCart s = new ShoppingCart(user,null);
				userService.updateUser(user);
				rrepo.save(b);
				screpo.save(s);
				return"successSignUp";
			}
				
		}
		
		@RequestMapping("/update")
		public String saveArtistForm(@ModelAttribute("user") User user, BindingResult bindingResult) {
			System.out.println(user.getUserID());
			userService.updateUser(user);
			return"successSignUp";
		}
		


}