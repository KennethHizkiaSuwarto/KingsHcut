package model;

public class Service {

	private String ServiceID;
	private String ServiceTypeID;
	private String ServiceName;
	private int ServicePrice;
	private int ServiceDuration;

	public Service(String serviceID, String serviceTypeID, String serviceName, int servicePrice, int serviceDuration) {
		super();
		ServiceID = serviceID;
		ServiceTypeID = serviceTypeID;
		ServiceName = serviceName;
		ServicePrice = servicePrice;
		ServiceDuration = serviceDuration;
	}

	public String getServiceID() {
		return ServiceID;
	}

	public void setServiceID(String serviceID) {
		ServiceID = serviceID;
	}

	public String getServiceTypeID() {
		return ServiceTypeID;
	}

	public void setServiceTypeID(String serviceTypeID) {
		ServiceTypeID = serviceTypeID;
	}

	public String getServiceName() {
		return ServiceName;
	}

	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}

	public int getServicePrice() {
		return ServicePrice;
	}

	public void setServicePrice(int servicePrice) {
		ServicePrice = servicePrice;
	}

	public int getServiceDuration() {
		return ServiceDuration;
	}

	public void setServiceDuration(int serviceDuration) {
		ServiceDuration = serviceDuration;
	}

}
