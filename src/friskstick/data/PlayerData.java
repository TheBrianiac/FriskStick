package friskstick.data;

import friskstick.main.FriskStick;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerData {

    private FriskStick plugin;

    private List<Player> playersOnTheRun = new ArrayList<Player>();
    private Map<String, Long> timeOnTheRun = new HashMap<String, Long>();

    public PlayerData(FriskStick plugin) {

        this.plugin = plugin;

    }

    /**
     * Sets a player to be on the run if they weren't already
     *
     * @param player The player in question
     */
    public void setPlayerOnTheRun(Player player, Player cop) {

        if(!playersOnTheRun.contains(player))
            playersOnTheRun.add(player);

        startTimer(player, cop);

    }

    /**
     * Checks if a player is on the run
     *
     * @param player The player in question
     * @return {@code true} if the player is on the run, {@code false} otherwise.
     */
    public boolean isPlayerOnTheRun(Player player) {

        return playersOnTheRun.contains(player);

    }

    /**
     * Sets a player to no longer be on the run
     *
     * @param player The player in question
     */
    public void setPlayerNotOnTheRun(Player player) {

        if(playersOnTheRun.contains(player))
            playersOnTheRun.remove(player);

    }

    /**
     * Starts the timer for beatdown mode for a specific player
     *
     * @param player The player in question
     * @param cop The cop chasing the player
     */
    public void startTimer(final Player player, final Player cop) {

        timeOnTheRun.put(player.getName(), System.currentTimeMillis());

        // Check every second
        new BukkitRunnable(){

            @Override
            public void run() {

                // If enough time has passed...
                if(System.currentTimeMillis() - timeOnTheRun.get(player.getName()) >= plugin.getConfig().getInt("on-the-run-cooldown") * 1000 && isPlayerOnTheRun(player)) {

                    // The player is no longer on the run.
                    setPlayerNotOnTheRun(player);
                    player.sendMessage(plugin.getConfig().getString("not-on-run-player-msg").replaceAll("&", "§"));
                    cop.sendMessage(plugin.getConfig().getString("not-on-run-cop-msg").replaceAll("&", "§").replaceAll("%player%", player.getName()));

                    this.cancel();

                }

            }

        }.runTaskTimer(plugin, 0, 20);

    }

}
