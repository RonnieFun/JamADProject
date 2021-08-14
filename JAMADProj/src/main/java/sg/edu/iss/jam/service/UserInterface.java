package sg.edu.iss.jam.service;

import java.util.List;

import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.User;

public interface UserInterface {

	User findById(Long userID);
	
	User saveUser(User user);
	
	List<Playlists> findPlaylistsByUserId(Long userID);
	
	Playlists findPlaylistByPlaylistID(long playlistID);
	
	Playlists savePlaylist(Playlists playlists);

	Media findByid(Long ID);
	
	void deleteByid(Long ID);
	
	List<Media> findMediaListByPlayListID(Long playlistID);
}
