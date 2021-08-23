package sg.edu.iss.jam.repo;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.Album;
import sg.edu.iss.jam.model.Channel;

public interface AlbumRepository extends JpaRepository<Album, Long>  {
	
	@Query("SELECT a from Album a join a.channel c where c.channelID =:channelID")
	Collection<Album> findBychannelID(@Param("channelID") Long channelID);
	
}
