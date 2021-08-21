package sg.edu.iss.jam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentID;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String country;
	
	private String address;
	
	private String city;
	
	private String postalCode;
	
	private String nameOnCard;
	
	private String phoneNumber;
	
	private String creditNumber;
	
	private String expiration;
	
	private String cvv;
	
	//relation with user
	@ManyToOne
	private User user;

	
	public Payment() {
		super();
	}

	public Payment(String firstName, String lastName, String email, String country, String address, String city,
			String postalCode, String nameOnCard, String phoneNumber, String creditNumber, String expiration,
			String cvv, User user) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.country = country;
		this.address = address;
		this.city = city;
		this.postalCode = postalCode;
		this.nameOnCard = nameOnCard;
		this.phoneNumber = phoneNumber;
		this.creditNumber = creditNumber;
		this.expiration = expiration;
		this.cvv = cvv;
		this.user = user;
	}

	public Long getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(Long paymentID) {
		this.paymentID = paymentID;
	}

	public String getCreditNumber() {
		return creditNumber;
	}

	public void setCreditNumber(String creditNumber) {
		this.creditNumber = creditNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}


}
