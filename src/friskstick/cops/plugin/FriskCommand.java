package friskstick.cops.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/*
 * 
 * This is where the command for frisk will be implemented, making it easier to organize the program.
 * 
 */

public class FriskCommand {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("frisk")){ // If the player typed /frisk then do the following...
			// doSomething
			return true;
		} //If this has happened the function will break and return true. if this hasn't happened the a value of false will be returned.
		return false; 
	}
	
}
