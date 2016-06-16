package org.vashonsd.pirateship.worldcreator;

import java.io.IOException;
import java.util.ArrayList;

import org.vashonsd.pirateship.io.ConsoleOut;
import org.vashonsd.pirateship.io.StringRead;
import org.vashonsd.pirateship.io.StringWrite;
import org.vashonsd.pirateship.io.UserInput;
import org.vashonsd.pirateship.structure.Location;
import org.vashonsd.pirateship.structure.Route;
import org.vashonsd.pirateship.structure.World;

public class WorldCreator {

	private StringRead reader;
	private StringWrite writer;
	private World newWorld;
	
	public WorldCreator() {
		
	}

	
	public World worldCreator() throws IOException {
		
		writer = new ConsoleOut();
		reader = new UserInput();
		
		//Get name of world
		writer.write("What is the name of your new world? ");
		newWorld = new World(reader.read());
		
		//get name of starting location
		//Location startingLocation;
		writer.write("What is the name of the starting location? ");
		String sName = reader.read();
		
		
		//get a description for the starting location
		writer.write("Describe the location" );
		String sDescription = reader.read();
		
		writer.write("Describe the scene" );
		String sSplash = reader.read();
		
		Location startingLocation = new Location(sName, sDescription, sSplash);
		
		/*
		writer.write("Would you like any items in this location");
		if(reader.read().equalsIgnoreCase("yes")) {
			writer.write("What item would you like to add");
			String newItem = reader.read();
			
			
			startingLocation.addToInventory(newItem);
		}
		
		*/
		
		//Add the starting location to the world and set it as the starting location
		newWorld.addLocation(startingLocation);
		newWorld.setStartingLocation(startingLocation);

		
		//Prompts user
		writer.write("Now we will create a location to connect to your starting location." );
		//Runs as long as user wants to create new locations
		while(true) {
			
			//Gets name of new location
			writer.write("What is the name of your new location?");
			String lName = reader.read();
			
			//Asks user name of location from which the new location will branch from
			writer.write("Which location would you like to branch from?");
			Location from = getLocation(reader.read());
			
			//gets description of new location and sets it to the new location
			writer.write("Describe the location. " );
			String lDescription = reader.read();
			
			writer.write("Describe the scene. " );
			String lSplash = reader.read();
			
			
			Location newLocation = new Location(lName, lDescription, lSplash);
			
			//Creates a new route to connect new locations
			//Route current = new Route(from.getName() + " - " + newLocation.getName());
			
			//gets description of route and sets it to the route
			writer.write("Describe the route. ");
			String rDescription = reader.read();
			
			writer.write("Describe how you get to the new location. ");
			String rSplash = reader.read();
			
			//Gets accessor for route and sets it
			writer.write("What word will access this route? ");
			String rAccessor = reader.read();
			
			from.addRoute(rDescription, rAccessor, rSplash, newLocation);
			
			//ADDS THE LOCTION TO THE WORLD
			newWorld.addLocation(newLocation);

			
			//Asks designer if he/she would like this route to go both ways
			writer.write("Would you like this to be a two way route? ");
			if(reader.read().equalsIgnoreCase("yes")) {
				
				//gets description of route and sets it to the route
				writer.write("Describe the route. ");
				String tDescription = reader.read();
				
				writer.write("Describe how you get back to the first location. ");
				String tSplash = reader.read();
				
				//Gets accessor for route and sets it
				writer.write("What word will access this route? ");
				String tAccessor = reader.read();
				
				newLocation.addRoute(tDescription, tAccessor, tSplash, from);
				
			}
			
			//Asks user if user would like to repeat this process
			writer.write("Would you like to make another location and route? ");
			if(reader.read().equalsIgnoreCase("no")) {
				break;
			}
		}
		//Breaks somewhere after here
		
		//Asks user if user would like any more routes connected to existing locations
		writer.write("Would you like to make any more routes connecting existing locations? ");
		if(reader.read().equalsIgnoreCase("yes")) {
	
			while(true) {
				
				//Gets location that route will start from
				writer.write("Which location would you like to branch from?");
				String fromName = reader.read();
				Location from;
				while(true) {
					if(locationExists(fromName)) {
						from = getLocation(fromName);
						break;
					}
					writer.write("Could not find location " + fromName + ". Try another Location.");
					fromName = reader.read();
				}
				
				
				
				//gets location the route will go to
				writer.write("Which location would you like to connect to? ");
				Location to = getLocation(reader.read());
				
				//gets description of route and sets it to the route
				writer.write("Describe the route. ");
				String cDescription = reader.read();
				
				//sets description of the route
				writer.write("Describe how you get to " + to.getName() + "? ");
				String cSplash = reader.read();				
				
				//gets and sets the accessor
				writer.write("What word will access this route? ");
				String cAccessor = reader.read();
				
				//adds route to location
				from.addRoute(cDescription, cAccessor, cSplash, to);
				
				
				//Asks if user would like to repeat this process
				writer.write("Would you like to connect any more existing locations ");
				if(reader.read().equalsIgnoreCase("no")) {
					break;
				}
			}
		}
		
		//Returns the newWorld
		return newWorld;
	}
	
	
	//method to get location from a sting name of the location
	private Location getLocation(String name) throws IOException {

		while(true) {
			for(Location l: newWorld.getLocations()) {
				if(l.getName().equalsIgnoreCase(name)) {
					return l;
				}
			}
			
			writer.write("This is not an existing location. Try again. ");
			name = reader.read();
		}
	}
	
	private boolean locationExists(String fromName) {
		for(Location l: newWorld.getLocations()) {
			if(l.getName().equalsIgnoreCase(fromName)) {
				return true;
			}
		}
		return false;
	}
	
}
