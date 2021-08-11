package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

}
