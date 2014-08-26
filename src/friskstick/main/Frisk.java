package friskstick.main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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
    private int drugCount = 0;

    private boolean complied = true;

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
     */
    public void friskPlayer(final Player frisked, final Player cop, boolean bypassComplianceWaiting) {

        if(!bypassComplianceWaiting)
            waitForCompliance(frisked, cop);

        // Schedule the frisking to take place after waiting to see if the player complied
        new BukkitRunnable() {

            @Override
            public void run() {

                if(complied) {

                    List<Material> materialsFrisked = new ArrayList<Material>();
                    List<Byte> dataOfMaterialsFrisked = new ArrayList<Byte>();

                    PlayerInventory friskedInv = frisked.getInventory();

                    boolean friskSuccessful = false;

                    List<ItemStack> drugs = new ArrayList<ItemStack>();

                    // Loop through the items in the player's inventory
                    for (ItemStack item : friskedInv.getContents()) {

                        if (item != null) {

                            // Loop through the drug list in the config
                            for (String drug : plugin.getConfig().getStringList("drugs")) {

                                // If the drug does not have metadata...
                                if (!drug.contains(" ")) {

                                    // If the item matches the drug...
                                    if (item.getType() == Material.matchMaterial(drug)) {

                                        // ...then confiscate the item from the player and add it to the list
                                        friskedInv.remove(item);
                                        drugs.add(item);

                                        // Send some messages their way, too, if the same type of drug hasn't been frisked already.
                                        if (!materialsFrisked.contains(item.getType())) {

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
                                    String[] drugSplit = drug.split(" ");

                                    // Get the data of the item
                                    MaterialData materialData = item.getData();

                                    // THEN check if the item matches the drug
                                    if (materialData.getItemType() == Material.matchMaterial(drugSplit[0]) && materialData.getData() == Byte.parseByte(drugSplit[1])) {

                                        // If it does, confiscate the item from the player and give it to the cop
                                        friskedInv.remove(item);
                                        drugs.add(item);

                                        // Send some messages their way, too, if the same type of drug hasn't been frisked already.
                                        if (!materialsFrisked.contains(item.getType()) || !dataOfMaterialsFrisked.contains(materialData.getData())) {

                                            frisked.sendMessage(plugin.getConfig().getString("frisk-success-player-msg").replaceAll("&", "§").replaceAll("%cop%", cop.getName()).replaceAll("%drugname%",
                                                    plugin.getConfig().getStringList("drug-names").get(plugin.getConfig().getStringList("drugs").indexOf(drug))));

                                            cop.sendMessage(plugin.getConfig().getString("frisk-success-cop-msg").replaceAll("&", "§").replaceAll("%player%", frisked.getName()).replaceAll("%drugname%",
                                                    plugin.getConfig().getStringList("drug-names").get(plugin.getConfig().getStringList("drugs").indexOf(drug))));

                                        }

                                        materialsFrisked.add(item.getType());
                                        dataOfMaterialsFrisked.add(materialData.getData());

                                        friskSuccessful = true;

                                    }

                                }

                            }

                        }

                    }

                    addDrugsToInv(cop, drugs);

                    // If the frisk was successful AND the cop has proper perms AND Essentials is installed AND the config allows auto-jailing...
                    if (friskSuccessful && cop.hasPermission("friskstick.jail") && plugin.getServer().getPluginManager().isPluginEnabled("Essentials") && plugin.getConfig().getBoolean("enable-auto-jailing")) {

                        Random random = new Random();

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

                        cop.sendMessage(ChatColor.GOLD + "Player jailed.");

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

        }.runTaskLater(plugin, bypassComplianceWaiting ? 0 : plugin.getConfig().getInt("time-before-frisking") * 20 + 5);

    }

    /**
     * Waits for a certain amount of time before frisking
     *
     * @param frisked The player being frisked
     * @param cop The cop frisking the player
     */
    private void waitForCompliance(final Player frisked, final Player cop) {

        if(!plugin.playerDataInstance.isPlayerOnTheRun(frisked) && plugin.getConfig().getBoolean("enable-beatdown")) {

            frisked.sendMessage(plugin.getConfig().getString("frisk-attempt-player-msg").replaceAll("&", "§").replaceAll("%cop%", cop.getName()).replaceAll("%time%",
                    Integer.toString(plugin.getConfig().getInt("time-before-frisking"))));

            cop.sendMessage(plugin.getConfig().getString("frisk-attempt-cop-msg").replaceAll("&", "§").replaceAll("%player%", frisked.getName()).replaceAll("%time%",
                    Integer.toString(plugin.getConfig().getInt("time-before-frisking"))));

            final Location loc = frisked.getLocation();

            // Check every second (20 ticks) if the player has run or not
            new BukkitRunnable() {

                @Override
                public void run() {

                    // If the player has run the specified number of blocks in the time allotted, they are on the run.
                    if(frisked.getLocation().distance(loc) >= plugin.getConfig().getDouble("beatdown-blocks")) {

                        plugin.playerDataInstance.setPlayerOnTheRun(frisked, cop);
                        frisked.sendMessage(plugin.getConfig().getString("on-run-player-msg").replaceAll("&", "§"));
                        cop.sendMessage(plugin.getConfig().getString("on-run-cop-msg").replaceAll("&", "§").replaceAll("%player%", frisked.getName()));

                        complied = false;

                        this.cancel();
                        return;

                    }

                    // If the specified time has passed since waiting began, cancel the timer
                    if(timesRun == plugin.getConfig().getInt("time-before-frisking")) {

                        this.cancel();
                        return;

                    }

                    timesRun++;

                }

            }.runTaskTimer(plugin, 0, 20);

        }

    }

    public void addDrugsToInv(final Player cop, final List<ItemStack> drugs) {

        new BukkitRunnable() {

            @Override
            public void run() {

                if(drugCount >= drugs.size()){

                    this.cancel();

                } else {

                    cop.getInventory().addItem(drugs.get(drugCount));
                    cop.updateInventory();

                    drugCount++;

                }

            }

        }.runTaskTimer(plugin, 0, 1);

    }

}
