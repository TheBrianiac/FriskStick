package friskstick.cops.data;

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
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import friskstick.cops.plugin.FriskStick;

public class WantedPlayer {

	/**
	 * The list of wanted players
	 */
	private ArrayList<Player> wanted;

	private HashMap<Player, Player> requests;

	/**
	 * The plugin
	 */
	private FriskStick plugin;

	/**
	 * The boolean that checks if it is the first time
	 * 
	 * This might be a problem because when the server restarts, firstTime will be reset to true
	 */
	private boolean firstTime = true;

	/**
	 * Constructs WantedPlayers and Initializez fields
	 * 
	 * @param plugin The plugin
	 */
	public WantedPlayer(FriskStick plugin) {

		wanted = new ArrayList<Player>();

		requests = new HashMap<Player, Player>();

		this.plugin = plugin;

		if(firstTime){

			updateWantedConfig("set", null);

			updateRequestConfig("add", null);

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
		updateWantedConfig("set", p.getName());

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
	 * Gets the list of the wanted players.
	 * 
	 * @return The list containing the wanted players.
	 */
	public ArrayList<Player> getWanted(){

		//TODO add getting usernames from the config list of wanted players
		return wanted;

	}

	/**
	 * Checks if the player is wanted or not.
	 * 
	 * @param p The player to check.
	 * @return True if the player is wanted, false otherwise.
	 */
	public boolean isWanted(Player p){

		for(int i = 0; i < wanted.size(); i++){

			if(wanted.get(i).equals(p)){

				return true;

			}

		}

		return false;

	}

	/**
	 * Adds a frisking request.
	 * 
	 * @param requester The player requesting to frisk.
	 * @param requested The player being requested.
	 */
	public void addRequest(Player requester, Player requested){

		requests.put(requester, requested);
		updateRequestConfig("add", null);

	}

	/**
	 * Removes a frisking request.
	 * 
	 * @param requester The player requesting to frisk.
	 * @param requested The player being requested.
	 */
	public void removeRequest(Player requester, Player requested){

		requests.remove(requester);
		updateRequestConfig("remove", requester.getName() + " - " + requested.getName());

	}

	/**
	 * Tells if a player has a frisk request or not.
	 * 
	 * @param requester The player requesting to frisk.
	 * @return True if the player has a request, false otherwise.
	 */
	public boolean hasRequest(Player requester){

		if(requests.containsKey(requester)){

			return true;

		}

		return false;

	}

	/**
	 * Updates the configuration file which contains the list of all the wanted players
	 * 
	 * @param operation The operation (set a player or remove a player) to perform on the wanted file.
	 * @param playerName The name of the player to do the operation on
	 */
	private void updateWantedConfig(String operation, String playerName) {

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

				writer.write(playerName);
				writer.newLine();

			} else if(operation.equalsIgnoreCase("remove")){

				removeLine(wantedFile, playerName);

			} else {

				writer.close();

				throw new IllegalArgumentException("Wrong argument used in WantedPlayer.updateWantedConfig() method!");

			}

			writer.close();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	/**
	 * Updates the configuration file which contains the list of all the requests
	 * 
	 * @param operation The operation (add a request or remove a request) to perform on the request file.
	 * @param requestToRemove Only applies to the remove operation. The request to remove from the file.
	 */
	private void updateRequestConfig(String operation, String requestToRemove) {

		File requestFile = new File(plugin.getDataFolder(), "/data/requests.txt");

		if(!requestFile.exists()) {

			try {

				requestFile.getParentFile().mkdir();
				requestFile.createNewFile(); //Creates the new file if it does not already exist

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

			reader = new BufferedReader(new FileReader(requestFile));

			if(requests.isEmpty()){

				String name = null;

				while((name = reader.readLine()) != null){

					String requester = name.split("-")[0].trim();
					String requested = name.split("-")[1].trim();

					requests.put((Player)Bukkit.getOfflinePlayer(requester), (Player)Bukkit.getOfflinePlayer(requested));

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

			writer = new BufferedWriter(new FileWriter(requestFile));

			if(operation.equalsIgnoreCase("add")){

				for(int i = 0; i < requests.size(); i++){

					Player[] requested = new Player[requests.size()];
					Player[] requesters = new Player[requests.size()];

					writer.write(requests.keySet().toArray(requesters)[i].getName() + " - " + requests.values().toArray(requested)[i].getName());

					writer.newLine();

				}

			} else if(operation.equalsIgnoreCase("remove")){

				removeLine(requestFile, requestToRemove);

			} else {

				writer.close();

				throw new IllegalArgumentException("Wrong argument used in WantedPlayer.updateRequestConfig() method!");

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
