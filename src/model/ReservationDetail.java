package model;

public class ReservationDetail {
	private String ReservationID;
	private String ServiceID;

	public ReservationDetail(String reservationID, String serviceID) {
		super();
		ReservationID = reservationID;
		ServiceID = serviceID;
	}

	public String getReservationID() {
		return ReservationID;
	}

	public void setReservationID(String reservationID) {
		ReservationID = reservationID;
	}

	public String getServiceID() {
		return ServiceID;
	}

	public void setServiceID(String serviceID) {
		ServiceID = serviceID;
	}

}
