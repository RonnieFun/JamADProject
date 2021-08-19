package sg.edu.iss.jam.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.controller.ShoppingCartController;
import sg.edu.iss.jam.model.Comments;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.model.UserHistory;
import sg.edu.iss.jam.repo.CommentsRepository;
import sg.edu.iss.jam.repo.MediaRepository;
import sg.edu.iss.jam.repo.PlaylistsRepository;
import sg.edu.iss.jam.repo.ProductRepository;
import sg.edu.iss.jam.repo.ShoppingCartDetailsRepository;
import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.Tag;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.MediaRepository;
import sg.edu.iss.jam.repo.SubscribedRepository;
import sg.edu.iss.jam.repo.TagRepository;
import sg.edu.iss.jam.repo.UserHistoryRepository;
import sg.edu.iss.jam.repo.ShoppingCartRepository;
import sg.edu.iss.jam.repo.UserRepository;
import sg.edu.iss.jam.model.ShoppingCart;
import sg.edu.iss.jam.model.ShoppingCartDetails;
@Service
public class UserImplementation implements UserInterface {

	@Autowired
	UserRepository urepo;

	@Autowired
	PlaylistsRepository plrepo;
	
	@Autowired
	MediaRepository mediarepo;
	
	@Autowired
	SubscribedRepository subrepo;
	
	@Autowired
	TagRepository tagrepo;
	
	@Autowired
	CommentsRepository commentsrepo;
	
	@Autowired
	UserHistoryRepository uhrepo;
	
	@Autowired
	ShoppingCartRepository shrepo;
	
	@Autowired
	ShoppingCartDetailsRepository shdrepo;
	
	@Autowired
	ProductRepository prepo;
	
	//USER REPO
	@Transactional
	public User findUserByUserId(Long userID) {
		return urepo.findById(userID).get();
	}

	@Transactional
	public User saveUser(User user) {
		return urepo.save(user);
	}
	

	//PLAYLISTS REPO
	@Transactional
	public List<Playlists> findPlaylistsByUserId(Long userID) {
		return plrepo.findPlaylistsByUserId(userID);
	}

	@Transactional
	public List<Playlists> savePlaylists(List<Playlists> playlists) {
		return plrepo.saveAll(playlists);
	}
	
	@Transactional
	public Playlists findPlaylistByPlaylistID(long playlistID) {
		
		return plrepo.findPlaylistByPlaylistID(playlistID);
	}
	
	@Transactional
	public Playlists savePlaylist(Playlists playlists) {
		return plrepo.save(playlists);
	}

	
	//MEDIA REPO
	@Transactional
	public Media findMediaByMediaId(Long ID) {
		return mediarepo.getById(ID);
	}

	@Transactional
	public List<Media> findMediaListByPlayListID(Long playlistID) {
		return mediarepo.findMediaListByPlayListID(playlistID);
	}
	
	@Transactional
	public List<Media> findAllMedia(){
		return mediarepo.findAll();
	}
	
	@Transactional
	public Media saveMedia(Media media) {
		return mediarepo.save(media);
	}
	

	//SUBSCRIBED REPO
	@Transactional
	public Subscribed saveSubscribed(Subscribed subscribed) {
		return subrepo.save(subscribed);
	}

	@Transactional
	public void deleteSubscribed(Subscribed s) {
		subrepo.delete(s);
	}

	//TAG REPO
	@Transactional
	public List<Tag> findTagsByMediaId(Long id) {
		return tagrepo.findTagsByMediaId(id);
	}
	
	//COMMENTS REPO
	@Transactional
	public List<Comments> findCommentsByMediaId(Long id) {
		return commentsrepo.findCommentsByMediaId(id);
	}
	
	@Transactional
	public List<Comments> findCommentsByUserId(Long id) {
		return commentsrepo.findCommentsByUserId(id);
	}
	
	@Transactional
	public Comments saveComment(Comments comment) {
		return commentsrepo.save(comment);
	}

	
	//USERHISTORY REPO
	@Transactional
	public List<UserHistory> findUserHistoryByMediaId(Long id) {
		
		return uhrepo.findUserHistoryByMediaId(id);
	}

	@Transactional
	public ShoppingCart getShoppingCartByUserID(long userID) {
		// TODO Auto-generated method stub
		return shrepo.getByUserID(userID);
	}

	@Override
	public Product findProduct(Long id) {
		// TODO Auto-generated method stub
		return prepo.getById(id);
	}

	@Override
	public void removeCartDetails(Long productID,Long cartID) {
		// TODO Auto-generated method stub
		shdrepo.deleteCartDetailsByID(productID,cartID);
	}

	
}
