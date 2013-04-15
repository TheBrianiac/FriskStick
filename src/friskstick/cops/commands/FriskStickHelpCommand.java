package friskstick.cops.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import friskstick.cops.plugin.FriskStick;

public class FriskStickHelpCommand implements CommandExecutor {

	public FriskStickHelpCommand(FriskStick plugin) {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		Player player = null;

		if(sender instanceof Player) {

			player = (Player)sender;

		}

		/**
		 * Check if the command is run my a player(if not, send error message)
		 */
		if(player == null) {

			sender.sendMessage(ChatColor.RED + "Error: You cannot run this command in the console!");

		} else {

			if(commandLabel.equalsIgnoreCase("friskstick")) {

				if (args.length == 0) {

					if(player.hasPermission("friskstick.help") || player.isOp()) {

						player.sendMessage(ChatColor.AQUA + "***************FriskStick Help***************");
						player.sendMessage(ChatColor.DARK_AQUA + "This plugin frisks players for illegal items.");
						player.sendMessage(ChatColor.DARK_AQUA + "Check out the bukkitdev page for more info at:");
						player.sendMessage(ChatColor.DARK_AQUA + "http://dev.bukkit.org/server-mods/friskstick/");
						player.sendMessage(ChatColor.LIGHT_PURPLE + "Use /FriskStick 1 for more pages and help");

					} else {

						player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");

					}

				} else if(args.length == 1) {

					if(commandLabel.equalsIgnoreCase("friskstick")) {

						if(player.hasPermission("friskstick.help")) {

							if(args[0].equalsIgnoreCase("1")) {

								player.sendMessage(ChatColor.AQUA + "**********FriskStick Page 1/2**********");
								player.sendMessage(ChatColor.AQUA + "To frisk a player use /frisk <playerName>");
								player.sendMessage(ChatColor.AQUA + "To report a player use /report <playerName>");
								player.sendMessage(ChatColor.AQUA + "To request assistance use /assist"); //TODO Implement assistance
								player.sendMessage(ChatColor.LIGHT_PURPLE + "Use /FriskStick 2 for more pages and help");

							}

							if(args[0].equalsIgnoreCase("2")) {

								player.sendMessage(ChatColor.AQUA + "**********FriskStick Page 2/2**********");
								player.sendMessage(ChatColor.AQUA + "");
								player.sendMessage(ChatColor.AQUA + "");
								player.sendMessage(ChatColor.AQUA + "");
								player.sendMessage(ChatColor.AQUA + "");

							}

						} else {

							player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");

						}

					}

				}

			}

		}

		return false;
	}

}
