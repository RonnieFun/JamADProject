package sg.edu.iss.jam.model;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class ShoppingCart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long shoppingCartID;
	
	private int quantity;
	
	//OneToOne relation with user
	@OneToOne(mappedBy = "shoppingCart")
	private User shoppingCartUser;
	
	//relation with product
	@ManyToMany
	private Collection<Product> products;

	public ShoppingCart() {
		super();
	}

	public ShoppingCart(int quantity, User shoppingCartUser, Collection<Product> products) {
		super();
		this.quantity = quantity;
		this.shoppingCartUser = shoppingCartUser;
		this.products = products;
	}

	public Long getShoppingCartID() {
		return shoppingCartID;
	}

	public void setShoppingCartID(Long shoppingCartID) {
		this.shoppingCartID = shoppingCartID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public User getShoppingCartUser() {
		return shoppingCartUser;
	}

	public void setShoppingCartUser(User shoppingCartUser) {
		this.shoppingCartUser = shoppingCartUser;
	}

	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}

	
}
