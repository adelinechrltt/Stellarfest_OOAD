package view.VendorViews;

import controller.VendorController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;
import model.Product;
import view.NavBar;

public class UpdateProductPage {
	public static Scene getScene(Product pr) {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		HBox navbar = NavBar.getNavbar(Main.currentUser.getRole());
		
		Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24);
		Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		
		Label titleLbl = new Label();
		titleLbl.setText("Update Product");
		titleLbl.setFont(titleFont);
		
		Label prodNameLbl = new Label();
		Label prodDescLbl = new Label();
		
		TextField prodNameField = new TextField();
		TextField prodDescField = new TextField();
		
		Button updateProdBtn = new Button();
		
		Label errorLbl = new Label();
				
		prodNameLbl.setText("Product Name: ");
		prodNameLbl.setFont(inputFont);
		prodNameField.setText(pr.getName());
		
		prodDescLbl.setText("Product Description: ");
		prodDescLbl.setFont(inputFont);
		prodDescField.setText(pr.getDescription());
		
		errorLbl.setText("");
		errorLbl.setStyle("-fx-text-fill: red;");
		errorLbl.setVisible(false);
		
		updateProdBtn.setOnAction(e -> {
			VendorController.updateProduct(Main.currentUser.getUserID(), pr.getProductID(), prodNameField.getText(), prodDescField.getText(), errorLbl);
		});
		updateProdBtn.setText("Update Product");
		
		GridPane input = new GridPane();
		input.setAlignment(Pos.CENTER);
		input.add(prodNameLbl, 0, 0);
		input.add(prodNameField, 1, 0);
		input.add(prodDescLbl, 0, 1);
		input.add(prodDescField, 1, 1);
		
		layout.getChildren().addAll(navbar, titleLbl, input, updateProdBtn);
		
		return new Scene(layout, 300, 200);
	}
}
