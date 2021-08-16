package sg.edu.iss.jam.model;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Subscribed {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subscribedID;
	
	// for artist, targetId is customer's Id
	// for customer, targetId is artist's Id
	private Long targetId;
	
	// ManyToOne relationship with User
	// For artist, it links to artist
	// For customer, it links to customer
	@ManyToOne
	private User user;
	
	
	public Subscribed() {
		super();
	}


	public Long getSubscribedID() {
		return subscribedID;
	}


	public void setSubscribedID(Long subscribedID) {
		this.subscribedID = subscribedID;
	}
	

	public Long getTargetId() {
		return targetId;
	}


	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	

}

