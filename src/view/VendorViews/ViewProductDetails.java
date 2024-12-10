package view.VendorViews;

import controller.ProductController;
import controller.VendorController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;
import model.Product;
import view.NavBar;

public class ViewProductDetails {
	private static Product product;
	
	public static Scene getScene(String productID) {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		HBox navbar = NavBar.getNavbar(Main.currentUser.getRole());
		
		Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 26);
		Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		
		Label titleLbl = new Label();
		titleLbl.setText("Product Details");
		titleLbl.setFont(titleFont);
		
		layout.getChildren().addAll(navbar, titleLbl);
		
		product = ProductController.getProductByID(productID);
		
		GridPane prodDetails = new GridPane();
		prodDetails.setAlignment(Pos.CENTER);
		
		Label prodIdLbl = new Label();
		prodIdLbl.setText("Product ID: ");
		Label prodIdVal = new Label();
		prodIdVal.setText(productID);
		
		prodDetails.add(prodIdLbl, 0, 0);
		prodDetails.add(prodIdVal, 1, 0);
		
		Label prodNameLbl = new Label();
		prodNameLbl.setText("Product Name: ");
		Label prodNameVal = new Label();
		prodNameVal.setText(product.getName());
		
		prodDetails.add(prodNameLbl, 0, 1);
		prodDetails.add(prodNameVal, 1, 1);
		
		Label prodDescLbl = new Label();
		prodDescLbl.setText("Product Description: ");
		Label prodDescVal = new Label();
		prodDescVal.setText(product.getDescription());
		
		prodDetails.add(prodDescLbl, 0, 2);
		prodDetails.add(prodDescVal, 1, 2);
		
		layout.getChildren().add(prodDetails);
		
		HBox buttons = new HBox();
		
		Button updateBtn = new Button();
		updateBtn.setText("Update Product Details");
		updateBtn.setOnAction(e -> {
			Main.switchScene(UpdateProductPage.getScene(product));
		});
		
		Button deleteBtn = new Button();
		deleteBtn.setText("Delete Product");
		deleteBtn.setOnAction(e -> {
			VendorController.deleteProduct(productID, Main.currentUser.getUserID());
		});
		
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(updateBtn, deleteBtn);
		
		layout.getChildren().add(buttons);
		return new Scene(layout, 300, 200);
	}
}
