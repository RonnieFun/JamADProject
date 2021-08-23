package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

}
