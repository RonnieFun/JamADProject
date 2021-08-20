package sg.edu.iss.jam.model;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PlaylistOrder {
	
	private String PlaylistOrderID;
	
	private int PlaylistOrder;
	
	@ManyToOne
	private Playlists playlists;
	
	@ManyToOne
	private Media media;

	public PlaylistOrder(int playlistOrder, Playlists playlists, Media media) {
		super();
		PlaylistOrder = playlistOrder;
		this.playlists = playlists;
		this.media = media;
	}

	public String getPlaylistOrderID() {
		return PlaylistOrderID;
	}

	public void setPlaylistOrderID(String playlistOrderID) {
		PlaylistOrderID = playlistOrderID;
	}

	public int getPlaylistOrder() {
		return PlaylistOrder;
	}

	public void setPlaylistOrder(int playlistOrder) {
		PlaylistOrder = playlistOrder;
	}

	public Playlists getPlaylists() {
		return playlists;
	}

	public void setPlaylists(Playlists playlists) {
		this.playlists = playlists;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}
	
	
	

}
