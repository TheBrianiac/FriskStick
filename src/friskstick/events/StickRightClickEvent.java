package friskstick.events;

import friskstick.main.Frisk;
import friskstick.main.FriskStick;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class StickRightClickEvent implements Listener {

    private FriskStick plugin;

    public StickRightClickEvent(FriskStick plugin) {

        this.plugin = plugin;

    }

    @EventHandler
    public void friskEvent(PlayerInteractEntityEvent event) {

        if (event.getRightClicked() instanceof Player) {

            Player frisked = (Player) event.getRightClicked();
            Player cop = event.getPlayer();

            if (event.getPlayer().getItemInHand().getType() == Material.STICK) {

                if (event.getPlayer().hasPermission("friskstick.frisk") && !frisked.hasPermission("friskstick.bypass")) {

                    if (plugin.getConfig().getString("frisk-method").equalsIgnoreCase("both") || plugin.getConfig().getString("frisk-method").equalsIgnoreCase("right-click")) {

                        if (!plugin.playerDataInstance.isPlayerOnTheRun(frisked)) {

                            if (!plugin.playerDataInstance.isPlayerBeingFrisked(frisked)) {

                                plugin.playerDataInstance.setPlayerBeingFrisked(frisked);

                                new Frisk(plugin).friskPlayer(frisked, cop, false);

                            } else {

                                cop.sendMessage(ChatColor.RED + "That player is already being frisked!");

                            }

                        }

                    }

                }

            }

        }

    }

}
