package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.UserHistory;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
	
	@Query("Select count(distinct h) from UserHistory h Join h.mediaHistoryList m where m.id =:mediaID")
	public int CountViewsByMedia(@Param("mediaID") long mediaID);


}
