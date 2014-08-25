package friskstick.main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is responsible for frisking players.
 */
public class Frisk {

    private FriskStick plugin;

    private int timesRun = 0;

    private Random random = new Random();

    /**
     * Creates a new {@link Frisk} object.
     *
     * @param plugin An instance of the {@link FriskStick} class
     */
    public Frisk(FriskStick plugin) {

        this.plugin = plugin;

    }

    /**
     * Frisks a player and sends them to jail if proper conditions are met (frisk was successful and cop has permission).
     * NOTE: Does NOT determine if the player can bypass the frisking or if the cop has permission to frisk at all. It simply frisks the player.
     *
     * @param frisked The player being frisked
     * @param cop The cop frisking the player
     * @param continueFrisk Tell whether or not the frisk should continue (only set to false in beatdown mode)
     */
    public void friskPlayer(Player frisked, Player cop, boolean continueFrisk) {

        waitForCompliance(frisked, cop);

        if(continueFrisk) {

            List<Material> materialsFrisked = new ArrayList<Material>();

            PlayerInventory friskedInv = frisked.getInventory();
            PlayerInventory copInv = cop.getInventory();

            boolean friskSuccessful = false;

            // Loop through the items in the player's inventory
            for (ItemStack item : friskedInv.getContents()) {

                // Loop through the drug list in the config
                for (String drug : plugin.getConfig().getStringList("drugs")) {

                    // If the drug does not have metadata...
                    if (!drug.contains(":")) {

                        // If the item matches the drug...
                        if (item.getType() == Material.getMaterial(drug.toUpperCase())) {

                            // ...then confiscate the item from the player and give it to the cop
                            friskedInv.remove(item);
                            copInv.addItem(item);

                            // Send some messages their way, too, if the same type of drug hasn't been frisked already.
                            if(!materialsFrisked.contains(item.getType())) {

                                frisked.sendMessage(plugin.getConfig().getString("frisk-success-player-msg").replaceAll("&", "§").replaceAll("%cop%", cop.getName()).replaceAll("%drugname%",
                                        plugin.getConfig().getStringList("drug-names").get(plugin.getConfig().getStringList("drugs").indexOf(drug))));

                                cop.sendMessage(plugin.getConfig().getString("frisk-success-cop-msg").replaceAll("&", "§").replaceAll("%player%", frisked.getName()).replaceAll("%drugname%",
                                        plugin.getConfig().getStringList("drug-names").get(plugin.getConfig().getStringList("drugs").indexOf(drug))));

                            }

                            materialsFrisked.add(item.getType());

                            friskSuccessful = true;

                        }

                    // If the drug DOES contain metadata...
                    } else {

                        // Split the drug name at the colon (the character separating the name from the data)
                        String[] drugSplit = drug.split(":");

                        // Get the data of the item
                        MaterialData materialData = item.getData();

                        // THEN check if the item matches the drug
                        if (materialData.getItemType() == Material.getMaterial(drugSplit[0].toUpperCase()) && materialData.getData() == Byte.parseByte(drugSplit[1])) {

                            // If it does, confiscate the item from the player and give it to the cop
                            friskedInv.remove(item);
                            copInv.addItem(item);

                            // Send some messages their way, too, if the same type of drug hasn't been frisked already.
                            if(!materialsFrisked.contains(item.getType())) {

                                frisked.sendMessage(plugin.getConfig().getString("frisk-success-player-msg").replaceAll("&", "§").replaceAll("%cop%", cop.getName()).replaceAll("%drugname%",
                                        plugin.getConfig().getStringList("drug-names").get(plugin.getConfig().getStringList("drugs").indexOf(drug))));

                                cop.sendMessage(plugin.getConfig().getString("frisk-success-cop-msg").replaceAll("&", "§").replaceAll("%player%", frisked.getName()).replaceAll("%drugname%",
                                        plugin.getConfig().getStringList("drug-names").get(plugin.getConfig().getStringList("drugs").indexOf(drug))));

                            }

                            materialsFrisked.add(item.getType());

                            friskSuccessful = true;

                        }

                    }

                    // If the frisk was successful, break from the current for loop
                    if (friskSuccessful)
                        break;

                }

            }

            // If the frisk was successful AND the cop has proper perms AND Essentials is installed AND the config allows auto-jailing...
            if (friskSuccessful && cop.hasPermission("friskstick.jail") && plugin.getServer().getPluginManager().isPluginEnabled("Essentials") && plugin.getConfig().getBoolean("enable-auto-jailing")) {

                // Get the list of jails in the config
                List<String> jails = plugin.getConfig().getStringList("jails");

                // Check whether time-in-jail in the config is -1 or not
                boolean infiniteTimeInJail = plugin.getConfig().getInt("time-in-jail") == -1;

                // If it isn't, jail the player for the specified time in a jail randomly chosen from the list
                if (!infiniteTimeInJail)
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "jail " + frisked.getName() + " "
                            + jails.get(random.nextInt(jails.size())) + " " + plugin.getConfig().getInt("time-in-jail"));

                // If it is, jail the player indefinitely in a jail randomly chosen from the list
                else
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "jail " + frisked.getName() + " "
                            + jails.get(random.nextInt(jails.size())));

            // Otherwise, if the frisk wasn't successful...
            } else if (!friskSuccessful) {

                // Send some messages...
                frisked.sendMessage(plugin.getConfig().getString("frisk-failure-player-msg").replaceAll("&", "§").replaceAll("%cop%", cop.getName()));

                cop.sendMessage(plugin.getConfig().getString("frisk-failure-cop-msg").replaceAll("&", "§").replaceAll("%player%", frisked.getName()));

                // ...and damage the cop by the amount specified in the config.
                cop.damage(plugin.getConfig().getDouble("frisk-failure-dmg"));

            }

        }

    }

    /**
     * Waits for a certain amount of time before frisking
     *
     * @param frisked The player being frisked
     * @param cop The cop frisking the player
     */
    private void waitForCompliance(final Player frisked, final Player cop) {

        if(!plugin.playerDataInstance.isPlayerOnTheRun(frisked)) {

            final Location loc = frisked.getLocation();

            // Check every second (20 ticks) if the player has run or not
            new BukkitRunnable() {

                @Override
                public void run() {

                    if(frisked.getLocation().distance(loc) >= 10.0) {

                        plugin.playerDataInstance.setPlayerOnTheRun(frisked);
                        frisked.sendMessage(plugin.getConfig().getString("beatdown-player-msg").replaceAll("&", "§"));
                        cop.sendMessage(plugin.getConfig().getString("beatdown-cop-msg").replaceAll("&", "§").replaceAll("%player%", frisked.getName()));

                    }

                    // If the specified time has passed since waiting began, cancel the timer
                    if(timesRun == plugin.getConfig().getInt("time-before-frisking")) {

                        timesRun = 0;
                        this.cancel();
                        return;

                    }

                    timesRun++;

                }

            }.runTaskTimer(plugin, 0, 20);

        }

    }

}
