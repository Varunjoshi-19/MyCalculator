package calculator.mycalculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws IOException {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root,442,595);
		
		String css = this.getClass().getResource("DarkMode.css").toExternalForm();
		scene.getStylesheets().add(css);
		
		stage.setScene(scene);
		stage.setTitle("CALCULATOR");
		stage.setResizable(false);
		stage.show();
		}
		catch(Exception e) {
			 e.printStackTrace();
		}

	}
		public static void main(String[] args) {
		
      launch(args);
     
    
	}
		
	
	
}