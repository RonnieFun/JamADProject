package sg.edu.iss.jam.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderID;
	
	private int quantity;
	
	private String orderDate;
	
	private String address;
	
	//ManyToMany relation with user
	@ManyToMany(mappedBy = "orders")
	private Collection<User> users;
	
	
	//ManyToMany relation with product
	@ManyToMany(mappedBy = "orders")
	private Collection<Product> products;


	public Orders() {
		super();
	}


	public Orders(int quantity, String orderDate, String address, Collection<User> users,
			Collection<Product> products) {
		super();
		this.quantity = quantity;
		this.orderDate = orderDate;
		this.address = address;
		this.users = users;
		this.products = products;
	}


	public Long getOrderID() {
		return orderID;
	}


	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Collection<User> getUsers() {
		return users;
	}


	public void setUsers(Collection<User> users) {
		this.users = users;
	}


	public Collection<Product> getProducts() {
		return products;
	}


	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
	
	

}
