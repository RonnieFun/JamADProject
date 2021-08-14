package sg.edu.iss.jam.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.UserRepository;

@Service
public class UserImplementation implements UserInterface {

	@Autowired
	UserRepository urepo;
	
	@Transactional
	public User findById(Long userID) {
		
		return urepo.findById(userID).get();
	}

	@Transactional
	public User saveUser(User user) {
		
		return urepo.save(user);
	}

}
