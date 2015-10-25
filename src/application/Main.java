package application;
	
import business.SystemController;
import business.ViewController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
	 SystemController.stageArea=stage;
	 new ViewController().SetViewLoginSystem(stage);
	}
	
	
}
