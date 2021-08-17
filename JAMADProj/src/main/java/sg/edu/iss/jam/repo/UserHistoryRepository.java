package sg.edu.iss.jam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.UserHistory;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

	@Query("SELECT uh FROM UserHistory uh JOIN uh.mediaHistoryList m WHERE m.id = :id")
	public List<UserHistory> findUserHistoryByMediaId(@Param("id") Long id);
}
