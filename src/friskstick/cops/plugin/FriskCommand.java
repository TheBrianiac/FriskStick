package friskstick.cops.plugin;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

/*
 * 
 * This is where the command for frisk will be implemented, making it easier to organize the program.
 * 
 */

public class FriskCommand {
	
	Player p;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("frisk")){ 
			
			if(sender.hasPermission("friskstick.chat.frisk")) {
				
				if(args.length == 0) {
					
					p.sendMessage("Usage: /frisk <playername>");
					
				} else if (args.length == 1) {
					
					PlayerInventory i = p.getServer().getPlayer(args[1]).getInventory();
					boolean found = false;
					
					for(String drug:getConfig().getStringList("drug-ids")) {
						
						if(drug.contains(" : ")) {
							
							String firsthalf = drug.split(":")[0];
							String lasthalf = drug.split(":")[1];
							
						}
						
					}
					
				}
				
			}
			
		} 
		return false; 
	}
	
}
