package sg.edu.iss.jam.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import sg.edu.iss.jam.model.Category;
import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.ChannelRepository;
import sg.edu.iss.jam.repo.MediaRepository;
import sg.edu.iss.jam.repo.UserRepository;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jamDTO.ChannelDTO;
import sg.edu.iss.jamDTO.MediaDTO;

@Controller
@RequestMapping("/artist")
public class ArtistController {

	@Autowired
	ChannelRepository ChannelRepo;
	@Autowired
	MediaRepository MediaRepo;
	@Autowired
	UserRepository UserRepo;
	@Autowired
	ArtistInterface ArtistService;

	// TODO awaiting sessions and userid
	@GetMapping("/manageshop")
	public String manageShopAllProducts(Model model) {
		User artist = ArtistService.getArtistByID(1);
		List<Product> productsInShop = ArtistService.getProductListByArtistID((long) 1);
		Map<Product, Long> productsAndCountShop = new HashMap<Product, Long>();
		for (Product product : productsInShop) {
			Long quantity = ArtistService.getQuantitySold(product.getProductID());
			productsAndCountShop.put(product, quantity);

		}
		model.addAttribute("productsAndCountShop", productsAndCountShop);
		model.addAttribute("artist", artist);
		model.addAttribute("category", "allProducts");
		return "artistmanageshop";
	}

	// TODO awaiting sessions and userid
	@GetMapping("/manageshop/musiccollection")
	public String manageShopMusicCollection(Model model) {
		User artist = ArtistService.getArtistByID(1);
		List<Product> productsInShop = ArtistService.getProductListByArtistIDAndCategory(1, Category.MusicCollection);
		Map<Product, Long> productsAndCountShop = new HashMap<Product, Long>();
		for (Product product : productsInShop) {
			Long quantity = ArtistService.getQuantitySold(product.getProductID());
			productsAndCountShop.put(product, quantity);
		}
		model.addAttribute("productsAndCountShop", productsAndCountShop);
		model.addAttribute("artist", artist);
		model.addAttribute("category", "musicCollection");
		return "artistmanageshop";
	}

	// TODO awaiting sessions and userid
	@GetMapping("/manageshop/merchandise")
	public String manageShopMerchandise(Model model) {
		User artist = ArtistService.getArtistByID(1);
		List<Product> productsInShop = ArtistService.getProductListByArtistIDAndCategory(1, Category.Merchandise);
		Map<Product, Long> productsAndCountShop = new HashMap<Product, Long>();
		for (Product product : productsInShop) {
			Long quantity = ArtistService.getQuantitySold(product.getProductID());
			productsAndCountShop.put(product, quantity);
		}
		model.addAttribute("productsAndCountShop", productsAndCountShop);
		model.addAttribute("artist", artist);
		model.addAttribute("category", "merchandise");
		return "artistmanageshop";
	}

	// TODO awaiting sessions and userid
	@GetMapping("/manageshop/clothing")
	public String manageShopClothing(Model model) {
		User artist = ArtistService.getArtistByID(1);
		List<Product> productsInShop = ArtistService.getProductListByArtistIDAndCategory(1, Category.Clothing);
		Map<Product, Long> productsAndCountShop = new HashMap<Product, Long>();
		for (Product product : productsInShop) {
			Long quantity = ArtistService.getQuantitySold(product.getProductID());
			productsAndCountShop.put(product, quantity);
		}
		model.addAttribute("productsAndCountShop", productsAndCountShop);
		model.addAttribute("artist", artist);
		model.addAttribute("category", "clothing");
		return "artistmanageshop";
	}

	@GetMapping("/addnewproduct")
	public String addNewProduct(Model model) {
		Product newProduct = new Product();
		model.addAttribute("newProduct", newProduct);
		Map<Category, String> categories = new HashMap<Category, String>();
		for (Category category : Category.values()) {
			if (category == Category.MusicCollection) {
				categories.put(category, "Music Collection");
			} else {
				categories.put(category, category.toString());
			}
		}
		model.addAttribute("categories", categories);
		return "addnewproduct";
	}

	@GetMapping("/editproduct")
	public String editProduct(@RequestParam("productID") Long productID, Model model) {
		Product product = ArtistService.getProductByID(productID);
		model.addAttribute("product", product);
		Map<Category, String> categories = new HashMap<Category, String>();
		for (Category category : Category.values()) {
			if (category == Category.MusicCollection) {
				categories.put(category, "Music Collection");
			} else {
				categories.put(category, category.toString());
			}
		}
		model.addAttribute("categories", categories);
		return "editproduct";
	}

	// TODO awaiting sessions and userid
	@PostMapping("/saveproduct")
	public String saveProduct(@Valid @ModelAttribute("product") Product product,
			@RequestParam("file") Optional<MultipartFile> rawfile, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "artist/editproduct";
		}

		product.setProductUser(ArtistService.findById((long) 1));
		ArtistService.saveProduct(product);

		if (!rawfile.get().isEmpty()) {

			MultipartFile file = rawfile.get();
			Long productidtemp = product.getProductID();

			Path fileStorageLocation = Paths.get("src/main/resources/static/productimages");
			String filename = productidtemp.toString() + "_productimg."
					+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			Path destinationFile = fileStorageLocation.resolve(Paths.get(filename)).toAbsolutePath();
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}

			product.setProductUrl("/productimages/" + filename);
			ArtistService.saveProduct(product);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "redirect:/artist/manageshop";
	}

	@GetMapping("/channel")
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

	@PostMapping("/channel/editchannel")
	public String EditChannel(@ModelAttribute("channel") @Validated Channel channeldto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "redirect:/artist/channel";
		}

		System.out.println(channeldto.getChannelID());
		System.out.println(channeldto.getChannelName());
		System.out.println(channeldto.getChannelDescription());

		Channel channel = ArtistService.getChannel(channeldto.getChannelID());

		channel.setChannelName(channeldto.getChannelName());
		channel.setChannelDescription(channeldto.getChannelDescription());

		ArtistService.saveChannel(channel);
		return "redirect:/artist/channel";
	}

	@GetMapping("channel/{MediaType}")
	public String ChannelContent(@PathVariable("MediaType") String MediaTypeString, Model model) {

		// get AristID(userID)
		Long userid = (long) 1;

		// get enum mediatype
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
			MediaDTOList.add(new MediaDTO(Media, ArtistService.getViewcountByMedia(Media),
					ArtistService.getCommentcountByMedia(Media), ArtistService.getTagsByMedia(Media)));
		}

		model.addAttribute("MediaDTOList", MediaDTOList);

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
//		Courses course1 = new Courses();
//		List<Users> lecturers = leservice.getAllUsersByRole(Roles.LECTURER);
//		model.addAttribute("course", course);
//		model.addAttribute("lecturers", lecturers);
//		model.addAttribute("statusupcoming", CourseStatus.UPCOMING);
//		model.addAttribute("statusongoing", CourseStatus.ONGOING);
//		model.addAttribute("statuscompleted", CourseStatus.COMPLETED);
//
//		// check if submission has any class annotation validation errors

//
//		// validate if submission's start date is before end date
//		if (course.getCourseStartDate() != null && course.getCourseEndDate() != null) {
//			if (course.getCourseStartDate().compareTo(course.getCourseEndDate()) > 0) {
//				model.addAttribute("enddateerror", "End date should not be before start date.");
//				return "admin/courseform";
//			}
//		}
//
//		// validate if submission's start date is before exam date
//		if (course.getCourseStartDate() != null && course.getExamDate() != null) {
//			if (course.getCourseStartDate().compareTo(course.getExamDate()) > 0) {
//				model.addAttribute("examdateerror", "Exam date should not be before start date.");
//				return "admin/courseform";
//			}
//		}
//
//		// validate if submission's start date and end date are populated for automatic
//		// course status
//		if (course.getCourseStatus() == null) {
//			if (course.getCourseStartDate() == null || course.getCourseEndDate() == null) {
//				model.addAttribute("datemissingerror",
//						"Course Start Date and Course End Date need to be filled in for automatic detection of course status.");
//				return "admin/courseform";
//			} else {
//				if (course.getCourseStartDate().compareTo(LocalDate.now()) > 0) {
//					course.setCourseStatus(CourseStatus.UPCOMING);
//				} else if (course.getCourseStartDate().compareTo(LocalDate.now()) <= 0
//						&& course.getCourseEndDate().compareTo(LocalDate.now()) >= 0) {
//					course.setCourseStatus(CourseStatus.ONGOING);
//				} else if (course.getCourseEndDate().compareTo(LocalDate.now()) < 0) {
//					course.setCourseStatus(CourseStatus.COMPLETED);
//				}
//			}
//		}
//
//		// save course + save course to selected lecturers
//		if (adservice.findCourseById(course.getCourseID()).isPresent()) {
//			course1 = adservice.getCourseById(course.getCourseID());
//		}
//		if (course1.getUsers() != null) {
//			for (Users a : course1.getUsers()) {
//				leservice.removeCourseTaught(a.getUserID(), course1);
//				adservice.save(a);
//			}
//		}
//		adservice.savecourse(course);
//		if (course.getUsers() != null) {
//			for (Users a : course.getUsers()) {
//				leservice.addCourseTaught(a.getUserID(), course);
//				adservice.save(a);
//			}
//		}
//		return "forward:/admin/courselist";
