package sg.edu.iss.jam.DTO;

public class AndroidGetAllVideosDTO {

	private String videoThumbnailUrl;
	private String artistProfileThumbnailUrl;
	private String artistName;
	private String videoTitle;
	private String videoDuration;
	private String tags;
	
	public AndroidGetAllVideosDTO() {
		super();
	}

	public AndroidGetAllVideosDTO(String videoThumbnailUrl, String artistProfileThumbnailUrl, String artistName,
			String videoTitle, String videoDuration, String tags) {
		super();
		this.videoThumbnailUrl = videoThumbnailUrl;
		this.artistProfileThumbnailUrl = artistProfileThumbnailUrl;
		this.artistName = artistName;
		this.videoTitle = videoTitle;
		this.videoDuration = videoDuration;
		this.tags = tags;
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
	
}
