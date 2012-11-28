package friskstick.cops.eventListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import friskstick.cops.plugin.FriskStick;

public class UpdateCheck extends FriskStick implements Listener{

	public static boolean updateNeeded;

	public UpdateCheck(FriskStick plugin) {

	}

	@EventHandler
	public void playerLoginUpdateCheck(PlayerJoinEvent e) {

		Player player = e.getPlayer();

		if(player.isOp() && updateNeeded) {

			player.sendMessage("FriskStick is due for an update!!");

		}

	}

}
