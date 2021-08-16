package sg.edu.iss.jam.service;

import java.util.List;

<<<<<<< HEAD
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.Playlists;
=======
import javax.print.attribute.standard.Media;

import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.Subscribed;
>>>>>>> refs/heads/master
import sg.edu.iss.jam.model.User;

public interface UserInterface {

	User findById(Long userID);
	
	User saveUser(User user);
	
<<<<<<< HEAD
	List<Playlists> findPlaylistsByUserId(Long userID);
	
	Playlists findPlaylistByPlaylistID(long playlistID);
	
	Playlists savePlaylist(Playlists playlists);

	List<Playlists> savePlaylists(List<Playlists> playlists);
	
	Media findByid(Long ID);
	
	void deleteByid(Long ID);
	
	List<Media> findMediaListByPlayListID(Long playlistID);
=======
	
	Subscribed saveSubscribed(Subscribed subscribed);

	void deleteSubscribed(Subscribed s);
>>>>>>> refs/heads/master
}

