package sg.edu.iss.jam.controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sg.edu.iss.jam.model.SCY;
import sg.edu.iss.jam.service.ScyInterface;

@Controller
public class ScyController {
	
	@Autowired
	ScyInterface scyservice;

	@GetMapping("/genericvideolandingpage")
	public String goToHomepage(Model model) {
//		String videoTitle = "You've Got a Very Good Friend.";
		String viddescription = "Rock";
		String vidauthor = "Michael Jackson";
		String videoDuration1 = "5:39"; 
		String videoDuration2 = "13:45";
		String videoDuration3 = "120:12";
		//
		
//		model.addAttribute("videoTitle", videoTitle);
		model.addAttribute("viddescription", viddescription);
		model.addAttribute("vidauthor", vidauthor);
		model.addAttribute("videoDuration1", videoDuration1);
		model.addAttribute("videoDuration2", videoDuration2);
		model.addAttribute("videoDuration3", videoDuration3);
		
		List<SCY> counts=scyservice.findAllCounts();
		List<SCY> top3=counts.stream()
								.sorted(Comparator.comparing(SCY::getCount).thenComparing(SCY::getTitle).reversed())
								.limit(3)
								.collect(Collectors.toList());
		
		String title1=top3.get(0).getTitle();
		String title2=top3.get(1).getTitle();
		String title3=top3.get(2).getTitle();
		model.addAttribute("videoTitle1", title1);
		model.addAttribute("videoTitle2", title2);
		model.addAttribute("videoTitle3", title3);
		return "videolandingpage";
		}
//	
//
//	
	
	
	
	
	
	
	
}
