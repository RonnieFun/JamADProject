package sg.edu.iss.jam.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.SubscribedRepository;
import sg.edu.iss.jam.repo.UserRepository;
import sg.edu.iss.jam.security.MyUserDetails;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.ConsumerInterface;
import sg.edu.iss.jam.service.UserInterface;



@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired 
	UserInterface uService;
	
	@Autowired
	ArtistInterface adService;
	
	@Autowired
	ConsumerInterface cService;
	
	@Autowired
	SubscribedRepository srepo;
	
	@Autowired
	UserRepository urepo;
	
//	@GetMapping("/")
//	public String goToHome(Model model,@AuthenticationPrincipal MyUserDetails userDetails) {
//		
//		User user = null;
//		user = uService.findUserByUserId(userDetails.getUserId());
//		
//		model.addAttribute("user", uService.findUserByUserId(userDetails.getUserId()));
//		model.addAttribute("profileUrl", user.getProfileUrl());
//		model.addAttribute("bannerUrl", user.getBannerUrl());
//		model.addAttribute("followers", srepo.countByTargetId(user.getUserID()));
//		model.addAttribute("following", cService.getFollowingByUserId(user.getUserID()));
//		
//
//		System.out.println("Total subscribers: " + srepo.countByTargetId(user.getUserID()));
//		System.out.println("Total following: " + cService.getFollowingByUserId(user.getUserID()));
//		
//		System.out.println("IDs of people I follow:" );
//		List<Subscribed> following = cService.getListofFollowingByUserId(user.getUserID());
//		following.stream().forEach(x->System.out.print(" " + x.getTargetId()));
//		
//		System.out.println();
//		System.out.println("IDs of people who follow me: ");
//		List<Subscribed> subs = srepo.findByTargetId(user.getUserID());
//		subs.stream().forEach(x->System.out.print(" " + x.getUser().getUserID()));
//
//
//		Long count = uService.getItemCountByUserID(userDetails.getUserId());
//		model.addAttribute("count", count);
//
//		
//		return "home";
//	}
//	
//	@RequestMapping("/subscribers")
//	public String viewSubs(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
//		User user = uService.findUserByUserId(userDetails.getUserId());
//		List<Subscribed> subs = srepo.findByTargetId(user.getUserID());
//		List<User> subscribers = subs.stream().map(x->x.getUser())
//				.collect(Collectors.toList());
//		
//		model.addAttribute("subscribers", subscribers);
//		model.addAttribute("user", uService.findUserByUserId(userDetails.getUserId()));
//		model.addAttribute("profileUrl", user.getProfileUrl());
//		model.addAttribute("noOfSubs", srepo.countByTargetId(user.getUserID()));
//		model.addAttribute("followers", srepo.countByTargetId(user.getUserID()));
//		model.addAttribute("following", cService.getFollowingByUserId(user.getUserID()));
//		
//	
//	
//		return "subscribers";
//	}
	

}
