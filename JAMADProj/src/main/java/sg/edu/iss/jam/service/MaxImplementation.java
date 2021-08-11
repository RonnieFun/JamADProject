package sg.edu.iss.jam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.Max;
import sg.edu.iss.jam.repo.MaxRepository;

@Service
public class MaxImplementation implements MaxInterface {

	@Autowired
	MaxRepository signuprepo;

	@Override
	public List<Max> findAllSignups() {
		
		return signuprepo.findAll();
	}

}
