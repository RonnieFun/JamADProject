package sg.edu.iss.jam.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import sg.edu.iss.jam.model.Comments;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.Tag;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.model.UserHistory;
import sg.edu.iss.jam.model.ShoppingCart;

public interface UserInterface {

	//USER
	User findUserByUserId(Long userID);
	User saveUser(User user);

	//PLAYLISTS
	List<Playlists> findPlaylistsByUserId(Long userID);
	Playlists findPlaylistByPlaylistID(long playlistID);
	Playlists savePlaylist(Playlists playlists);
	List<Playlists> savePlaylists(List<Playlists> playlists);
	
	//MEDIA
	Media findMediaByMediaId(Long ID);
	List<Media> findMediaListByPlayListID(Long playlistID);
	List<Media> findAllMedia();
	Media saveMedia(Media media);

	//SUBSCRIBED
	Subscribed saveSubscribed(Subscribed subscribed);
	void deleteSubscribed(Subscribed s);
	
	//COMMENTS
	List<Comments> findCommentsByMediaId(Long id);
	List<Comments> findCommentsByUserId(Long id);
	Comments saveComment(Comments comment);
	
	//TAGS
	List<Tag> findTagsByMediaId(Long id);
	
	//USER HISTORY
	List<UserHistory> findUserHistoryByMediaId(Long id);
	
	ShoppingCart getShoppingCartByUserID(long userID);
	
	Product findProduct(Long id);

}

