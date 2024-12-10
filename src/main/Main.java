package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;
import view.LoginPage;

public class Main extends Application {
	
//	event organizer
//	public static User currentUser = new EventOrganizer("U7494612", "caelus@gmail.com", "caelus", "1234");
//	public static User currentUser = new Vendor("U7337645", "herobrine1234@gmail.com", "herobrine1234", "herobrine1234");
	
	public static User currentUser = null;
	
	private static Stage primaryStage;
	private static String css = Main.class.getResource("app.css").toExternalForm();

    @Override
    public void start(Stage primaryStage) {
    	Main.primaryStage = primaryStage;
        primaryStage.setTitle("Stellarfest");
        primaryStage.setResizable(false);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(750);
        primaryStage.show();

        
        Scene scene = LoginPage.getScene();
//        Scene scene = RegisterPage.getScene();
//        Scene scene = ViewMyEventsPage.getScene();
//        Scene scene = Home.getScene();
        
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
    }
    
    public static void switchScene(Scene scene) {
        scene.getStylesheets().add(css);
    	primaryStage.setScene(scene);
    	primaryStage.show();
    }
    
    public static void displayAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(primaryStage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
	
}