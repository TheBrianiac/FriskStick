package friskstick.cops.drugs.effects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import friskstick.cops.drugs.DrugEffect;
import friskstick.cops.drugs.Inflictable;

public class DrugEffectIllness implements Inflictable {

	public void inflict(Player player) {

		for(int i = 0; i < DrugEffect.ILLNESS.getTypes().length; i++) {

			DrugEffect ill = DrugEffect.ILLNESS;
			player.addPotionEffect(new PotionEffect(ill.getTypes()[i], ill.getDuration(), ill.getPower()));

		}

	}

}
