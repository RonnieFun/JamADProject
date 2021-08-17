package sg.edu.iss.jam.repo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;

public interface MediaRepository extends JpaRepository<Media, Long> {
	
	
	//Find Media Count by channel 
	@Query("Select count(distinct m) from Media m Join m.mediachannel c where c.channelID =:channelid AND m.mediaType =:MediaType")
	public int CountMediaByChannel(@Param("channelid") long channelid,@Param("MediaType") String MediaType);

	

}
