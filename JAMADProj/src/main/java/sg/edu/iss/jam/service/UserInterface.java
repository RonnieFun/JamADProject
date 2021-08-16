package sg.edu.iss.jam.service;

import java.util.List;

import javax.print.attribute.standard.Media;

import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.User;

public interface UserInterface {

	User findById(Long userID);
	
	User saveUser(User user);
	
	
	Subscribed saveSubscribed(Subscribed subscribed);

	void deleteSubscribed(Subscribed s);
}

