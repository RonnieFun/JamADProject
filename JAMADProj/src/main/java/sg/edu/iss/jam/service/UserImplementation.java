package sg.edu.iss.jam.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
import sg.edu.iss.jam.model.Roles;
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
import sg.edu.iss.jam.repo.RolesRepository;
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
	
	@Autowired
	RolesRepository rrepo;
	
  	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    Set<Object> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(keyExtractor.apply(t));
	}
	
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

	@Transactional
	public void removeComments(Long commentId) {
		commentsrepo.deleteById(commentId);
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

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return urepo.findAll();
	}

	@Override
	public void saveRole(Roles b) {
		// TODO Auto-generated method stub
		rrepo.save(b);
	}
	
	@Override
	public List<User> getUserSubs(Long userID) {
		
	User user = findUserByUserId(userID);
			
			List<Subscribed> mySubs = subrepo.getArtistSubscribed(user.getUserID());
			mySubs.stream().forEach(x->System.out.println("mySubsStart: " + x.getSubscriber().getUserID()));
			
			List<Subscribed> subscribers = subrepo.getArtistSubscribed(user.getUserID());
			List<Subscribed> unsubs = subrepo.getArtistUnSubscribed(user.getUserID());
			Set<Long> confirmedSubs = new HashSet<>();
			List<Subscribed> tobeRemoved = new ArrayList<>();
			for(Subscribed s : subscribers) {
				if(!confirmedSubs.add(s.getSubscriber().getUserID())) {
					tobeRemoved.add(s);
				}
			}
	
			unsubs.stream().collect(Collectors.toCollection(()->tobeRemoved));
			System.out.println("TBR size: " + tobeRemoved.size());
			System.out.println("mySubs size:" + mySubs.size());
			
			int mySubsSize = mySubs.size();
			List<Subscribed>timeAdjustment = new ArrayList<>();
			
			for(int i = 0; i<mySubsSize; i++) {
				for(int j = 0; j<tobeRemoved.size(); j++) {
					if(mySubs.get(i).getSubscriber().getUserID() == tobeRemoved.get(j).getSubscriber().getUserID()) {
						mySubs.get(i).setTimeSubscribed(mySubs.get(i).getTimeSubscribed().plusYears(100));
						timeAdjustment.add(mySubs.get(i));
					}
				}
			}
					
			Iterator<Subscribed> itr = mySubs.iterator();
			while(itr.hasNext()) {
				Subscribed s = itr.next();
				if(s.getTimeSubscribed().isAfter(LocalDateTime.now())) {
					itr.remove();
				}
			}
			timeAdjustment.stream().forEach(x->x.setTimeSubscribed(x.getTimeSubscribed().minusYears(100)));
			mySubs.stream().forEach(x->System.out.println("ConfirmedSubs: " + x.getSubscriber().getUserID()));
			
			List<Subscribed> mySubCheck = 	subscribers.stream()
					.filter(y->mySubs.stream()
							.noneMatch(x->x.getArtist().getUserID()==(y.getArtist().getUserID())
									&& x.getSubscriber().getUserID()==(y.getSubscriber().getUserID())))
					.filter(distinctByKey(x->x.getSubscriber().getUserID()))
					.collect(Collectors.toList());
			
			mySubCheck.stream().forEach(x->System.out.println("subCheckResult: " + x.getSubscribedID()));
			
			List<Subscribed> latestStatus = new ArrayList<>();
			for(Subscribed s : mySubCheck) {
				List<Subscribed> temp = subrepo.findAllSubscribedBySubId(s.getSubscriber().getUserID());
				Subscribed latest = Collections.max(temp,Comparator.comparing(x->x.getTimeSubscribed()));
				if(latest.isSubscribed()==true && latest.getArtist().getUserID() == user.getUserID()) {
						latestStatus.add(latest);
					}
				}
			
			latestStatus.stream().forEach(x->System.out.println("ConfirmedSubsAfterCheck: " + x.getSubscribedID()));
			
			latestStatus.stream().filter(distinctByKey(x->x.getSubscriber().getUserID())).collect(Collectors.toCollection(()->mySubs));
				
			List<User> subUsers = new ArrayList<>();	
			for(Subscribed s : mySubs) {
				
				Optional<User> ou = urepo.findById(s.getSubscriber().getUserID());
				User u = ou.get();
				subUsers.add(u);
				
			}
					
			for(Subscribed s : mySubs) {
				System.out.println("mySubsFinal" + s.getSubscriber().getUserID());
			}
			
			return subUsers;
		}

	@Override
	public List<User> getFollowing(Long userID) {
		
		User user = findUserByUserId(userID);
		
		List<Subscribed> myFollowings = subrepo.getSubscriptions(user.getUserID());
		myFollowings.stream().forEach(x->System.out.println("myFollowingStart: " + x.getArtist().getUserID()));
		List<Subscribed> following = subrepo.getSubscriptions(user.getUserID());
		List<Subscribed> unfollows = subrepo.getMyUnsubscribe(user.getUserID());
		Set<Long> confirmedFollowings = new HashSet<>();
		List<Subscribed> tobeRemoved = new ArrayList<>();
		for(Subscribed s : following) {
					
					if(!confirmedFollowings.add(s.getArtist().getUserID())) {
						tobeRemoved.add(s);
					}
				}
		unfollows.stream().collect(Collectors.toCollection(()->tobeRemoved));

		int myFollowSize = myFollowings.size();
				
				for(int i = 0; i<myFollowSize; i++) {
					for(int j = 0; j<tobeRemoved.size(); j++) {
						if(myFollowings.get(i).getArtist().getUserID() == tobeRemoved.get(j).getArtist().getUserID()) {
							myFollowings.get(i).setTimeSubscribed(myFollowings.get(i).getTimeSubscribed().plusYears(100));
						}
					}
				}

				Iterator<Subscribed> itr = myFollowings.iterator();
				while(itr.hasNext()) {
					Subscribed s = itr.next();
					if(s.getTimeSubscribed().isAfter(LocalDateTime.now())) {
						s.setTimeSubscribed(s.getTimeSubscribed().minusYears(100));
						itr.remove();
					}
				}
		myFollowings.stream().forEach(x->System.out.println("ConfirmedSubs: " + x.getArtist().getUserID()));
		
		
		myFollowings.stream().forEach(x->System.out.println("myFollowingRemove: " + x.getArtist().getUserID()));
				
				List<Subscribed> myFollowingCheck = following.stream()
						.filter(y->unfollows.stream()
								.anyMatch(x->x.getArtist().getUserID().equals(y.getArtist().getUserID())
										&& x.getSubscriber().getUserID().equals(y.getSubscriber().getUserID())
										&& x.getTimeSubscribed().isBefore(y.getTimeSubscribed())))
						.filter(distinctByKey(x->x.getArtist().getUserID()))
						.collect(Collectors.toList());

	
		myFollowingCheck.stream().forEach(x->System.out.println("followingCheckResult: " + x.getSubscribedID()));
		
		List<Subscribed> latestStatus = new ArrayList<>();
		for(Subscribed s : myFollowingCheck) {
			List<Subscribed> temp = subrepo.findAllFollowingByArtistId(s.getArtist().getUserID());
			Subscribed latest = Collections.max(temp,Comparator.comparing(x->x.getTimeSubscribed()));
			if(latest.isSubscribed()==true && latest.getSubscriber().getUserID() == user.getUserID()) {
					latestStatus.add(latest);
				}
		}
	
		latestStatus.stream().forEach(x->System.out.println("ConfirmedFollowingAfterCheck: " + x.getSubscribedID()));
		latestStatus.stream().filter(distinctByKey(x->x.getArtist().getUserID())).collect(Collectors.toCollection(()->myFollowings));

					List<User> followUsers = new ArrayList<>();			
					for(Subscribed s : myFollowings) {
						
						Optional<User> ou = urepo.findById(s.getArtist().getUserID());
						User u = ou.get();
						followUsers.add(u);	
					}
							
					 myFollowings.stream().forEach(x->System.out.println("mySubsFinal" + x.getArtist().getUserID()));
					 
					 return followUsers;
	}
	
	


}
