package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Main;
import model.Product;
import util.Connect;
import view.VendorViews.MyProductsPage;

public class ProductController {

	private static Connect db = Connect.getInstance();

	public ProductController() {
		super();
	}

	public static String generateProductID() {
		String PID = "P";
		for(int i=0; i<7; i++) {
			PID = PID + String.valueOf((int)(Math.random()*10));
		}
		return PID;
	}
	
	public static void createProduct(String userID, String name, String desc) {
		String query = "INSERT INTO Products (productID, userID, name, description) \n"
				+ "VALUES (?, ?, ?, ?)";
		PreparedStatement ps;
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, generateProductID());
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
