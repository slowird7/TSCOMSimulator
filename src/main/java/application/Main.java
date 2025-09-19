package application;

import command.CommandTable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class Main extends Application {
	private final static Logger logger = LogManager.getLogger(Main.class);

	@Override
	public void start(Stage primaryStage) throws IOException {
//		CommandTable c = new CommandTable("C:/Users/otsuka/Documents/GitHub/TSCOMSimulator/target/classes/sokkiacommand");
//		c.loadCommands();

		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainController.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		primaryStage.setTitle("TSCOMSimulator");
		primaryStage.setScene(scene);
		primaryStage.show();

	}


	public static void main(String[] args) {
		launch(args);
	}
}
