package sg.edu.iss.jam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.User;

public interface SubscribedRepository extends JpaRepository<Subscribed, Long> {
	
	@Query("SELECT count (distinct s) FROM Subscribed s JOIN s.user u where u.userID = :userid")
	int getFollowingByUserId(@Param("userid") long userId);
	
	int countByTargetId(Long targetId);
	
	List<Subscribed>findByTargetId(Long targetId);


}
