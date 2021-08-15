package sg.edu.iss.jam.service;

import org.springframework.http.ResponseEntity;

public interface VideoServiceInterface {
	
	public ResponseEntity<byte[]> getContent(String location, String fileName, String range, String contentTypePrefix);


}
