package friskstick.cops.plugin;

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PluginUpdateCheck implements Listener {
	
	private int recentVersion = 0;
	private int currentVersion = 0;
	private String files = "http://dev.bukkit.org/server-mods/friskstick/";
	private PluginDescriptionFile pdf;

	public PluginUpdateCheck(FriskStick plugin) {
		
		pdf = plugin.getDescription();
		
	}

	public String getCurrentVersion() {
		
		Logger logger = Logger.getLogger("Minecraft");
		
		try {
			
			URL filepage = new URL(files);
			BufferedReader reader = new BufferedReader(new InputStreamReader(filepage.openStream()));
			String versions;
			String currentVersion = "";
			
			while((versions = reader.readLine()) != null) {
				
				if(versions.trim().equalsIgnoreCase("<dt>Recent files</dt>")) {
					
					currentVersion = removeTags(reader.readLine());
					
				}
				
			}
			
			reader.close();
			
			if(currentVersion.equalsIgnoreCase("")) {
				
				logger.log(Level.WARNING, "Could not check for updates!");
				
			} else {
				
				String[] fileVersions = currentVersion.split("A: ");
				return fileVersions[1].trim();
				
			}
			
		} catch(Exception e) {
			
			logger.log(Level.WARNING, "Could not check for updates!");
			
		}
		
		return null;
		
	}
	
	private String removeTags(String HTMLcode) {
		
		String removedTags = "";
		boolean isTag = false;
		
		for(int i = 0; i < HTMLcode.length(); i++) {
			
			if(HTMLcode.charAt(i) == '<') {
				
				isTag = true;
				
			} else if(HTMLcode.charAt(i) == '>') {
				
				if(!isTag) {
					
					removedTags += HTMLcode.charAt(i);
					
				} else {
					
					isTag = false;
					
				}
				
			} else {
				
				if(!isTag) {
					
					removedTags += HTMLcode.charAt(i);
					
				}
				
			}
			
		}
		
		return removedTags;
		
	}
	
	public void checkForUpdates() {
		
		String recent = this.getCurrentVersion().split(".")[2].trim();
		String current = pdf.getVersion().split("Alpha")[0].trim();
		StringBuilder rc = new StringBuilder();
		StringBuilder cc = new StringBuilder();
		
		for(int i = 0; i < recent.split(".").length; i++) {
			
			rc.append(recent.split(".")[i]);
			
		}
		
		for(int i = 0; i < current.split(".").length; i++) {
			
			cc.append(current.split(".")[i]);
			
		}
		
		recentVersion = Integer.parseInt(rc.toString());
		currentVersion = Integer.parseInt(cc.toString());
		
	}
	
	@EventHandler
	public void checkJoin(PlayerJoinEvent event) {
		
		this.checkForUpdates();
		
		if(currentVersion != 0 || recentVersion != 0) {
			
			if(currentVersion < recentVersion) {
				
				Player player = event.getPlayer();
				
				if(player.hasPermission("friskstick.notify")) {
					
					player.sendMessage(ChatColor.DARK_AQUA + "FriskStick has been updated to: " + this.getCurrentVersion());
					
				}
				
			}
			
		}
		
	}
	
}
