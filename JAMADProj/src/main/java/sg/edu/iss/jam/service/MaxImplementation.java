package sg.edu.iss.jam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.jam.model.Max;
import sg.edu.iss.jam.repo.MaxRepository;

@Service
public class MaxImplementation implements MaxInterface {

	@Autowired
	MaxRepository signuprepo;

	@Transactional
	public List<Max> findAllSignups() {
		
		return signuprepo.findAll();
	}

	@Transactional
	public Max findById(Long id) {
		return signuprepo.findById(id).get();
	}

	@Transactional
	public Max saveMax(Max max) {
		
		return signuprepo.save(max);
	}

}
