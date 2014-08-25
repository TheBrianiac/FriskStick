package friskstick.events;

import friskstick.main.Frisk;
import friskstick.main.FriskStick;
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

        if(event.getRightClicked() instanceof Player) {

            if(event.getPlayer().getItemInHand().getType() == Material.STICK) {

                if(event.getPlayer().hasPermission("friskstick.frisk") && !((Player) event.getRightClicked()).hasPermission("friskstick.bypass")) {

                    if(!plugin.playerDataInstance.isPlayerOnTheRun((Player) event.getRightClicked()))
                        new Frisk(plugin).friskPlayer((Player)event.getRightClicked(), event.getPlayer(), true);

                    else
                        new Frisk(plugin).friskPlayer((Player)event.getRightClicked(), event.getPlayer(), false);

                }


            }

        }

    }

}
