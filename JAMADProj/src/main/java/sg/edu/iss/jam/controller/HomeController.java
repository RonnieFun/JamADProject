package sg.edu.iss.jam.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.jam.model.Post;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.SubscribedRepository;
import sg.edu.iss.jam.repo.UserRepository;
import sg.edu.iss.jam.security.MyUserDetails;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.ConsumerInterface;
import sg.edu.iss.jam.service.HomeInterface;
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
	
	@Autowired
	HomeInterface hService;
	
	@GetMapping("/")
	public String goToHome(Model model,@AuthenticationPrincipal MyUserDetails userDetails) {
		
		User user = null;
		user = uService.findUserByUserId(userDetails.getUserId());
		
		model.addAttribute("user", uService.findUserByUserId(userDetails.getUserId()));
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("bannerUrl", user.getBannerUrl());
		
		
		model.addAttribute("followers", ((srepo.getArtistSubscribed(user.getUserID())).size() - (srepo.getArtistUnSubscribed(user.getUserID())).size()));
		model.addAttribute("following", ((srepo.getSubscriptions(user.getUserID())).size() - srepo.getMyUnsubscribe(user.getUserID()).size()));
		

		System.out.println("Total subscribers: " + ((srepo.getArtistSubscribed(user.getUserID())).size() - (srepo.getArtistUnSubscribed(user.getUserID())).size()));
		System.out.println("Total following: " + ((srepo.getSubscriptions(user.getUserID())).size() - srepo.getMyUnsubscribe(user.getUserID()).size()));
		
//		System.out.println("IDs of people I follow:" );
//		List<Subscribed> following = srepo.getListofFollowingByUserId(user.getUserID());
//		following.stream().forEach(x->System.out.print(" " + x.getTargetId()));
		
		System.out.println();
		System.out.println("IDs of people who follow me: ");
		List<Subscribed> subs = srepo.getArtistSubscribed(user.getUserID());
		subs.stream().forEach(x->System.out.print(" " + x.getSubscriber().getUserID()));


		Long count = uService.getItemCountByUserID(userDetails.getUserId());
		model.addAttribute("count", count);
		model.addAttribute("user",user);

		
		return "home";
	}
	
	
	
	//--------------------Posts Methods------------------//
	
	@GetMapping("/getposts/{userid}")
	public String getPosts(Model model,@AuthenticationPrincipal MyUserDetails userDetails,@PathVariable("userid")Long userid) {
		
		//logged in user
		User loggedinuser = uService.findUserByUserId(userDetails.getUserId());
		
		//User who's page you are viewing
		User targetuser = uService.findUserByUserId(userid);
		
		model.addAttribute("posts",hService.getPostsbyID(targetuser.getUserID()));
		
		return "/Fragments/PostContent";
	}
	
	
	//Ajax Controller to post/edit Content
	@PostMapping("/postpost")
	public String postPosts(Model model,@AuthenticationPrincipal MyUserDetails userDetails,
			@RequestParam(value = "submittedContent") String submittedContent,
			@Nullable@RequestParam(value = "postID") Long postID) {
		
		//logged in user
		User loggedinuser = uService.findUserByUserId(userDetails.getUserId());
		
	    Post post = new Post();
		
		//Add New Post
		if (postID == null) {
			post.setContent(submittedContent);
			post.setDateTime(LocalDateTime.now().toString());
			post.setUser(loggedinuser);
			hService.SavePost(post);
		}else {
			hService.getPostbyID(postID);
			post.setContent(submittedContent);
			post.setDateTime(LocalDateTime.now().toString());
			post.setUser(loggedinuser);
			hService.SavePost(post);
		}
		
		model.addAttribute("posts",hService.getPostsbyID(loggedinuser.getUserID()));
		
		return "/Fragments/PostContent"; 
	}
	
	@PostMapping("/deletepost")
	public String deletePosts(Model model,@AuthenticationPrincipal MyUserDetails userDetails,
			@RequestParam(value = "postID") Long postID) {

		//logged in user
		User loggedinuser = uService.findUserByUserId(userDetails.getUserId());
		
		if (hService.deletepost(hService.getPostbyID(postID))) {
			model.addAttribute("error","Post not deleted");
		}

		model.addAttribute("posts",hService.getPostsbyID(loggedinuser.getUserID()));
		
		return "/Fragments/PostContent";
	}
	
	
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
//	

}
