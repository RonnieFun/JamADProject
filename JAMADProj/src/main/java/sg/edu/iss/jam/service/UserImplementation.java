package sg.edu.iss.jam.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.MediaRepository;
import sg.edu.iss.jam.repo.PlaylistsRepository;

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
	PlaylistsRepository plrepo;
	
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
	public List<Playlists> findPlaylistsByUserId(Long userID) {
		
		return plrepo.findPlaylistsByUserId(userID);
	}

	@Transactional
	public Playlists findPlaylistByPlaylistID(long playlistID) {
		
		return plrepo.findPlaylistByPlaylistID(playlistID);
	}
	
	@Transactional
	public Media findByid(Long ID) {
		
		return mediarepo.getById(ID);
	}

	@Transactional
	public List<Media> findMediaListByPlayListID(Long playlistID) {
		
		return mediarepo.findMediaListByPlayListID(playlistID);
	}

	@Transactional
	public Playlists savePlaylist(Playlists playlists) {
		
		return plrepo.save(playlists);
	}

	@Transactional
	public void deleteByid(Long ID) {
		
		this.mediarepo.deleteById(ID);
	}

	@Transactional
	public List<Playlists> savePlaylists(List<Playlists> playlists) {
		
		return plrepo.saveAll(playlists);
	}

	@Transactional
	public Subscribed saveSubscribed(Subscribed subscribed) {
		
		return subrepo.save(subscribed);
	}

	@Transactional
	public void deleteSubscribed(Subscribed s) {
		
		subrepo.delete(s);
	}

}
