package sg.edu.iss.jam.service;

import sg.edu.iss.jam.model.User;

public interface UserInterface {

	User findById(Long userID);
	
	User saveUser(User user);
}
