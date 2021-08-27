package sg.edu.iss.jam.DTO;

public class AndroidMediaDTO {

	private Long mediaId;
	private Long artistId;
	private String mediaThumbnailUrl;
	private String artistProfileThumbnailUrl;
	private String artistName;
	private String mediaTitle;
	private String mediaDuration;
	private String tags;
	private String mediaUrl;
	private Boolean subscribed;
	
	
	public AndroidMediaDTO() {
		super();
	}
	

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public Boolean getSubscribed() {
		return subscribed;
	}

	public void setSubscribed(Boolean subscribed) {
		this.subscribed = subscribed;
	}

	public String getMediaThumbnailUrl() {
		return mediaThumbnailUrl;
	}

	public void setMediaThumbnailUrl(String videoThumbnailUrl) {
		this.mediaThumbnailUrl = videoThumbnailUrl;
	}

	public String getArtistProfileThumbnailUrl() {
		return artistProfileThumbnailUrl;
	}

	public void setArtistProfileThumbnailUrl(String artistProfileThumbnailUrl) {
		this.artistProfileThumbnailUrl = artistProfileThumbnailUrl;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getMediaTitle() {
		return mediaTitle;
	}

	public void setMediaTitle(String videoTitle) {
		this.mediaTitle = videoTitle;
	}

	public String getMediaDuration() {
		return mediaDuration;
	}

	public void setMediaDuration(String videoDuration) {
		this.mediaDuration = videoDuration;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void getMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public Long getArtistId() {
		return artistId;
	}

	public void setArtistId(Long artistId) {
		this.artistId = artistId;
	}

}