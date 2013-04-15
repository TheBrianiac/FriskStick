package friskstick.cops.eventListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.Timer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import friskstick.cops.plugin.FriskStick;
import friskstick.cops.plugin.JailPlayer;

/*
 * This class is currently being modified to introduce a beatdown mode. 
 * In the future, I think we should have a section in the config to keep track of what players are on the run.
 */

/**
 * The class that is responsible for handling a right-click with a stick (in other words, frisking someone with a stick). 
 */
public class StickRightClickEvent implements Listener {

	private FriskStick plugin;

	int time = 0;

	/**
	 * The boolean that tells whether or not the player can move.
	 */
	public boolean isStopped = false;

	/**
	 * The boolean that tells whether or not the plugin is waiting for a response from the player.
	 */
	public boolean isWaiting = false;

	/**
	 * The boolean that tells whether or not the player has complied to the cop's request to frisk.
	 */
	public boolean hasComplied = false;

	/**
	 * The boolean that tells whether or not the player is on the run.
	 */
	public boolean isRunning = false;

	private Timer t;

	/**
	 * The constructor for {@link Stick this} class.
	 * 
	 * @param plugin The {@link FriskStick} object required for the stick frisking to function.
	 */
	public StickRightClickEvent(FriskStick plugin) {

		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

	}

	private int index = 0;
	private JailPlayer jailed = new JailPlayer(plugin);

	/**
	 * The method that detects if a player frisks someone with a stick.
	 * 
	 * @param event The event for Bukkit to handle.
	 */
	@SuppressWarnings("deprecation")
	@EventHandler
	public void friskStickPlayer(PlayerInteractEntityEvent event) {

		if(event.getRightClicked() instanceof Player && event.getPlayer().getItemInHand().getType() == Material.STICK) {

			Player frisked = (Player)event.getRightClicked();
			Player cop = event.getPlayer();

			if(cop.hasPermission("friskstick.use")) {

				if(!frisked.hasPermission("friskstick.bypass")) {

					isWaiting = true;
					isStopped = true;
					time = 0;

					t = new Timer(1000, new ActionListener(){

						public void actionPerformed(ActionEvent event) {

							time++;

							if(time == 5) {

								isStopped = false;
								t.stop();

							}

						}

					});

					t.start();

					frisked.sendMessage(ChatColor.RED + cop.getName() + " is trying to frisk you! Type /comply to accept, or try to make a run for it!");

					while(isWaiting){

						if(hasComplied){

							isWaiting = false;

						}

					}

					if(hasComplied){

						PlayerInventory inventory = frisked.getInventory();
						boolean found = false;

						for(String item : plugin.getConfig().getStringList("item-ids")) {

							if(item.contains(":")) {

								String firsthalf = item.split(":")[0];
								String lasthalf = item.split(":")[1];

								for(int i = 1; i <= plugin.getConfig().getInt("amount-to-search-for"); i++) {

									if(inventory.contains(new ItemStack(Integer.parseInt(firsthalf), i, Short.parseShort(lasthalf)))) {

										Iterator<Integer> iter = inventory.all(new ItemStack(Integer.parseInt(firsthalf), i, Short.parseShort(lasthalf))).keySet().iterator();
										ItemStack[] contents = inventory.getContents();

										while(iter.hasNext()) {

											cop.getInventory().addItem(new ItemStack(contents[iter.next()]));
											cop.updateInventory();

										}

										inventory.removeItem(new ItemStack(Integer.parseInt(firsthalf), 2305, Short.parseShort(lasthalf)));

										cop.sendMessage(plugin.getConfig().getString("cop-found-msg").replaceAll("&", "§").replaceAll("%itemname%", plugin.getConfig().getStringList("item-names").toArray()[index].toString()).replaceAll("%player%", frisked.getName()));
										frisked.sendMessage(plugin.getConfig().getString("player-found-msg").replaceAll("&", "§").replaceAll("%cop%", cop.getName()).replaceAll("%itemname%", plugin.getConfig().getStringList("item-names").toArray()[index].toString()));

										if(cop.hasPermission("friskstick.jail")) {

											jailed.jailPlayer(frisked.getName(), cop);

										}

										found = true;

									}

								}

							} else {

								if(inventory.contains(Integer.parseInt(item))) {

									int itemid = Integer.parseInt(item);
									Iterator<Integer> iter = inventory.all(itemid).keySet().iterator();
									ItemStack[] contents = inventory.getContents();

									while(iter.hasNext()) {

										cop.getInventory().addItem(new ItemStack(contents[iter.next()]));

									}

									/*
									 * This is where the /frisk command exception points to
									 */
									inventory.removeItem(new ItemStack(itemid, 2305));

									cop.sendMessage(plugin.getConfig().getString("cop-found-msg").replaceAll("&", "§").replaceAll("%itemname%", plugin.getConfig().getStringList("item-names").toArray()[index].toString()).replaceAll("%player%", frisked.getName()));
									frisked.sendMessage(plugin.getConfig().getString("player-found-msg").replaceAll("&", "§").replaceAll("%cop%", cop.getName()).replaceAll("%itemname%", plugin.getConfig().getStringList("item-names").toArray()[index].toString()));

									if(cop.hasPermission("friskstick.jail")) {

										jailed.jailPlayer(frisked.getName(), cop);

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

						cop.sendMessage(ChatColor.GOLD + frisked.getName() + " is now on the run! Beat them as much as you can!");
						frisked.sendMessage(ChatColor.RED + "You are now on the run! Beware of the cop who frisked you!");
						isRunning = true;

					}

				} else {

					cop.sendMessage(ChatColor.DARK_RED + "That player cannot be frisked!");

				}

			} else {

				cop.sendMessage(ChatColor.DARK_RED + "You don't have permission to frisk anyone!");

			}

		}

	}

	@EventHandler
	public void checkMove(PlayerMoveEvent e){

		if(isWaiting){

			isWaiting = false;

		} else if(isStopped) {

			e.setCancelled(true);

		}

	}

	@EventHandler
	public void beatPlayer(EntityDamageByEntityEvent e){

		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){

			if(isRunning){

				Player cop = (Player)e.getDamager();
				Player beaten = (Player)e.getEntity();

			}

		}

	}

}
