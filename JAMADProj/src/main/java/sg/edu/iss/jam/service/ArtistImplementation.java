package sg.edu.iss.jam.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.ChannelRepository;
import sg.edu.iss.jam.repo.SubscribedRepository;
import sg.edu.iss.jam.repo.UserRepository;

@Service
public class ArtistImplementation implements ArtistInterface {
	
	@Autowired
	UserRepository urepo;
	
	@Autowired
	SubscribedRepository subrepo;
	
	@Autowired
	ChannelRepository channelrepo;
	
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
	@Transactional
	public Channel saveChannel(Channel channel) {
		return channelrepo.save(channel);
	}
	


}
