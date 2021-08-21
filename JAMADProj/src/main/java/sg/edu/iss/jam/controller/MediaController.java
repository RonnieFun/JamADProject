package sg.edu.iss.jam.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.edu.iss.jam.model.Album;
import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Comments;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.model.UserHistory;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.UserInterface;
import sg.edu.iss.jam.service.VideoServiceInterface;

@Controller
public class MediaController {

	@Autowired
	UserInterface uservice;
	
	@Autowired
	ArtistInterface aservice;
	
	//scy-landingpage
		@Autowired
		VideoServiceInterface vservice;
		
		@GetMapping("/video/videolandingpage")
		public String showVideoLandingPage(Model model) {
//			List<Object[]> topVideosByUserHistory=vservice.getTopMediasByUserHistory(6,MediaType.Video);
//			List<Media> videos=new ArrayList<Media>();
//			for(Object[] object: topVideosByUserHistory) {
//				videos.add((Media)object[0]);
//			}
//			model.addAttribute("videos",videos);
		 
			
			List<Media> allVideos=vservice.getMediaByUserHistory(MediaType.Video,LocalDate.now().minusMonths(36));
			List<Media> topVideos=new ArrayList<Media>();
			for(int i=0;i<=11;i++) {
				topVideos.add(allVideos.get(i));}
//			int number=11;
//			if(topVideos.size()<number) {
//				for(int i=0;i<=topVideos.size();i++) {
//					topVideos.add(allVideos.get(i));}
//			}
//			else if(topVideos.size()>=number){
//				for(int i=0;i<=number;i++) {
//					topVideos.add(allVideos.get(i));}
//				}
//			else {
//				topVideos=allVideos;
//			}

			model.addAttribute("topvideos",topVideos);
			return "videolandingpage";
		}
		
//		@GetMapping("/music/musiclandingpage")
//		public String showMusicLandingPage(Model model) {
//			List<Media> allMusics=vservice.getMediaByTypeAndCount(MediaType.Music);
//			List<Media> topMusics=new ArrayList<Media>();
//			for(int i=0;i<=11;i++) {
//				topMusics.add(allMusics.get(i));
//			}
	//
//			model.addAttribute("topmusics",topMusics);
//			return "musiclandingpage";
//		}

	
	
	
	
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
	
	@GetMapping("/carthometab2")
	public String shoppingCartHome(Model model) {
		return "carthometab";
	}
	
	@GetMapping("/cartothertab2")
	public String shoppingCartOther(Model model) {
		return "cartothertab";
	}
	
	@GetMapping("/video/watchvideo/{mediaId}")
	public String watchVideo(Model model, @PathVariable Long mediaId) {
		
//		//Add new view count upon page load
//		
//		Media loadedMedia = uservice.findMediaByMediaId(2L);
//		int numberOfViews = loadedMedia.getViewCount();
//		numberOfViews += 1;
//		loadedMedia.setViewCount(numberOfViews);
//		
//		uservice.saveMedia(loadedMedia);
	
//		model.addAttribute("commentCount", commentCount);
		
		//Currently, assume the userID = 1. This userID will be changed upon implementation
		//of Spring Security to authenticate logged in user

		int commentCount = uservice.findCommentsByMediaId(mediaId).size();
		
		User loggedInUser = uservice.findUserByUserId(2L);
		
		Media selectedMedia = uservice.findMediaByMediaId(mediaId);
		
		// Add new userhistory object based on logged in user's userid on each page reload
		UserHistory userhistory = new UserHistory(LocalDateTime.now(), loggedInUser, selectedMedia);
		List<UserHistory> userHistory = uservice.findUserHistoryByMediaId(mediaId);
		uservice.saveUserHistory(userhistory);
		userHistory.add(userhistory);
		
		//Retrieve number of views based on userhistory size for the selected Media
		int viewCount = userHistory.size();
		
		model.addAttribute("commentCount", commentCount);
		model.addAttribute("user", loggedInUser);
		model.addAttribute("playlists", uservice.findPlaylistsByUserId(2L));
		model.addAttribute("media", selectedMedia);
		model.addAttribute("allMedia", uservice.findAllMedia());
		model.addAttribute("comments", uservice.findCommentsByMediaId(mediaId));
		model.addAttribute("tags", uservice.findTagsByMediaId(mediaId));
		model.addAttribute("viewCount", viewCount);
	    
		boolean liked = false;
		
		List<Playlists> loggedInUserPlaylists = uservice.findPlaylistsByUserId(2L);
		
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
					
		Long artistId = selectedMedia.getChannel().getChannelUser().getUserID();
		User artist = selectedMedia.getChannel().getChannelUser();
					
		Boolean subscribeStatus = false;				
					
		for(Subscribed s: artist.getSubscribers()) {
			// if the customer already subscribed the artist, it shows true
			if (s.getTargetId() == customerId) {
				subscribeStatus = true;
				}
			}
		
		if (artistId == customerId) {
			subscribeStatus = null;
		}
		
		model.addAttribute("subscribeStatus", subscribeStatus);
			
		return "userwatchvideo";
	}
	
	@GetMapping("/video/aftersubmitcomment/{mediaId}")
	public String afterSubmitComment(Model model, @PathVariable Long mediaId) {
		
//		Media loadedMedia = uservice.findMediaByMediaId(2L);
//		int numberOfViews = loadedMedia.getViewCount();
//		loadedMedia.setViewCount(numberOfViews);
//		
//		uservice.saveMedia(loadedMedia);
	
		int commentCount = uservice.findCommentsByMediaId(mediaId).size();
		
		//Currently, assume the userID = 1. This userID will be changed upon implementation
		//of Spring Security to authenticate logged in user
		
		model.addAttribute("commentCount", commentCount);
		model.addAttribute("user", uservice.findUserByUserId(2L));
		model.addAttribute("media", uservice.findMediaByMediaId(mediaId));
		model.addAttribute("comments", uservice.findCommentsByMediaId(mediaId));
		
		return "aftersubmitcomment";
	}
	
	//ajax call for Like Button Add to play list
	@PostMapping("/video/addToPlaylist")
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
	@PostMapping("/video/removeFromPlaylist")
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
	@PostMapping("/video/subscribe")
	@ResponseBody
	public String subscribeArtist(@RequestParam(value = "artistId") Long artistId) throws Exception{
		
		// currently assume the userID = 2,
		Long customerId = (long) 2;
		User customer = uservice.findUserByUserId(customerId);
		User artist = aservice.findById(artistId);
		
		if (artist == null || customer == null) {
			return "userwatchvideo";
		}
	
		// for artist, add the customer to the subscribed collection
		Collection<Subscribed> customers_subscribed_me_list = new HashSet<Subscribed>();
		Subscribed customer_subscribed_me = new Subscribed();
		customer_subscribed_me.setTargetId(customerId);
		customer_subscribed_me.setUser(artist);	
		customers_subscribed_me_list.add(customer_subscribed_me);
		artist.setSubscribers(customers_subscribed_me_list);
		
		// For customer, add the artist to the subscribed collection
		Collection<Subscribed> artists_I_subscribed_list = new HashSet<Subscribed>();
		Subscribed artist_I_subscribed = new Subscribed();
		artist_I_subscribed.setTargetId(artistId);
		artist_I_subscribed.setUser(customer);	
		artists_I_subscribed_list.add(artist_I_subscribed);
		customer.setSubscribers(artists_I_subscribed_list);
		
		aservice.saveUser(artist);
		aservice.saveSubscribed(customer_subscribed_me);
		
		uservice.saveUser(customer);
		uservice.saveSubscribed(artist_I_subscribed);
		
		return "userwatchvideo";
	
	}
	
	//ajax call for unsubscribe button
	@PostMapping("/video/unsubscribe")	
	@ResponseBody
	public String unsubscribeArtist(@RequestParam(value = "artistId") Long artistId) throws Exception {
		
		// currently assume the userID = 2,
		Long customerId = (long) 2;
		User customer = uservice.findUserByUserId(customerId);
		User artist = aservice.findById(artistId);
		
		if (artist == null || customer == null) {
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
	@PostMapping("/video/submitComments")
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
	
	
	
//--------------------------User views Artist Video Channel Page by ZQ--------------------------------------------------
	@GetMapping("/video/viewartistvideochannel/{artistId}")
	public String viewArtistVideoChannel(Model model, @PathVariable Long artistId) {
		
		String artistVideoChannelName = "";
		int numberOfArtistVideos = 0;
		List<Media> artistVideos = new ArrayList<Media>();
		
		// currently assume the userID = 2
		Long customerId = (long) 2;
		User customer = uservice.findUserByUserId(customerId);
				
		// currently assume the artistID = 1		
		User artist = aservice.findById(artistId);
		// get artist's name (will change to displayName while DB data is ready)
		String artistName = artist.getFirstName() + " " + artist.getLastName();
		// get artist's video channel and videos
		List<Channel> artistChannels = (List<Channel>) artist.getChannels();
		
		for(Channel c: artistChannels) {
			if(c.getMediaType() == MediaType.Video) {	
				artistVideos = (List<Media>) c.getChannelMediaList();
				
				artistVideoChannelName =  c.getChannelName();
				numberOfArtistVideos =  c.getChannelMediaList().size();
			}
		}
		// get artist's subscribers
		List<Subscribed> users_subscribed_jaychou = (List<Subscribed>) artist.getSubscribers();
		int NumberOfSubscribers = 0;
		
		if (users_subscribed_jaychou == null) {
			NumberOfSubscribers = 0;
		}	
		NumberOfSubscribers = users_subscribed_jaychou.size();
		
		// check the subscribe status
		Boolean subscribeStatus = false;
		for(Subscribed s: artist.getSubscribers()) {
			// if the customer already subscribed the artist, it shows true
			if (s.getTargetId() == customerId) {
				subscribeStatus = true;
			}
		}
		
		if (artistId == customerId) {
			subscribeStatus = null;
		}

		model.addAttribute("artistVideoChannelName", artistVideoChannelName);
		model.addAttribute("numberOfArtistVideos", numberOfArtistVideos);
		model.addAttribute("artistVideos", artistVideos);
		model.addAttribute("numberOfSubscribers", NumberOfSubscribers);
		model.addAttribute("subscribeStatus", subscribeStatus);
		model.addAttribute("artistName", artistName);
		model.addAttribute("artistId", artistId);
		model.addAttribute("artist", artist);
		
		return "ArtistVideoChannel";
	}
	
	
	@GetMapping("/video/subscribenoajax/{artistId}")
	public String subscribeArtistNoAjax(@PathVariable Long artistId){
		
		// currently assume the userID = 2,
		Long customerId = (long) 2;
		User customer = uservice.findUserByUserId(customerId);
		User artist = aservice.findById(artistId);
		
		if (artist == null || customer == null) {
			return "redirect:/video/viewartistvideochannel/{artistId}";
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
		
		return "redirect:/video/viewartistvideochannel/{artistId}";
	}
	
	
	@GetMapping("/video/unsubscribenoajax/{artistId}")	
	public String unsubscribeArtistNoAjax(@PathVariable Long artistId) {
		
		// currently assume the userID = 2,
		Long customerId = (long) 2;
		User customer = uservice.findUserByUserId(customerId);
		User artist = aservice.findById(artistId);
		
		
		if (artist == null || customer == null) {
			return "redirect:/video/viewartistvideochannel/{artistId}";
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
		return "redirect:/video/viewartistvideochannel/{artistId}";
				
	}
	
	
//--------------------------User views Artist Music Channel Page by ZQ--------------------------------------------------
	@GetMapping("music/viewartistmusicchannel")
	public String viewArtistMusicChannel1(Model model) {
		
		String artistMusicChannelName = "";
		int numberOfArtistAlbums = 0;
		int numberOfArtistMusics = 0;
		List<Album> artistAlbums = new ArrayList<Album>();
		
		// currently assume the userID = 2
		Long customerId = (long) 2;
		User customer = uservice.findUserByUserId(customerId);
				
		// currently assume the artistID = 1		
		Long artistId = (long) 1;
		User artist = aservice.findById(artistId);
		// get artist's name (will change to displayName while DB data is ready)
		String artistName = artist.getFirstName() + " " + artist.getLastName();
		// get artist's video channel and videos
		List<Channel> artistChannels = (List<Channel>) artist.getChannels();
		
		for(Channel c: artistChannels) {
			if(c.getMediaType() == MediaType.Music) {	
				artistMusicChannelName =  c.getChannelName();
				artistAlbums = (List<Album>) c.getAlbumslist();
				numberOfArtistAlbums =  c.getAlbumslist().size();
				
				for(Album a: artistAlbums) {
					numberOfArtistMusics += a.getAlbumMedia().size();
				}
			}
		}
		// get artist's subscribers
		List<Subscribed> users_subscribed_jaychou = (List<Subscribed>) artist.getSubscribers();
		int NumberOfSubscribers = 0;
		
		if (users_subscribed_jaychou == null) {
			NumberOfSubscribers = 0;
		}	
		NumberOfSubscribers = users_subscribed_jaychou.size();
		
		// check the subscribe status
		boolean subscribeStatus = false;
		for(Subscribed s: customer.getSubscribers()) {
			// if the customer already subscribed the artist, it shows true
			if (s.getTargetId() == artistId) {
				subscribeStatus = true;
			}
		}
		
		
		model.addAttribute("artistMusicChannelName", artistMusicChannelName);
		model.addAttribute("numberOfArtistAlbums", numberOfArtistAlbums);
		model.addAttribute("numberOfArtistMusics", numberOfArtistMusics);
		model.addAttribute("artistalbums", artistAlbums);
		model.addAttribute("numberOfSubscribers", NumberOfSubscribers);
		model.addAttribute("subscribeStatus", subscribeStatus);
		model.addAttribute("artistName", artistName);
		model.addAttribute("artistId", artistId);
		model.addAttribute("artist", artist);	
		return "ArtistMusicChannel";
	}

}
