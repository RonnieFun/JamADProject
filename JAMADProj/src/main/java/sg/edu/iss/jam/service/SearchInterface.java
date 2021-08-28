package sg.edu.iss.jam.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.Album;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.User;

public interface SearchInterface {
	
	//public Collection<User> SearchUserbyFirstName(String name);
	//public Collection<User> SearchUserbyLastName(String name);
	
	public Collection<User> SearchUsersbyName(String name);
	
	//public Collection<Media> SearchMediabyMediaName(String name, MediaType mediaType);
	//public Collection<Media> SearchMediabyArtistName(String name, MediaType mediaType);
	//public Collection<Media> SearchMediabytag(String name, MediaType mediaType);
	
	public Collection<Media> SearchMediabyVarious(String name, MediaType mediaType);
	
	public Collection<Album> SearchAlbumbyName(String name);
	
}
