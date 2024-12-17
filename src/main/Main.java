package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import util.LoginSession;
import view.LoginPage;

// --> dokumentasi internal yang lengkap berada pada file-file controller serta model
// --> file-file view juga memiliki dokumentasi, namun tidak selengkap file-file controller dan model

public class Main extends Application {	
	public static LoginSession login = LoginSession.getInstance();
	
	private static Stage primaryStage;
	private static String css = Main.class.getResource("app.css").toExternalForm();

    @Override
    public void start(Stage primaryStage) {
    	Main.primaryStage = primaryStage;
        primaryStage.setTitle("Stellarfest");
        primaryStage.setResizable(true);
        primaryStage.show();
        
        Scene scene = LoginPage.getScene();
        
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
    }
    
    // method untuk mengganti scene di primarystage
    public static void switchScene(Scene scene) {
        scene.getStylesheets().add(css);
    	primaryStage.setScene(scene);
    	primaryStage.show();
    }
    
    // method untuk menampilkan alert
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