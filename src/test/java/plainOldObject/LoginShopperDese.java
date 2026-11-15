package plainOldObject;

public class LoginShopperDese
{
	private int statusCode;
	private String message;
	private Data data;
	
	public int getStatusCode() {
		return statusCode;
	}
	public String getMessage() {
		return message;
	}
	public Data getData() {
		return data;
	}
	
	public class Data {
	    private String userId;
	    private String email;
	    private String role;
	    private String status;
	    private String createdDateTime;
	    private String firstName;
	    private String lastName;
	    private String city;
	    private String state;
	    private String country;
	    private String zoneId;
	    private String imageId;
	    private String jwtToken;

	    public String getUserId() { return userId; }
	    public String getEmail() { return email; }
	    public String getRole() { return role; }
	    public String getStatus() { return status; }
	    public String getCreatedDateTime() { return createdDateTime; }
	    public String getFirstName() { return firstName; }
	    public String getLastName() { return lastName; }
	    public String getCity() { return city; }
	    public String getState() { return state; }
	    public String getCountry() { return country; }
	    public String getZoneId() { return zoneId; }
	    public String getImageId() { return imageId; }
	    public String getJwtToken() { return jwtToken; }
	}

}
