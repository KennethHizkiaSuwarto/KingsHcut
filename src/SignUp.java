import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.User;
import util.Connect;

public class SignUp {

	private Connect connect = Connect.getInstance();
	ObservableList<User> userList = FXCollections.observableArrayList();

	TextField emailField = new TextField();
	PasswordField passwordField = new PasswordField();
	PasswordField confirmField = new PasswordField();
	TextField phoneField = new TextField();
	TextField usernameField = new TextField();
	RadioButton maleButton = new RadioButton();
	RadioButton femaleButton = new RadioButton();
	Label usernameLabel = new Label("Username");
	Label phoneLabel = new Label("Phone Number");
	Label maleLabel = new Label("Male");
	Label femaleLabel = new Label("Female");
	Label signUpLabel = new Label("Register");
	Label emailLabel = new Label("Email");
	Label passwordLabel = new Label("Password");
	Label confirmLabel = new Label("Confirm Password");
	Button button = new Button("Register");
	ToggleGroup genderGroup = new ToggleGroup();
	HBox hBox = new HBox(5);
	VBox vBox = new VBox(8);
	VBox usernameContainer = new VBox();
	VBox phoneContainer = new VBox();
	VBox emailContainer = new VBox();
	VBox passwordContainer = new VBox();
	VBox confirmContainer = new VBox();
	Hyperlink link = new Hyperlink("Already have an account? Click here to login!");

	public VBox getvBox() {
		return vBox;
	}

	public void setvBox(VBox vBox) {
		this.vBox = vBox;
	}

	public void addComponent() {
		userList = connect.getLogin();

		maleButton.setToggleGroup(genderGroup);
		femaleButton.setToggleGroup(genderGroup);

		usernameContainer.getChildren().addAll(usernameLabel, usernameField);
		phoneContainer.getChildren().addAll(phoneLabel, phoneField);
		emailContainer.getChildren().addAll(emailLabel, emailField);
		passwordContainer.getChildren().addAll(passwordLabel, passwordField);
		confirmContainer.getChildren().addAll(confirmLabel, confirmField);
		hBox.getChildren().addAll(maleButton, maleLabel, femaleButton, femaleLabel);
		vBox.getChildren().addAll(signUpLabel, usernameContainer, emailContainer, passwordContainer, confirmContainer,
				phoneContainer, hBox, button, link);
	}

	public void style() {
		usernameField.setPromptText("Input your username here");
		passwordField.setPromptText("Input your password here");
		confirmField.setPromptText("Confirm your password here");
		phoneField.setPromptText("Input your phone number here");
		emailField.setPromptText("Input your email here");

		usernameContainer.setMaxWidth(300);
		emailContainer.setMaxWidth(300);
		passwordContainer.setMaxWidth(300);
		confirmContainer.setMaxWidth(300);
		phoneContainer.setMaxWidth(300);
		hBox.setMaxWidth(300);
		vBox.setAlignment(Pos.CENTER);
		vBox.setMaxWidth(500);
		signUpLabel.setFont(Font.font(null, FontWeight.BOLD, 30));
		button.setMinWidth(100);
		button.setMinHeight(40);
		button.setFont(Font.font(null, FontWeight.NORMAL, 12));
	}

	public boolean checkAlphaNum(String password) {
		boolean isAlpha = false;
		boolean isNum = false;

		for (int i = 0; i < password.length(); i++) {
			if (Character.isAlphabetic(password.charAt(i))) {
				isAlpha = true;
			} else {
				isNum = true;
			}

			if (isAlpha && isNum) {
				return true;
			}
		}
		return false;
	}

	public String generateUserID(int idNumber) {
		String UserID = "";

		if (idNumber < 10) {
			UserID = "US00" + idNumber;
		} else if (idNumber < 100) {
			UserID = "US0" + idNumber;
		} else if (idNumber < 1000) {
			UserID = "US" + idNumber;
		}

		return UserID;
	}

	public boolean validEmail(String email) {
		for (User user : userList) {
			if (user.getUserEmail().equals(email)) {
				return false;
			}
		}
		return true;
	}

	public boolean validPhone(String phone) {
		for (User user : userList) {
			if (user.getUserPhoneNumber().equals(phone)) {
				return false;
			}
		}
		return true;
	}

	public void event() {

		ObservableList<User> userList = connect.getLogin();

		button.setOnAction(e -> {

			Alert alert;
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Register Error");
			if (usernameField.getText().isEmpty()) {
				alert.setContentText("Username cannot be empty");
				alert.show();
				return;
			} else if (usernameField.getText().length() < 5 || usernameField.getText().length() > 30) {
				alert.setContentText("Username must be 5 - 30 characters");
				alert.show();
				return;
			} else if (emailField.getText().isEmpty()) {
				alert.setContentText("Email cannot be empty");
				alert.show();
				return;
			} else if (!emailField.getText().endsWith("@gmail.com")) {
				alert.setContentText("Email must ends with @gmail.com");
				alert.show();
				return;
			} else if (!validEmail(emailField.getText())) {
				alert.setContentText("Email has already been used");
				alert.show();
				return;
			} else if (passwordField.getText().isEmpty()) {
				alert.setContentText("Password cannot be empty");
				alert.show();
				return;
			} else if (passwordField.getText().length() <= 8) {
				alert.setContentText("Password must be 8 - 15 characters");
				alert.show();
				return;
			} else if (!checkAlphaNum(passwordField.getText())) {
				alert.setContentText("Password must be alphanumeric");
				alert.show();
				return;
			} else if (!confirmField.getText().equals(passwordField.getText())) {
				alert.setContentText("Password must be the same as confirm password");
				alert.show();
			} else if (phoneField.getText().isEmpty()) {
				alert.setContentText("Phone number cannot be empty");
				alert.show();
				return;
			} else if (!phoneField.getText().startsWith("62")) {
				alert.setContentText("Phone number must start with 62");
				alert.show();
				return;
			} else if (!validPhone(phoneField.getText())) {
				alert.setContentText("Phone number has already been used");
				alert.show();
				return;
			} else if (phoneField.getText().length() <= 9 || phoneField.getText().length() >= 13) {
				alert.setContentText("Phone number must be 9 - 13 characters");
				alert.show();
				return;
			} else if (!maleButton.isSelected() && !femaleButton.isSelected()) {
				alert.setContentText("Gender must be selected");
				alert.show();
				return;
			}

			String ID = "";
			String email = emailField.getText();
			String password = passwordField.getText();
			String phoneNumber = phoneField.getText();
			String username = usernameField.getText();
			String gender = "";
			String role = "User";

			if (maleButton.isSelected()) {
				gender = "Male";
			} else {
				gender = "Female";
			}

			boolean validEmail = true;
			boolean validPhone = true;

			for (User user : userList) {
				if (user.getUserEmail().equals(email)) {
					validEmail = false;
					alert.setContentText("Email has already been used");
					alert.show();
				} else if (user.getUserPhoneNumber().equals(phoneNumber)) {
					validPhone = false;
					alert.setContentText("PhoneNumber has already been used");
					alert.show();
				}
			}
			if (validPhone && validEmail) {
				if (username.contains("admin")) {
					role = "Admin";
				}

				phoneNumber = "0" + phoneNumber.substring(2, phoneNumber.length());

				String currUserID = userList.get(userList.size() - 1).getUserID();
				int userNumber = Integer.parseInt(currUserID.substring(2, 5)) + 1;
				ID = generateUserID(userNumber);

				User user = new User(ID, username, password, email, role, gender, phoneNumber);
				connect.insertUser(user);

				alert.setAlertType(AlertType.INFORMATION);
				alert.setHeaderText("Sign Up Success");
				alert.setContentText("Sign Up Successful");
				alert.show();

				emailField.setText("");
				passwordField.setText("");
				confirmField.setText("");
				usernameField.setText("");
				phoneField.setText("");
				maleButton.setSelected(false);
				femaleButton.setSelected(false);
			} else {
				return;
			}
		});

		link.setOnAction(e -> {
			Main.getScene().setRoot(new SignIn().getvBox());
		});
	}

	public SignUp() {
		super();
		addComponent();
		style();
		event();
	}

}
