package sg.edu.iss.jam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.SCY;
import sg.edu.iss.jam.repo.ScyRepository;

@Service
public class ScyImplementation implements ScyInterface {
	@Autowired
	ScyRepository scyrepo;
	
    @Override
	public List<SCY> findAllCounts() {
		return scyrepo.findAll();
	}

}
 