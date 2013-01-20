package friskstick.cops.eventListeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import friskstick.cops.plugin.FriskStick;

/**
 * Handler for update checking on player join(op)
 */
public class PlayerJoinEventUpdateCheck extends FriskStick implements Listener{

	public static boolean updateNeeded;

	public PlayerJoinEventUpdateCheck(FriskStick plugin) {

	}

	/**
	 * Player Login EventHandler
	 */
	@EventHandler
	public void playerLoginUpdateCheck(PlayerJoinEvent e) {

		Player player = e.getPlayer();

		if(player.isOp() && updateNeeded) {

			player.sendMessage(ChatColor.DARK_AQUA + "********FriskStick Notice********");
			player.sendMessage(ChatColor.YELLOW + "FriskStick is due for an update!! Go to " + ChatColor.RED + "http://dev.bukkit.org/server-mods/friskstick/" + ChatColor.YELLOW + " to download");
			player.sendMessage(ChatColor.GOLD + "Please download the plugin and put it in the \"plugins\" folder in your server directory. Please do NOT just reload your server. To make sure that the plugin is completely loaded properly, please RESTART your server.");

		}

	}

}
