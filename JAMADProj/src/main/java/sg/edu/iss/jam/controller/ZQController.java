package sg.edu.iss.jam.controller;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.UserInterface;

@Controller
public class ZQController {

	@Autowired
	UserInterface uservice;
	
	@Autowired
	ArtistInterface aservice;
	
	
	// By Zhao Qi
	@GetMapping("/watchvideozq")
	public String watchVideo( Model model) {
		// currently assume the userID = 3, 
		Long customerId = (long) 2;
		User customer = uservice.findById(customerId);
		
		// this method aims to show how to subscribe an Artist,
		// so we won't show media details
		
		Long artistId = (long) 1;
		User jayChou = aservice.findById(artistId);
		String artistName = jayChou.getFirstName() + " " + jayChou.getLastName();
		
		boolean subscribeStatus = false;
		
		
		for(Subscribed s: customer.getSubscribers()) {
			// if the customer already subscribed the artist, it shows true
			if (s.getTargetId() == artistId) {
				subscribeStatus = true;
			}
		}
		model.addAttribute("subscribeStatus", subscribeStatus);
		model.addAttribute("artistId", artistId);
		model.addAttribute("artistName", artistName);
		return "watchvideozq";
	}
	
	@PostMapping("/subscribe")
	@ResponseBody
	public String subscribeArtist(@RequestParam(value = "artistId") Long artistId) throws Exception{
		
		// currently assume the userID = 2,
		Long customerId = (long) 2;
		User customer = uservice.findById(customerId);
		User artist = aservice.findById(artistId);
		
		if (artist == null || customer == null || customerId == artistId) {
			return "redirect:/watchvideozq";
		}
			
		// for artist, add the customer to the subscribed collection
		Collection<Subscribed> customers_subscribed_me = new HashSet<Subscribed>();
		Subscribed customer_subscribed_me = new Subscribed();
		customer_subscribed_me.setTargetId(customerId);
		customer_subscribed_me.setUser(artist);	
		customers_subscribed_me.add(customer_subscribed_me);
		artist.setSubscribers(customers_subscribed_me);
		
		// For customer, add the artist to the subscribed collection
		Collection<Subscribed> artists_I_subscribed = new HashSet<Subscribed>();
		Subscribed artist_I_subscribed = new Subscribed();
		artist_I_subscribed.setTargetId(artistId);
		artist_I_subscribed.setUser(customer);	
		artists_I_subscribed.add(artist_I_subscribed);
		customer.setSubscribers(artists_I_subscribed);
		
		
		aservice.saveUser(artist);
		aservice.saveSubscribed(customer_subscribed_me);
		
		uservice.saveUser(customer);
		uservice.saveSubscribed(artist_I_subscribed);
		
		return "redirect:/watchvideozq";
	
	}
	
	
	
	@PostMapping("/unsubscribe")	
	@ResponseBody
	public String unsubscribeArtist(@RequestParam(value = "artistId") Long artistId) throws Exception {
		
		// currently assume the userID = 3,
		Long customerId = (long) 2;
		User customer = uservice.findById(customerId);
		User artist = aservice.findById(artistId);
		
		if (artist == null || customer == null || customerId == artistId) {
			return "redirect:/watchvideozq";
		}
		
		// for artist, remove the customer from subscribed collection
		Collection<Subscribed> customers_subscribed_me = artist.getSubscribers();
		for (Subscribed s: customers_subscribed_me) {
			if (s.getTargetId() == customerId) {
				aservice.deleteSubscribed(s);
			}
		}
		
		// for customer, remove the artist from subscribed collection
		Collection<Subscribed> artists_I_subscribed = customer.getSubscribers();
		for (Subscribed s: artists_I_subscribed) {
			if (s.getTargetId() == artistId) {
				uservice.deleteSubscribed(s);
			}
		}		
		return "redirect:/watchvideozq";
				
	}

}

