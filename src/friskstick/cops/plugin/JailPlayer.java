package friskstick.cops.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class JailPlayer extends JavaPlugin {

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
