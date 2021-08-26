package sg.edu.iss.jam.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.iss.jam.DTO.UserDTO;
import sg.edu.iss.jam.model.Role;
import sg.edu.iss.jam.model.Roles;
import sg.edu.iss.jam.model.Status;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.service.UserInterface;

@RestController
@RequestMapping("/api")
public class LoginRestController {
	
	@Autowired
	UserInterface userService;
	
	
	@PostMapping("/register")
    public String registerUser(@Valid @RequestBody User newUser) {
        List<User> users = userService.getAllUser();
        System.out.println("New user: " + newUser.toString());
        for (User user : users) {
            System.out.println("Registered user: " + newUser.toString());
            if (user.getEmail().equals(newUser.getEmail())) {
                System.out.println("User Already exists!");
                return "USER_ALREADY_EXISTS";
            }
        }
        
        Collection<Roles> roles = new ArrayList<Roles>();
        String rawPassword = newUser.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(rawPassword);
		newUser.setPassword(encodedPassword);
		newUser.setProfileUrl("/images/default_user.jpg");
		newUser.setRoles(roles);
		Roles b = new Roles(Role.Customer, newUser);
		userService.updateUser(newUser);
		userService.saveRole(b);
        return "SUCCESS";
    }
    
    @PostMapping("/login")
    public String loginUser(@Valid @RequestBody User user) {
        List<User> users = userService.getAllUser();
        for (User other : users) {
            if (other.getEmail().equals(user.getEmail())) {
                return "SUCCESS";
            }
        }
        return "FAILURE";
    }
}
