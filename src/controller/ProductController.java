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

	public ProductController() {
		super();
	}

	public static String generateProductID() {
		// method untuk generate product ID tanpa regex
		// format: P123456
		String PID = "P";
		for(int i=0; i<7; i++) {
			PID = PID + String.valueOf((int)(Math.random()*10));
		}
		return PID;
	}
	
	public static void createProduct(String userID, String name, String desc) {
		// method untuk membuat product baru berdasarkan input
		String productID = generateProductID();
		Product.createProduct(productID, userID, name, desc);
	}
	
	public static void updateProduct(String productID, String name, String desc) {
		// method untuk mengubah data detail dari suatu product
	    Product.updateProduct(productID, name, desc);
	}
	
	public static void deleteProduct(String productID) {
		// method untuk menghapus suatu product
		Product.deleteProduct(productID);
	}
	
	public static Product getProductByID(String prodID) {
		// method untuk mendapatkan suatu product dari ID nya
		return Product.getProductByID(prodID);
	}
	
	public static ArrayList<Product> getProductsByUserID(String userID){
		// method untuk mendapatkan semua product yang sudah dibuat oleh seorang vendor
		return Product.getProductsByUserID(userID);

	}
}
