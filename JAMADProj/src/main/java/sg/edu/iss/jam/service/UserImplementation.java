package sg.edu.iss.jam.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.Album;
import sg.edu.iss.jam.model.Category;
import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Comments;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.OrderDetails;
import sg.edu.iss.jam.model.Orders;
import sg.edu.iss.jam.model.Payment;
import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.ShoppingCart;
import sg.edu.iss.jam.model.ShoppingCartDetails;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.Tag;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.model.UserHistory;
import sg.edu.iss.jam.repo.CommentsRepository;
import sg.edu.iss.jam.repo.MediaRepository;
import sg.edu.iss.jam.repo.OrderDetailsRepository;
import sg.edu.iss.jam.repo.OrdersRepository;
import sg.edu.iss.jam.repo.PaymentRepository;
import sg.edu.iss.jam.repo.PlaylistsRepository;
import sg.edu.iss.jam.repo.ProductRepository;
import sg.edu.iss.jam.repo.ShoppingCartDetailsRepository;
import sg.edu.iss.jam.repo.ShoppingCartRepository;
import sg.edu.iss.jam.repo.SubscribedRepository;
import sg.edu.iss.jam.repo.TagRepository;
import sg.edu.iss.jam.repo.UserHistoryRepository;
import sg.edu.iss.jam.repo.UserRepository;
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
	
	@Autowired
	OrdersRepository orepo;
	
	@Autowired
	OrderDetailsRepository odrepo;
	
	@Autowired
	PaymentRepository payrepo;
	
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
	public List<Playlists> findPlaylistByUserIdAndMediaType(Long userID, MediaType mediaType) {
		return plrepo.findPlaylistByUserIdAndMediaType(userID, mediaType);
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
	public List<Media> findAllMediaByMediaType(MediaType mediaType) {
		return mediarepo.findAllMediaByMediaType(mediaType);
	}
	
	@Transactional
	public Media saveMedia(Media media) {
		return mediarepo.save(media);
	}
	
	@Transactional
	public Media findMediaByMediaTypeAndMediaId(MediaType mediaType, Long id) {
		return mediarepo.findMediaByMediaTypeAndMediaId(mediaType, id);
	}

	@Transactional
	public List<Media> findMediaByAlbumAndMediaType(Album album, MediaType mediaType) {
		return mediarepo.findMediaByAlbumAndMediaType(album, mediaType);
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
	
	@Transactional
	public List<Subscribed> getAllSubscribed() {
		return subrepo.findAll();
	}
	
	
	@Transactional
	public List<Subscribed> getArtistSubscribed(Long artistId) {
		return subrepo.getArtistSubscribed(artistId);
	}
	
	@Transactional
	public List<Subscribed> getArtistUnSubscribed(Long artistId) {
		return subrepo.getArtistUnSubscribed(artistId);
	}
	
//	@Transactional
//	public List<Subscribed> getArtistSubscribedUnsubscribed(Long userID) {
//		return subrepo.getArtistSubscribedUnsubscribed(userID);
//	}
	
	
	@Override
	public List<Subscribed> getArtistUnsubscribedByLoggInUserId(Long artistId, Long loggedInUserId) {
		// TODO Auto-generated method stub
		return  subrepo.getArtistUnsubscribedByLoggInUserId(artistId, loggedInUserId);
	}
	
	@Override
	public List<Subscribed> getArtistSubscribedByLoggInUserId(Long artistId, Long loggedInUserId) {
		// TODO Auto-generated method stub
		return subrepo.getArtistSubscribedByLoggInUserId(artistId, loggedInUserId);
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
	public UserHistory saveUserHistory(UserHistory userHistory) {
		return uhrepo.save(userHistory);
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
		shdrepo.deleteCartDetailsByID(productID, cartID);
	}

	@Override
	public List<Object[]> getTopAllProductsInPastWeekByOrderDetailsQuantity(int i) {
		return prepo.getTopProductsByOrderDetailsQuantity(PageRequest.of(0, i), LocalDate.now().minusWeeks(1));
	}

	@Override
	public List<Object[]> getTopMusicCollectionProductsInPastWeekByOrderDetailsQuantity(int i) {
		return prepo.getTopProductsByCategoryInPastWeekByOrderDetailsQuantity(PageRequest.of(0, i),
				LocalDate.now().minusWeeks(1), Category.MusicCollection);
	}

	@Override
	public List<Object[]> getTopMerchandiseProductsInPastWeekByOrderDetailsQuantity(int i) {
		return prepo.getTopProductsByCategoryInPastWeekByOrderDetailsQuantity(PageRequest.of(0, i),
				LocalDate.now().minusWeeks(1), Category.Merchandise);
	}

	@Override
	public List<Object[]> getTopClothingProductsInPastWeekByOrderDetailsQuantity(int i) {
		return prepo.getTopProductsByCategoryInPastWeekByOrderDetailsQuantity(PageRequest.of(0, i),
				LocalDate.now().minusWeeks(1), Category.Clothing);
	}

	@Override
	public List<Object[]> getAllProducts() {
		return prepo.getAllProductsAndQuantity();
	}

	@Override
	public List<Object[]> getAllMusicCollections() {
		return prepo.getAllProductsAndQuantityByCategory(Category.MusicCollection);
	}

	@Override
	public List<Object[]> getAllClothing() {
		return prepo.getAllProductsAndQuantityByCategory(Category.Clothing);
	}

	@Override
	public List<Object[]> getAllMerchandise() {
		return prepo.getAllProductsAndQuantityByCategory(Category.Merchandise);
	}

	@Override
	public Long getItemCountByUserID(long artistid) {
		// TODO Auto-generated method stub
		return shdrepo.getItemCount(artistid);
	}

	@Override
	public void saveOrder(Orders neworder) {
		// TODO Auto-generated method stub
		orepo.save(neworder);
	}

	@Override
	public void saveOrderDetailsList(List<OrderDetails> orderDetailList) {
		// TODO Auto-generated method stub
		odrepo.saveAll(orderDetailList);
	}

	@Override
	public void saveCartDetails(ShoppingCartDetails carddetail) {
		// TODO Auto-generated method stub
		shdrepo.save(carddetail);
	}

	@Override
	public ShoppingCartDetails getCartDetailByProductID(Long productId, Long shoppingCartID) {
		// TODO Auto-generated method stub
		return shdrepo.getByProductIdAndCartID(productId,shoppingCartID);
	}
	@Override
	public void updateUser(User user) {
		urepo.save(user);
	}

	@Override
	public void savePayement(@Valid Payment payment) {
		// TODO Auto-generated method stub
		payrepo.save(payment);
	}

	@Override
	public void deleteCartDetails(ShoppingCartDetails cardetail) {
		// TODO Auto-generated method stub
		shdrepo.delete(cardetail);
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		prepo.save(product);
	}

	@Override
	public List<Orders> getPurchaseHistoryByUserId(Long userID) {
		return orepo.getPurchaseHistoryByUserId(userID);
	}

	@Override
	public List<Product> getListOfAllProduts() {
		return prepo.findAll();
	}

	@Override
	public List<UserHistory> findUserHistoryByUserId(Long userId) {
		
		return uhrepo.findUserHistoryByUserId(userId);
	}

	@Override
	public List<UserHistory> findUserHistoryByUserIdAndMediaType(Long userId, MediaType  mediaType) {
		
		return uhrepo.findUserHistoryByUserIdAndMediaType(userId, mediaType);
	}
	
	public List<UserHistory> findAllUserHistory(){
		return uhrepo.findAll();
	}








}
