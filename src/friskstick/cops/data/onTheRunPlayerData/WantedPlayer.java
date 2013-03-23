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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import friskstick.cops.plugin.FriskStick;

public class WantedPlayer {

	private ArrayList<Player> wanted;

	private FriskStick plugin;

	private boolean firstTime = true;

	public WantedPlayer(FriskStick plugin) {

		wanted = new ArrayList<Player>();

		this.plugin = plugin;

		if(firstTime){

			updateWantedConfig("set", null);

			firstTime = false;

		}

	}

	/**
	 * Will set the status of a certain player as wanted if the player doesn't comply
	 * 
	 * @param p The player to set as wanted
	 */
	public void setWanted(Player p) {

		wanted.add(p);
		updateWantedConfig("set", null);

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
				updateWantedConfig("remove", p.getName());

			}

		}

	}

	/**
	 * Updates the configuration file which contains the list of all the wanted players
	 * 
	 * @param operation The operation (set a player or remove a player) to perform on the wanted file.
	 * @param nameToRemove Only applies to the remove operation. The name of the player to remove from the file.
	 */
	private void updateWantedConfig(String operation, String nameToRemove) {

		/*TODO
		 * FINISH THE WANTED LIST UPDATING
		 * fix paths
		 */

		File wantedFile = new File(plugin.getDataFolder(), "/data/wantedList.txt");

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
		 * READER
		 */
		try {

			reader = new BufferedReader(new FileReader(wantedFile));

			if(wanted.isEmpty()){

				String name = null;

				while((name = reader.readLine()) != null){

					wanted.add((Player)Bukkit.getServer().getOfflinePlayer(name));

				}

			}

			reader.close();

		} catch (IOException e) {

			e.printStackTrace();

		}

		/*
		 * WRITER
		 */
		try {

			writer = new BufferedWriter(new FileWriter(wantedFile));

			if(operation.equalsIgnoreCase("set")){

				for(int i = 0; i < wanted.size(); i++){

					writer.write(wanted.get(i).getName());

					writer.newLine();

				}

			} else if(operation.equalsIgnoreCase("remove")){

				removeLine(wantedFile, nameToRemove);

			} else {

				writer.close();

				throw new IllegalArgumentException("Wrong argument used in WantedPlayer.updateWantedConfig() method!");

			}

			writer.close();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	private void removeLine(File file, String textToRemove){

		File temp = new File(file.getAbsolutePath() + ".tmp");

		try {

			BufferedReader reader = new BufferedReader(new FileReader(file));
			PrintWriter writer = new PrintWriter(new FileWriter(temp));

			String line = null;

			while((line = reader.readLine()) != null){

				if(!line.trim().equals(textToRemove)){

					writer.println(line);
					writer.flush();

				}

				writer.close();
				reader.close();

				if(!file.delete()){

					throw new RuntimeException("Could not update wanted file correctly! (deletion)");

				}

				if(!temp.renameTo(file)){

					throw new RuntimeException("Could not update wanted file correctly! (renaming)");

				}

			}

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
