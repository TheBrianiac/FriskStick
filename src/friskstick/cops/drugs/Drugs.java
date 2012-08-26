package friskstick.cops.drugs;

import org.bukkit.entity.Player;

public class Drugs {

	/*
	 * 
	 * Main class where drugs will be implemented. The drugs will have effects.
	 * 
	 */

	public void inflict(Inflictable inflict, Player player) {

		inflict.inflict(player);

	}

}
