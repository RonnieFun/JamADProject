package sg.edu.iss.jam.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.jam.DTO.AndroidGetAllVideosDTO;
import sg.edu.iss.jam.model.Album;
import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Comments;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.Tag;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.model.UserHistory;
import sg.edu.iss.jam.security.MyUserDetails;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.MediaServiceInterface;
import sg.edu.iss.jam.service.UserInterface;

@RestController
@RequestMapping("/api")
public class MediaControllerAPI {

	@Autowired
	UserInterface uservice;

	@Autowired
	ArtistInterface aservice;

	// scy-videolandingpage
	@Autowired
	MediaServiceInterface mservice;

	// for recommendation
	@Autowired
	private RestTemplate restTemplate;

	String response_model1 = "";
	String response_model2 = "";
	String response_model3 = "";
	String response_model4 = "";
	List<String> recommendMediaNames_model1 = new ArrayList<String>();
	List<String> recommendMediaNames_model2 = new ArrayList<String>();
	List<String> recommendMediaNames_model3 = new ArrayList<String>();
	List<String> recommendMediaNames_model4 = new ArrayList<String>();

	@GetMapping("/video/getallvideos")
	public ResponseEntity<?> getAllVideos() {
		List<Media> allVideos = mservice.findAllVideos();
		List<AndroidGetAllVideosDTO> androidGetAllVideosDTOList = new ArrayList<>();
		for (Media video : allVideos) {
			String tags = "";
			AndroidGetAllVideosDTO androidGetAllVideosDTO = new AndroidGetAllVideosDTO();
			androidGetAllVideosDTO.setArtistName(video.getChannel().getChannelUser().getFullname());
			androidGetAllVideosDTO.setArtistProfileThumbnailUrl(video.getChannel().getChannelUser().getProfileUrl());
			androidGetAllVideosDTO.setVideoDuration(video.getDuration());
			androidGetAllVideosDTO.setVideoThumbnailUrl(video.getThumbnailUrl());
			androidGetAllVideosDTO.setVideoTitle(video.getTitle());
			for (Tag tag: video.getTagList()) {
				tags = tags + tag.getTagName() + " ";
				tags.trim();
			}
			androidGetAllVideosDTO.setTags(tags);
			androidGetAllVideosDTOList.add(androidGetAllVideosDTO);
		}
		return new ResponseEntity<>(androidGetAllVideosDTOList, HttpStatus.OK);
	}
	
//	@GetMapping("/video/getallvideos")
//	public ResponseEntity<?> getAllVideos() {
//		List<Media> allVideos = mservice.findAllVideos();
//		return new ResponseEntity<>(allVideos, HttpStatus.OK);
//	}

	@GetMapping("/video/medianotfound/{mediaId}")
	public String videoNotFound(Model model, @PathVariable Long mediaId,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		if (userDetails == null) {
			return "/login";
		}

		String errorMessage = "Your Requested Video Title is not found.";
		String returnToVideoLandingMessage = "Please click here to return to Video Landing Page";
		String returnLink = "/video/genericvideolandingpage";

		model.addAttribute("returnLink", returnLink);
		model.addAttribute("returnToMediaLandingMessage", returnToVideoLandingMessage);
		model.addAttribute("errorMessage", errorMessage);
		return "MediaNotFound";
	}

	@GetMapping("/music/medianotfound/{mediaId}")
	public String musicNotFound(Model model, @PathVariable Long mediaId,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		if (userDetails == null) {
			return "/login";
		}

		String errorMessage = "Your Requested Music Title is not found.";
		String returnToMusicLandingMessage = "Please click here to return to Music Landing Page";
		String returnLink = "/music/genericmusiclandingpage";

		model.addAttribute("returnLink", returnLink);
		model.addAttribute("returnToMediaLandingMessage", returnToMusicLandingMessage);
		model.addAttribute("errorMessage", errorMessage);
		return "MediaNotFound";
	}

	@GetMapping("/video/genericvideolandingpage")
	public String genericVideoLandingPage(Model model) {

//				List<Object[]> topVideosByUserHistory=mservice.getTopMediasByUserHistory(6,MediaType.Video);
//				List<Media> videos=new ArrayList<Media>();
//				for(Object[] object: topVideosByUserHistory) {
//					videos.add((Media)object[0]);
//				}
//				model.addAttribute("videos",videos);

		List<Media> allVideos = mservice.getMediaByUserHistory(MediaType.Video, LocalDate.now().minusMonths(36));
		List<Media> topVideos = new ArrayList<Media>();
		List<Media> toptwelveVideos = new ArrayList<Media>();

		for (Media m : allVideos) {
			if (m.getCreatedOn().compareTo(LocalDate.now().minusDays(180)) > 0)
				topVideos.add(m);
		}

		if (topVideos.size() > 11) {
			for (int i = 0; i <= 11; i++) {
				toptwelveVideos.add(topVideos.get(i));
			}

		} else {
			toptwelveVideos = topVideos;
		}

		model.addAttribute("topvideos", toptwelveVideos);
		return "genericvideolandingpage";
	}

	// musiclanding page

	@GetMapping("/music/genericmusiclandingpage")
	public String genericMusicLandingPage(Model model) {

		List<Media> allMusics = mservice.getMediaByUserHistory(MediaType.Music, LocalDate.now().minusMonths(36));
		List<Media> topMusics = new ArrayList<Media>();
		List<Media> toptwelveMusics = new ArrayList<Media>();

		for (Media m : allMusics) {
			if (m.getCreatedOn().compareTo(LocalDate.now().minusDays(180)) > 0)
				topMusics.add(m);
		}

		if (topMusics.size() > 11) {
			for (int i = 0; i <= 11; i++) {
				toptwelveMusics.add(topMusics.get(i));
			}

		} else {
			toptwelveMusics = topMusics;
		}

		model.addAttribute("topmusic", toptwelveMusics);
		return "genericmusiclandingpage";
	}

	// ----------------------------------Login Video Landing Page(Recommendation
	// Model 1) ------------------------------------

	@GetMapping("/video/loginvideolandingpage")
	public String loginVideoLandingPage(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		if (userDetails == null) {
			return "redirect:/login";
		}

		// if the code comes here, it means the user exists in database,
		// then check whether it's new user or not
		boolean hasUserHistoryVideo = true;
		List<UserHistory> userHistoryVideo = uservice.findUserHistoryByUserIdAndMediaType(userDetails.getUserId(),
				MediaType.Video);

		if (userHistoryVideo == null || userHistoryVideo.size() == 0) {
			hasUserHistoryVideo = false;
		}

		// If the user has no video UserHistory data (new user),
		// recommend items the same as genericvideolandingpage
		if (hasUserHistoryVideo == false) {

			List<Media> allVideos = mservice.getMediaByUserHistory(MediaType.Video, LocalDate.now().minusMonths(36));
			List<Media> topVideos = new ArrayList<Media>();
			List<Media> toptwelveVideos = new ArrayList<Media>();

			for (Media m : allVideos) {
				if (m.getCreatedOn().compareTo(LocalDate.now().minusDays(180)) > 0)
					topVideos.add(m);
			}

			if (topVideos.size() > 11) {
				for (int i = 0; i <= 11; i++) {
					toptwelveVideos.add(topVideos.get(i));
				}

			} else {
				toptwelveVideos = topVideos;
			}

			model.addAttribute("topvideos", toptwelveVideos);
		}

		// Else if the user has UserHistory data,
		// recommend items based on ML model

		else {

			List<Long> recommend_mediaid_list = new ArrayList<Long>();
			List<Media> recommend_medialist = new ArrayList<Media>();

			String url = "http://127.0.0.1:5000/model1?user_id={1}";
			ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class,
					userDetails.getUserId());
			response_model1 = responseEntity.getBody();

			if (!response_model1.isEmpty()) {

				List<String> strList = new ArrayList<String>();
				strList = Arrays.asList(response_model1.split(","));
				for (String s : strList) {
					recommend_mediaid_list.add(Long.parseLong(s));
				}
			}

			for (Long id : recommend_mediaid_list) {
				Media recommendMedia = mservice.getMediaById(id);
				if (recommendMedia != null) {
					recommend_medialist.add(recommendMedia);
				}
			}

			model.addAttribute("recommend_medialist", recommend_medialist);
		}

		model.addAttribute("hasUserHistoryVideo", hasUserHistoryVideo);
		return "loginvideolandingpage";
	}

	// ----------------------------------Login Music Landing Page(Recommendation
	// Model 2)------------------------------------

	@GetMapping("/music/loginmusiclandingpage")
	public String loginMusicLandingPage(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		if (userDetails == null) {
			return "redirect:/login";
		}

		// if the code comes here, it means the user exists in database,
		// then check whether it's new user or not
		boolean hasUserHistoryMusic = true;
		List<UserHistory> userHistoryMusic = uservice.findUserHistoryByUserIdAndMediaType(userDetails.getUserId(),
				MediaType.Music);
		if (userHistoryMusic == null || userHistoryMusic.size() == 0) {
			hasUserHistoryMusic = false;
		}

		// If the user has no music UserHistory data (new user),
		// recommend items the same as genericmusiclandingpage
		if (hasUserHistoryMusic == false) {

			List<Media> allMusics = mservice.getMediaByUserHistory(MediaType.Music, LocalDate.now().minusMonths(36));
			List<Media> topMusics = new ArrayList<Media>();
			List<Media> toptwelveMusics = new ArrayList<Media>();

			for (Media m : allMusics) {
				if (m.getCreatedOn().compareTo(LocalDate.now().minusDays(180)) > 0)
					topMusics.add(m);
			}

			if (topMusics.size() > 11) {
				for (int i = 0; i <= 11; i++) {
					toptwelveMusics.add(topMusics.get(i));
				}

			} else {
				toptwelveMusics = topMusics;
			}

			model.addAttribute("topmusic", toptwelveMusics);
		}

		// Else if the user has UserHistory data,
		// recommend items based on ML model
		else {
			List<Long> recommend_mediaid_list = new ArrayList<Long>();
			List<Media> recommend_medialist = new ArrayList<Media>();

			String url = "http://127.0.0.1:5000/model2?user_id={1}";
			ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class,
					userDetails.getUserId());
			response_model2 = responseEntity.getBody();

			if (!response_model2.isEmpty()) {

				List<String> strList = new ArrayList<String>();
				strList = Arrays.asList(response_model2.split(","));
				for (String s : strList) {
					recommend_mediaid_list.add(Long.parseLong(s));
				}
			}

			for (Long id : recommend_mediaid_list) {
				Media recommendMedia = mservice.getMediaById(id);
				if (recommendMedia != null) {
					recommend_medialist.add(recommendMedia);
				}
			}

			model.addAttribute("recommend_medialist", recommend_medialist);

		}

		model.addAttribute("hasUserHistoryMusic", hasUserHistoryMusic);
		return "loginmusiclandingpage";
	}

	@GetMapping("/video/watchvideo/{mediaId}")
	public String watchVideo(Model model, @PathVariable Long mediaId, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		if (userDetails == null) {
			return "/login";
		}

		long loggedInUserId = userDetails.getUserId();

		int commentCount = uservice.findCommentsByMediaId(mediaId).size();

		User loggedInUser = uservice.findUserByUserId(loggedInUserId);

		Media selectedMedia = uservice.findMediaByMediaTypeAndMediaId(MediaType.Video, mediaId);

		if (selectedMedia == null) {
			redirectAttributes.addAttribute("mediaId", mediaId);
			return "redirect:/video/medianotfound/{mediaId}";
		}

		// Add new userhistory object based on logged in user's userid on each page
		// reload
		UserHistory userhistory = new UserHistory(LocalDateTime.now(), loggedInUser, selectedMedia);
		List<UserHistory> userHistory = uservice.findUserHistoryByMediaId(mediaId);
		uservice.saveUserHistory(userhistory);
		userHistory.add(userhistory);

		// Retrieve number of views based on userhistory size for the selected Media
		int viewCount = userHistory.size();

		model.addAttribute("commentCount", commentCount);
		model.addAttribute("user", loggedInUser);
		model.addAttribute("playlists", uservice.findPlaylistByUserIdAndMediaType(loggedInUserId, MediaType.Video));
		model.addAttribute("media", selectedMedia);
		model.addAttribute("allMedia", uservice.findAllMediaByMediaType(MediaType.Video));
		model.addAttribute("comments", uservice.findCommentsByMediaId(mediaId));
		model.addAttribute("tags", uservice.findTagsByMediaId(mediaId));
		model.addAttribute("viewCount", viewCount);

		boolean liked = false;

		List<Playlists> loggedInUserPlaylists = uservice.findPlaylistsByUserId(loggedInUserId);

		for (Playlists playlist : loggedInUserPlaylists) {
			if (playlist.getMediaPlayList().contains(selectedMedia)) {
				liked = true;
			}
		}

		model.addAttribute("liked", liked);

		User customer = uservice.findUserByUserId(loggedInUserId);

		// this method aims to show how to subscribe an Artist,
		// so we won't show media details

		Long artistId = selectedMedia.getChannel().getChannelUser().getUserID();
		User artist = selectedMedia.getChannel().getChannelUser();

		Boolean subscribeStatus = false;

		// Get list of all Subscribe objects in Database
		List<Subscribed> listOfSubscribe = uservice.getAllSubscribed();

		// get artist's subscribers
		List<Subscribed> users_Unsubscribed = uservice.getArtistUnSubscribed(artistId);

		List<Subscribed> users_Subscribed = uservice.getArtistSubscribed(artistId);

		for (Subscribed s : listOfSubscribe) {
			// if the customer already subscribed the artist, it shows true
			if (s.getSubscriber() == customer && s.getArtist() == artist && s.isSubscribed() == true
					&& users_Unsubscribed.size() < users_Subscribed.size()) {
				subscribeStatus = true;
			}

			if (s.getSubscriber() == customer && s.getArtist() == artist && s.isSubscribed() == false
					&& users_Unsubscribed.size() > users_Subscribed.size()) {
				subscribeStatus = false;
			}
		}

		if (artistId == loggedInUserId) {
			subscribeStatus = null;
		}

		// side bar recommendations
		// Recommendation Model 3
//		List<Long> recommend_mediaid_list = new ArrayList<Long>();
//		List<Media> recommend_medialist = new ArrayList<Media>();
//		
//		String url = "http://127.0.0.1:5000/model3?item_id={1}";
//		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, mediaId);
//		response_model3 = responseEntity.getBody();
//		
//		if (! response_model3.isEmpty()) {
//			
//			List<String> strList = new ArrayList<String>();
//			strList = Arrays.asList(response_model2.split(","));
//			for (String s: strList) {
//				recommend_mediaid_list.add(Long.parseLong(s));
//			}
//		}
//		
//		for(Long id: recommend_mediaid_list) {
//			Media recommendMedia = mservice.getMediaById(id);
//			if (recommendMedia != null) {
//				recommend_medialist.add(recommendMedia);
//			}
//		}

		model.addAttribute("subscribeStatus", subscribeStatus);
//		model.addAttribute("recommend_medialist", recommend_medialist);

		return "userwatchvideo";
	}

	// Load the next music in the same Music Channel from Listen Music page
	@GetMapping("/music/loadnextmusic/{mediaId}")
	public String listenNextMusic(Model model, @PathVariable Long mediaId, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		if (userDetails == null) {
			return "/login";
		}

		long loggedInUserId = userDetails.getUserId();

		User loggedInUser = uservice.findUserByUserId(loggedInUserId);

		Media selectedMedia = uservice.findMediaByMediaTypeAndMediaId(MediaType.Music, mediaId);

		if (selectedMedia == null) {
			redirectAttributes.addAttribute("mediaId", mediaId);
			return "redirect:/music/medianotfound/{mediaId}";
		}

		Album selectedMediaAlbum = selectedMedia.getAlbum();

		List<Media> albumMediaList = uservice.findMediaByAlbumAndMediaType(selectedMediaAlbum, MediaType.Music);

		int selectedMediaCurrentPositionInAlbum = 0;
		Boolean isLastMusic = false;

		for (int i = 0; i < albumMediaList.size(); i++) {
			if (albumMediaList.get(i) == selectedMedia) {
				selectedMediaCurrentPositionInAlbum = i;
			}
		}

		if ((selectedMediaCurrentPositionInAlbum + 1) != albumMediaList.size()) {
			Media nextMediaToPlay = albumMediaList.get(selectedMediaCurrentPositionInAlbum + 1);
			redirectAttributes.addAttribute(isLastMusic);
			redirectAttributes.addAttribute("mediaId", nextMediaToPlay.getId());
		}

		if (selectedMediaCurrentPositionInAlbum == albumMediaList.size() - 1) {
			Media nextMediaToPlay = albumMediaList.get(selectedMediaCurrentPositionInAlbum);
			isLastMusic = true;
			redirectAttributes.addAttribute(isLastMusic);
			redirectAttributes.addAttribute("mediaId", nextMediaToPlay.getId());
		}

		return "redirect:/music/listenmusic/{mediaId}";

	}

	// Load the previous music in the same Music Channel from Listen Music page
	@GetMapping("/music/loadpreviousmusic/{mediaId}")
	public String listenPreviousMusic(Model model, @PathVariable Long mediaId, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		if (userDetails == null) {
			return "/login";
		}

		long loggedInUserId = userDetails.getUserId();

		User loggedInUser = uservice.findUserByUserId(loggedInUserId);

		Media selectedMedia = uservice.findMediaByMediaTypeAndMediaId(MediaType.Music, mediaId);

		if (selectedMedia == null) {
			redirectAttributes.addAttribute("mediaId", mediaId);
			return "redirect:/music/medianotfound/{mediaId}";
		}

		Album selectedMediaAlbum = selectedMedia.getAlbum();

		List<Media> albumMediaList = uservice.findMediaByAlbumAndMediaType(selectedMediaAlbum, MediaType.Music);

		int selectedMediaCurrentPositionInAlbum = 0;
		Boolean isLastMusic = false;

		for (int i = 0; i < albumMediaList.size(); i++) {
			if (albumMediaList.get(i) == selectedMedia) {
				selectedMediaCurrentPositionInAlbum = i;
			}
		}

		if (selectedMediaCurrentPositionInAlbum > 0) {
			Media nextMediaToPlay = albumMediaList.get(selectedMediaCurrentPositionInAlbum - 1);
			redirectAttributes.addAttribute(isLastMusic);
			redirectAttributes.addAttribute("mediaId", nextMediaToPlay.getId());
		}

		if (selectedMediaCurrentPositionInAlbum == 0) {
			Media nextMediaToPlay = albumMediaList.get(0);
			isLastMusic = true;
			redirectAttributes.addAttribute(isLastMusic);
			redirectAttributes.addAttribute("mediaId", nextMediaToPlay.getId());
		}

		return "redirect:/music/listenmusic/{mediaId}";

	}

	@GetMapping("/music/listenmusic/{mediaId}")
	public String listenMusic(Model model, @PathVariable Long mediaId, Boolean isLastMusic,
			RedirectAttributes redirectAttributes, @AuthenticationPrincipal MyUserDetails userDetails) {

		if (userDetails == null) {
			return "/login";
		}

		long loggedInUserId = userDetails.getUserId();

		int commentCount = uservice.findCommentsByMediaId(mediaId).size();

		User loggedInUser = uservice.findUserByUserId(loggedInUserId);

		Media selectedMedia = uservice.findMediaByMediaTypeAndMediaId(MediaType.Music, mediaId);

		if (selectedMedia == null) {
			redirectAttributes.addAttribute("mediaId", mediaId);
			return "redirect:/music/medianotfound/{mediaId}";
		}

		// Add new userhistory object based on logged in user's userid on each page
		// reload
		UserHistory userhistory = new UserHistory(LocalDateTime.now(), loggedInUser, selectedMedia);
		List<UserHistory> userHistory = uservice.findUserHistoryByMediaId(mediaId);
		uservice.saveUserHistory(userhistory);
		userHistory.add(userhistory);

		// Retrieve number of views based on userhistory size for the selected Media
		int viewCount = userHistory.size();

		Boolean isLastMusicSelection = false;

		if (isLastMusic != null) {
			isLastMusicSelection = isLastMusic;
		}

		model.addAttribute("isLastMusicSelection", isLastMusicSelection);
		model.addAttribute("commentCount", commentCount);
		model.addAttribute("user", loggedInUser);
		model.addAttribute("playlists", uservice.findPlaylistByUserIdAndMediaType(loggedInUserId, MediaType.Music));
		model.addAttribute("media", selectedMedia);
		model.addAttribute("allMedia", uservice.findAllMediaByMediaType(MediaType.Music));
		model.addAttribute("comments", uservice.findCommentsByMediaId(mediaId));
		model.addAttribute("tags", uservice.findTagsByMediaId(mediaId));
		model.addAttribute("viewCount", viewCount);

		boolean liked = false;

		List<Playlists> loggedInUserPlaylists = uservice.findPlaylistsByUserId(loggedInUserId);

		for (Playlists playlist : loggedInUserPlaylists) {
			if (playlist.getMediaPlayList().contains(selectedMedia)) {
				liked = true;
			}
		}

		model.addAttribute("liked", liked);

		User customer = uservice.findUserByUserId(loggedInUserId);

		// this method aims to show how to subscribe an Artist,
		// so we won't show media details

		Long artistId = selectedMedia.getChannel().getChannelUser().getUserID();
		User artist = selectedMedia.getChannel().getChannelUser();

		Boolean subscribeStatus = false;

		// Get list of all Subscribe objects in Database
		List<Subscribed> listOfSubscribe = uservice.getAllSubscribed();

		// get artist's subscribers
		List<Subscribed> users_Unsubscribed = uservice.getArtistUnSubscribed(artistId);

		List<Subscribed> users_Subscribed = uservice.getArtistSubscribed(artistId);

		for (Subscribed s : listOfSubscribe) {
			// if the customer already subscribed the artist, it shows true
			if (s.getSubscriber() == customer && s.getArtist() == artist && s.isSubscribed() == true
					&& users_Unsubscribed.size() < users_Subscribed.size()) {
				subscribeStatus = true;
			}

			if (s.getSubscriber() == customer && s.getArtist() == artist && s.isSubscribed() == false
					&& users_Unsubscribed.size() > users_Subscribed.size()) {
				subscribeStatus = false;
			}
		}

		if (artistId == loggedInUserId) {
			subscribeStatus = null;
		}

		// side bar recommendations
		// Recommendation Model 3
//		List<Long> recommend_mediaid_list = new ArrayList<Long>();
//		List<Media> recommend_medialist = new ArrayList<Media>();
//		
//		String url = "http://127.0.0.1:5000/model3?item_id={1}";
//		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, mediaId);
//		response_model2 = responseEntity.getBody();
//		
//		if (! response_model2.isEmpty()) {
//			
//			List<String> strList = new ArrayList<String>();
//			strList = Arrays.asList(response_model2.split(","));
//			for (String s: strList) {
//				recommend_mediaid_list.add(Long.parseLong(s));
//			}
//		}
//		
//		for(Long id: recommend_mediaid_list) {
//			Media recommendMedia = mservice.getMediaById(id);
//			if (recommendMedia != null) {
//				recommend_medialist.add(recommendMedia);
//			}
//		}

		model.addAttribute("subscribeStatus", subscribeStatus);
//		model.addAttribute("recommend_medialist", recommend_medialist);

		return "userlistenmusic";
	}

	// Get Mapping to reload Comments Section in Watch Videos page. Added Ajax
	// Checkers
	// to ensure logged-in user does not accidentally go to this url if he enters a
	// random URL in browser. Ajaxcheckers are passed to
	// controller through the Submit Comments button ajax.
	@GetMapping("/video/aftersubmitcomment/{mediaId}/{ajaxChecker}/{ajaxChecker2}")
	public String afterSubmitCommentVideo(Model model, @PathVariable Long mediaId, @PathVariable Long ajaxChecker,
			@PathVariable Long ajaxChecker2, @AuthenticationPrincipal MyUserDetails userDetails) {

		if (userDetails == null) {
			return "/login";
		}

		if (ajaxChecker != 543501872 || ajaxChecker2 != 32163231) {
			return "error";
		}

		long loggedInUserId = userDetails.getUserId();

		int commentCount = uservice.findCommentsByMediaId(mediaId).size();

		model.addAttribute("commentCount", commentCount);
		model.addAttribute("user", uservice.findUserByUserId(loggedInUserId));
		model.addAttribute("media", uservice.findMediaByMediaId(mediaId));
		model.addAttribute("comments", uservice.findCommentsByMediaId(mediaId));

		return "aftersubmitcommentvideo";
	}

	// Get Mapping to reload Comments Section in Listen Music page. Added Ajax
	// Checkers
	// to ensure logged-in user does not accidentally go to this url if he enters a
	// random URL in browser. Ajaxcheckers are passed to
	// controller through the Submit Comments button ajax.
	@GetMapping("/music/aftersubmitcomment/{mediaId}/{ajaxCheckerMusic}/{ajaxChecker2Music}")
	public String afterSubmitCommentMusic(Model model, @PathVariable Long mediaId, @PathVariable Long ajaxCheckerMusic,
			@PathVariable Long ajaxChecker2Music, @AuthenticationPrincipal MyUserDetails userDetails) {

		if (userDetails == null) {
			return "/login";
		}

		if (ajaxCheckerMusic != 723472837 || ajaxChecker2Music != 340982904) {
			return "error";
		}

		long loggedInUserId = userDetails.getUserId();

		int commentCountMusic = uservice.findCommentsByMediaId(mediaId).size();

		model.addAttribute("commentCount", commentCountMusic);
		model.addAttribute("user", uservice.findUserByUserId(loggedInUserId));
		model.addAttribute("media", uservice.findMediaByMediaId(mediaId));
		model.addAttribute("comments", uservice.findCommentsByMediaId(mediaId));

		return "aftersubmitcommentmusic";
	}

	// ajax call for Like Button Add to VIDEO play list
	@PostMapping("/video/addToPlaylist")
	@ResponseBody
	public String addToPlaylist(Model model, @AuthenticationPrincipal MyUserDetails userDetails,
			@RequestParam(value = "userID") Long userID, @RequestParam(value = "playlistID") Long playlistID,
			@RequestParam(value = "mediaID") Long mediaID) throws Exception {

		if (userDetails == null) {
			return "/login";
		}

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

	// ajax call for Like Button Remove from VIDEO play list
	@PostMapping("/video/removeFromPlaylist")
	@ResponseBody
	public String removeFromPlaylist(Model model, @AuthenticationPrincipal MyUserDetails userDetails,
			@RequestParam(value = "userID") Long userID, @RequestParam(value = "mediaID") Long mediaID)
			throws Exception {

		if (userDetails == null) {
			return "/login";
		}

		List<Playlists> playlists = uservice.findPlaylistByUserIdAndMediaType(userID, MediaType.Video);

		Media selectedMediaToRemoveFromPlaylist = uservice.findMediaByMediaId(mediaID);

		for (Playlists playlist : playlists) {
			if (playlist.getMediaPlayList().contains(selectedMediaToRemoveFromPlaylist)) {
				playlist.getMediaPlayList().remove(selectedMediaToRemoveFromPlaylist);
			}
		}

		uservice.savePlaylists(playlists);
		return "userwatchvideo";
	}

	// ajax call for Like Button Add to MUSIC play list
	@PostMapping("/music/addToPlaylist")
	@ResponseBody
	public String addToMusicPlaylist(Model model, @AuthenticationPrincipal MyUserDetails userDetails,
			@RequestParam(value = "userIDmusic") Long userIDmusic,
			@RequestParam(value = "playlistIDmusic") Long playlistIDmusic,
			@RequestParam(value = "mediaIDmusic") Long mediaIDmusic) throws Exception {

		if (userDetails == null) {
			return "/login";
		}

		User existingUser = uservice.findUserByUserId(userIDmusic);

		Playlists playlist = uservice.findPlaylistByPlaylistID(playlistIDmusic);

		List<Media> selectedPlayListMediaList = uservice.findMediaListByPlayListID(playlistIDmusic);

		Media selectedMediaToSave = uservice.findMediaByMediaId(mediaIDmusic);

		selectedPlayListMediaList.add(selectedMediaToSave);

		playlist.setMediaPlayList(selectedPlayListMediaList);

		uservice.savePlaylist(playlist);
		uservice.saveUser(existingUser);
		return "userlistenmusic";
	}

	// ajax call for Like Button Remove from MUSIC play list
	@PostMapping("/music/removeFromPlaylist")
	@ResponseBody
	public String removeFromMusicPlaylist(Model model, @AuthenticationPrincipal MyUserDetails userDetails,
			@RequestParam(value = "userIDmusic") Long userIDmusic,
			@RequestParam(value = "mediaIDmusic") Long mediaIDmusic) throws Exception {

		if (userDetails == null) {
			return "/login";
		}

		List<Playlists> playlists = uservice.findPlaylistByUserIdAndMediaType(userIDmusic, MediaType.Music);

		Media selectedMediaToRemoveFromPlaylist = uservice.findMediaByMediaId(mediaIDmusic);

		for (Playlists playlist : playlists) {
			if (playlist.getMediaPlayList().contains(selectedMediaToRemoveFromPlaylist)) {
				playlist.getMediaPlayList().remove(selectedMediaToRemoveFromPlaylist);
			}
		}

		uservice.savePlaylists(playlists);
		return "userlistenmusic";
	}

	// ajax call for Video Subscribe button
	@PostMapping("/video/subscribe")
	@ResponseBody
	public String subscribeArtistVideo(@RequestParam(value = "artistId") Long artistId,
			@AuthenticationPrincipal MyUserDetails userDetails) throws Exception {

		if (userDetails == null) {
			return "/login";
		}

		long loggedInUserId = userDetails.getUserId();

		Long customerId = loggedInUserId;
		User customer = uservice.findUserByUserId(customerId);
		User artist = aservice.findById(artistId);

		if (artist == null || customer == null) {
			return "userwatchvideo";
		}

		// add new subscriber object for new subscription
		Subscribed newSubscription = new Subscribed();
		newSubscription.setSubscribed(true);
		newSubscription.setArtist(artist);
		newSubscription.setSubscriber(customer);
		newSubscription.setTimeSubscribed(LocalDateTime.now());

		uservice.saveSubscribed(newSubscription);

		return "userwatchvideo";

	}

	// ajax call for Video unsubscribe button
	@PostMapping("/video/unsubscribe")
	@ResponseBody
	public String unsubscribeArtistVideo(@RequestParam(value = "artistId") Long artistId,
			@AuthenticationPrincipal MyUserDetails userDetails) throws Exception {

		if (userDetails == null) {
			return "/login";
		}

		long loggedInUserId = userDetails.getUserId();

		Long customerId = loggedInUserId;
		User customer = uservice.findUserByUserId(customerId);
		User artist = aservice.findById(artistId);

		if (artist == null || customer == null) {
			return "userwatchvideo";
		}

		// add new subscriber object for new unsubscription
		Subscribed newUnsubscription = new Subscribed();
		newUnsubscription.setSubscribed(false);
		newUnsubscription.setArtist(artist);
		newUnsubscription.setSubscriber(customer);
		newUnsubscription.setTimeSubscribed(LocalDateTime.now());

		uservice.saveSubscribed(newUnsubscription);

		return "userwatchvideo";
	}

	// ajax call for Music Subscribe button
	@PostMapping("/music/subscribe")
	@ResponseBody
	public String subscribeArtistMusic(@RequestParam(value = "artistIdMusic") Long artistIdMusic,
			@AuthenticationPrincipal MyUserDetails userDetails) throws Exception {

		if (userDetails == null) {
			return "/login";
		}

		long loggedInUserId = userDetails.getUserId();

		Long customerId = loggedInUserId;
		User customer = uservice.findUserByUserId(customerId);
		User artist = aservice.findById(artistIdMusic);

		if (artist == null || customer == null) {
			return "userlistenmusic";
		}

		// add new subscriber object for new subscription
		Subscribed newSubscription = new Subscribed();
		newSubscription.setSubscribed(true);
		newSubscription.setArtist(artist);
		newSubscription.setSubscriber(customer);
		newSubscription.setTimeSubscribed(LocalDateTime.now());

		uservice.saveSubscribed(newSubscription);

		return "userlistenmusic";

	}

	// ajax call for Music unsubscribe button
	@PostMapping("/music/unsubscribe")
	@ResponseBody
	public String unsubscribeArtistMusic(@RequestParam(value = "artistIdMusic") Long artistIdMusic,
			@AuthenticationPrincipal MyUserDetails userDetails) throws Exception {

		if (userDetails == null) {
			return "/login";
		}

		long loggedInUserId = userDetails.getUserId();

		Long customerId = loggedInUserId;
		User customer = uservice.findUserByUserId(customerId);
		User artist = aservice.findById(artistIdMusic);

		if (artist == null || customer == null) {
			return "userlistenmusic";
		}

		// add new subscriber object for new unsubscription
		Subscribed newUnsubscription = new Subscribed();
		newUnsubscription.setSubscribed(false);
		newUnsubscription.setArtist(artist);
		newUnsubscription.setSubscriber(customer);
		newUnsubscription.setTimeSubscribed(LocalDateTime.now());

		uservice.saveSubscribed(newUnsubscription);

		return "userlistenmusic";
	}

	// ajax call for submit video comments
	@PostMapping("/video/submitComments")
	@ResponseBody
	public String submitVideoComments(Model model, @AuthenticationPrincipal MyUserDetails userDetails,
			@RequestParam(value = "submittedComment") String submittedComment,
			@RequestParam(value = "commentUserId") Long commentUserId,
			@RequestParam(value = "commentDisplayName") String commentDisplayName,
			@RequestParam(value = "commentDateTime") String commentDateTime,
			@RequestParam(value = "commentMediaId") Long commentMediaId) throws Exception {

		if (userDetails == null) {
			return "/login";
		}

		User commentUser = uservice.findUserByUserId(commentUserId);
		Media commentedMedia = uservice.findMediaByMediaId(commentMediaId);
		List<Comments> existingUserComments = uservice.findCommentsByMediaId(commentMediaId);

		// Add new Comment to existing user's comments

		Comments newComment = new Comments(commentDateTime, submittedComment, commentedMedia, commentUser);

		existingUserComments.add(newComment);
		commentedMedia.setCommentList(existingUserComments);
		uservice.saveComment(newComment);
		uservice.saveUser(commentUser);
		uservice.saveMedia(commentedMedia);

		return "userwatchvideo";
	}

	// ajax call for submit video comments
	@PostMapping("/music/submitComments")
	@ResponseBody
	public String submitMusicComments(Model model, @AuthenticationPrincipal MyUserDetails userDetails,
			@RequestParam(value = "submittedCommentMusic") String submittedCommentMusic,
			@RequestParam(value = "commentUserIdMusic") Long commentUserIdMusic,
			@RequestParam(value = "commentDisplayNameMusic") String commentDisplayNameMusic,
			@RequestParam(value = "commentDateTimeMusic") String commentDateTimeMusic,
			@RequestParam(value = "commentMediaIdMusic") Long commentMediaIdMusic) throws Exception {

		if (userDetails == null) {
			return "/login";
		}

		User commentUserMusic = uservice.findUserByUserId(commentUserIdMusic);
		Media commentedMediaMusic = uservice.findMediaByMediaId(commentMediaIdMusic);
		List<Comments> existingUserCommentsMusic = uservice.findCommentsByMediaId(commentMediaIdMusic);

		// Add new Comment to existing user's comments

		Comments newComment = new Comments(commentDateTimeMusic, submittedCommentMusic, commentedMediaMusic,
				commentUserMusic);

		existingUserCommentsMusic.add(newComment);
		commentedMediaMusic.setCommentList(existingUserCommentsMusic);
		uservice.saveComment(newComment);
		uservice.saveUser(commentUserMusic);
		uservice.saveMedia(commentedMediaMusic);

		return "userlistenmusic";
	}

//--------------------------User views Artist Video Channel Page by ZQ--------------------------------------------------
	@GetMapping("/video/viewartistvideochannel/{artistId}")
	public String viewArtistVideoChannel(@PathVariable("artistId") Long artistId, Model model,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		if (userDetails == null) {
			return "redirect:/login";
		}

		String artistVideoChannelName = "";
		int numberOfArtistVideos = 0;
		List<Media> artistVideos = new ArrayList<Media>();

		User customer = uservice.findUserByUserId(userDetails.getUserId());
		User artist = aservice.findById(artistId);
		String artistName = artist.getDisplayName();

		// get artist's video channel and videos
		List<Channel> artistChannels = (List<Channel>) artist.getChannels();

		for (Channel c : artistChannels) {
			if (c.getMediaType() == MediaType.Video) {
				artistVideos = (List<Media>) c.getChannelMediaList();

				artistVideoChannelName = c.getChannelName();
				numberOfArtistVideos = c.getChannelMediaList().size();
			}
		}
		// get artist's subscribers
		List<Subscribed> users_Unsubscribed = uservice.getArtistUnSubscribed(artistId);
		List<Subscribed> users_Subscribed = uservice.getArtistSubscribed(artistId);

		int NumberOfSubscribers = 0;

		// check the subscribe status
		Boolean subscribeStatus = false;

		// Get list of all Subscribe objects in Database
		List<Subscribed> listOfSubscribe = uservice.getAllSubscribed();

		for (Subscribed s : listOfSubscribe) {
			// if the customer already subscribed the artist, it shows true
			if (s.getSubscriber() == customer && s.getArtist() == artist && s.isSubscribed() == true
					&& users_Unsubscribed.size() < users_Subscribed.size()) {
				subscribeStatus = true;
			}

			if (s.getSubscriber() == customer && s.getArtist() == artist && s.isSubscribed() == false
					&& users_Unsubscribed.size() > users_Subscribed.size()) {
				subscribeStatus = false;
			}
		}

		if (artistId == userDetails.getUserId()) {
			subscribeStatus = null;
		}

		NumberOfSubscribers = users_Subscribed.size() - users_Unsubscribed.size();

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
	public String subscribeArtistNoAjaxVideo(@PathVariable("artistId") Long artistId,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		if (userDetails == null) {
			return "redirect:/login";
		}

		User customer = uservice.findUserByUserId(userDetails.getUserId());
		User artist = aservice.findById(artistId);

		if (artist == null || customer == null) {
			return "redirect:/video/viewartistvideochannel/{artistId}";
		}

		// add new subscriber object for new subscription
		Subscribed newSubscription = new Subscribed();
		newSubscription.setSubscribed(true);
		newSubscription.setArtist(artist);
		newSubscription.setSubscriber(customer);
		newSubscription.setTimeSubscribed(LocalDateTime.now());

		uservice.saveSubscribed(newSubscription);

		return "redirect:/video/viewartistvideochannel/{artistId}";
	}

	@GetMapping("/video/unsubscribenoajax/{artistId}")
	public String unsubscribeArtistNoAjaxVideo(@PathVariable("artistId") Long artistId,
			@AuthenticationPrincipal MyUserDetails userDetails) {
		if (userDetails == null) {
			return "redirect:/login";
		}

		User customer = uservice.findUserByUserId(userDetails.getUserId());
		User artist = aservice.findById(artistId);

		if (artist == null || customer == null) {
			return "redirect:/video/viewartistvideochannel/{artistId}";
		}

		// add new subscriber object for new unsubscription
		Subscribed newUnsubscription = new Subscribed();
		newUnsubscription.setSubscribed(false);
		newUnsubscription.setArtist(artist);
		newUnsubscription.setSubscriber(customer);
		newUnsubscription.setTimeSubscribed(LocalDateTime.now());

		uservice.saveSubscribed(newUnsubscription);

		return "redirect:/video/viewartistvideochannel/{artistId}";

	}

//--------------------------User views Artist Music Channel Page by ZQ--------------------------------------------------
	@GetMapping("music/viewartistmusicchannel/{artistId}")
	public String viewArtistMusicChannel1(@PathVariable("artistId") Long artistId, Long AlbumID, Model model,
			@AuthenticationPrincipal MyUserDetails userDetails) {
		if (userDetails == null) {
			return "redirect:/login";
		}

		String artistMusicChannelName = "";
		int numberOfArtistAlbums = 0;
		int numberOfArtistMusics = 0;
		List<Album> artistAlbums = new ArrayList<Album>();

		User customer = uservice.findUserByUserId(userDetails.getUserId());
		User artist = aservice.findById(artistId);
		String artistName = artist.getDisplayName();

		// get artist's video channel and albums
		List<Channel> artistChannels = (List<Channel>) artist.getChannels();
		for (Channel c : artistChannels) {
			if (c.getMediaType() == MediaType.Music) {
				artistMusicChannelName = c.getChannelName();
				artistAlbums = (List<Album>) c.getAlbumslist();
				if (artistAlbums != null) {
					numberOfArtistAlbums = artistAlbums.size();
					for (Album a : artistAlbums) {
						numberOfArtistMusics += a.getAlbumMedia().size();

					}
				}
			}
		}

		// get artist's subscribers
		List<Subscribed> users_Unsubscribed = uservice.getArtistUnSubscribed(artistId);
		List<Subscribed> users_Subscribed = uservice.getArtistSubscribed(artistId);

		int NumberOfSubscribers = 0;

		// check the subscribe status
		Boolean subscribeStatus = false;

		// Get list of all Subscribe objects in Database
		List<Subscribed> listOfSubscribe = uservice.getAllSubscribed();

		for (Subscribed s : listOfSubscribe) {
			// if the customer already subscribed the artist, it shows true
			if (s.getSubscriber() == customer && s.getArtist() == artist && s.isSubscribed() == true
					&& users_Unsubscribed.size() < users_Subscribed.size()) {
				subscribeStatus = true;
			}

			if (s.getSubscriber() == customer && s.getArtist() == artist && s.isSubscribed() == false
					&& users_Unsubscribed.size() > users_Subscribed.size()) {
				subscribeStatus = false;
			}
		}

		if (artistId == userDetails.getUserId()) {
			subscribeStatus = null;
		}

		// code by Max: Get the Current Album's List of Music

		if (AlbumID != null) {
			Album selectedAlbum = aservice.getAlbumByAlbumId(AlbumID);

			Collection<Media> listOfMusic = selectedAlbum.getAlbumMedia();
			model.addAttribute("listOfMusic", listOfMusic);
		}

		NumberOfSubscribers = users_Subscribed.size() - users_Unsubscribed.size();

		model.addAttribute("artistMusicChannelName", artistMusicChannelName);
		model.addAttribute("numberOfArtistAlbums", numberOfArtistAlbums);
		model.addAttribute("numberOfArtistMusics", numberOfArtistMusics);
		model.addAttribute("artistAlbums", artistAlbums);
		model.addAttribute("numberOfSubscribers", NumberOfSubscribers);
		model.addAttribute("subscribeStatus", subscribeStatus);
		model.addAttribute("artistName", artistName);
		model.addAttribute("artistId", artistId);
		model.addAttribute("artist", artist);
		return "ArtistMusicChannel";
	}

	@GetMapping("/music/subscribenoajax/{artistId}")
	public String subscribeArtistNoAjaxMusic(@PathVariable("artistId") Long artistId,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		if (userDetails == null) {
			return "redirect:/login";
		}

		User customer = uservice.findUserByUserId(userDetails.getUserId());
		User artist = aservice.findById(artistId);

		if (artist == null || customer == null) {
			return "redirect:/video/viewartistmusicchannel/{artistId}";
		}

		// add new subscriber object for new subscription
		Subscribed newSubscription = new Subscribed();
		newSubscription.setSubscribed(true);
		newSubscription.setArtist(artist);
		newSubscription.setSubscriber(customer);
		newSubscription.setTimeSubscribed(LocalDateTime.now());

		uservice.saveSubscribed(newSubscription);

		return "redirect:/music/viewartistmusicchannel/{artistId}";
	}

	@GetMapping("/music/unsubscribenoajax/{artistId}")
	public String unsubscribeArtistNoAjaxMusic(@PathVariable("artistId") Long artistId,
			@AuthenticationPrincipal MyUserDetails userDetails) {
		if (userDetails == null) {
			return "redirect:/login";
		}

		User customer = uservice.findUserByUserId(userDetails.getUserId());
		User artist = aservice.findById(artistId);

		if (artist == null || customer == null) {
			return "redirect:/video/viewartistmusicchannel/{artistId}";
		}

		// add new subscriber object for new unsubscription
		Subscribed newUnsubscription = new Subscribed();
		newUnsubscription.setSubscribed(false);
		newUnsubscription.setArtist(artist);
		newUnsubscription.setSubscriber(customer);
		newUnsubscription.setTimeSubscribed(LocalDateTime.now());

		uservice.saveSubscribed(newUnsubscription);

		return "redirect:/music/viewartistmusicchannel/{artistId}";

	}

}