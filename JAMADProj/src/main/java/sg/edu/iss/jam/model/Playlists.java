package sg.edu.iss.jam.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


@Entity
public class Playlists {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long playlistID;
	
	private String playlistName;
	
	private String playlistOrder;
	
	private String playlistDescription;
	
	private MediaType mediaType;
	
	//ManyToOne relation with user
	@ManyToOne
	private User playlistUser;
	
	//ManyToMany relation with media
	@ManyToMany
	private Collection<Media> mediaPlayList;

	public Playlists() {
		super();
	}

	public Playlists(String playlistName, String playlistOrder, String playlistDescription, MediaType mediaType,
			User playlistUser, Collection<Media> mediaPlayList) {
		super();
		this.playlistName = playlistName;
		this.playlistOrder = playlistOrder;
		this.playlistDescription = playlistDescription;
		this.mediaType = mediaType;
		this.playlistUser = playlistUser;
		this.mediaPlayList = mediaPlayList;
	}

	public Long getPlaylistID() {
		return playlistID;
	}

	public void setPlaylistID(Long playlistID) {
		this.playlistID = playlistID;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public String getPlaylistOrder() {
		return playlistOrder;
	}

	public void setPlaylistOrder(String playlistOrder) {
		this.playlistOrder = playlistOrder;
	}

	public String getPlaylistDescription() {
		return playlistDescription;
	}

	public void setPlaylistDescription(String playlistDescription) {
		this.playlistDescription = playlistDescription;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public User getPlaylistUser() {
		return playlistUser;
	}

	public void setPlaylistUser(User playlistUser) {
		this.playlistUser = playlistUser;
	}

	public Collection<Media> getMediaPlayList() {
		return mediaPlayList;
	}

	public void setMediaPlayList(Collection<Media> mediaPlayList) {
		this.mediaPlayList = mediaPlayList;
	}
	
	

}
