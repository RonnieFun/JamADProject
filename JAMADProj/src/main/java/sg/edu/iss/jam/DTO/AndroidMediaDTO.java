package sg.edu.iss.jam.DTO;

public class AndroidMediaDTO {

	private Long videoId;
	private Long artistId;
	private String videoThumbnailUrl;
	private String artistProfileThumbnailUrl;
	private String artistName;
	private String videoTitle;
	private String videoDuration;
	private String tags;
	private String videoUrl;
	private Boolean subscribed;

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Boolean getSubscribed() {
		return subscribed;
	}

	public void setSubscribed(Boolean subscribed) {
		this.subscribed = subscribed;
	}

	public AndroidMediaDTO() {
		super();
	}

	public String getVideoThumbnailUrl() {
		return videoThumbnailUrl;
	}

	public void setVideoThumbnailUrl(String videoThumbnailUrl) {
		this.videoThumbnailUrl = videoThumbnailUrl;
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

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public String getVideoDuration() {
		return videoDuration;
	}

	public void setVideoDuration(String videoDuration) {
		this.videoDuration = videoDuration;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Long getVideoId() {
		return videoId;
	}

	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}

	public Long getArtistId() {
		return artistId;
	}

	public void setArtistId(Long artistId) {
		this.artistId = artistId;
	}

}
