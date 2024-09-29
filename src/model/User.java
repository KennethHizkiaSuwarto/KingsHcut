package model;

public class User {

	private String UserID;
	private String Username;
	private String UserPassword;
	private String UserEmail;
	private String UserRole;
	private String UserGender;
	private String UserPhoneNumber;

	public User(String userID, String username, String userPassword, String userEmail, String userRole,
			String userGender, String userPhoneNumber) {
		super();
		UserID = userID;
		Username = username;
		UserPassword = userPassword;
		UserEmail = userEmail;
		UserRole = userRole;
		UserGender = userGender;
		UserPhoneNumber = userPhoneNumber;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getUserPassword() {
		return UserPassword;
	}

	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}

	public String getUserEmail() {
		return UserEmail;
	}

	public void setUserEmail(String userEmail) {
		UserEmail = userEmail;
	}

	public String getUserRole() {
		return UserRole;
	}

	public void setUserRole(String userRole) {
		UserRole = userRole;
	}

	public String getUserGender() {
		return UserGender;
	}

	public void setUserGender(String userGender) {
		UserGender = userGender;
	}

	public String getUserPhoneNumber() {
		return UserPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		UserPhoneNumber = userPhoneNumber;
	}

}
