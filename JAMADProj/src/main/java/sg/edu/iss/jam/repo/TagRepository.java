package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
