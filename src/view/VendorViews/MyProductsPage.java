package view.VendorViews;

import java.util.ArrayList;

import controller.ProductController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;
import model.Product;
import util.LoginSession;
import view.NavBar;
import view.ViewEventDetails;

public class MyProductsPage {
	
	public static LoginSession login = LoginSession.getInstance();
	
	public static String productID;
	private static int clickCount = 0;
	
	public static Scene getScene(String userID) {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		HBox navbar = NavBar.getNavbar(login.getLoggedInUser().getRole());
		
		Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24);
		Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		
		Label title = new Label();
		title.setText("My Products");
		title.setFont(titleFont);
		
		layout.getChildren().addAll(navbar, title);
		
		ArrayList<Product> products = ProductController.getProductsByUserID(userID);
		if(products == null) {
			Label nullDisplay = new Label();
			nullDisplay.setText("No products yet!");
			layout.getChildren().add(nullDisplay);
		} else {
			Label subtitleLbl = new Label();
			subtitleLbl.setText("Double-click on an entry for more actions.");
			subtitleLbl.setFont(inputFont);
			
			ObservableList<Product> prodList= FXCollections.observableArrayList(products);
			TableView<Product> productsView = new TableView<>();

			TableColumn<Product, String> idCol = new TableColumn<>("ID");
			idCol.setCellValueFactory(new PropertyValueFactory<>("productID"));

			TableColumn<Product, String> nameCol = new TableColumn<>("Name");
			nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

			TableColumn<Product, String> descCol = new TableColumn<>("Description");
			descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

			productsView.getColumns().addAll(idCol, nameCol, descCol);
			productsView.setItems(prodList);
			
			productsView.setOnMouseClicked(e -> {
				try {
					 TableSelectionModel<Product> prodModel = productsView.getSelectionModel();
			    	 prodModel.setSelectionMode(SelectionMode.SINGLE);
			    	 Product prod = prodModel.getSelectedItem();
			    	 productID = prod.getProductID();
			    	 
			    	 clickCount++;
			    	 if(clickCount == 2) {
			    		 Main.switchScene(ViewProductDetails.getScene(productID));
			    		 clickCount = 0;
			    	 }
				} catch (Exception error) {
					error.printStackTrace();
				}
		     });
			
			layout.getChildren().addAll(subtitleLbl, productsView);
		}
		
		Button addProductBtn = new Button();
		addProductBtn.setText("Add New Product");
		addProductBtn.setOnAction(e -> {
			Main.switchScene(CreateProductPage.getScene());
		});
		
		layout.getChildren().add(addProductBtn);
		
		return new Scene(layout, 300, 200);
	}
}
