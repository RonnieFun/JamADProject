package sg.edu.iss.jam.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.iss.jam.DTO.ChannelDTO;
import sg.edu.iss.jam.DTO.MediaDTO;
import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.repo.ChannelRepository;
import sg.edu.iss.jam.repo.MediaRepository;
import sg.edu.iss.jam.repo.UserRepository;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.FileUploadUtil;

@Controller
@RequestMapping("/channel")
public class ArtistController {

	@Autowired
	ChannelRepository ChannelRepo;
	@Autowired
	MediaRepository MediaRepo;
	@Autowired
	UserRepository UserRepo;

	@Autowired
	ArtistInterface ArtistService;

	// 3. SearchBar ???

	@GetMapping("/")
	public String ViewChannel(Model model) {

		// get AristID(userID)
		Long userid = (long) 1;

		Channel ChannelVideo = ChannelRepo.findByChannelUserAndMediaType(UserRepo.findById(userid), MediaType.Video);
		Channel ChannelMusic = ChannelRepo.findByChannelUserAndMediaType(UserRepo.findById(userid), MediaType.Music);

		int VideoCount = MediaRepo.CountMediaByChannel(ChannelVideo.getChannelID(), MediaType.Video);
		int MusicCount = MediaRepo.CountMediaByChannel(ChannelMusic.getChannelID(), MediaType.Music);

		// Repo-Channel Details and Sum up Channel (Create DTO?)
		ChannelDTO ChannelDTOVideo = new ChannelDTO(ChannelVideo, VideoCount, 0);
		ChannelDTO ChannelDTOMusic = new ChannelDTO(ChannelMusic, 0, MusicCount);

		Collection<ChannelDTO> ChannelDTOlist = new ArrayList<ChannelDTO>();
		ChannelDTOlist.add(ChannelDTOVideo);
		ChannelDTOlist.add(ChannelDTOMusic);

		// Add to Model
		model.addAttribute("ChannelDTOlist", ChannelDTOlist);

		return "ChannelList.html";

	}

	@PostMapping("/editchannel")
	public String EditChannel(@ModelAttribute("channel") @Validated Channel channeldto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "ChannelList.html";
		}

		System.out.println(channeldto.getChannelID());
		System.out.println(channeldto.getChannelName());
		System.out.println(channeldto.getChannelDescription());

		Channel channel = ArtistService.getChannel(channeldto.getChannelID());

		channel.setChannelName(channeldto.getChannelName());
		channel.setChannelDescription(channeldto.getChannelDescription());

		ArtistService.saveChannel(channel);
		return "redirect:/channel/";
	}

	@GetMapping("/{MediaType}")
	public String ChannelContent(@PathVariable("MediaType") String MediaTypeString, Model model) {

		// get AristID(userID)
		Long userid = (long) 1;
		
		//get enum mediatype
		MediaType mediaType = MediaType.valueOf(MediaTypeString);
		
		// get channelID
		Channel Channel = ChannelRepo.findByChannelUserAndMediaType(UserRepo.findById(userid), mediaType);
		
		// Get all media where channel=channelID
		Collection<Media> medias = ArtistService.getMedias(Channel.getChannelID());

		// Create Collection of MediaDTO
		Collection<MediaDTO> MediaDTOList = new ArrayList<MediaDTO>();

		// Add all media to MediaDTO, count view, count comments, concat all tags
		for (Iterator<Media> iterator = medias.iterator(); iterator.hasNext();) {
			Media Media = (Media) iterator.next();
			MediaDTOList.add(new MediaDTO(Media,
					ArtistService.getViewcountByMedia(Media),
					ArtistService.getCommentcountByMedia(Media),
					ArtistService.getTagsByMedia(Media)));
		}
		

		model.addAttribute("MediaDTOList",MediaDTOList);

		return "ChannelContent.html";
	}

	@PostMapping("/editmedia")
	public ResponseEntity<?> EditMedia(@RequestBody @Validated Media media,
			@RequestParam("thumbnail") MultipartFile multipartFileThumbnail,
			@RequestParam("media") MultipartFile multipartFileMedia, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		// String ThumbnailFileName = channel.getChannelID().toString() + "thumbnail";
		// String uploadDir = "channel/ "channel.getChannelID() +"/media";

		if (media.getId() == null) {

		} else {

		}

		// Get Media Object from Form
		// Get Thumbnail File, Rename, Upload
		// Set Media.setthumbnailurl to new thumbnail
		// Get Media File, Rename, Save
		// Set Media.setMediaURL to new File
		// Save Media Object

		return ResponseEntity.ok("Channel Updated");
	}

}
