package sg.edu.iss.jam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.Subscribed;

public interface SubscribedRepository extends JpaRepository<Subscribed, Long> {

	@Query("SELECT s FROM Subscribed s WHERE s.artist.userID = :userID AND s.subscribed = false")
	List<Subscribed> getArtistUnSubscribed(@Param("userID") Long userID);
	
	@Query("SELECT s FROM Subscribed s WHERE s.artist.userID = :userID AND s.subscribed = true")
	List<Subscribed> getArtistSubscribed(@Param("userID") Long userID);
	
	@Query("SELECT s FROM Subscribed s WHERE s.artist.userID = :userID")
	List<Subscribed> getArtistSubscribedUnsubscribed(@Param("userID") Long userId);
}
