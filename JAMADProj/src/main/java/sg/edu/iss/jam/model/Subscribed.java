package sg.edu.iss.jam.model;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Subscribed {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subscribedID;
	
	//manyToMany relation with user
	
	@ManyToMany(mappedBy = "subscribers")
	private Collection<User> subscribers;

	public Subscribed() {
		super();
	}

	public Subscribed(Collection<User> subscribers) {
		super();
		this.subscribers = subscribers;
	}

	public Long getSubscribedID() {
		return subscribedID;
	}

	public void setSubscribedID(Long subscribedID) {
		this.subscribedID = subscribedID;
	}

	public Collection<User> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Collection<User> subscribers) {
		this.subscribers = subscribers;
	}
	
	

}
