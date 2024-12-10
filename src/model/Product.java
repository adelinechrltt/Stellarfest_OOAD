package model;

public class Product {

	// assume vendor can decide what one product they want to sell in a certain
	// event

	private String productID;
	private String userID;
	private String name;
	private String description;

	public Product(String productID, String userID, String name, String description) {
		super();
		this.productID = productID;
		this.userID = userID;
		this.name = name;
		this.description = description;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}