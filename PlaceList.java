import java.util.ArrayList;

/**Create another class that will manage a list of instances of the place 
 * class using an ArrayList. This class will have methods to add and 
 * remove places, retrieve a place, determine whether a place already 
 * exists in the list, return the number of places in the list and, 
 * in milestone 3, sort places.
 * @author
 *
 */
public class PlaceList {

	private ArrayList<Place> placeList;
	
	public PlaceList() {
		this.placeList = new ArrayList<Place>();
	}
	
	public ArrayList<Place> getList() {
		return this.placeList;
	}
	
	public boolean addPlace(Place p) {
		if (!placeList.contains(p)) {
			placeList.add(0, p);
			return true;
		}
		else return false;
	}
	
//	private void deletePlace(Place p) {
//		placeList.remove(p);
//	}
	
	public void deletePlace(int i) {
		Place toRemove = placeList.remove(i-1);
		String name = toRemove.getName();
		System.out.println(name + " deleted.");
	}
	
	public Place lookupPlace(String name) {
		for (Place p: placeList) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}
	
	public Place getPlace(int i) {
		if ((i <= placeList.size()) && (i >= 0)) return placeList.get(i);
		else return null;
	}
	
	public int size() {
		return placeList.size();
	}
	
	public void sortPlaces() {
		//to be implemented later
	}
	
	public boolean contains(Place p) {
		return placeList.contains(p);
	}
	
	public void displayPlaces() {
		for (int i = 0; i < placeList.size(); i++) {
			System.out.println((i + 1) + ") " + placeList.get(i).getName());
		}
	}
	
	public void showPlace(int i) {
		Place p = placeList.get(i - 1);
		System.out.println(p.getName());
		System.out.println(p.getAddress());
		System.out.println(p.getLat() + "," + p.getLong());
		System.out.println(p.getUrl());
	}

}
