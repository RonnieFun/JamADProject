package sg.edu.iss.jam.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.ProductRepository;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.SubscribedRepository;
import sg.edu.iss.jam.repo.UserRepository;

@Service
public class ArtistImplementation implements ArtistInterface {
	
	@Autowired
	UserRepository urepo;
	
	@Autowired
	SubscribedRepository subrepo;
	
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
