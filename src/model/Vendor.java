package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import util.Connect;

public class Vendor extends User{
	
	private static Connect db = Connect.getInstance();
	private ArrayList<String> acceptedInvites;

	public Vendor(String userID, String email, String name, String password) {
		super(userID, email, name, password);
		this.role = "Vendor";
		this.acceptedInvites = new ArrayList<>();
	}
	
	
	public static Vendor getVendorByEmail(String email) {
		Vendor vend = null;
		String query = "SELECT * FROM Users\n"
				+ "WHERE email = ?";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				
				String userID = rs.getString("userID");
				String name = rs.getString("name");
				String password = rs.getString("password");
				vend = new Vendor(userID, email, name, password);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return vend;
	}
	
	public static ArrayList<Vendor> getAllVendors(){
		ArrayList<Vendor> vendors = new ArrayList<>();
		
		String query = "SELECT * FROM users "
        		+ "WHERE role = 'Vendor'";
        PreparedStatement ps;

        try {
        	ps = db.getConnection().prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	String vendorID = rs.getString("UserID");
            	String vendorEmail = rs.getString("Email");
            	String vendorName = rs.getString("Name");
            	String vendorPw = rs.getString("Password");
            	vendors.add(new Vendor(vendorID, vendorEmail, vendorName, vendorPw));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return vendors;
	}
}
