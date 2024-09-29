import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.User;
import util.Connect;

public class SignIn {

	private Connect connect = Connect.getInstance();

	TextField emailField = new TextField();
	PasswordField passwordField = new PasswordField();
	Label signInLabel = new Label("Login");
	Label emailLabel = new Label("Email");
	Label passwordLabel = new Label("Password");
	Button button = new Button("Login");
	VBox vBox = new VBox(8);
	VBox emailContainer = new VBox();
	VBox passwordContainer = new VBox();
	Hyperlink link = new Hyperlink("Don't have an account yet? Register Here!");

	public VBox getvBox() {
		return vBox;
	}

	public void setvBox(VBox vBox) {
		this.vBox = vBox;
	}

	public void addComponent() {
		emailContainer.getChildren().addAll(emailLabel, emailField);
		passwordContainer.getChildren().addAll(passwordLabel, passwordField);
		vBox.getChildren().addAll(signInLabel, emailContainer, passwordContainer, button, link);
	}

	public void style() {
		emailField.setPromptText("Input your username here");
		passwordField.setPromptText("Input your password here");

		emailField.setMaxWidth(300);
		passwordField.setMaxWidth(300);
		emailContainer.setMaxWidth(300);
		passwordContainer.setMaxWidth(300);
		vBox.setAlignment(Pos.CENTER);
		vBox.setMaxWidth(500);
		signInLabel.setFont(Font.font(null, FontWeight.BOLD, 30));
		button.setMinWidth(100);
		button.setMinHeight(40);
		button.setFont(Font.font(null, FontWeight.NORMAL, 12));
	}

	public void event() {

		ObservableList<User> userList = connect.getLogin();

		button.setOnAction(e -> {
			String email = emailField.getText();
			String password = passwordField.getText();
			boolean validSignIn = false;

			if (emailField.getText().equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Login Error");
				alert.setContentText("Email cannot be empty");
				alert.show();
			}

			for (User user : userList) {
				if (user.getUserEmail().equals(email) && user.getUserPassword().equals(password)) {
					validSignIn = true;
					if (user.getUserRole().equals("User")) {
						Main.getScene().setRoot(new ReserveService(user).getBp());
					} else {
						Main.getScene().setRoot(new ReservationManagement(user).getBp());
					}
				}
			}

			if (!validSignIn) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Sign In Error");
				alert.setContentText("Incorrect sign in credentials");
				alert.show();
			}

		});

		link.setOnAction(e -> {
			Main.getScene().setRoot(new SignUp().getvBox());
		});
	}

	public SignIn() {
		super();
		addComponent();
		style();
		event();
	}

}
