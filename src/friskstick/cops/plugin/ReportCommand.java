package friskstick.cops.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportCommand implements CommandExecutor {

	private FriskStick plugin;

	public ReportCommand(FriskStick plugin) {

		this.plugin = plugin; 

	}

	@Override
	public boolean onCommand(CommandSender sender, Command smd, String commandLabel, String[] args) {

		Player player = (Player)sender;

		if(player == null) {

			sender.sendMessage("You cannot run this command in the console!");

		} else {

			if(commandLabel.equalsIgnoreCase("report")) {

				if(player.hasPermission("friskstick.report")) {

					if(args.length == 0) {

						player.sendMessage("Usage: /report <player>");

					} else if(args.length == 1) {

						Player reported = plugin.getServer().getPlayer(args[0]);
						
					}

				}
			}

		}

		return false;

	}

}
