package friskstick.cops.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.ArrayList;

import friskstick.cops.eventListeners.StickRightClickEvent;
import friskstick.cops.plugin.FriskStick;

/**
 * The class for having players agree/disagree to searches(frisks)
 */
public class ComplyCommand implements CommandExecutor {

	List<Player> reportedlist = new ArrayList<Player>();
	private FriskStick plugin;

	/**
	 * The constructor for {@link ComplyCommand this} class.
	 * 
	 * @param plugin The {@link FriskStick} object required for the command to function.
	 */
	public ComplyCommand(FriskStick plugin) {

		this.plugin = plugin; 

	}

	StickRightClickEvent rightclick = new StickRightClickEvent(plugin);

	/**
	 * The method that, in this case, executes the '/comply' command.
	 */
	public boolean onCommand(CommandSender sender, Command smd, String commandLabel, String[] args) {

		Player player = null;

		if(sender instanceof Player) {

			player = (Player)sender;

		}

		if(player == null) {

			sender.sendMessage("You cannot run this command in the console!");

		} else {

			if(commandLabel.equalsIgnoreCase("comply")) {

				if(rightclick.isWaiting){

					rightclick.hasComplied = true;

				}

			}

		}

		return false;

	}

}
