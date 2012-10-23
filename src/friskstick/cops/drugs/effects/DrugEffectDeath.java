package friskstick.cops.drugs.effects;

import org.bukkit.entity.Player;

/**
 * The class handling death as a result from drugs.
 */
public class DrugEffectDeath {

	/**
	 * The method used to kill a player.
	 * 
	 * @param player The player to kill.
	 */
	public static void kill(Player player) {

		player.setHealth(0);

	}

}
