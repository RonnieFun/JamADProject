package sg.edu.iss.jam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.ProductRepository;
import sg.edu.iss.jam.repo.UserRepository;

@Service
public class ArtistImplementation implements ArtistInterface {

	@Autowired
	ProductRepository prepo;
	
	@Autowired
	UserRepository urepo;
	
	@Override
	public List<Product> getProductListByArtistID(long userID) {
		// TODO Auto-generated method stub
		return prepo.findAll();
	}
	@Override
	public User getArtistByID(long artistid) {
		// TODO Auto-generated method stub
		return urepo.getById(artistid);
	}

}
