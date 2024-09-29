package model;

public class ReservationHeader {

	private String ReservationID;
	private String UserID;
	private String ReservationDate;
	private String StartReservationTime;
	private String EndReservationTime;
	private String ReservationStatus;

	public ReservationHeader(String reservationID, String userID, String reservationDate, String startReservationTime,
			String endReservationTime, String reservationStatus) {
		super();
		ReservationID = reservationID;
		UserID = userID;
		ReservationDate = reservationDate;
		StartReservationTime = startReservationTime;
		EndReservationTime = endReservationTime;
		ReservationStatus = reservationStatus;
	}

	public String getReservationID() {
		return ReservationID;
	}

	public void setReservationID(String reservationID) {
		ReservationID = reservationID;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getReservationDate() {
		return ReservationDate;
	}

	public void setReservationDate(String reservationDate) {
		ReservationDate = reservationDate;
	}

	public String getStartReservationTime() {
		return StartReservationTime;
	}

	public void setStartReservationTime(String startReservationTime) {
		StartReservationTime = startReservationTime;
	}

	public String getEndReservationTime() {
		return EndReservationTime;
	}

	public void setEndReservationTime(String endReservationTime) {
		EndReservationTime = endReservationTime;
	}

	public String getReservationStatus() {
		return ReservationStatus;
	}

	public void setReservationStatus(String reservationStatus) {
		ReservationStatus = reservationStatus;
	}

}
