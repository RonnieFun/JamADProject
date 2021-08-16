package sg.edu.iss.jam.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productID;
	
	private String productName;
	
	private String productDes;
	
	private int productQty;
	
	private int productCategory;
	
	private double productPrice;
	
	private String productUrl;
	
	//relation with Wishlist
	@ManyToMany
	private Collection<Wishlist> wishlists;
	
	//relation with shoppingCart
	@ManyToMany(mappedBy = "products")
	private Collection<ShoppingCart> shoppingCarts;
	
	//ManyToMany relation with orders
	@ManyToMany
	private Collection<Orders> orders;
	
	//ManyToOne relation with User
	@ManyToOne
	private User productUser;

	public Product() {
		super();
	}

	public Product(String productName, String productDes, int productQty, int productCategory, double productPrice,
			String productUrl, Collection<Wishlist> wishlists, Collection<ShoppingCart> shoppingCarts,
			Collection<Orders> orders, User productUser) {
		super();
		this.productName = productName;
		this.productDes = productDes;
		this.productQty = productQty;
		this.productCategory = productCategory;
		this.productPrice = productPrice;
		this.productUrl = productUrl;
		this.wishlists = wishlists;
		this.shoppingCarts = shoppingCarts;
		this.orders = orders;
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

	public int getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(int productCategory) {
		this.productCategory = productCategory;
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

	public Collection<Orders> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Orders> orders) {
		this.orders = orders;
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
