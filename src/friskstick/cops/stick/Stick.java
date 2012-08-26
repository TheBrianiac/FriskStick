package friskstick.cops.stick;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import friskstick.cops.plugin.FriskStick;
import friskstick.cops.plugin.JailPlayer;

public class Stick implements Listener {

	public Stick(FriskStick plugin) {

		plugin.getServer().getPluginManager().registerEvents(this, plugin);

	}

	FriskStick plugin;
	int index = 0;
	JailPlayer jailed = new JailPlayer();

	@EventHandler
	public void friskStickPlayer(PlayerInteractEntityEvent event) {

		if(event.getRightClicked() instanceof Player && event.getPlayer().getItemInHand().getType() == Material.STICK) {

			Player frisked = (Player)event.getRightClicked();
			Player cop = event.getPlayer();

			if(cop.hasPermission("friskstick.use")) {

				if(!frisked.hasPermission("friskstick.bypass")) {

					PlayerInventory inventory = frisked.getInventory();
					boolean found = false;

					for(String drug: plugin.getConfig().getStringList("drug-ids")) {

						if(drug.contains(":")) {

							String firsthalf = drug.split(":")[0];
							String lasthalf = drug.split(":")[1];

							for(int i = 1; i <= plugin.getConfig().getInt("amount-to-search-for"); i++) {

								if(inventory.contains(new ItemStack(Integer.parseInt(firsthalf), i, Short.parseShort(lasthalf)))) {

									ItemStack[] contents = inventory.getContents();
									cop.getInventory().addItem(new ItemStack(contents[inventory.first(new ItemStack(Integer.parseInt(firsthalf), i, Short.parseShort(lasthalf)))]));
									inventory.removeItem(new ItemStack(Integer.parseInt(firsthalf), 2305, Short.parseShort(lasthalf)));
									cop.sendMessage(plugin.getConfig().getString("cop-found-msg").replaceAll("&", "§").replaceAll("%itemname%", plugin.getConfig().getStringList("drug-names").toArray()[index].toString()).replaceAll("%player%", frisked.getName()));
									frisked.sendMessage(plugin.getConfig().getString("player-found-msg").replaceAll("&", "§").replaceAll("%cop%", cop.getName()).replaceAll("%itemname%", plugin.getConfig().getStringList("drug-names").toArray()[index].toString()));

									if(cop.hasPermission("friskstick.jail")) {

										jailed.jail(frisked.getName());

									}

									found = true;

								}

							}

						} else {

							if(inventory.contains(Integer.parseInt(drug))) {

								int drugid = Integer.parseInt(drug);
								ItemStack[] contents = inventory.getContents();
								cop.getInventory().addItem(new ItemStack(contents[inventory.first(drugid)]));
								inventory.removeItem(new ItemStack(drugid, 2305));
								cop.sendMessage(plugin.getConfig().getString("cop-found-msg").replaceAll("&", "§").replaceAll("%itemname%", plugin.getConfig().getStringList("drug-names").toArray()[index].toString()).replaceAll("%player%", frisked.getName()));
								frisked.sendMessage(plugin.getConfig().getString("player-found-msg").replaceAll("&", "§").replaceAll("%cop%", cop.getName()).replaceAll("%itemname%", plugin.getConfig().getStringList("drug-names").toArray()[index].toString()));

								if(cop.hasPermission("friskstick.jail")) {

									jailed.jail(frisked.getName());

								}

								found = true;

							}

						}

						index++;

					}

					index = 0;

					if(!found) {

						cop.sendMessage(plugin.getConfig().getString("cop-not-found-msg").replaceAll("&", "§").replaceAll("%player%", frisked.getName()));
						frisked.sendMessage(plugin.getConfig().getString("player-not-found-msg").replaceAll("&", "§").replaceAll("%cop%", cop.getName()));

						if(cop.getHealth() >= 2) {

							cop.setHealth(cop.getHealth() - 2);

						} else {

							cop.setHealth(0);

						}

					}

				} else {

					cop.sendMessage("That player cannot be frisked!");

				}

			}

		}

	}

}
