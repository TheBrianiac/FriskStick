package friskstick.cops.drugs.effects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import friskstick.cops.drugs.DrugEffect;
import friskstick.cops.drugs.Inflictable;

public class DrugEffectHallucination implements Inflictable {

	public void inflict(Player player) {

		for(int i = 0; i < DrugEffect.HALLUCINATION.getTypes().length; i++) {

			DrugEffect hall = DrugEffect.HALLUCINATION;
			player.addPotionEffect(new PotionEffect(hall.getTypes()[i], hall.getDuration(), hall.getPower()));

		}

	}

}
