package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.jam.model.ShoppingCartDetails;

public interface ShoppingCartDetailsRepository extends JpaRepository<ShoppingCartDetails, Long> {

	@Transactional
	@Modifying
	@Query("Delete FROM ShoppingCartDetails u where u.shoppingCart.shoppingCartID =:shoppingCartID and u.product.productID =:productID")
	void deleteCartDetailsByID(@Param("productID") Long productID , @Param("shoppingCartID") Long shoppingCartID );

}
