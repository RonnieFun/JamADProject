package sg.edu.iss.jam.service;
import java.util.List;

import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.User;

public interface ArtistInterface {
	
	User findById(Long userID);
	
	User saveUser(User user);
	
	Subscribed saveSubscribed(Subscribed subscribed);

	void deleteSubscribed(Subscribed s);

	List<Product> getProductListByArtistID(long userID);

	User getArtistByID(long artistid);

}

