package model;

public class History {
	
	private int historyNo;			// 검색 번호 
	private double longitude;		// 경도(x축)
	private double latitude;		// 위도(y축)
	private String serchDate;		// 검색 날짜
	private boolean isDelete;		// 삭제 여부
	
	public int getHistoryNo() {
		return historyNo;
	}
	public void setHistoryNo(int historyNo) {
		this.historyNo = historyNo;
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
	public String getSerchDate() {
		return serchDate;
	}
	public void setSerchDate(String serchDate) {
		this.serchDate = serchDate;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
}
