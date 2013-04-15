package friskstick.cops.testingSources;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import friskstick.cops.plugin.FriskStick;
import friskstick.cops.plugin.JailPlayer;

/**
 * GOING TO REWRITE THE CODE TO SEE IF I CAN FIX/CHANGE SOMETHING
 */
public class FriskCommand implements CommandExecutor {

	private FriskStick plugin;
	private JailPlayer jailed = new JailPlayer(plugin);

	/**
	 * The constructor for {@link FriskCommand this} class.
	 * 
	 * @param plugin The {@link FriskStick} object required for the command to function.
	 */
	public FriskCommand(FriskStick plugin) {

		this.plugin = plugin;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

		Player player = null;

		if(sender instanceof Player) {

			player = (Player)sender;

		}

		if(player == null) {

			sender.sendMessage(ChatColor.RED + "You must run this command in game!");

		} else {

			if(commandLabel.equalsIgnoreCase("frisk")) { //If the command is /frisk

				if(player.hasPermission("frisktick.chat")) { //Check if the player has the proper permissions

					if(args.length == 0 || args.length > 1) { //Check if the player typed the command properly

						player.sendMessage(ChatColor.RED + "/frisk <playerName>"); //

					} else if(args.length == 1) {

						Player friskedPlayer = plugin.getServer().getPlayer(args[0]);

						if(!friskedPlayer.hasPermission("friskstick.bypass")) {

							friskedPlayer.sendMessage(player.getName() + " is trying to frisk you! Use /comply to agree to be frisked.");



						} else {

							player.sendMessage(ChatColor.RED + "You cannot frisk this player!");

						}

					}

				} else {

					player.sendMessage(ChatColor.RED + "You don't have permission!");

				}

			}

		}

		return false;

	}

}
