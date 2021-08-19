package sg.edu.iss.jam.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.util.Collection;
@Entity
public class Media {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private MediaType mediaType;
	
	private String mediaUrl;
	
	private String title;
	
	private String description;
	
	private String duration;
	
	private String createdOn;
	
	private String publishStatus;
	
	private String thumbnailUrl;

	//relation with userhistory
	@OneToMany(mappedBy = "mediaHistory")
	private Collection<UserHistory> userHistory;
	
	//relation with comment
	@OneToMany(mappedBy = "mediaComment")
	private Collection<Comments> commentList;
	
	//relation with channel
	@ManyToOne
	private Channel channel;

	//relation with playlists
	@ManyToMany(mappedBy = "mediaPlayList")
	private Collection<Playlists> playLists;
	
	//relation with tag
	@ManyToMany(mappedBy = "mediaTagList")
	private Collection<Tag> tagList;

	public Media() {
		super();
	}
	
	public Media(MediaType mediaType, String mediaUrl, String title, String duration, String createdOn,
			String publishStatus, String thumbnailUrl, Collection<UserHistory> userHistory,
			Collection<Comments> commentList, Channel channel, Collection<Playlists> playLists,
			Collection<Tag> tagList) {
		super();
		this.mediaType = mediaType;
		this.mediaUrl = mediaUrl;
		this.title = title;
		this.duration = duration;
		this.createdOn = createdOn;
		this.publishStatus = publishStatus;
		this.thumbnailUrl = thumbnailUrl;
		this.userHistory = userHistory;
		this.commentList = commentList;
		this.channel = channel;
		this.playLists = playLists;
		this.tagList = tagList;
	}
	
	

	public Media(MediaType mediaType, String mediaUrl, String title, String description, String duration, String createdOn,
			String publishStatus, String thumbnailUrl, Collection<UserHistory> userHistory,
			Collection<Comments> commentList, Channel channel, Collection<Playlists> playLists,
			Collection<Tag> tagList) {
		super();
		this.mediaType = mediaType;
		this.mediaUrl = mediaUrl;
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.createdOn = createdOn;
		this.publishStatus = publishStatus;
		this.thumbnailUrl = thumbnailUrl;
		this.userHistory = userHistory;
		this.commentList = commentList;
		this.channel = channel;
		this.playLists = playLists;
		this.tagList = tagList;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public Collection<UserHistory> getUserHistory() {
		return userHistory;
	}

	public void setUserHistory(Collection<UserHistory> userHistory) {
		this.userHistory = userHistory;
	}

	public Collection<Comments> getCommentList() {
		return commentList;
	}

	public void setCommentList(Collection<Comments> commentList) {
		this.commentList = commentList;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Collection<Playlists> getPlayLists() {
		return playLists;
	}

	public void setPlayLists(Collection<Playlists> playLists) {
		this.playLists = playLists;
	}

	public Collection<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(Collection<Tag> tagList) {
		this.tagList = tagList;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	
}
