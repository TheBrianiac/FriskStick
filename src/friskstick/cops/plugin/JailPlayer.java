package friskstick.cops.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class JailPlayer extends JavaPlugin {


	/**
	 * 
	 * Jails the specified player based on what is specified in the configuration. Only works with Essentials.
	 * @param name The player to be jailed's name.
	 */

	public void jail(String name) {

		if(this.getConfig().getBoolean("auto-jail")) {

			if(this.getServer().getPluginManager().isPluginEnabled("Essentials")) {

				if(this.getConfig().getInt("time-in-jail") > 0) {

					this.getServer().dispatchCommand(getServer().getConsoleSender(), "jail " + name + " " + this.getConfig().getString("jail-name") + " " + (this.getConfig().getInt("time-in-jail") + 1));

				} else if(this.getConfig().getInt("time-in-jail") == -1) {

					this.getServer().dispatchCommand(getServer().getConsoleSender(), "jail " + name + " " + this.getConfig().getString("jail-name"));

				}

			}

		}

	}

}
