package friskstick.cops.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import friskstick.cops.plugin.FriskStick;

public class FriskStickHelpCommand implements CommandExecutor {

	private FriskStick plugin;

	public FriskStickHelpCommand(FriskStick plugin) {

		this.plugin = plugin;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		return false;
	}

}
