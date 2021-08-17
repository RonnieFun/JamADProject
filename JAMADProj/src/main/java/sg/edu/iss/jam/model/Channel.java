package sg.edu.iss.jam.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Channel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long channelID;
	
	private String channelName;
	
	private String channelDescription;
	
	private MediaType mediaType;
	
	private String createdOn;
	
	//ManyToMany relation with media

	@OneToMany(mappedBy = "channel")
	private Collection<Media> channelMediaList;
	
	//ManyToOne relation with user
	@ManyToOne
	private User channelUser;

	public Channel() {
		super();
	}

	public Channel(String channelName, String channelDescription, MediaType mediaType, String createdOn,
			Collection<Media> channelMediaList, User channelUser) {
		super();
		this.channelName = channelName;
		this.channelDescription = channelDescription;
		this.mediaType = mediaType;
		this.createdOn = createdOn;
		this.channelMediaList = channelMediaList;
		this.channelUser = channelUser;
	}

	public Long getChannelID() {
		return channelID;
	}

	public void setChannelID(Long channelID) {
		this.channelID = channelID;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelDescription() {
		return channelDescription;
	}

	public void setChannelDescription(String channelDescription) {
		this.channelDescription = channelDescription;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public Collection<Media> getChannelMediaList() {
		return channelMediaList;
	}

	public void setChannelMediaList(Collection<Media> channelMediaList) {
		this.channelMediaList = channelMediaList;
	}

	public User getChannelUser() {
		return channelUser;
	}

	public void setChannelUser(User channelUser) {
		this.channelUser = channelUser;
	}
	
	

}
