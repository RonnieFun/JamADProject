package sg.edu.iss.jam.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.service.ArtistInterface;

@Controller
@RequestMapping("/artist")
public class ArtistController {

	@Autowired
	ArtistInterface arservice;

	@GetMapping("/manageshop")
	public String manageShop(Model model) {
		User artist = arservice.getArtistByID(1);
		List<Product> productsInShop = arservice.getProductListByArtistID(1);
		Map<Product, Long> productsAndCountShop = new HashMap<Product, Long>();
		for (Product product: productsInShop) 
		{
			Long quantity = arservice.getQuantitySold(product.getProductID());
			productsAndCountShop.put(product, quantity);
			
		}
		model.addAttribute("productsAndCountShop", productsAndCountShop);
		model.addAttribute("artist", artist);
		
		return "artistmanageshop";
	}

	@GetMapping("/addnewproduct")
	public String addNewProduct(Model model) {
		Product newProduct = new Product();
		model.addAttribute("newProduct", newProduct);
		return "addnewproduct";
	}

	@PostMapping("/saveproduct")
	public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
			Model model) {
//		
//		Path fileStorageLocation = Paths.get("src\\main\\resources\\static\\media\\ProductImages");		
//		Path destinationFile = fileStorageLocation.resolve(Paths.get(file.getOriginalFilename())).normalize()
//				.toAbsolutePath();
//		try (InputStream inputStream = file.getInputStream()) {
//			Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		arservice.saveProduct(product);
		
		if (bindingResult.hasErrors()) {
		return "admin/courseform";
	}

		return "redirect:/artist/manageshop";

		// add in the user who saved this product
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
