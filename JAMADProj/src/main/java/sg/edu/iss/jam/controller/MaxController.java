package sg.edu.iss.jam.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.edu.iss.jam.model.Max;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.service.MaxInterface;
import sg.edu.iss.jam.service.UserInterface;

@Controller
public class MaxController {
	
	@Autowired
	MaxInterface signupservice;
	
	@Autowired
	UserInterface uservice;
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("signup", signupservice.findAllSignups());
		return "signup";
	}
	
	@GetMapping("/signup2")
	public String signup2(Model model) {
		model.addAttribute("signup2", signupservice.findAllSignups());
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
		int reviews = 12345678;
		int commentCount = 12345;
		
		String publishDate = "May 27, 2020";
		String title = "You've Got a Very Good Friend. Be Happy and Stay Safe! :) Have Fun!";
		String artistName = "Bruce Lee - The Legend";
		
		String commentorName = "Chuck Norris The Legend";
		String commentDate = "3 months ago";
		String comment = "Nice music, nice mv, Thanks! . I have subscribed this channel "
				+ "for many years, Carole King is excellent in both Rock and Dance.";
		
		String videoTitle = "You've Got a Very Good Friend. Be Happy and Stay Safe! :) Have Fun!";
		String viddescription = "Rock";
		String vidauthor = "Michael Jackson";
		String videoDuration1 = "5:39";
		String videoDuration2 = "13:45";
		String videoDuration3 = "120:12";
		
		model.addAttribute("reviews", reviews);
		model.addAttribute("dateOfPublish", publishDate);
		model.addAttribute("title", title);
		model.addAttribute("artistname", artistName);
		model.addAttribute("commentCount", commentCount);
		model.addAttribute("commentorName", commentorName);
		model.addAttribute("commentDate", commentDate);
		model.addAttribute("comment", comment);
		model.addAttribute("videoTitle", videoTitle);
		model.addAttribute("viddescription", viddescription);
		model.addAttribute("vidauthor", vidauthor);
		model.addAttribute("videoDuration1", videoDuration1);
		model.addAttribute("videoDuration2", videoDuration2);
		model.addAttribute("videoDuration3", videoDuration3);
		
		model.addAttribute("user", uservice.findById(1L));
		model.addAttribute("playlists", uservice.findPlaylistsByUserId(1L));
		model.addAttribute("media", uservice.findByid(1L));
		
		boolean liked = false;
		
		Media selectedMedia = uservice.findByid(1L);
		
		List<Playlists> loggedInUserPlaylists = uservice.findPlaylistsByUserId(1L);
		
		for (Playlists playlist : loggedInUserPlaylists) {
			if(playlist.getMediaPlayList().contains(selectedMedia)) {
				liked = true;
			} 
		}	
		
		model.addAttribute("liked", liked);
		
		model.addAttribute("demo", signupservice.findAllSignups());
		return "userwatchvideo";
	}
	
	//ajax call for Like Button Add to play list
	@PostMapping("/addToPlaylist")
	@ResponseBody
	public String addToPlaylist(Model model, 
			@RequestParam(value = "userID") Long userID, 
			@RequestParam(value = "playlistID") Long playlistID,
			@RequestParam(value= "mediaID") Long mediaID) throws Exception {
		
		User existingUser = uservice.findById(userID);
		
		Playlists playlist = uservice.findPlaylistByPlaylistID(playlistID);

		List<Media> selectedPlayListMediaList = uservice.findMediaListByPlayListID(playlistID);
		
		Media selectedMediaToSave = uservice.findByid(mediaID);
		
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
		
		Media selectedMediaToRemoveFromPlaylist = uservice.findByid(mediaID);
		
		for (Playlists playlist : playlists) {
			if (playlist.getMediaPlayList().contains(selectedMediaToRemoveFromPlaylist)) {
				playlist.getMediaPlayList().remove(selectedMediaToRemoveFromPlaylist);
			}
		}
			
		uservice.savePlaylists(playlists);
		return "userwatchvideo";
	}
	
	@GetMapping("/home")
	public String goToHomepage(Model model) {
		return "home";
	}
}