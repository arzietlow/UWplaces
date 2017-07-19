import java.io.IOException;

public class Place implements Comparable {

	private static Place currentPlace;
	private String name;
	private String address;
	private double latitude;
	private double longitude;
	private String url;

	//Constructor for file read
	public Place(String name, String address, double lattitude, double longitude) {
		this.name = name;
		this.address = address;
		this.latitude = -1;
		this.longitude = 1;
	}

	//Constructor for user input
	public Place(String name, String address) {
		this.name = name;
		this.address = address;
	}

	//Accesor methods
	public String getName() {
		return this.name;
	}

	public String getAddress() {
		return this.address;
	}

	public String getUrl() {
		return this.url;
	}

	public double getLong() {
		return this.longitude;
	}

	public double getLat() {
		return this.latitude;
	}

	//Mutator methods
	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setLong(double longitude) {
		this.longitude = longitude;
	}

	public void setLat(double lat) {
		this.latitude = lat;
	}

	//the equals method will return true when the name and address are the same.
	@Override
	public boolean equals(Object o) {
		return ((this.name.equals(((Place) o).getName()) && 
				(this.address.equals(((Place) o).getAddress()))));
	}


	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean lookupGeo(String newAddress) {
		String jsonString;
		try {
			jsonString = Geocoding.find(newAddress);
		} catch (IOException e) {
			return false;
		}
		GResponse placeInfo = GeocodeResponse.parse(jsonString);
		if (!placeInfo.hasAddress()) return false;
		this.latitude = placeInfo.getLatitude();
		this.longitude = placeInfo.getLongitude();
		this.address = placeInfo.getFormattedAddress();
		this.url = formURL(this.address, this.latitude, this.longitude);
		return true;
	}
	
	//The main program, when showing place details, will call this method to obtain the URL.
	public String formURL(String address, double lattitude, double longitude) {
		//https://www.google.com/maps/place/702+S+Randall+Ave,+Madison,+WI+53715/@43.0606104,-89.410035,17z/
		String url = "https://www.google.com/maps/place/" + address.replace(' ', '+');
		url = url + "/@" + lattitude + "," + longitude + ",17z/";
		return url;
	}
}
