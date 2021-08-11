package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.Playlists;

public interface PlaylistsRepository extends JpaRepository<Playlists, Long> {

}
