package sg.edu.iss.jam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.repo.SubscribedRepository;
import sg.edu.iss.jam.repo.UserRepository;

@Service
public class ConsumerImplementation implements ConsumerInterface{
	
	@Autowired
	SubscribedRepository srepo;
	
	@Autowired
	UserRepository urepo;

	


}
