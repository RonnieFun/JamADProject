package sg.edu.iss.jam.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.MediaRepository;
import sg.edu.iss.jam.repo.SubscribedRepository;
import sg.edu.iss.jam.repo.UserRepository;

@Service
public class UserImplementation implements UserInterface {

	@Autowired
	UserRepository urepo;

	
	@Autowired
	MediaRepository mediarepo;
	
	@Autowired
	SubscribedRepository subrepo;
	
	@Transactional
	public User findById(Long userID) {
		
		return urepo.findById(userID).get();
	}

	@Transactional
	public User saveUser(User user) {
		
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

}
