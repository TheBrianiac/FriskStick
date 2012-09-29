package friskstick.cops.plugin;

public class JailPlayer{
	
	FriskStick plugin;
	
	public JailPlayer(FriskStick plugin){
		this.plugin = plugin;
	}

	/**
	 * 
	 * Jails the specified player based on what is specified in the configuration. Only works with Essentials.
	 * 
	 * @param name The player to be jailed's name.
	 * 
	 */

	public void jail(String name) {

		/*if(plugin.getConfig().getBoolean("auto-jail")) {

			if(plugin.getServer().getPluginManager().isPluginEnabled("Essentials")) {

				if(plugin.getConfig().getInt("time-in-jail") > 0) {

					plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "jail " + name + " " + plugin.getConfig().getString("jail-name") + " " + (plugin.getConfig().getInt("time-in-jail") + 1));

				} else if(plugin.getConfig().getInt("time-in-jail") == -1) {

					plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "jail " + name + " " + plugin.getConfig().getString("jail-name"));

				}

			}

		}*/

	}

}
