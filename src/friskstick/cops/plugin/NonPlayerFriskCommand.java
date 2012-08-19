package friskstick.cops.plugin;

/*import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
*/

public class NonPlayerFriskCommand /*extends JavaPlugin*/{
	/*int index = 0;
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(commandLabel.equalsIgnoreCase("frisk")){
			if(args.length == 0){
				sender.sendMessage("Usage: /frisk <playername>");
			}else if(args.length == 1){
				if(sender.hasPermission("friskstick.chat")){
					PlayerInventory inventory = this.getServer().getPlayer(args[0]).getInventory();
					boolean found = false;
					for(String drug: getConfig().getStringList("drug-ids")){
						if(drug.contains(":")){
							String firsthalf = drug.split(":")[0];
							String lasthalf = drug.split(":")[1];
							for(int i = 1; i <= getConfig().getInt("amount-to-search-for"); i++){
								if(inventory.contains(new ItemStack(Integer.parseInt(firsthalf), i, Short.parseShort(lasthalf)))){
									inventory.removeItem(new ItemStack(Integer.parseInt(firsthalf), 2305, Short.parseShort(lasthalf)));
									sender.sendMessage(getConfig().getString("cop-found-msg").replaceAll("&", "§").replaceAll("%itemname%", getConfig().getStringList("drug-names").toArray()[index].toString()).replaceAll("%sender%", this.getServer().getPlayer(args[0]).getName()));
									this.getServer().getPlayer(args[0]).sendMessage(getConfig().getString("sender-found-msg").replaceAll("&", "§").replaceAll("%sender%", sender.getName()).replaceAll("%itemname%", getConfig().getStringList("drug-names").toArray()[index].toString()));
									if(sender.hasPermission("friskstick.jail")){
										jail(this.getServer().getPlayer(args[0]).getName());
									}
									found = true;
								}
							}
						}else{
							if(inventory.contains(Integer.parseInt(drug))){
								int drugid = Integer.parseInt(drug);
								inventory.removeItem(new ItemStack(drugid, 2305));
								sender.sendMessage(getConfig().getString("cop-found-msg").replaceAll("&", "§").replaceAll("%itemname%", getConfig().getStringList("drug-names").toArray()[index].toString()).replaceAll("%sender%", this.getServer().getPlayer(args[0]).getName()));
								this.getServer().getPlayer(args[0]).sendMessage(getConfig().getString("sender-found-msg").replaceAll("&", "§").replaceAll("%sender%", sender.getName()).replaceAll("%itemname%", getConfig().getStringList("drug-names").toArray()[index].toString()));
								if(sender.hasPermission("friskstick.jail")){
									jail(this.getServer().getPlayer(args[0]).getName());
								}
								found = true;
							}
						}
						index++;
					}
					index = 0;
					if(!found){
						sender.sendMessage(getConfig().getString("cop-not-found-msg").replaceAll("&", "§").replaceAll("%sender%", this.getServer().getPlayer(args[0]).getName()));
						this.getServer().getPlayer(args[0]).sendMessage(getConfig().getString("sender-not-found-msg").replaceAll("&", "§").replaceAll("%sender%", sender.getName()));
					}
				}
			}
			return true;
		}
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
