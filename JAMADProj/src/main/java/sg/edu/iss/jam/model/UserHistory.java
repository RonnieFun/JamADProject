package sg.edu.iss.jam.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class UserHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int count;
	
	//ManyToOne relation with user 
	@ManyToOne
	private User historyUser;
	
	
	//ManyToMany relation with media
	@ManyToMany
	private Collection<Media> mediaHistoryList;


	public UserHistory() {
		super();
	}


	public UserHistory(int count, User historyUser, Collection<Media> mediaHistoryList) {
		super();
		this.count = count;
		this.historyUser = historyUser;
		this.mediaHistoryList = mediaHistoryList;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public User getHistoryUser() {
		return historyUser;
	}


	public void setHistoryUser(User historyUser) {
		this.historyUser = historyUser;
	}


	public Collection<Media> getMediaHistoryList() {
		return mediaHistoryList;
	}


	public void setMediaHistoryList(Collection<Media> mediaHistoryList) {
		this.mediaHistoryList = mediaHistoryList;
	}
	
	

}
