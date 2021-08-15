package sg.edu.iss.jam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import sg.edu.iss.jam.service.VideoServiceImplementation;

@RestController
@RequestMapping("/api/media")
public class MediaRestAPIController {
	
	private static final String VIDEO_PATH = "src\\main\\resources\\static\\media\\video"; // CHANGE THIS
	private static final String AUDIO_PATH = "src\\main\\resources\\static\\media\\audio";
	
	@Autowired
	VideoServiceImplementation vidservice;

	@GetMapping("/video/{fileName}")
	public Mono<ResponseEntity<byte[]>> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeList,
													@PathVariable("fileName") String fileName) {
		return Mono.just(vidservice.getContent(VIDEO_PATH, fileName, httpRangeList, "video"));
	}
	
	@GetMapping("/audio/{fileName}")
	public Mono<ResponseEntity<byte[]>> streamAudio(@RequestHeader(value = "Range", required = false) String httpRangeList,
													@PathVariable("fileName") String fileName) {
		return Mono.just(vidservice.getContent(AUDIO_PATH, fileName, httpRangeList, "audio"));
	}

}
