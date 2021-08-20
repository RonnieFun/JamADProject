package sg.edu.iss.jam;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.edu.iss.jam.model.Category;
import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Comments;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.Role;
import sg.edu.iss.jam.model.Roles;
import sg.edu.iss.jam.model.ShoppingCart;
import sg.edu.iss.jam.model.ShoppingCartDetails;
import sg.edu.iss.jam.model.Tag;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.model.UserHistory;
import sg.edu.iss.jam.repo.ChannelRepository;
import sg.edu.iss.jam.repo.CommentsRepository;
import sg.edu.iss.jam.repo.MediaRepository;
import sg.edu.iss.jam.repo.OrderDetailsRepository;
import sg.edu.iss.jam.repo.PlaylistsRepository;
import sg.edu.iss.jam.repo.ProductRepository;
import sg.edu.iss.jam.repo.RolesRepository;
import sg.edu.iss.jam.repo.ShoppingCartDetailsRepository;
import sg.edu.iss.jam.repo.ShoppingCartRepository;
import sg.edu.iss.jam.repo.TagRepository;
import sg.edu.iss.jam.repo.UserHistoryRepository;
import sg.edu.iss.jam.repo.UserRepository;

@SpringBootApplication
public class JamadProjApplication {
	
	@Autowired
	RolesRepository rolesrepo;
	
	@Autowired
	PlaylistsRepository plrepo;
	
	@Autowired
	MediaRepository mediarepo;
	
	@Autowired
	UserRepository urepo;
	
	@Autowired
	TagRepository tagrepo;
	
	@Autowired
	CommentsRepository commentsrepo;
	
	@Autowired
	ChannelRepository channelrepo;
	
	@Autowired
	UserHistoryRepository uhrepo;
	
	@Autowired
	RolesRepository rrepo;

	@Autowired
	ProductRepository prepo;

	@Autowired
	OrderDetailsRepository odrepo;
	
	@Autowired
	MediaRepository mrepo;
	
	@Autowired
	ShoppingCartRepository srepo;
	
	@Autowired
	ShoppingCartDetailsRepository shdrepo;

	public static void main(String[] args) {
		SpringApplication.run(JamadProjApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner() {
//
//		return args -> {
//
////			Max signup1 = new Max(1L, "Brandon", "1 Day Ago", "How's everyone doing?");
////			Max signup2 = new Max(2L, "Chang Ying", "2 Days Ago", "This is so cool! I'm excited!");
////			Max signup3 = new Max(3L, "Phyu Sin", "1 Day Ago", "I can't wait to go on the concert tour!");
////			Max signup4 = new Max(4L, "Alejandro", "1 Week Ago", "This is nice! Please come to Singapore!");
////			Max signup5 = new Max(5L, "Ronnie", "1 Year Ago",
////					"I give this guy 1000 likes! This is the best music ever!");
////			Max signup6 = new Max(6L, "Zhao Qi", "2 Weeks Ago",
////					"I give this guy 1000 likes! This is the best music ever!");
////			Max signup7 = new Max(7L, "Max", "2 Years Ago",
////					"I give this guy 1000 likes! This is the best music ever! Yes it is! really good!");
////			Max signup8 = new Max(8L, "Jay Chou", "3 Years Ago",
////					"Thank you everyone for supporting me! I will continue to work hard and make sure all your efforts to support me are worth it! Cheers! Thanks guys!!! :D Support Bruce Lee Too!");
//
////			User user1 =  new User("Jack", "Son", "jack@gmail.com", "123", "20-02-2021", "Daniel", "Youngest artist in Industry","https://www.seekpng.com/png/full/799-7991970_kpop-sticker-got7-miracle-jackson.png",null);
////			
////			urepo.save(user1);
////			Roles role1 = new Roles(Role.Artist, user1);
////			rrepo.save(role1);
////			
////			Product product1= new Product("Twice pink hoodie", "Top Selling product", 2, 1, 12.0,"https://scene7.zumiez.com/is/image/zumiez/product_main_medium/Married-To-The-Mob-Bitch-Pink-Hoodie-_332418-front-US.jpg", null, null, null, user1);
////			Product product2= new Product("Best Album Collection", "Top Selling product", 2, 1, 57.0,"https://upload.wikimedia.org/wikipedia/en/f/f0/2PMBest-Regular.jpeg", null, null, null, user1);
////			Product product3= new Product("Nice Pink Hat", "Top Selling product", 2, 1, 115.0,"http://picture-cdn.wheretoget.it/0v3cfh-l-610x610.jpg", null, null, null, user1);
////			Product product4= new Product("Pink Sneakers", "Top Selling product", 2, 1, 65.0, "https://cdn.trendhunterstatic.com/thumbs/retro-pink-shoes.jpeg",null, null, null, user1);
////			prepo.save(product1);
////			prepo.save(product2);
////			prepo.save(product3);
////			prepo.save(product4);
//			
//			
////			Max signup1 = new Max(1L, "Brandon", "1 Day Ago", "How's everyone doing?");
////			Max signup2 = new Max(2L, "Chang Ying", "2 Days Ago", "This is so cool! I'm excited!");
////			Max signup3 = new Max(3L, "Phyu Sin", "1 Day Ago", "I can't wait to go on the concert tour!");
////			Max signup4 = new Max(4L, "Alejandro", "1 Week Ago", "This is nice! Please come to Singapore!");
////			Max signup5 = new Max(5L, "Ronnie", "1 Year Ago", "I give this guy 1000 likes! This is the best music ever!");
////			Max signup6 = new Max(6L, "Zhao Qi", "2 Weeks Ago","I give this guy 1000 likes! This is the best music ever!");
////			Max signup7 = new Max(7L, "Max","2 Years Ago", "I give this guy 1000 likes! This is the best music ever! Yes it is! really good!");
////			Max signup8 = new Max(8L, "Jay Chou","3 Years Ago", "Thank you everyone for supporting me! I will continue to work hard and make sure all your efforts to support me are worth it! Cheers! Thanks guys!!! :D Support Bruce Lee Too!");
////			
////			Max signup9 = new Max(9L, "Bruce Lee","3 Years Ago", "Thank you everyone for supporting me! I will continue to work hard "
////					+ "and make sure all your efforts to support me are worth it! Cheers! Thanks guys!!! :D Support Bruce Lee Too!"
////					+ "Cheers! Thanks guys!!! :D Support Bruce Lee Too! Cheers! Thanks guys!!! :D Support Bruce Lee Too!");
////			
////			testrepo.save(signup1);
////			testrepo.save(signup2);
////			testrepo.save(signup3);
////			testrepo.save(signup4);
////			testrepo.save(signup5);
////			testrepo.save(signup6);
////			testrepo.save(signup7);
////			testrepo.save(signup8);
////			testrepo.save(signup9);
//
//			
//			// ZQ's dummy data
////			Roles Artist = new Roles(Role.Artist);
////			Roles Customer = new Roles(Role.Customer);
////			Roles ServiceProvider = new Roles(Role.ServiceProvider);
////
////			List<Roles> jayChouRoles = new ArrayList<>();
////			jayChouRoles.add(Artist);
////			User jayChou = new User("Jay", "Chou", "jaychou@gmail.com", "abcdefg", "21 June 1990", "MaxChen87", "Hello I am JayChou, I am an Artist", "www.jaychou.com", jayChouRoles);
////
////			List<Roles> qiZhaoRoles = new ArrayList<>();
////			qiZhaoRoles.add(Customer);
////		    User zhaoQi = new User("Qi", "Zhao", "qizhao@gmail.com", "abcdefg", "20 August 1998", "QiZhao98", "Hello I am Qi Zhao, I am a customer", "www.qizhao.com", qiZhaoRoles);
////
////
////		    urepo.save(jayChou);
////		    urepo.save(zhaoQi);
////
////			Max signup9 = new Max(9L, "Bruce Lee", "3 Years Ago",
////					"Thank you everyone for supporting me! I will continue to work hard "
////							+ "and make sure all your efforts to support me are worth it! Cheers! Thanks guys!!! :D Support Bruce Lee Too!"
////							+ "Cheers! Thanks guys!!! :D Support Bruce Lee Too! Cheers! Thanks guys!!! :D Support Bruce Lee Too!");
////
////			testrepo.save(signup1);
////			testrepo.save(signup2);
////			testrepo.save(signup3);
////			testrepo.save(signup4);
////			testrepo.save(signup5);
////			testrepo.save(signup6);
////			testrepo.save(signup7);
////			testrepo.save(signup8);
////			testrepo.save(signup9);
//			
////			Media media1 = new Media();
////			Media media2 = new Media();
////			media1.setTitle("This is video");
////			media2.setTitle("This is music");
////			media1.setMediaType(MediaType.Video);
////			media2.setMediaType(MediaType.Music);
////			mrepo.save(media1);
////			mrepo.save(media2);
//			Roles Artist = new Roles(Role.Artist);
//			Roles Customer = new Roles(Role.Customer);
//			Roles ServiceProvider = new Roles(Role.ServiceProvider);
//
//			rolesrepo.save(Artist);
//			
//			List<Roles> maxRoles = new ArrayList<>();
//			
//			maxRoles.add(Artist);
//			
//			User max = new User("Max", "Chen", "max@gmail.com", "abcdefg", "21 June 1987", "MaxChen87", "Best User Ever", "www.max.com", maxRoles);
//			
//			urepo.save(max);
//			
//			List<Media> FirstPlayListMedia = new ArrayList<>();
//			
//			List<Media> SecondPlayListMedia = new ArrayList<>();
//			
//			List<UserHistory> userHistory = new ArrayList<>();
//			
//			List<Comments> maxComments = new ArrayList<>();
//			
//			List<Playlists> maxPlaylists = new ArrayList<>();
//			
//			List<Tag> maxTags = new ArrayList<>();
//
////			Channel videoChannel = new Channel("Bruce Lee's Channel", "Nice Channel", MediaType.Video, 
////					"21 July 2021", FirstPlayListMedia, max);
//			
////			channelrepo.save(videoChannel);
//			
//			Media jaychouvideo = new Media(MediaType.Video, "http://localhost:8080/videos/android_screens_.mkv", "You've Got a Very Good Friend. Be Happy and Stay Safe! :) Have Fun!", "10:00", "21 July, 2002", "published", 
//					"http://localhost:8080/images/brucelee.PNG", userHistory, maxComments, videoChannel, maxPlaylists, maxTags);
//			
//			Media bruceleevideo = new Media(MediaType.Video, "http://localhost:8080/videos/bigbuckbunny.mp4", "Bruce Lee's Video - Best Kungfu", "12:00", "21 July, 2002", "published", 
//					"http://localhost:8080/images/pinkhat.png", userHistory, maxComments, videoChannel, maxPlaylists, maxTags);
//			
//			Media jjlinvideo = new Media(MediaType.Video, "http://localhost:8080/videos/jazz_in_paris.mp3", "JJ Lin's best video in the world", "13:00", "21 July, 2002", "published", 
//					"http://localhost:8080/images/pinksneaker.jpg", userHistory, maxComments, videoChannel, maxPlaylists, maxTags);
//			
//			Media monsterhuntervideo = new Media(MediaType.Video, "http://localhost:8080/videos/samplevideo.mp4", "Monsters in the wild! Hide fast and run fast!", "14:00", "21 July, 2002", "published", 
//					"http://localhost:8080/images/kpopalbum.png", userHistory, maxComments, videoChannel, maxPlaylists, maxTags);
//			
//			Media wwevideo = new Media(MediaType.Video, "http://localhost:8080/videos/bigbuckbunny.mp4", "WWE Video", "15:00", "21 July, 2002", "published", 
//					"http://localhost:8080/images/pinkdress.jpg", userHistory, maxComments, videoChannel, maxPlaylists, maxTags);
//			
//			Media maxvideo = new Media(MediaType.Video, "http://localhost:8080/videos/android_screens_.mkv", "Max's Personal Music Video - Come Support Me Bro", "16:00", "21 July, 2002", "published", 
//					"http://localhost:8080/images/pinkhat.png", userHistory, maxComments, videoChannel, maxPlaylists, maxTags);
//			
//			Media dinovideo = new Media(MediaType.Video, "http://localhost:8080/videos/android_screens_.mkv", "Dino Video Dino Video Dino's Video! Woohoo! Dino Dino Dino Dino Dino :D Dino", "17:00", "21 July, 2002", "published", 
//					"http://localhost:8080/images/estelle.PNG", userHistory, maxComments, videoChannel, maxPlaylists, maxTags);
//			
//			mediarepo.save(jaychouvideo);
//			mediarepo.save(bruceleevideo);
//			mediarepo.save(jjlinvideo);
//			mediarepo.save(monsterhuntervideo);
//			mediarepo.save(wwevideo);
//			mediarepo.save(maxvideo);
//			mediarepo.save(dinovideo);
//			
//			FirstPlayListMedia.add(jaychouvideo);
//			FirstPlayListMedia.add(bruceleevideo);
//			FirstPlayListMedia.add(jjlinvideo);
//			FirstPlayListMedia.add(monsterhuntervideo);
//			FirstPlayListMedia.add(wwevideo);
//			FirstPlayListMedia.add(maxvideo);
//			FirstPlayListMedia.add(dinovideo);
//			
//			Tag tag1 = new Tag("Classical Music", FirstPlayListMedia);
//			
//			Tag tag2 = new Tag("Romantic Songs", SecondPlayListMedia);
//						tagrepo.save(tag1);
//			
//			tagrepo.save(tag2);
//			
//			maxTags.add(tag1);
//			maxTags.add(tag2);
//			
//			Playlists FirstPlaylist = new Playlists("Max's First Playlist", "1", "Nice Playlist", MediaType.Video, 
//					max, FirstPlayListMedia);
//			
//			Playlists SecondPlaylist = new Playlists("Max's Second Playlist", "2", "Second Playlist", MediaType.Video, 
//					max, SecondPlayListMedia);
//			
//			plrepo.save(FirstPlaylist);
//			plrepo.save(SecondPlaylist);
//		
//			maxPlaylists.add(FirstPlaylist);
//			maxPlaylists.add(SecondPlaylist);			
//
//			List<Roles> jayChouRoles = new ArrayList<>();
//			jayChouRoles.add(Artist);
//			User jayChou = new User("Jay", "Chou", "jaychou@gmail.com", "abcdefg", "21 June 1990", "MaxChen87", "Hello I am JayChou, I am an Artist", "www.jaychou.com", jayChouRoles);
//
//			List<Roles> qiZhaoRoles = new ArrayList<>();
//			qiZhaoRoles.add(Customer);
//		    User zhaoQi = new User("Qi", "Zhao", "qizhao@gmail.com", "abcdefg", "20 August 1998", "QiZhao98", "Hello I am Qi Zhao, I am a customer", "www.qizhao.com", qiZhaoRoles);
//
//		    urepo.save(jayChou);
//		    urepo.save(zhaoQi);
//
//			Max signup9 = new Max(9L, "Bruce Lee", "3 Years Ago",
//					"Thank you everyone for supporting me! I will continue to work hard "
//							+ "and make sure all your efforts to support me are worth it! Cheers! Thanks guys!!! :D Support Bruce Lee Too!"
//							+ "Cheers! Thanks guys!!! :D Support Bruce Lee Too! Cheers! Thanks guys!!! :D Support Bruce Lee Too!");
//
//			testrepo.save(signup1);
//			testrepo.save(signup2);
//			testrepo.save(signup3);
//			testrepo.save(signup4);
//			testrepo.save(signup5);
//			testrepo.save(signup6);
//			testrepo.save(signup7);
//			testrepo.save(signup8);
//			testrepo.save(signup9);
			
//			Media media1 = new Media();
//			Media media2 = new Media();
//			media1.setTitle("This is video");
//			media2.setTitle("This is music");
//			media1.setMediaType(MediaType.Video);
//			media2.setMediaType(MediaType.Music);
//			mrepo.save(media1);
//			mrepo.save(media2);
//			Roles Artist = new Roles(Role.Artist);
//			Roles Customer = new Roles(Role.Customer);
//			Roles ServiceProvider = new Roles(Role.ServiceProvider);
//
//			rolesrepo.save(Artist);
//			
//			List<Roles> maxRoles = new ArrayList<>();
//			
//			maxRoles.add(Artist);
//			
//			User max = new User("Max", "Chen", "max@gmail.com", "abcdefg", "21 June 1987", "MaxChen87", "Best User Ever", "www.max.com", maxRoles);
//			
//			urepo.save(max);
//			
//			List<Media> FirstPlayListMedia = new ArrayList<>();
//			
//			List<Media> SecondPlayListMedia = new ArrayList<>();
//			
//			List<UserHistory> userHistory = new ArrayList<>();
//			
//			List<Comments> maxComments = new ArrayList<>();
//			
//			List<Playlists> maxPlaylists = new ArrayList<>();
//			
//			List<Tag> maxTags = new ArrayList<>();
//
//			Channel videoChannel = new Channel("Bruce Lee's Channel", "Nice Channel", MediaType.Video, 
//					"21 July 2021", FirstPlayListMedia, max);
//			
//			channelrepo.save(videoChannel);
//			
//			Media jaychouvideo = new Media(MediaType.Video, "http://localhost:8080/videos/android_screens_.mkv", "You've Got a Very Good Friend. Be Happy and Stay Safe! :) Have Fun!", "10:00", "21 July, 2002", "published", 
//					"http://localhost:8080/images/brucelee.PNG", userHistory, maxComments, videoChannel, maxPlaylists, maxTags);
//			
//			Media bruceleevideo = new Media(MediaType.Video, "http://localhost:8080/videos/bigbuckbunny.mp4", "Bruce Lee's Video - Best Kungfu", "12:00", "21 July, 2002", "published", 
//					"http://localhost:8080/images/pinkhat.png", userHistory, maxComments, videoChannel, maxPlaylists, maxTags);
//			
//			Media jjlinvideo = new Media(MediaType.Video, "http://localhost:8080/videos/jazz_in_paris.mp3", "JJ Lin's best video in the world", "13:00", "21 July, 2002", "published", 
//					"http://localhost:8080/images/pinksneaker.jpg", userHistory, maxComments, videoChannel, maxPlaylists, maxTags);
//			
//			Media monsterhuntervideo = new Media(MediaType.Video, "http://localhost:8080/videos/samplevideo.mp4", "Monsters in the wild! Hide fast and run fast!", "14:00", "21 July, 2002", "published", 
//					"http://localhost:8080/images/kpopalbum.png", userHistory, maxComments, videoChannel, maxPlaylists, maxTags);
//			
//			Media wwevideo = new Media(MediaType.Video, "http://localhost:8080/videos/bigbuckbunny.mp4", "WWE Video", "15:00", "21 July, 2002", "published", 
//					"http://localhost:8080/images/pinkdress.jpg", userHistory, maxComments, videoChannel, maxPlaylists, maxTags);
//			
//			Media maxvideo = new Media(MediaType.Video, "http://localhost:8080/videos/android_screens_.mkv", "Max's Personal Music Video - Come Support Me Bro", "16:00", "21 July, 2002", "published", 
//					"http://localhost:8080/images/pinkhat.png", userHistory, maxComments, videoChannel, maxPlaylists, maxTags);
//			
//			Media dinovideo = new Media(MediaType.Video, "http://localhost:8080/videos/android_screens_.mkv", "Dino Video Dino Video Dino's Video! Woohoo! Dino Dino Dino Dino Dino :D Dino", "17:00", "21 July, 2002", "published", 
//					"http://localhost:8080/images/estelle.PNG", userHistory, maxComments, videoChannel, maxPlaylists, maxTags);
//			
//			mediarepo.save(jaychouvideo);
//			mediarepo.save(bruceleevideo);
//			mediarepo.save(jjlinvideo);
//			mediarepo.save(monsterhuntervideo);
//			mediarepo.save(wwevideo);
//			mediarepo.save(maxvideo);
//			mediarepo.save(dinovideo);
//			
//			FirstPlayListMedia.add(jaychouvideo);
//			FirstPlayListMedia.add(bruceleevideo);
//			FirstPlayListMedia.add(jjlinvideo);
//			FirstPlayListMedia.add(monsterhuntervideo);
//			FirstPlayListMedia.add(wwevideo);
//			FirstPlayListMedia.add(maxvideo);
//			FirstPlayListMedia.add(dinovideo);
//			
//			Tag tag1 = new Tag("Classical Music", FirstPlayListMedia);
//			
//			Tag tag2 = new Tag("Romantic Songs", SecondPlayListMedia);
//			
//			tagrepo.save(tag1);
//			
//			tagrepo.save(tag2);
//			
//			maxTags.add(tag1);
//			maxTags.add(tag2);
//			
//			Playlists FirstPlaylist = new Playlists("Max's First Playlist", "1", "Nice Playlist", MediaType.Video, 
//					max, FirstPlayListMedia);
//			
//			Playlists SecondPlaylist = new Playlists("Max's Second Playlist", "2", "Second Playlist", MediaType.Video, 
//					max, SecondPlayListMedia);
//			
//			plrepo.save(FirstPlaylist);
//			plrepo.save(SecondPlaylist);
//		
//			maxPlaylists.add(FirstPlaylist);
//			maxPlaylists.add(SecondPlaylist);			
//
//			List<Roles> jayChouRoles = new ArrayList<>();
//			jayChouRoles.add(Artist);
//			User jayChou = new User("Jay", "Chou", "jaychou@gmail.com", "abcdefg", "21 June 1990", "MaxChen87", "Hello I am JayChou, I am an Artist", "www.jaychou.com", jayChouRoles);
//
//			List<Roles> qiZhaoRoles = new ArrayList<>();
//			qiZhaoRoles.add(Customer);
//		    User zhaoQi = new User("Qi", "Zhao", "qizhao@gmail.com", "abcdefg", "20 August 1998", "QiZhao98", "Hello I am Qi Zhao, I am a customer", "www.qizhao.com", qiZhaoRoles);
//
//		    urepo.save(jayChou);
//		    urepo.save(zhaoQi);
//			
//		    Comments comment1 = new Comments("27 July 2021, 11:59", 
//		    		"Nice music, nice mv, Thanks! "
//		    		+ "I have subscribed this channel for many years, "
//		    		+ "Carole King is excellent in both Rock and Dance.",
//		    		jaychouvideo, max);
//		    
//		    Comments comment2 = new Comments("27 July 2021, 23:00", 
//		    		"Nice music, nice mv, Thanks!",
//		    		jaychouvideo, max);
//		        
//		    commentsrepo.save(comment1);
//		    commentsrepo.save(comment2);
//		    
//		    maxComments.add(comment1);
//		    maxComments.add(comment2);
//		    
//		    UserHistory userHistory1 = new UserHistory(1, jayChou, FirstPlayListMedia);
//		    UserHistory userHistory2 = new UserHistory(2, zhaoQi, SecondPlayListMedia);
//		    UserHistory userHistory3 = new UserHistory(1, jayChou, SecondPlayListMedia);
//		    UserHistory userHistory4 = new UserHistory(2, zhaoQi, FirstPlayListMedia);
//		    
//		    Comments comment2 = new Comments("27 July 2021, 23:00", 
//		    		"Nice music, nice mv, Thanks!",
//		    		jaychouvideo, max);
//		        
//		    commentsrepo.save(comment1);
//		    commentsrepo.save(comment2);
//		    
//		    maxComments.add(comment1);
//		    maxComments.add(comment2);
//		    
//		    
//		    List<Product> productList = new ArrayList<>();
//		    Product p1 = new Product("Nice Pink Hat","Top Selling product",1,Category.Clothing,13.0,"http://picture-cdn.wheretoget.it/0v3cfh-l-610x610.jpg",null,null,null,max);
//		    Product p2 = new Product("Bickni","Top Selling product",1,Category.Clothing,13.0,"http://picture-cdn.wheretoget.it/0v3cfh-l-610x610.jpg",null,null,null,max);
//		    prepo.save(p1);
//		    prepo.save(p2);
//		    productList.add(p1);
//		    productList.add(p2);
//		    
////set order details for testing			
////			OrderDetails od1 = new OrderDetails();
////			od1.setProduct(prepo.getById((long) 1));
////			od1.setQuantity(3);
////			OrderDetails od2 = new OrderDetails();
////			od1.setProduct(prepo.getById((long) 1));
////			od1.setQuantity(4);
////			OrderDetails od3 = new OrderDetails();
////			od1.setProduct(prepo.getById((long) 1));
////			od1.setQuantity(2);
////
////			odrepo.save(od1);
////			odrepo.save(od2);
////			odrepo.save(od3);
			
//			Product product1 = new Product();
//			product1.setProductName("Clothing");
//			product1.setProductCategory(Category.Clothing);
//			
//			Product product2 = new Product();
//			product2.setProductName("Merchandise");
//			product2.setProductCategory(Category.Merchandise);
//			
//			Product product3 = new Product();
//			product3.setProductName("Music Collection");
//			product3.setProductCategory(Category.MusicCollection);
//			
//			prepo.save(product1);
//			prepo.save(product2);
//			prepo.save(product3);

//		};
//	}

}
