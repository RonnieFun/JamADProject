package sg.edu.iss.jam.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.Category;
import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.Tag;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.ChannelRepository;
import sg.edu.iss.jam.repo.CommentsRepository;
import sg.edu.iss.jam.repo.MediaRepository;
import sg.edu.iss.jam.repo.OrderDetailsRepository;
import sg.edu.iss.jam.repo.ProductRepository;
import sg.edu.iss.jam.repo.SubscribedRepository;
import sg.edu.iss.jam.repo.TagRepository;
import sg.edu.iss.jam.repo.UserHistoryRepository;
import sg.edu.iss.jam.repo.UserRepository;

@Service
public class ArtistImplementation implements ArtistInterface {
	
	@Autowired
	UserRepository urepo;
	
	@Autowired
	SubscribedRepository subrepo;

	@Autowired
	ProductRepository prepo;
	
	@Autowired
	OrderDetailsRepository odrepo;
	
	@Autowired
	ChannelRepository channelrepo;
	
	@Autowired
	MediaRepository mediarepo;
	
	@Autowired
	CommentsRepository commentrepo;
	
	@Autowired
	TagRepository tagrepo;
	
	@Autowired
	UserHistoryRepository historyrepo;
	
	@Transactional
	public User findById(Long userID) {
		
		return urepo.findById(userID).get();
	}


	@Transactional
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return urepo.save(user);
	}
	
	
	@Transactional
	public Subscribed saveSubscribed(Subscribed subscribed) {
		// TODO Auto-generated method stub
		return subrepo.save(subscribed);
	}


	@Transactional
	public void deleteSubscribed(Subscribed s) {
		subrepo.delete(s);
	}
	
	@Override
	public List<Product> getProductListByArtistID(long artistid) {
		// TODO Auto-generated method stub
		return prepo.getProductListByArtistID(artistid);
	}
	@Override
	public User getArtistByID(long artistid) {
		// TODO Auto-generated method stub
		return urepo.getById(artistid);
	}
	
	@Transactional
	public void saveProduct(Product product) {
		prepo.save(product);
	}


	@Override
	public long getQuantitySold(Long productID) {
		if (odrepo.countbyProductId(productID) == null) {
			return (long) 0;
		}
		else {
		return odrepo.countbyProductId(productID);
		}
	}


	@Override
	public Product getProductByID(long productid) {
		// TODO Auto-generated method stub
		return prepo.getById(productid);
	}


	@Override
	public List<Product> getProductListByArtistIDAndCategory(long artistid, Category category) {
		return prepo.getProductListByArtistIDAndCategory(artistid,category);
	}

	@Override
	public List<Object[]> getTopAllProductsInPastWeekByOrderDetailsQuantity(int i) {
		return prepo.getTopProductsByOrderDetailsQuantity(PageRequest.of(0, i), LocalDate.now().minusWeeks(1));
	}
//
//
//	@Override
//	public List<Object[]> getTopMusicCollectionProductsInPastWeekByOrderDetailsQuantity(int i) {
//		return prepo.getTopProductsByCategoryInPastWeekByOrderDetailsQuantity(PageRequest.of(0, i), LocalDate.now().minusWeeks(1), Category.MusicCollection);
//	}
//
//
//	@Override
//	public List<Object[]> getTopMerchandiseProductsInPastWeekByOrderDetailsQuantity(int i) {
//		return prepo.getTopProductsByCategoryInPastWeekByOrderDetailsQuantity(PageRequest.of(0, i), LocalDate.now().minusWeeks(1), Category.Merchandise);
//	}
//
//
//	@Override
//	public List<Object[]> getTopClothingProductsInPastWeekByOrderDetailsQuantity(int i) {
//		return prepo.getTopProductsByCategoryInPastWeekByOrderDetailsQuantity(PageRequest.of(0, i), LocalDate.now().minusWeeks(1), Category.Clothing);
//	}


	@Override
	public Channel getChannel(Long channelID) {

		return channelrepo.findById(channelID).get();
	}


	@Override
	public Channel saveChannel(Channel channel) {

		return channelrepo.save(channel);
	}


	@Override
	public Collection<Media> getMedias(Long channelID) {
		
		Channel channel = channelrepo.findById(channelID).get();
		
		Collection<Media> medias = mediarepo.findBychannel(channel);

		return medias;
	}


	@Override
	public int getCommentcountByMedia(Media Media) {
		
		return commentrepo.CountCommentsByMedia(Media.getId());
	}


	@Override
	public int getViewcountByMedia(Media Media) {
		
		
		return historyrepo.CountViewsByMedia(Media.getId());
	}


	@Override
	public String getTagsByMedia(Media Media) {
		
		Collection<Tag> taglist = tagrepo.findByMediaTagList(Media);
		
		String Concat = "";
		
		for (Iterator iterator = taglist.iterator(); iterator.hasNext();) {
			Tag tag = (Tag) iterator.next();
			Concat = Concat + tag.getTagName() +", ";
		}
		
		return Concat;
	}
}
