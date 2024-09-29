package model;

public class ServiceType {

	private String ServiceTypeID;
	private String ServiceTypeName;

	public ServiceType(String serviceTypeID, String serviceTypeName) {
		super();
		ServiceTypeID = serviceTypeID;
		ServiceTypeName = serviceTypeName;
	}

	public String getServiceTypeID() {
		return ServiceTypeID;
	}

	public void setServiceTypeID(String serviceTypeID) {
		ServiceTypeID = serviceTypeID;
	}

	public String getServiceTypeName() {
		return ServiceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		ServiceTypeName = serviceTypeName;
	}

}
