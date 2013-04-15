package friskstick.cops.testingSources;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import friskstick.cops.plugin.FriskStick;

public class ComplyCommand implements CommandExecutor {

	private boolean hasComplied;
	private FriskStick plugin;

	/**
	 * Constructor
	 */
	public ComplyCommand(FriskStick plugin) {

		this.plugin = plugin;
		hasComplied = false;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		Player player = null;

		if(sender instanceof Player) {

			player = (Player)sender;

		}

		if(player == null) {

			sender.sendMessage("You cannot run this command in the console!");

		} else {

			if(commandLabel.equalsIgnoreCase("comply")) {

				if(isTargeted(plugin.getServer().getPlayer(player.getName()))) {



				} else {

					player.sendMessage(ChatColor.RED + "You are not being frisked!");

				}

			}

		}

		return false;

	}

	/**
	 * Chacks if the player has complied
	 * @return true if the player complied/agreed ot get frisked
	 */
	public static boolean hasComplied() {

		return hasComplied();

	}

	/**
	 * Checks whether the player is being targeted to get frisked
	 * 
	 * @param player
	 * @return
	 */
	private boolean isTargeted(Player player) {



		return false;

	}

}
