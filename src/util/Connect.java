package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ReservationDetail;
import model.ReservationHeader;
import model.Service;
import model.ServiceType;
import model.User;

public class Connect {

	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "kingshcut";
	private final String HOST = "localhost:3306";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

	public ResultSet rs;
	public ResultSetMetaData rsm;
	private Connection con;
	private Statement st;

	private static volatile Connect instance;

	public static Connect getInstance() {

		if (instance == null) {
			synchronized (Connect.class) {
				if (instance == null) {
					return new Connect();
				}
			}
		}

		return instance;
	}

	private Connect() {

		try {
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			st = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void insertUser(User user) {
		String query = String.format("INSERT INTO msuser VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
				user.getUserID(), user.getUsername(), user.getUserPassword(), user.getUserEmail(), user.getUserRole(),
				user.getUserGender(), user.getUserPhoneNumber());
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertService(Service service) {
		String query = String.format("INSERT INTO msservice VALUES ('%s', '%s', '%s', %d, %d)", service.getServiceID(),
				service.getServiceTypeID(), service.getServiceName(), service.getServicePrice(),
				service.getServiceDuration());
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertReservationHeader(ReservationHeader header) {
		String query = String.format("INSERT INTO reservationheader VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
				header.getReservationID(), header.getUserID(), header.getReservationDate(),
				header.getStartReservationTime(), header.getEndReservationTime(), header.getReservationStatus());
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void insertReservationDetail(ReservationDetail detail) {
		String query = String.format("INSERT INTO reservationdetail VALUES ('%s', '%s')", detail.getReservationID(),
				detail.getServiceID());
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void cancelReservation(ReservationHeader reservationHeader) {
		String query = String.format(
				"UPDATE ReservationHeader SET ReservationStatus = 'Cancelled' WHERE ReservationID = '%s'",
				reservationHeader.getReservationID());
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void finishReservation(ReservationHeader reservationHeader) {
		String query = String.format(
				"UPDATE ReservationHeader SET ReservationStatus = 'Finished' WHERE ReservationID = '%s'",
				reservationHeader.getReservationID());
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateService(Service service) {
		String query = String.format(
				"UPDATE MsService SET ServiceTypeID = '%s', ServiceName = '%s', ServicePrice = %d, ServiceDuration= %d WHERE ServiceID = '%s'",
				service.getServiceTypeID(), service.getServiceName(), service.getServicePrice(),
				service.getServiceDuration(), service.getServiceID());
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteService(Service service) {
		String query = String.format("DELETE FROM MsService WHERE ServiceID = '%s'", service.getServiceID());
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ObservableList<User> getLogin() {
		ObservableList<User> userList = FXCollections.observableArrayList();

		try {
			String query = "SELECT * FROM msuser";
			ResultSet resultSet = st.executeQuery(query);

			while (resultSet.next()) {
				String userID = resultSet.getString("UserID");
				String username = resultSet.getString("Username");
				String userEmail = resultSet.getString("UserEmail");
				String userPassword = resultSet.getString("UserPassword");
				String userGender = resultSet.getString("UserGender");
				String userRole = resultSet.getString("UserRole");
				String userPhoneNumber = resultSet.getString("UserPhoneNumber");

				userPhoneNumber = "62" + userPhoneNumber.substring(1, userPhoneNumber.length());

				User user = new User(userID, username, userPassword, userEmail, userRole, userGender, userPhoneNumber);
				userList.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userList;
	}

	public ObservableList<ReservationHeader> getReservationHeader() {
		ObservableList<ReservationHeader> reservationHeaderList = FXCollections.observableArrayList();

		try {
			String query = "SELECT * FROM reservationheader";
			ResultSet resultSet = st.executeQuery(query);

			while (resultSet.next()) {
				String ReservationID = resultSet.getString("ReservationID");
				String UserID = resultSet.getString("UserID");
				String ReservationDate = resultSet.getString("ReservationDate");
				String StartReservationTime = resultSet.getString("StartReservationTime");
				String EndReservationTime = resultSet.getString("EndReservationTime");
				String ReservationStatus = resultSet.getString("ReservationStatus");

				ReservationHeader reservationHeader = new ReservationHeader(ReservationID, UserID, ReservationDate,
						StartReservationTime, EndReservationTime, ReservationStatus);
				reservationHeaderList.add(reservationHeader);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reservationHeaderList;
	}

	public boolean checkValidReservationTime(String date, String time) {
		int rowCount = 0;
		try {
			String query = String.format(
					"SELECT * FROM ReservationHeader WHERE ReservationDate = '%s' AND '%s' BETWEEN StartReservationTime AND EndReservationTime AND ReservationStatus IN('Finished', 'In Progress')",
					date, time);
			ResultSet resultSet = st.executeQuery(query);

			while (resultSet.next()) {
				rowCount++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rowCount > 0) {
			return false;
		}

		return true;
	}

	public ObservableList<Service> getService() {
		ObservableList<Service> serviceList = FXCollections.observableArrayList();

		try {
			String query = "SELECT * FROM msservice";
			ResultSet resultSet = st.executeQuery(query);

			while (resultSet.next()) {
				String ServiceID = resultSet.getString("ServiceID");
				String ServiceTypeID = resultSet.getString("ServiceTypeID");
				String ServiceName = resultSet.getString("ServiceName");
				int ServicePrice = resultSet.getInt("ServicePrice");
				int ServiceDuration = resultSet.getInt("ServiceDuration");

				Service service = new Service(ServiceID, ServiceTypeID, ServiceName, ServicePrice, ServiceDuration);
				serviceList.add(service);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return serviceList;
	}

	public ObservableList<ServiceType> getServiceType() {
		ObservableList<ServiceType> serviceTypeList = FXCollections.observableArrayList();

		try {
			String query = "SELECT * FROM msservicetype";
			ResultSet resultSet = st.executeQuery(query);

			while (resultSet.next()) {
				String ServiceTypeID = resultSet.getString("ServiceTypeID");
				String ServiceTypeName = resultSet.getString("ServiceTypeName");

				ServiceType serviceType = new ServiceType(ServiceTypeID, ServiceTypeName);
				serviceTypeList.add(serviceType);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return serviceTypeList;
	}

}
