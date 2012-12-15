package friskstick.cops.plugin;

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
	 */
	public void jail(String name) {

		if(plugin.getConfig().getBoolean("auto-jail")) {

			if(plugin.getServer().getPluginManager().isPluginEnabled("Essentials")) {

				if(plugin.getConfig().getInt("time-in-jail") > 0) {

					plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "jail " + name + " " + plugin.getConfig().getString("jail-name") + " " + (plugin.getConfig().getInt("time-in-jail") + 1));

				} else if(plugin.getConfig().getInt("time-in-jail") == -1) {

					plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "jail " + name + " " + plugin.getConfig().getString("jail-name"));

				}

			}

		}

	}

}
