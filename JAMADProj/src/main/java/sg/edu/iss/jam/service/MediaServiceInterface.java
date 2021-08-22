package sg.edu.iss.jam.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;

public interface MediaServiceInterface {
	
	public ResponseEntity<byte[]> getContent(String location, String fileName, String range, String contentTypePrefix);
	 
	//scy-videolandingpage
	
	public List<Media> getMediaByTypeAndCount(MediaType mediaType);

	public List<Media> getMediaByUserHistory(MediaType mediaType,LocalDate lesscurrentdate);

//	public List<Object[]> getTopMediasByUserHistory(int i, MediaType mediaType);


}
