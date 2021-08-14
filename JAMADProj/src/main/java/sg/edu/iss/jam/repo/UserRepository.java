package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
}
