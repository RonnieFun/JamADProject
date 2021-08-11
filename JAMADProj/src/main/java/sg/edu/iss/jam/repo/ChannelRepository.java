package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

}
