package friskstick.cops.plugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * The class that is responsible for jailing the player, provided that Essentials is installed.
 */
public class JailPlayer {

	private FriskStick plugin;

	/**
	 * The constructor for {@link JailPlayer this} class.
	 * 
	 * @param plugin The {@link FriskStick} object required for the jailing feature to function.
	 */
	public JailPlayer(FriskStick plugin) {

		this.plugin = plugin;

	}

	/**
	 * Jails the specified player based on what is specified in the configuration. Only works with Essentials. At the moment, this method is disabled due to errors.
	 * 
	 * @param name The player to be jailed's name.
	 * @param cop The cop who jailed the player
	 */
	public void jailPlayer(String name, Player cop) {

		if(plugin.getConfig().getBoolean("auto-jail")) {

			if(plugin.getServer().getPluginManager().getPlugin("Essentials").isEnabled()) {

				if(plugin.getConfig().getInt("time-in-jail") > 0) {

					plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "jail " + name + " " + plugin.getConfig().getString("jail-name") + " " + (plugin.getConfig().getInt("time-in-jail") + 1));

				} else if(plugin.getConfig().getInt("time-in-jail") == -1) {

					plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "jail " + name + " " + plugin.getConfig().getString("jail-name"));

				} else {

					for(Player p : plugin.getServer().getOnlinePlayers()) {

						if(p.isOp() && p.isOnline()) {

							p.sendMessage(ChatColor.RED + "There is a problem with your jailtime setup in the config which doesn't allow cops to jail players! Please fix it.");

						}

					}

					cop.sendMessage(ChatColor.RED + "You failed to jail the suspect! This is an internal problem. Please urge your server administrator to fix the issue.");

				}

			}

		}

	}

}
