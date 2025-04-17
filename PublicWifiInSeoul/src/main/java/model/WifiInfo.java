package model;

public class WifiInfo {

	private double distance;		// 조회한 위치와 공공 WiFi의 거리
	private String wifiNo;			// 와이파이 번호
	
	private String district;		// 자치구
	private String wifiName;		// 와이파이명
	private String address1;		// 도로명 주소
	private String address2;		// 상세 주소
	private String installFloor;	// 설치 위치(층)
	private String installType;		// 설치 유형
	private String installMby;		// 설치 기관
	
	private String serviceType;		// 서비스 구분
	private String networkType;		// 망 종류
	private String installYear;		// 설치 년도
	private String inoutDoor;		// 실내외구분
	private String wifiConnection;	// 와이파이 접속 환경

	private double longitude;		// 경도(X축)
	private double latitude;		// 위도(Y축)
	private String workDate;		// 작업 일자 


	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public String getWifiNo() {
		return wifiNo;
	}
	
	public void setWifiNo(String wifiNo) {
		this.wifiNo = wifiNo;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getWifiName() {
		return wifiName;
	}

	public void setWifiName(String wifiName) {
		this.wifiName = wifiName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getInstallFloor() {
		return installFloor;
	}

	public void setInstallFloor(String installFloor) {
		this.installFloor = installFloor;
	}

	public String getInstallType() {
		return installType;
	}

	public void setInstallType(String installType) {
		this.installType = installType;
	}

	public String getInstallMby() {
		return installMby;
	}

	public void setInstallMby(String installMby) {
		this.installMby = installMby;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getInstallYear() {
		return installYear;
	}

	public void setInstallYear(String installYear) {
		this.installYear = installYear;
	}

	public String getInoutDoor() {
		return inoutDoor;
	}

	public void setInoutDoor(String inoutDoor) {
		this.inoutDoor = inoutDoor;
	}

	public String getWifiConnection() {
		return wifiConnection;
	}

	public void setWifiConnection(String wifiConnection) {
		this.wifiConnection = wifiConnection;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	
}
