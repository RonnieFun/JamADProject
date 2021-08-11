package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.UserHistory;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

}
