package view.VendorViews;

import controller.EventOrganizerController;
import controller.VendorController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;
import util.LoginSession;
import view.NavBar;

public class CreateProductPage {
	
	public static LoginSession login = LoginSession.getInstance();
	
	public static Scene getScene() {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		HBox navbar = NavBar.getNavbar(login.getLoggedInUser().getRole());
		
		Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24);
		Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		
		Label titleLbl = new Label();
		titleLbl.setText("Create New Product");
		titleLbl.setFont(titleFont);
		
		Label prodNameLbl = new Label();
		Label prodDescLbl = new Label();
		
		TextField prodNameField = new TextField();
		TextField prodDescField = new TextField();
		
		Button createProdBtn = new Button();
		
		Label errorLbl = new Label();
				
		prodNameLbl.setText("Product Name: ");
		prodNameLbl.setFont(inputFont);
		prodDescLbl.setText("Product Description: ");
		prodDescLbl.setFont(inputFont);
		
		errorLbl.setText("");
		errorLbl.setStyle("-fx-text-fill: red;");
		errorLbl.setVisible(false);
		
		createProdBtn.setOnAction(e -> {
			VendorController.createProduct(login.getLoggedInUser().getUserID(), prodNameField.getText(), prodDescField.getText(), errorLbl);
		});
		createProdBtn.setText("Create Product");
		
		GridPane input = new GridPane();
		input.setAlignment(Pos.CENTER);
		input.add(prodNameLbl, 0, 0);
		input.add(prodNameField, 1, 0);
		input.add(prodDescLbl, 0, 1);
		input.add(prodDescField, 1, 1);
		
		layout.getChildren().addAll(navbar, titleLbl, input, createProdBtn);
		
		return new Scene(layout, 1200, 600);
	}
}
