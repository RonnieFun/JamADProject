
package sg.edu.iss.jam.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import java.util.List;


import org.hibernate.criterion.Distinct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.MethodInvocationRecorder.Recorded.ToCollectionConverter;
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
  
  	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    Set<Object> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(keyExtractor.apply(t));
	}
	
	
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
		
		
		System.out.println();
		System.out.println("IDs of people who follow me: ");
		List<Subscribed> subs = srepo.getArtistSubscribed(user.getUserID());
		subs.stream().forEach(x->System.out.print(" " + x.getSubscriber().getUserID()));


		Long count = uService.getItemCountByUserID(userDetails.getUserId());
		model.addAttribute("count", count);
		
		return "home";
	}
	

	@RequestMapping("/subscribers")
	public String viewSubs(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
		User user = uService.findUserByUserId(userDetails.getUserId());
		
		List<Subscribed> mySubs = srepo.getArtistSubscribed(user.getUserID());
		mySubs.stream().forEach(x->System.out.println("mySubsStart: " + x.getSubscriber().getUserID()));
		
		List<Subscribed> subscribers = srepo.getArtistSubscribed(user.getUserID());
		List<Subscribed> unsubs = srepo.getArtistUnSubscribed(user.getUserID());
		Set<Long> confirmedSubs = new HashSet<>();
		List<Subscribed> tobeRemoved = new ArrayList<>();
		for(Subscribed s : subscribers) {
			if(!confirmedSubs.add(s.getSubscriber().getUserID())) {
				tobeRemoved.add(s);
			}
		}

		unsubs.stream().collect(Collectors.toCollection(()->tobeRemoved));
		System.out.println("TBR size: " + tobeRemoved.size());
		System.out.println("mySubs size:" + mySubs.size());
		
		int mySubsSize = mySubs.size();
		
		for(int i = 0; i<mySubsSize; i++) {
			for(int j = 0; j<tobeRemoved.size(); j++) {
				if(mySubs.get(i).getSubscriber().getUserID() == tobeRemoved.get(j).getSubscriber().getUserID()) {
					mySubs.get(i).setTimeSubscribed(LocalDateTime.now().plusYears(1));
				}
			}
		}
				
		Iterator<Subscribed> itr = mySubs.iterator();
		while(itr.hasNext()) {
			Subscribed s = itr.next();
			if(s.getTimeSubscribed().isAfter(LocalDateTime.now())) {
				itr.remove();
			}
		}
		mySubs.stream().forEach(x->System.out.println("ConfirmedSubs: " + x.getSubscriber().getUserID()));
		
		List<Subscribed> mySubCheck = 	subscribers.stream()
				.filter(y->mySubs.stream()
						.noneMatch(x->x.getArtist().getUserID()==(y.getArtist().getUserID())
								&& x.getSubscriber().getUserID()==(y.getSubscriber().getUserID())))
				.filter(distinctByKey(x->x.getSubscriber().getUserID()))
				.collect(Collectors.toList());
		
		mySubCheck.stream().forEach(x->System.out.println("subCheckResult: " + x.getSubscribedID()));
		
		List<Subscribed> latestStatus = new ArrayList<>();
		Subscribed latest = new Subscribed();
		latest.setTimeSubscribed(LocalDateTime.now().minusYears(1));
		Subscribed latestTemp = latest;
		for(Subscribed s : mySubCheck) {
			List<Subscribed> temp = srepo.findAllSubscribedBySubId(s.getSubscriber().getUserID());
			for(Subscribed sb : temp) {
				if(sb.getTimeSubscribed().isAfter(latestTemp.getTimeSubscribed()) && sb.isSubscribed()==true && sb.getArtist().getUserID() == user.getUserID()) {
					latestTemp = sb;
					latestStatus.add(sb);
				}
			}
		}
		
		latestStatus.stream().forEach(x->System.out.println("ConfirmedSubsAfterCheck: " + x.getSubscribedID()));
		
		latestStatus.stream().filter(distinctByKey(x->x.getSubscriber().getUserID())).collect(Collectors.toCollection(()->mySubs));
			
		List<User> subUsers = new ArrayList<>();
		
				
		for(Subscribed s : mySubs) {
			
			Optional<User> ou = urepo.findById(s.getSubscriber().getUserID());
			User u = ou.get();
			subUsers.add(u);
			
		}
				
		for(Subscribed s : mySubs) {
			System.out.println("mySubsFinal" + s.getSubscriber().getUserID());
		}
		
		model.addAttribute("subscribers", subUsers);
		model.addAttribute("user", uService.findUserByUserId(userDetails.getUserId()));
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("followers", ((srepo.getArtistSubscribed(user.getUserID())).size() - (srepo.getArtistUnSubscribed(user.getUserID())).size()));
		model.addAttribute("following", ((srepo.getSubscriptions(user.getUserID())).size() - srepo.getMyUnsubscribe(user.getUserID()).size()));
	
		return "subscribers";
	}
	
	@RequestMapping("/following")
	public String viewFollowing(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
		User user = uService.findUserByUserId(userDetails.getUserId());
		
		List<Subscribed> myFollowings = srepo.getSubscriptions(user.getUserID());
		myFollowings.stream().forEach(x->System.out.println("myFollowingStart: " + x.getArtist().getUserID()));
		List<Subscribed> following = srepo.getSubscriptions(user.getUserID());
		List<Subscribed> unfollows = srepo.getMyUnsubscribe(user.getUserID());
		Set<Long> confirmedFollowings = new HashSet<>();
		List<Subscribed> tobeRemoved = new ArrayList<>();
		for(Subscribed s : following) {
					
					if(!confirmedFollowings.add(s.getArtist().getUserID())) {
						tobeRemoved.add(s);
					}
				}
		unfollows.stream().collect(Collectors.toCollection(()->tobeRemoved));
		
		for(int i = 0; i<myFollowings.size(); i++) {
			for(int j = 0; j<tobeRemoved.size(); j++) {
				if(myFollowings.get(i).getArtist().getUserID() == tobeRemoved.get(j).getArtist().getUserID()) {
					myFollowings.remove(i);
				}
			}
		}
		
		int myFollowSize = myFollowings.size();
				
				for(int i = 0; i<myFollowSize; i++) {
					for(int j = 0; j<tobeRemoved.size(); j++) {
						if(myFollowings.get(i).getArtist().getUserID() == tobeRemoved.get(j).getArtist().getUserID()) {
							myFollowings.get(i).setTimeSubscribed(LocalDateTime.now().plusYears(1));
						}
					}
				}
						
				Iterator<Subscribed> itr = myFollowings.iterator();
				while(itr.hasNext()) {
					Subscribed s = itr.next();
					if(s.getTimeSubscribed().isAfter(LocalDateTime.now())) {
						itr.remove();
					}
				}
		myFollowings.stream().forEach(x->System.out.println("ConfirmedSubs: " + x.getSubscriber().getUserID()));
		
		
		myFollowings.stream().forEach(x->System.out.println("myFollowingRemove: " + x.getArtist().getUserID()));
				
				List<Subscribed> myFollowingCheck = following.stream()
						.filter(y->unfollows.stream()
								.anyMatch(x->x.getArtist().getUserID().equals(y.getArtist().getUserID())
										&& x.getSubscriber().getUserID().equals(y.getSubscriber().getUserID())
										&& x.getTimeSubscribed().isBefore(y.getTimeSubscribed())))
						.filter(distinctByKey(x->x.getArtist().getUserID()))
						.collect(Collectors.toList());

	
		myFollowingCheck.stream().forEach(x->System.out.println("followingCheckResult: " + x.getSubscribedID()));
		
		List<Subscribed> latestStatus = new ArrayList<>();
		Subscribed latest = new Subscribed();
		latest.setTimeSubscribed(LocalDateTime.now().minusYears(1));
		Subscribed latestTemp = latest;
		for(Subscribed s : myFollowingCheck) {
			List<Subscribed> temp = srepo.findAllFollowingByArtistId(s.getArtist().getUserID());
			for(Subscribed sb : temp) {
				if(sb.getTimeSubscribed().isAfter(latestTemp.getTimeSubscribed()) && sb.isSubscribed()==true && sb.getSubscriber().getUserID() == user.getUserID()) {
					latestTemp = sb;
					latestStatus.add(sb);
				}
			}
		}
		
		latestStatus.stream().forEach(x->System.out.println("ConfirmedFollowingAfterCheck: " + x.getSubscribedID()));
		latestStatus.stream().filter(distinctByKey(x->x.getArtist().getUserID())).collect(Collectors.toCollection(()->myFollowings));

					List<User> followUsers = new ArrayList<>();			
					for(Subscribed s : myFollowings) {
						
						Optional<User> ou = urepo.findById(s.getArtist().getUserID());
						User u = ou.get();
						followUsers.add(u);	
					}
							
					 myFollowings.stream().forEach(x->System.out.println("mySubsFinal" + x.getArtist().getUserID()));
					 
		model.addAttribute("followingUsers", followUsers);
		model.addAttribute("user", uService.findUserByUserId(userDetails.getUserId()));
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("followers", ((srepo.getArtistSubscribed(user.getUserID())).size() - (srepo.getArtistUnSubscribed(user.getUserID())).size()));
		model.addAttribute("following", ((srepo.getSubscriptions(user.getUserID())).size() - srepo.getMyUnsubscribe(user.getUserID()).size()));
						
		
		return "following";
	}
	//--------------------Posts Methods------------------//
	
		@GetMapping("/getposts/{userid}")
		public String getPosts(Model model,@AuthenticationPrincipal MyUserDetails userDetails,@PathVariable("userid")Long userid) {
			
			//logged in user
			User loggedinuser = uService.findUserByUserId(userDetails.getUserId());
			
			//User who's page you are viewing
			User targetuser = uService.findUserByUserId(userid);
			
			model.addAttribute("posts",hService.getPostsbyID(targetuser.getUserID()));
			model.addAttribute("profileUrl", userDetails.getProfileUrl());
			model.addAttribute("firstName", userDetails.getFirstName());
			
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
			model.addAttribute("profileUrl", userDetails.getProfileUrl());
			model.addAttribute("firstName", userDetails.getFirstName());
			
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
			
			
			return "redirect:/home/";
		}

}

