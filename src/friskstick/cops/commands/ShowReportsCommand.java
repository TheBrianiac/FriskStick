package friskstick.cops.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import friskstick.cops.plugin.FriskStick;

/**
 * The class that is responsible for handling the '/showreports' command. 
 */
public class ShowReportsCommand implements CommandExecutor {

	private FriskStick plugin;
	private ReportCommand rc = new ReportCommand(plugin);

	/**
	 * The constructor for {@link ShowReportsCommand this} class.
	 * 
	 * @param plugin The {@link FriskStick} object required for the command to function.
	 */
	public ShowReportsCommand(FriskStick plugin) {

		this.plugin = plugin;

	}

	/**
	 * The method that, in this case, executes the '/showreports' command.
	 */
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player player = null;

		if(sender instanceof Player) {

			player = (Player)sender;

		}

		if(player != null) {

			if(label.equalsIgnoreCase("showreports")) {

				if(player.hasPermission("friskstick.report.show") || player.isOp()) {

					for(Player reported : rc.getReported()) {

						player.sendMessage(ChatColor.DARK_RED + "[Reported]" + ChatColor.RED + reported.getName());

					}

				}

			}

		} else {

			sender.sendMessage(ChatColor.RED + "This command cannot be run in the console!");

		}

		return false;

	}

}
