package sg.edu.iss.jam;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import sg.edu.iss.jam.model.Max;
import sg.edu.iss.jam.model.Role;
import sg.edu.iss.jam.model.Roles;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.MaxRepository;
import sg.edu.iss.jam.repo.UserRepository;

@SpringBootApplication
public class JamadProjApplication {

	@Autowired
	MaxRepository testrepo;
	
	@Autowired
	UserRepository urepo;
	
	public static void main(String[] args) {
		SpringApplication.run(JamadProjApplication.class, args);
	}

	
}
