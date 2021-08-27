package sg.edu.iss.jam.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import sg.edu.iss.jam.DTO.AndroidMediaDTO;
import sg.edu.iss.jam.DTO.MediaDTO;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.Tag;
import sg.edu.iss.jam.model.UserHistory;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.MediaServiceInterface;
import sg.edu.iss.jam.service.UserInterface;

@RestController
@RequestMapping("/api")
public class MediaRestController {

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

	@GetMapping("/video/getallrecommendedvideos")
	public ResponseEntity<?> loginVideoLandingPage(@RequestParam("userID") long userID) {

		List<AndroidMediaDTO> androidGetAllVideosDTOList = new ArrayList<>();

		// if the code comes here, it means the user exists in database,
		// then check whether it's new user or not
		boolean hasUserHistoryVideo = true;
		List<UserHistory> userHistoryVideo = uservice.findUserHistoryByUserIdAndMediaType(userID, MediaType.Video);

		if (userHistoryVideo == null || userHistoryVideo.size() == 0) {
			hasUserHistoryVideo = false;
		}

		// If the user has no video UserHistory data (new user),
		// recommend items the same as genericvideolandingpage
		if (hasUserHistoryVideo == false) {

			List<Media> allVideos = mservice.getMediaByUserHistory(MediaType.Video, LocalDate.now().minusMonths(36));
			List<Media> topVideos = new ArrayList<Media>();

			for (Media m : allVideos) {
				if (m.getCreatedOn().compareTo(LocalDate.now().minusDays(180)) > 0)
					topVideos.add(m);
			}

			for (Media video : topVideos) {
				String tags = "";
				AndroidMediaDTO mediaDTO = new AndroidMediaDTO();
				mediaDTO.setArtistName(video.getChannel().getChannelUser().getFullname());
				mediaDTO.setArtistProfileThumbnailUrl(video.getChannel().getChannelUser().getProfileUrl());
				mediaDTO.setMediaDuration(video.getDuration());
				mediaDTO.setMediaThumbnailUrl(video.getThumbnailUrl());
				mediaDTO.setMediaTitle(video.getTitle());
				mediaDTO.setArtistId(video.getChannel().getChannelUser().getUserID());
				mediaDTO.getMediaId(video.getId());
				mediaDTO.getMediaUrl(video.getMediaUrl());
				mediaDTO.getCreatedOn(video.getCreatedOn().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
				for (Tag tag : video.getTagList()) {
					tags = tags + tag.getTagName() + " ";
					tags.trim();
				}
				mediaDTO.setTags(tags);
				androidGetAllVideosDTOList.add(mediaDTO);
			}
			return new ResponseEntity<>(androidGetAllVideosDTOList, HttpStatus.OK);
		}

		// Else if the user has UserHistory data,
		// recommend items based on ML model

		else {

			List<Long> recommend_mediaid_list = new ArrayList<Long>();
			List<Media> recommend_medialist = new ArrayList<Media>();

			String url = "http://127.0.0.1:5000/model1?user_id={1}";
			ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, userID);
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

			for (Media video : recommend_medialist) {
				String tags = "";
				AndroidMediaDTO androidGetAllVideosDTO = new AndroidMediaDTO();
				androidGetAllVideosDTO.setArtistName(video.getChannel().getChannelUser().getFullname());
				androidGetAllVideosDTO
						.setArtistProfileThumbnailUrl(video.getChannel().getChannelUser().getProfileUrl());
				androidGetAllVideosDTO.setMediaDuration(video.getDuration());
				androidGetAllVideosDTO.setMediaThumbnailUrl(video.getThumbnailUrl());
				androidGetAllVideosDTO.setMediaTitle(video.getTitle());
				androidGetAllVideosDTO.setArtistId(video.getChannel().getChannelUser().getUserID());
				androidGetAllVideosDTO.getMediaId(video.getId());
				androidGetAllVideosDTO.getMediaUrl(video.getMediaUrl());
				androidGetAllVideosDTO.getCreatedOn(video.getCreatedOn().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
				for (Tag tag : video.getTagList()) {
					tags = tags + tag.getTagName() + " ";
					tags.trim();
				}
				androidGetAllVideosDTO.setTags(tags);
				androidGetAllVideosDTOList.add(androidGetAllVideosDTO);
			}

			List<Media> allVideos = mservice.findAllVideos();
			Collections.shuffle(allVideos);
			for (Media video : allVideos) {
				if (!recommend_medialist.contains(video)) {
					String tags = "";
					AndroidMediaDTO androidGetAllVideosDTO = new AndroidMediaDTO();
					androidGetAllVideosDTO.setArtistName(video.getChannel().getChannelUser().getFullname());
					androidGetAllVideosDTO
							.setArtistProfileThumbnailUrl(video.getChannel().getChannelUser().getProfileUrl());
					androidGetAllVideosDTO.setMediaDuration(video.getDuration());
					androidGetAllVideosDTO.setMediaThumbnailUrl(video.getThumbnailUrl());
					androidGetAllVideosDTO.setMediaTitle(video.getTitle());
					androidGetAllVideosDTO.setArtistId(video.getChannel().getChannelUser().getUserID());
					for (Tag tag : video.getTagList()) {
						tags = tags + tag.getTagName() + " ";
						tags.trim();
					}
					androidGetAllVideosDTO.setTags(tags);
					androidGetAllVideosDTOList.add(androidGetAllVideosDTO);
				}
			}
			return new ResponseEntity<>(androidGetAllVideosDTOList, HttpStatus.OK);
		}
	}		
}

//	@GetMapping("/video/getallvideos")
//	public ResponseEntity<?> getAllVideos() {
//		List<Media> allVideos = mservice.findAllVideos();
//		Collections.shuffle(allVideos);
//		List<AndroidGetAllVideosDTO> androidGetAllVideosDTOList = new ArrayList<>();
//		for (Media video : allVideos) {
//			String tags = "";
//			AndroidGetAllVideosDTO androidGetAllVideosDTO = new AndroidGetAllVideosDTO();
//			androidGetAllVideosDTO.setArtistName(video.getChannel().getChannelUser().getFullname());
//			androidGetAllVideosDTO.setArtistProfileThumbnailUrl(video.getChannel().getChannelUser().getProfileUrl());
//			androidGetAllVideosDTO.setVideoDuration(video.getDuration());
//			androidGetAllVideosDTO.setVideoThumbnailUrl(video.getThumbnailUrl());
//			androidGetAllVideosDTO.setVideoTitle(video.getTitle());
//			for (Tag tag: video.getTagList()) {
//				tags = tags + tag.getTagName() + " ";
//				tags.trim();
//			}
//			androidGetAllVideosDTO.setTags(tags);
//			androidGetAllVideosDTOList.add(androidGetAllVideosDTO);
//		}
//		return new ResponseEntity<>(androidGetAllVideosDTOList, HttpStatus.OK);
//	}

//	@GetMapping("/video/getallvideos")
//	public ResponseEntity<?> getAllVideos() {
//		List<Media> allVideos = mservice.findAllVideos();
//		return new ResponseEntity<>(allVideos, HttpStatus.OK);
//	}
