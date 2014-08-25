package friskstick.data;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {

    private List<Player> playersOnTheRun = new ArrayList<Player>();

    /**
     * Sets a player to be on the run if they weren't already
     *
     * @param player The player in question
     */
    public void setPlayerOnTheRun(Player player) {

        if(!playersOnTheRun.contains(player))
            playersOnTheRun.add(player);

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

}
