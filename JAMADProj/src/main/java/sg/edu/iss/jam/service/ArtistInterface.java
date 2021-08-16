package sg.edu.iss.jam.service;
import java.util.List;

import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.User;
public interface ArtistInterface {

	List<Product> getProductListByArtistID(long userID);

	User getArtistByID(long artistid);

}
