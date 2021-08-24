package sg.edu.iss.jam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.UserRepository;

@SpringBootApplication
public class JamadProjApplication {
	@Autowired
	UserRepository urepo;

	public static void main(String[] args) {
		SpringApplication.run(JamadProjApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runner() {
		

		
		return args -> {
			User user = new User();
			user.setEmail("asdf@email");
			user.setEnabled(true);
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String rawPassword = "asdf";
			String encodedPassword = encoder.encode(rawPassword);
			
			user.setPassword(encodedPassword);
			urepo.save(user);
		};
	}
}