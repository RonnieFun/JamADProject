package sg.edu.iss.jam.service;

import java.util.Collection;

import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.User;

public interface ArtistInterface {
	
	User findById(Long userID);
	
	User saveUser(User user);
	
	Subscribed saveSubscribed(Subscribed subscribed);

	void deleteSubscribed(Subscribed s);
	
	Channel getChannel(Long channelID);
	
	Channel saveChannel(Channel channel);
	
	Collection<Media> getMedias(Long channelID);
	
	int getCommentcountByMedia(Media Media);
	
	int getViewcountByMedia(Media Media);
	
	String getTagsByMedia(Media Media);

}

