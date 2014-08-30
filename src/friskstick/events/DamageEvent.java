package friskstick.events;

import friskstick.main.Frisk;
import friskstick.main.FriskStick;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEvent implements Listener {

    private FriskStick plugin;

    public DamageEvent(FriskStick plugin) {

        this.plugin = plugin;

    }

    @EventHandler
    public void playerDamaged(EntityDamageByEntityEvent event) {

        // If the two entities are players...
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {

            // If the damager has proper perms...
            if(((Player) event.getDamager()).hasPermission("friskstick.frisk")) {

                Player cop = (Player) event.getDamager();
                Player frisked = (Player) event.getEntity();

                if (plugin.getConfig().getString("frisk-method").equalsIgnoreCase("both") || plugin.getConfig().getString("frisk-method").equalsIgnoreCase("hit")) {

                    if (cop.getItemInHand().getType() == Material.STICK) {

                        // If the player being frisked is NOT on the run, frisk the player
                        if (!plugin.playerDataInstance.isPlayerOnTheRun(frisked))
                            new Frisk(plugin).friskPlayer(frisked, cop, false);

                    }

                }

                // If the player being frisked IS on the run AND beatdown mode is enabled...
                if (plugin.playerDataInstance.isPlayerOnTheRun(frisked) && plugin.getConfig().getBoolean("enable-beatdown")) {

                    // If the runner's health is below the maximum for beatdown mode...
                    if (frisked.getHealth() <= plugin.getConfig().getDouble("beatdown-frisk-health")) {

                        // The player is no longer on the run since he/she's been caught
                        plugin.playerDataInstance.setPlayerNotOnTheRun(frisked);

                        // Frisk the player
                        new Frisk(plugin).friskPlayer(frisked, cop, true);

                    }

                }

            }

        }

    }

}
