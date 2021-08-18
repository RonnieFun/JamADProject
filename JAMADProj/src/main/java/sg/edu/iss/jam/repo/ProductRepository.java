package sg.edu.iss.jam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.Category;
import sg.edu.iss.jam.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT c FROM Product c WHERE c.productUser.userID = :artistid AND c.productCategory = :category")
	List<Product> getProductListByArtistIDAndCategory(@Param("artistid") long artistid,@Param("category") Category category);

	@Query("SELECT c FROM Product c WHERE c.productUser.userID = :artistid")
	List<Product> getProductListByArtistID(long artistid);

}
