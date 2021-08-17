package sg.edu.iss.jam.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productID;

	@NotNull (message = "Product name must be filled in.")
	private String productName;

	private String productDes;

	@NotNull (message = "Product quantity must be filled in.")
	private int productQty;

	@Enumerated(EnumType.STRING)
	private Category productCategory;

	@NotNull (message = "Product price must be filled in.")
	private double productPrice;

	private String productUrl;

	// relation with Wishlist
	@ManyToMany
	private Collection<Wishlist> wishlists;
	
	@OneToMany(mappedBy = "product")
	private Collection<OrderDetails> orderDetails;

	// relation with shoppingCart
	@ManyToMany(mappedBy = "products")
	private Collection<ShoppingCart> shoppingCarts;

	// ManyToOne relation with User
	@ManyToOne
	private User productUser;

	public Product() {
		super();
	}

	

	public Product(@NotNull(message = "Product name must be filled in.") String productName, String productDes,
			@NotNull(message = "Product quantity must be filled in.") int productQty, Category productCategory,
			@NotNull(message = "Product price must be filled in.") double productPrice, String productUrl,
			Collection<Wishlist> wishlists, Collection<OrderDetails> orderDetails,
			Collection<ShoppingCart> shoppingCarts, User productUser) {
		super();
		this.productName = productName;
		this.productDes = productDes;
		this.productQty = productQty;
		this.productCategory = productCategory;
		this.productPrice = productPrice;
		this.productUrl = productUrl;
		this.wishlists = wishlists;
		this.orderDetails = orderDetails;
		this.shoppingCarts = shoppingCarts;
		this.productUser = productUser;
	}



	public Long getProductID() {
		return productID;
	}

	public void setProductID(Long productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDes() {
		return productDes;
	}

	public void setProductDes(String productDes) {
		this.productDes = productDes;
	}

	public int getProductQty() {
		return productQty;
	}

	public void setProductQty(int productQty) {
		this.productQty = productQty;
	}


	public Category getProductCategory() {
		return productCategory;
	}



	public void setProductCategory(Category productCategory) {
		this.productCategory = productCategory;
	}



	public Collection<OrderDetails> getOrderDetails() {
		return orderDetails;
	}



	public void setOrderDetails(Collection<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}



	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public Collection<Wishlist> getWishlists() {
		return wishlists;
	}

	public void setWishlists(Collection<Wishlist> wishlists) {
		this.wishlists = wishlists;
	}

	public Collection<ShoppingCart> getShoppingCarts() {
		return shoppingCarts;
	}

	public void setShoppingCarts(Collection<ShoppingCart> shoppingCarts) {
		this.shoppingCarts = shoppingCarts;
	}

	public User getProductUser() {
		return productUser;
	}

	public void setProductUser(User productUser) {
		this.productUser = productUser;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

}
