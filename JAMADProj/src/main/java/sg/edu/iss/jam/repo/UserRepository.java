package sg.edu.iss.jam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u where u.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
//	
//	@Query("SELECT s FROM Subscribed s JOIN s.user u where u.userID = :userid")
//	List<Subscribed>getListofFollowingByUserId(@Param("userid") long userId);
//	
	
}
