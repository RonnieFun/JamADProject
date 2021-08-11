package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.Subscribed;

public interface SubscribedRepository extends JpaRepository<Subscribed, Long> {

}
