package sg.edu.iss.jam.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderID;

	private String orderDate;

	private String address;

	// ManyToMany relation with user
	@ManyToOne
	private User user;

	@OneToMany (mappedBy="order")
	private Collection<OrderDetails> orderDetails;

	public Orders() {
		super();
	}

	public Orders(int quantity, String orderDate, String address, Collection<User> users,
			Collection<Product> products) {
		super();
		this.orderDate = orderDate;
		this.address = address;
	}

	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
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
}
