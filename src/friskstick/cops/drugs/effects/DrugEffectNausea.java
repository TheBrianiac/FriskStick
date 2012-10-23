package friskstick.cops.drugs.effects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import friskstick.cops.drugs.DrugEffect;
import friskstick.cops.drugs.Inflictable;

/**
 * The class handling nausea as a result from drugs.
 */
public class DrugEffectNausea implements Inflictable {

	public void inflict(Player player) {

		for(int i = 0; i < DrugEffect.NAUSEA.getTypes().length; i++) {

			DrugEffect nausea = DrugEffect.NAUSEA;
			player.addPotionEffect(new PotionEffect(nausea.getTypes()[i], nausea.getDuration(), nausea.getPower()));

		}

	}

}
