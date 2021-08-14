package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.Media;

public interface MediaRepository extends JpaRepository<Media, Long> {
	
}
