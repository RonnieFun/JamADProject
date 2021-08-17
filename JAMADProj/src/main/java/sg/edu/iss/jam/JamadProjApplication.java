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
import sg.edu.iss.jam.repo.OrderDetailsRepository;
import sg.edu.iss.jam.repo.ProductRepository;
import sg.edu.iss.jam.repo.RolesRepository;
import sg.edu.iss.jam.repo.UserRepository;

@SpringBootApplication
public class JamadProjApplication {

	@Autowired
	MaxRepository testrepo;

	@Autowired
	UserRepository urepo;

	@Autowired
	RolesRepository rrepo;

	@Autowired
	ProductRepository prepo;

	@Autowired
	OrderDetailsRepository odrepo;

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
			Max signup5 = new Max(5L, "Ronnie", "1 Year Ago",
					"I give this guy 1000 likes! This is the best music ever!");
			Max signup6 = new Max(6L, "Zhao Qi", "2 Weeks Ago",
					"I give this guy 1000 likes! This is the best music ever!");
			Max signup7 = new Max(7L, "Max", "2 Years Ago",
					"I give this guy 1000 likes! This is the best music ever! Yes it is! really good!");
			Max signup8 = new Max(8L, "Jay Chou", "3 Years Ago",
					"Thank you everyone for supporting me! I will continue to work hard and make sure all your efforts to support me are worth it! Cheers! Thanks guys!!! :D Support Bruce Lee Too!");

//			User user1 =  new User("Jack", "Son", "jack@gmail.com", "123", "20-02-2021", "Daniel", "Youngest artist in Industry","https://www.seekpng.com/png/full/799-7991970_kpop-sticker-got7-miracle-jackson.png",null);
//			
//			urepo.save(user1);
//			Roles role1 = new Roles(Role.Artist, user1);
//			rrepo.save(role1);
//			
//			Product product1= new Product("Twice pink hoodie", "Top Selling product", 2, 1, 12.0,"https://scene7.zumiez.com/is/image/zumiez/product_main_medium/Married-To-The-Mob-Bitch-Pink-Hoodie-_332418-front-US.jpg", null, null, null, user1);
//			Product product2= new Product("Best Album Collection", "Top Selling product", 2, 1, 57.0,"https://upload.wikimedia.org/wikipedia/en/f/f0/2PMBest-Regular.jpeg", null, null, null, user1);
//			Product product3= new Product("Nice Pink Hat", "Top Selling product", 2, 1, 115.0,"http://picture-cdn.wheretoget.it/0v3cfh-l-610x610.jpg", null, null, null, user1);
//			Product product4= new Product("Pink Sneakers", "Top Selling product", 2, 1, 65.0, "https://cdn.trendhunterstatic.com/thumbs/retro-pink-shoes.jpeg",null, null, null, user1);
//			prepo.save(product1);
//			prepo.save(product2);
//			prepo.save(product3);
//			prepo.save(product4);
//			Max signup1 = new Max(1L, "Brandon", "1 Day Ago", "How's everyone doing?");
//			Max signup2 = new Max(2L, "Chang Ying", "2 Days Ago", "This is so cool! I'm excited!");
//			Max signup3 = new Max(3L, "Phyu Sin", "1 Day Ago", "I can't wait to go on the concert tour!");
//			Max signup4 = new Max(4L, "Alejandro", "1 Week Ago", "This is nice! Please come to Singapore!");
//			Max signup5 = new Max(5L, "Ronnie", "1 Year Ago", "I give this guy 1000 likes! This is the best music ever!");
//			Max signup6 = new Max(6L, "Zhao Qi", "2 Weeks Ago","I give this guy 1000 likes! This is the best music ever!");
//			Max signup7 = new Max(7L, "Max","2 Years Ago", "I give this guy 1000 likes! This is the best music ever! Yes it is! really good!");
//			Max signup8 = new Max(8L, "Jay Chou","3 Years Ago", "Thank you everyone for supporting me! I will continue to work hard and make sure all your efforts to support me are worth it! Cheers! Thanks guys!!! :D Support Bruce Lee Too!");
//			
//			Max signup9 = new Max(9L, "Bruce Lee","3 Years Ago", "Thank you everyone for supporting me! I will continue to work hard "
//					+ "and make sure all your efforts to support me are worth it! Cheers! Thanks guys!!! :D Support Bruce Lee Too!"
//					+ "Cheers! Thanks guys!!! :D Support Bruce Lee Too! Cheers! Thanks guys!!! :D Support Bruce Lee Too!");
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
			
			// ZQ's dummy data
			Roles Artist = new Roles(Role.Artist);
			Roles Customer = new Roles(Role.Customer);
			Roles ServiceProvider = new Roles(Role.ServiceProvider);

			List<Roles> jayChouRoles = new ArrayList<>();
			jayChouRoles.add(Artist);
			User jayChou = new User("Jay", "Chou", "jaychou@gmail.com", "abcdefg", "21 June 1990", "MaxChen87", "Hello I am JayChou, I am an Artist", "www.jaychou.com", jayChouRoles);

			List<Roles> qiZhaoRoles = new ArrayList<>();
			qiZhaoRoles.add(Customer);
		    User zhaoQi = new User("Qi", "Zhao", "qizhao@gmail.com", "abcdefg", "20 August 1998", "QiZhao98", "Hello I am Qi Zhao, I am a customer", "www.qizhao.com", qiZhaoRoles);


		    urepo.save(jayChou);
		    urepo.save(zhaoQi);

			Max signup9 = new Max(9L, "Bruce Lee", "3 Years Ago",
					"Thank you everyone for supporting me! I will continue to work hard "
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
			
//set order details for testing			
//			OrderDetails od1 = new OrderDetails();
//			od1.setProduct(prepo.getById((long) 1));
//			od1.setQuantity(3);
//			OrderDetails od2 = new OrderDetails();
//			od1.setProduct(prepo.getById((long) 1));
//			od1.setQuantity(4);
//			OrderDetails od3 = new OrderDetails();
//			od1.setProduct(prepo.getById((long) 1));
//			od1.setQuantity(2);
//
//			odrepo.save(od1);
//			odrepo.save(od2);
//			odrepo.save(od3);
		};
	}

}
