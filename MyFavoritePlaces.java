import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MyFavoritePlaces 
{	
	static boolean emptyList;
	private static final Scanner input = new Scanner(System.in);
	private static PlaceList placeList = new PlaceList();
	private static boolean done;

	public static void main(String args[]) 
	{
		//PlaceList placeList = new PlaceList();

		//initialize database from .txt (using args[0]?) [needs execption handling]
		//initializePlaces(fileName);

		//process user additions and modifications to data
		processInput();

		//write changes to .txt
		//writeChanges(fileName); [needs exception handling]
	}

	private static void processInput() {

		String command = null;

		while (!done) {

			System.out.println("My Favorite Places 2016\n"
					+ "--------------------------");
			if (placeList.size() == 0) {
				emptyList = true;
				System.out.println("No places loaded.");
			}
			else {
				emptyList = false;
				placeList.displayPlaces();
			}
			System.out.println("--------------------------");
			if (emptyList) System.out.print("A)dd R)ead Q)uit : ");
			else System.out.print("A)dd S)how E)dit D)elete C)urrent R)ead W)rite Q)uit : ");


			command = input.nextLine().toLowerCase();

			if (emptyList) switch (command) { //limited options because no places loaded
			case "a" :
				processAddCommand();
				break;
			case "r" : 
				processReadCommand();
				break;
			case "q" : 
				processQuitCommand();
				break;
			}

			else switch (command) { //all options available because at least 1 place loaded
			case "a" : 
				processAddCommand();
				break;
			case "s" : 
				processShowCommand();
				break;
			case "e" : //edit provides a way for the user to change the name or address
				processEditCommand();
				break;
			case "d" :// Delete removes a place from the list.
				processDeleteCommand();
				break;
			case "c" : 
				processCurrentCommand();
				break;
			case "r" : 
				processReadCommand();
				break;
			case "w" : 
				processWriteCommand();
				break;
			case "q" : 
				processQuitCommand();
				break;
			}
		}
	}

	public static boolean validChoice(int choiceInt) {
		input.nextLine();
		if ((choiceInt <= placeList.size()) && (choiceInt > 0)) 
			return true;
		else {
			System.out.println("Invalid value: " + choiceInt);
			return false;
		}
	}

	public static void processAddCommand() {
		String nameStr = null;
		String addressStr = null;

		System.out.print("Enter the name: ");
		nameStr = input.nextLine();
		System.out.print("Enter the address: ");
		addressStr = input.nextLine();
		Place toAdd = new Place(nameStr, addressStr);

		if (!toAdd.lookupGeo(addressStr)) {
			System.out.println("Place not found using address: " + addressStr);
			System.out.print("Press Enter to continue.");
			input.nextLine();
		}
		else placeList.addPlace(toAdd);

	}

	public static void processQuitCommand() {
		System.out.println("Thank you for using My Favorite Places 2016!");
		done = true;
		input.close();
		System.exit(0); //remove after milestone 3(?) probably not.
	}

	//When Show is selected, also open a browser at the specified URL. 
	//The openBrowser method in the Geocoding class does this.
	//simply catch and handle the exceptions appropriately.
	public static void processShowCommand() {
		System.out.print("Enter number of place to Show: ");
		int showInt = input.nextInt();
		if (validChoice(showInt)) {
			placeList.showPlace(showInt);
			System.out.print("Press Enter to continue.");
			input.nextLine();
		}
	}

	public static void processReadCommand() {
		System.out.println("Available Files: ");
		File folder = new File(".");
		for ( File file : folder.listFiles()) {
			if ( file.getName().endsWith(".mfp")) {
				System.out.println("\t" + file.getName());
			}
		}
		System.out.println();
		System.out.print("Enter filename: ");
		String fileName = input.nextLine();
		try {
			File tryFile = new File(fileName);
			Scanner in = new Scanner(tryFile);
			while (in.hasNextLine()) {
				String[] placeString = in.nextLine().split(";");
				String nameString    = placeString[0];
				String addressString = placeString[1];
				double readLattitude = Double.parseDouble(placeString[2]);
				double readLongitude = Double.parseDouble(placeString[3]);
				Place readPlace = new Place(nameString, addressString, 
						readLattitude, readLongitude);
				if (!placeList.contains(readPlace)) {
					placeList.addPlace(readPlace);
				}
				else System.out.println("Place " + readPlace.getName() + 
				" already in list.");
			}
			in.close();
			System.out.println();
		}
		catch (FileNotFoundException e) {
			System.out.println("Unable to read from file: " + fileName);
		}
	}


	public static void processWriteCommand() {
		System.out.println("Current Files:");
		File folder = new File(".");
		for ( File file : folder.listFiles()) {
			if ( file.getName().endsWith(".mfp")) {
				System.out.println("\t" + file.getName());
			}
		}
		System.out.println();
		System.out.print("Enter filename: ");
		String fileName = input.nextLine();
		try {
			PrintWriter writer = new PrintWriter(fileName);
			for (Place p : placeList.getList()) {
				writer.write(p.getName() + ";" + p.getAddress() + 
						";" + p.getLat() + ";" + p.getLong() + "\n");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("Uanable to write to file: " + fileName);
		}
	}

	public static void processDeleteCommand() {
		System.out.print("Enter number of place to Delete: ");
		int delInt = input.nextInt();
		if (validChoice(delInt)) {
			placeList.deletePlace(delInt);
			System.out.println("Press Enter to continue.");
			input.nextLine();
		}
	}

	public static void processEditCommand() {
		String newName = null;
		String newAddress = null;
		System.out.print("Enter number of place to Edit: ");
		int editInt = input.nextInt();
		if (validChoice(editInt)) {
			Place place = placeList.getPlace(editInt - 1);
			System.out.println("Current name: " + place.getName());
			System.out.print("Enter a new name: ");
			newName = input.nextLine();
			System.out.println("Current address: " + place.getAddress());
			System.out.print("Enter a new address: ");
			newAddress = input.nextLine();

			if (!place.lookupGeo(newAddress)) {
				//find out what console is supposed to say in this case
				//this is the case where you edit an existing place's address with an invalid new address
			}
			place.setName(newName);
		}
	}

	public static void processCurrentCommand() {
		//Enter number of place to be Current place: 2
		//Good set as Current place.
		//Press Enter to continue.
	}

	//	private static void displayMenu() {
	//		System.out.println("My Favorite Places 2016\n"
	//				+ "--------------------------");
	//		if (placeList.size() == 0) {
	//			multiOption = false;
	//			System.out.println("No places loaded.");
	//		}
	//		else {
	//			multiOption = true;
	//			placeList.DisplayPlaces();
	//		}
	//		System.out.println("--------------------------");
	//	}

}
