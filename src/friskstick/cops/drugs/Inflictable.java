package friskstick.cops.drugs;

import org.bukkit.entity.Player;

/**
 * 
 * The interface that all drug effects should implement.
 *
 */

public interface Inflictable {
	
	/**
	 * The method used to inflict a player with a certain drug effect.
	 * @param player The {@link Player player} to apply the effect to.
	 */
	
	public void inflict(Player player);

}
