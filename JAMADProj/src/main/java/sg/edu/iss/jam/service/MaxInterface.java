package sg.edu.iss.jam.service;

import java.util.List;

import sg.edu.iss.jam.model.Max;

public interface MaxInterface {

	List<Max> findAllSignups();
	
	Max findById(Long id);
	
	Max saveMax(Max max);
}
