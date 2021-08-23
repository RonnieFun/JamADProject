package sg.edu.iss.jam.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.Album;
import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;

public interface MediaRepository extends JpaRepository<Media, Long> {
	
	@Query("SELECT m FROM Media m JOIN m.playLists pl WHERE pl.playlistID = :playlistID")
	List<Media> findMediaListByPlayListID(@Param("playlistID") Long playlistID);
	
	//Find Media Count by channel 
	@Query("Select count(distinct m) from Media m Join m.channel c where c.channelID =:channelid AND m.mediaType =:mediaType")
	public int CountMediaByChannel(@Param("channelid") long channelid,@Param("mediaType") MediaType video);
	
	@Query("Select count (distinct m) from Media m Join m.album a where a.AlbumID =:AlbumID")
	public int CountMediaByAlbum(@Param("AlbumID") long AlbumID);
	
	public Collection<Media> findBychannel(Channel channel);
	
	@Query("Select m FROM Media m JOIN m.album al WHERE al.AlbumID =:AlbumID ORDER BY m.AlbumOrder ")
	public List<Media> findByalbum(Long AlbumID);
	
	
		
	
}
