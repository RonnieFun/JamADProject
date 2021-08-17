package sg.edu.iss.jam.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.edu.iss.jam.model.Comments;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.UserInterface;

@Controller
public class MediaController {

	@Autowired
	UserInterface uservice;
	
	@Autowired
	ArtistInterface aservice;
	
	@GetMapping("/home")
	public String goToHomepage(Model model) {
		return "home";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		return "signup";
	}
	
	@GetMapping("/signup2")
	public String signup2(Model model) {
		return "signup2";
	}
	
	@GetMapping("/carthometab")
	public String shoppingCartHome(Model model) {
		return "carthometab";
	}
	
	@GetMapping("/cartothertab")
	public String shoppingCartOther(Model model) {
		return "cartothertab";
	}
	
	@GetMapping("/watchvideo")
	public String watchVideo(Model model) {

		int commentCount = uservice.findCommentsByMediaId(2L).size();
		
		//Add new view count upon page load
		
		Media loadedMedia = uservice.findMediaByMediaId(2L);
		int numberOfViews = loadedMedia.getViewCount();
		numberOfViews += 1;
		loadedMedia.setViewCount(numberOfViews);
		
		uservice.saveMedia(loadedMedia);
	
		//Currently, assume the userID = 1. This userID will be changed upon implementation
		//of Spring Security to authenticate logged in user
		
		model.addAttribute("commentCount", commentCount);
		model.addAttribute("user", uservice.findUserByUserId(1L));
		model.addAttribute("playlists", uservice.findPlaylistsByUserId(1L));
		model.addAttribute("media", uservice.findMediaByMediaId(2L));
		model.addAttribute("allMedia", uservice.findAllMedia());
		model.addAttribute("comments", uservice.findCommentsByMediaId(2L));
		model.addAttribute("tags", uservice.findTagsByMediaId(2L));
		
		boolean liked = false;
		
		Media selectedMedia = uservice.findMediaByMediaId(2L);
		
		List<Playlists> loggedInUserPlaylists = uservice.findPlaylistsByUserId(1L);
		
		for (Playlists playlist : loggedInUserPlaylists) {
			if(playlist.getMediaPlayList().contains(selectedMedia)) {
				liked = true;
			} 
		}	
		
		model.addAttribute("liked", liked);

		//BY ZHAO QI
		// currently assume the userID = 3, 
		Long customerId = (long) 2;
		User customer = uservice.findUserByUserId(customerId);
					
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

		return "userwatchvideo";
	}
	
	@GetMapping("/aftersubmitcomment")
	public String afterSubmitComment(Model model) {

		int commentCount = uservice.findCommentsByMediaId(2L).size();
		
		//Add new view count upon page load
		
		Media loadedMedia = uservice.findMediaByMediaId(2L);
		int numberOfViews = loadedMedia.getViewCount();
		loadedMedia.setViewCount(numberOfViews);
		
		uservice.saveMedia(loadedMedia);
	
		//Currently, assume the userID = 1. This userID will be changed upon implementation
		//of Spring Security to authenticate logged in user
		
		model.addAttribute("commentCount", commentCount);
		model.addAttribute("user", uservice.findUserByUserId(1L));
		model.addAttribute("media", uservice.findMediaByMediaId(2L));
		model.addAttribute("comments", uservice.findCommentsByMediaId(2L));
		
		return "aftersubmitcomment";
	}
	
	//ajax call for Like Button Add to play list
	@PostMapping("/addToPlaylist")
	@ResponseBody
	public String addToPlaylist(Model model, 
			@RequestParam(value = "userID") Long userID, 
			@RequestParam(value = "playlistID") Long playlistID,
			@RequestParam(value= "mediaID") Long mediaID) throws Exception {
		
		User existingUser = uservice.findUserByUserId(userID);
		
		Playlists playlist = uservice.findPlaylistByPlaylistID(playlistID);

		List<Media> selectedPlayListMediaList = uservice.findMediaListByPlayListID(playlistID);
		
		Media selectedMediaToSave = uservice.findMediaByMediaId(mediaID);
		
		selectedPlayListMediaList.add(selectedMediaToSave);
		
		playlist.setMediaPlayList(selectedPlayListMediaList);

		uservice.savePlaylist(playlist);
		uservice.saveUser(existingUser);
		return "userwatchvideo";
	}
	
	//ajax call for Like Button Remove from play list
	@PostMapping("/removeFromPlaylist")
	@ResponseBody
	public String removeFromPlaylist(Model model, 
			@RequestParam(value = "userID") Long userID, 
			@RequestParam(value= "mediaID") Long mediaID) throws Exception {
		
		List<Playlists> playlists = uservice.findPlaylistsByUserId(userID);
		
		Media selectedMediaToRemoveFromPlaylist = uservice.findMediaByMediaId(mediaID);
		
		for (Playlists playlist : playlists) {
			if (playlist.getMediaPlayList().contains(selectedMediaToRemoveFromPlaylist)) {
				playlist.getMediaPlayList().remove(selectedMediaToRemoveFromPlaylist);
			}
		}
		
		uservice.savePlaylists(playlists);
		return "userwatchvideo";
	}
	
	//ajax call for Subscribe button
	@PostMapping("/subscribe")
	@ResponseBody
	public String subscribeArtist(@RequestParam(value = "artistId") Long artistId) throws Exception{
		
		// currently assume the userID = 2,
		Long customerId = (long) 2;
		User customer = uservice.findUserByUserId(customerId);
		User artist = aservice.findById(artistId);
		
		if (artist == null || customer == null || customerId == artistId) {
			return "userwatchvideo";
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
		
		return "userwatchvideo";
	
	}
	
	//ajax call for unsubscribe button
	@PostMapping("/unsubscribe")	
	@ResponseBody
	public String unsubscribeArtist(@RequestParam(value = "artistId") Long artistId) throws Exception {
		
		// currently assume the userID = 3,
		Long customerId = (long) 2;
		User customer = uservice.findUserByUserId(customerId);
		User artist = aservice.findById(artistId);
		
		if (artist == null || customer == null || customerId == artistId) {
			return "userwatchvideo";
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
		return "userwatchvideo";				
	}
	
	//ajax call for submit comments
	@PostMapping("/submitComments")
	@ResponseBody
	public String submitComments(Model model, 
			@RequestParam(value = "submittedComment") String submittedComment,
			@RequestParam(value = "commentUserId") Long commentUserId,
			@RequestParam(value = "commentDisplayName") String commentDisplayName,
			@RequestParam(value = "commentDateTime") String commentDateTime, 
			@RequestParam(value = "commentMediaId") Long commentMediaId)
					throws Exception {
		
		User commentUser = uservice.findUserByUserId(commentUserId);
		Media commentedMedia = uservice.findMediaByMediaId(commentMediaId);
		List<Comments> existingUserComments = uservice.findCommentsByMediaId(commentMediaId);
		
		//Add new Comment to existing user's comments
		
	
		
		Comments newComment = new Comments(commentDateTime, submittedComment, commentedMedia, commentUser);
		
		existingUserComments.add(newComment);
		commentedMedia.setCommentList(existingUserComments);
		uservice.saveComment(newComment);
		uservice.saveUser(commentUser);
		uservice.saveMedia(commentedMedia);

		return "userwatchvideo";
	}

}
