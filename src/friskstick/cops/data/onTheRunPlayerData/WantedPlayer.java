package friskstick.cops.data.onTheRunPlayerData;

/*
 * TODO
 * Implement this with frisking
 * 
 * It will work like this: A player has a set amount of time that he is required to comply
 * before he is automatically marked wanted. Other things are going to be implemented too.
 * 
 * We should make a wiki with all that we've been adding/plan on adding
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.entity.Player;

public class WantedPlayer {

	private ArrayList<Player> wanted;

	public WantedPlayer() {

		wanted = new ArrayList<Player>();

	}

	/**
	 * Will set the status of a certin player as wanted if the player doesn't comply
	 * 
	 * @param p The player to set as wanted
	 */
	public void setWanted(Player p) {

		wanted.add(p);
		updateWantedConfig();

	}

	/**
	 * Removes the player from the wanted status
	 * 
	 * @param p The player to remove from the wanted list
	 */
	public void removeWanted(Player p) {

		for(int i = 0; i < wanted.size(); i++) {

			if(wanted.get(i).getName().equals(p.getName())) {

				wanted.remove(i);
				updateWantedConfig();

			}

		}

	}

	/**
	 * Updates the configuration file which contains the list of all the wanted players
	 */
	private void updateWantedConfig() {

		/*TODO
		 * FINISH THE WANTED LIST UPDATING
		 * fix paths
		 */

		File wantedFile = new File("data/wantedList.txt");

		if(!wantedFile.exists()) {

			try {

				wantedFile.getParentFile().mkdir();
				wantedFile.createNewFile(); //Creates the new file if it does not already exist

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

		BufferedWriter writer;
		BufferedReader reader;

		/*
		 * WRITER
		 */
		try {

			writer = new BufferedWriter(new FileWriter("data/wantedList.txt"));

		} catch (IOException e) {

			e.printStackTrace();

		}

		/*
		 * READER
		 */
		try {

			reader = new BufferedReader(new FileReader("data/wantedList.txt"));

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}

	}

}
