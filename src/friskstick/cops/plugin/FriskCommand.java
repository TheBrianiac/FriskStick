package friskstick.cops.plugin;

/*import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
*/
/*
 * 
 * This is where the command for frisk will be implemented, making it easier to organize the program.
 * 
 */

public class FriskCommand extends FriskStick{
	/*int index = 0;
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(commandLabel.equalsIgnoreCase("frisk")){ // If the player typed /frisk then do the following...
			Player player = (Player)sender;
			if(args.length == 0){
				player.sendMessage("Usage: /frisk <playername>");
			}else if(args.length == 1){
				Player frisked = this.getServer().getPlayer(args[0]);
				if(player.hasPermission("friskstick.chat")){
					PlayerInventory inventory = frisked.getInventory();
					boolean found = false;
					for(String drug: getConfig().getStringList("drug-ids")){
						if(drug.contains(":")){
							String firsthalf = drug.split(":")[0];
							String lasthalf = drug.split(":")[1];
							for(int i = 1; i <= getConfig().getInt("amount-to-search-for"); i++){
								ItemStack[] contents = inventory.getContents();
								if(inventory.contains(new ItemStack(Integer.parseInt(firsthalf), i, Short.parseShort(lasthalf)))){
									player.getInventory().addItem(new ItemStack(contents[inventory.first(new ItemStack(Integer.parseInt(firsthalf), i, Short.parseShort(lasthalf)))]));
									inventory.removeItem(new ItemStack(Integer.parseInt(firsthalf), 2305, Short.parseShort(lasthalf)));
									player.sendMessage(getConfig().getString("cop-found-msg").replaceAll("&", "§").replaceAll("%itemname%", getConfig().getStringList("drug-names").toArray()[index].toString()).replaceAll("%player%", this.getServer().getPlayer(args[0]).getName()));
									this.getServer().getPlayer(args[0]).sendMessage(getConfig().getString("player-found-msg").replaceAll("&", "§").replaceAll("%player%", player.getName()).replaceAll("%itemname%", getConfig().getStringList("drug-names").toArray()[index].toString()));
									if(player.hasPermission("friskstick.jail")){
										jail(this.getServer().getPlayer(args[0]).getName());
									}
									found = true;
								}
							}
						}else{
							if(inventory.contains(Integer.parseInt(drug))){
								int drugid = Integer.parseInt(drug);
								ItemStack[] contents = inventory.getContents();
								player.getInventory().addItem(new ItemStack(contents[inventory.first(drugid)]));
								inventory.removeItem(new ItemStack(drugid, 2305));
								player.sendMessage(getConfig().getString("cop-found-msg").replaceAll("&", "§").replaceAll("%itemname%", getConfig().getStringList("drug-names").toArray()[index].toString()).replaceAll("%player%", this.getServer().getPlayer(args[0]).getName()));
								this.getServer().getPlayer(args[0]).sendMessage(getConfig().getString("player-found-msg").replaceAll("&", "§").replaceAll("%player%", player.getName()).replaceAll("%itemname%", getConfig().getStringList("drug-names").toArray()[index].toString()));
								if(player.hasPermission("friskstick.jail")){
									jail(this.getServer().getPlayer(args[0]).getName());
								}
								found = true;
							}
						}
						index++;
					}
					index = 0;
					if(!found){
						player.sendMessage(getConfig().getString("cop-not-found-msg").replaceAll("&", "§").replaceAll("%player%", this.getServer().getPlayer(args[0]).getName()));
						this.getServer().getPlayer(args[0]).sendMessage(getConfig().getString("player-not-found-msg").replaceAll("&", "§").replaceAll("%player%", player.getName()));
						if(player.getHealth() >= 2){
							player.setHealth(player.getHealth() - 2);
						}else{
							player.setHealth(0);
						}
					}
				}
			}
			return true;
		} //If this has happened the function will break and return true. if this hasn't happened the a value of false will be returned.
		return false; 
	}
	public void jail(String name){
		if(this.getConfig().getBoolean("auto-jail")){
			if(this.getServer().getPluginManager().isPluginEnabled("Essentials")){
				if(getConfig().getInt("time-in-jail") > 0){
					this.getServer().dispatchCommand(this.getServer().getConsoleSender(), "jail " + name + " " + this.getConfig().getString("jail-name") + " " + (this.getConfig().getInt("time-in-jail") + 1));
				}else if(getConfig().getInt("time-in-jail") == -1){
					this.getServer().dispatchCommand(this.getServer().getConsoleSender(), "jail " + name + " " + this.getConfig().getString("jail-name"));
				}
			}
		}
	}
	*/
	
}
