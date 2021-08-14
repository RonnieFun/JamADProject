package sg.edu.iss.jam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Comments;
import sg.edu.iss.jam.model.Max;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.Role;
import sg.edu.iss.jam.model.Roles;
import sg.edu.iss.jam.model.Tag;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.model.UserHistory;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.repo.MaxRepository;
import sg.edu.iss.jam.repo.MediaRepository;
import sg.edu.iss.jam.repo.PlaylistsRepository;
import sg.edu.iss.jam.repo.RolesRepository;
import sg.edu.iss.jam.repo.UserRepository;

@SpringBootApplication
public class JamadProjApplication {

	@Autowired
	MaxRepository testrepo;
	
	@Autowired
    UserRepository urepo;
	
	@Autowired
	RolesRepository rolesrepo;
	
	@Autowired
	PlaylistsRepository plrepo;
	
	@Autowired
	MediaRepository mediarepo;
	
	public static void main(String[] args) {
		SpringApplication.run(JamadProjApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		
		return args -> {
			
			Max signup1 = new Max(1L, "Brandon", "1 Day Ago", "How's everyone doing?");
			Max signup2 = new Max(2L, "Chang Ying", "2 Days Ago", "This is so cool! I'm excited!");
			Max signup3 = new Max(3L, "Phyu Sin", "1 Day Ago", "I can't wait to go on the concert tour!");
			Max signup4 = new Max(4L, "Alejandro", "1 Week Ago", "This is nice! Please come to Singapore!");
			Max signup5 = new Max(5L, "Ronnie", "1 Year Ago", "I give this guy 1000 likes! This is the best music ever!");
			Max signup6 = new Max(6L, "Zhao Qi", "2 Weeks Ago","I give this guy 1000 likes! This is the best music ever!");
			Max signup7 = new Max(7L, "Max","2 Years Ago", "I give this guy 1000 likes! This is the best music ever! Yes it is! really good!");
			Max signup8 = new Max(8L, "Jay Chou","3 Years Ago", "Thank you everyone for supporting me! I will continue to work hard and make sure all your efforts to support me are worth it! Cheers! Thanks guys!!! :D Support Bruce Lee Too!");
			
			Max signup9 = new Max(9L, "Bruce Lee","3 Years Ago", "Thank you everyone for supporting me! I will continue to work hard "
					+ "and make sure all your efforts to support me are worth it! Cheers! Thanks guys!!! :D Support Bruce Lee Too!"
					+ "Cheers! Thanks guys!!! :D Support Bruce Lee Too! Cheers! Thanks guys!!! :D Support Bruce Lee Too!");
			
			testrepo.save(signup1);
			testrepo.save(signup2);
			testrepo.save(signup3);
			testrepo.save(signup4);
			testrepo.save(signup5);
			testrepo.save(signup6);
			testrepo.save(signup7);
			testrepo.save(signup8);
			testrepo.save(signup9);
			
			Roles Artist = new Roles(Role.Artist);
			Roles Customer = new Roles(Role.Customer);
			Roles ServiceProvider = new Roles(Role.ServiceProvider);
			
			rolesrepo.save(Artist);
			rolesrepo.save(Customer);
			rolesrepo.save(ServiceProvider);
			
			List<Roles> maxRoles = new ArrayList<>();
			
			maxRoles.add(Artist);
			
			User max = new User("max", "chen", "max@gmail.com", "abcdefg", "21 June 1987", "MaxChen87", "Best User Ever", "www.max.com", maxRoles);
			
			urepo.save(max);
			
			List<Media> FirstPlayListMedia = new ArrayList<>();
			
			List<Media> SecondPlayListMedia = new ArrayList<>();
			
			List<UserHistory> userHistory = new ArrayList<>();
			
			List<Comments> maxComments = new ArrayList<>();

			List<Channel> maxChannel = new ArrayList<>();
			
			List<Playlists> maxPlaylists = new ArrayList<>();
			
			List<Tag> maxTags = new ArrayList<>();
			
			Media jaychoumusic = new Media(MediaType.Video, "www.jaychou.com", "You've Got a Very Good Friend. Be Happy and Stay Safe! :) Have Fun!", "10:00", "21 July, 2002", "published", 
					"www.jaychou.com/thumbnail", userHistory, maxComments, maxChannel, maxPlaylists, maxTags);
			
			mediarepo.save(jaychoumusic);
		
			Playlists FirstPlaylist = new Playlists("Max's Playlist", "1", "Nice Playlist", MediaType.Video, 
					max, FirstPlayListMedia);
			
			Playlists SecondPlaylist = new Playlists("Max's Second Playlist", "2", "Second Playlist", MediaType.Video, 
					max, SecondPlayListMedia);
			
			plrepo.save(FirstPlaylist);
			plrepo.save(SecondPlaylist);
		
			maxPlaylists.add(FirstPlaylist);
			maxPlaylists.add(SecondPlaylist);			
			
		};
	}
	
}
