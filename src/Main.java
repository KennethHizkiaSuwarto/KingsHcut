import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static Main main;
	private Scene scene;

	public static Scene getScene() {
		return main.scene;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		main = this;
		scene = new Scene(new SignIn().getvBox(), 800, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("KingsHcut");
		primaryStage.show();
	}

}
