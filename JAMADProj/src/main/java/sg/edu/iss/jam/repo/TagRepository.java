package sg.edu.iss.jam.repo;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	
	
	public Collection<Tag> findByMediaTagList(Media media);
	

}
