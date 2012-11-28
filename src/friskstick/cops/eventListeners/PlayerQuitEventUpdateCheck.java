package friskstick.cops.eventListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import friskstick.cops.plugin.FriskStick;

/**
 * Resets for update checking for next update
 */
public class PlayerQuitEventUpdateCheck extends FriskStick implements Listener{

	public PlayerQuitEventUpdateCheck(FriskStick friskStick) {

	}

	/**
	 * Player Quit EventHandler
	 */
	@EventHandler
	public void playerQuitUpdateCheckHandle(PlayerQuitEvent e) {

		Player player = e.getPlayer();

		if(player.isOp() && PlayerJoinEventUpdateCheck.updateNeeded) {

			PlayerJoinEventUpdateCheck.updateNeeded = false;

		}

	}

}
