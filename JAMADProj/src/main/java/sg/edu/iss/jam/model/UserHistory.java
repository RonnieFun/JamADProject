package sg.edu.iss.jam.model;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class UserHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate datetime;
	
	//ManyToOne relation with user 
	@ManyToOne
	private User historyUser;
	
	//ManyToMany relation with media
	@ManyToOne
	private Media mediaHistory;

	public UserHistory() {
		super();
	}


	public UserHistory(LocalDate datetime, User historyUser, Media mediaHistory) {
		super();
		this.datetime = datetime;
		this.historyUser = historyUser;
		this.mediaHistory = mediaHistory;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public LocalDate getDatetime() {
		return datetime;
	}


	public void setDatetime(LocalDate datetime) {
		this.datetime = datetime;
	}


	public User getHistoryUser() {
		return historyUser;
	}


	public void setHistoryUser(User historyUser) {
		this.historyUser = historyUser;
	}


	public Media getMediaHistory() {
		return mediaHistory;
	}


	public void setMediaHistory(Media mediaHistory) {
		this.mediaHistory = mediaHistory;
	}


	
	
}
