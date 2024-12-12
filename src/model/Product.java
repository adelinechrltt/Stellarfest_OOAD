package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.Connect;

public class Product {

	// assume vendor can decide what one product they want to sell in a certain
	// event
	
	private static Connect db = Connect.getInstance();
	
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
	
	public static void createProduct(String productID, String userID, String name, String desc) {
		String query = "INSERT INTO Products (productID, userID, name, description) \n"
				+ "VALUES (?, ?, ?, ?)";
		PreparedStatement ps;
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, productID);
			ps.setString(2, userID);
			ps.setString(3, name);
			ps.setString(4, desc);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateProduct(String productID, String name, String desc) {
		String query = "UPDATE Products\n"
	    		+ "SET name = ?,\n"
	    		+ "description = ?\n"
	    		+ "WHERE productID = ?";
	    
		PreparedStatement ps;

	    try {
			ps = db.getConnection().prepareStatement(query);
	        ps.setString(1, name);
	        ps.setString(2, desc);
	        ps.setString(3, productID);
	        ps.execute();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }	
	}
	
	public static void deleteProduct(String productID) {
		String query = "DELETE FROM Products\n" +
				"WHERE ProductID = ?";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, productID);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Product getProductByID(String prodID) {
		Product prod = null;
		String query = "SELECT * FROM Products\n" 
				+ "WHERE productID = ?";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, prodID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String ID = rs.getString("productID");
				String userID = rs.getString("userID");
				String name = rs.getString("name");
				String desc = rs.getString("description");
				
				prod = new Product(ID, userID, name, desc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return prod;
	}
	
	public static ArrayList<Product> getProductsByUserID(String userID){
		ArrayList<Product> products = new ArrayList<>();
	    
		String query = "SELECT * FROM Products\n"
	    		+ "WHERE userID = ?";
	    PreparedStatement ps;
	    
	    try {
	    	ps = db.getConnection().prepareStatement(query);
	        ps.setString(1, userID);
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            String productID = rs.getString("productID");
	            String name = rs.getString("name");
	            String desc = rs.getString("description");
	            products.add(new Product(productID, userID, name, desc));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    
	    if(products.isEmpty()) products = null;
	    return products;

	}

}