package friskstick.cops.drugs.effects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import friskstick.cops.drugs.DrugEffect;
import friskstick.cops.drugs.Inflictable;

public class DrugEffectDizziness implements Inflictable {

	public void inflict(Player player) {

		for(int i = 0; i < DrugEffect.DIZZINESS.getTypes().length; i++) {

			DrugEffect diz = DrugEffect.DIZZINESS;
			player.addPotionEffect(new PotionEffect(diz.getTypes()[i], diz.getDuration(), diz.getPower()));

		}

	}

}
