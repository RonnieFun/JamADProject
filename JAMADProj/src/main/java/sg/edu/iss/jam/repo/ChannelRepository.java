package sg.edu.iss.jam.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.User;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
	
	public Channel findByChannelUserAndMediaType(Optional<User> optional, MediaType MediaType);

}
