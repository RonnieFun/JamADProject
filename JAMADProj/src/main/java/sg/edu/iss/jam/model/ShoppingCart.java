package sg.edu.iss.jam.model;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ShoppingCart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long shoppingCartID;
	
	//OneToOne relation with user
	@OneToOne(mappedBy = "shoppingCart")
	private User shoppingCartUser;
	
	//relation with shoppingcartDetail
	@OneToMany(mappedBy="shoppingCart")
	private Collection<ShoppingCartDetails> cartDetails;

	public ShoppingCart() {
		super();
	}

	public ShoppingCart(User shoppingCartUser, Collection<ShoppingCartDetails> cartDetails) {
		super();
		this.shoppingCartUser = shoppingCartUser;
		this.cartDetails = cartDetails;
	}



	public Long getShoppingCartID() {
		return shoppingCartID;
	}

	public void setShoppingCartID(Long shoppingCartID) {
		this.shoppingCartID = shoppingCartID;
	}

	public User getShoppingCartUser() {
		return shoppingCartUser;
	}

	public void setShoppingCartUser(User shoppingCartUser) {
		this.shoppingCartUser = shoppingCartUser;
	}


	public Collection<ShoppingCartDetails> getCartDetails() {
		return cartDetails;
	}


	public void setCartDetails(Collection<ShoppingCartDetails> cartDetails) {
		this.cartDetails = cartDetails;
	}

	
}
