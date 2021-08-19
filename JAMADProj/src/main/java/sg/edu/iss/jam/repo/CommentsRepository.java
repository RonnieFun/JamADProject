package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
	
	@Query("Select count(distinct c) from Comments c Join c.mediaComment m where m.id =:MediaID")
	public int CountCommentsByMedia(@Param("MediaID") long MediaID);

}
