package sg.edu.iss.jam.repo;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.Playlists;

public interface MediaRepository extends JpaRepository<Media, Long> {
	
	
	//Find Media Count by channel 
	@Query("Select count(distinct m) from Media m Join m.mediachannel c where c.channelID =:channelid AND m.mediaType =:mediaType")
	public int CountMediaByChannel(@Param("channelid") long channelid,@Param("mediaType") MediaType video);
	
	public Collection<Media> findBymediachannel(Channel channel);

}
