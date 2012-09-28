package friskstick.cops.drugs;

import friskstick.cops.plugin.FriskStick;
import friskstick.cops.drugs.effects.*;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Random;

/*
 * 
 * Main class where drugs will be implemented. The drugs will have effects.
 * 
 */

public class Drugs implements Listener {

	private FriskStick plugin;
	private Random random;

	public Drugs(FriskStick plugin) {

		this.plugin = plugin;

	}

	public void inflict(Inflictable inflict, Player player) {

		inflict.inflict(player);

	}

	//Currently NOT implemented
	@EventHandler
	public void drugUse(PlayerInteractEvent event) {

		for(String drugidraw : plugin.getConfig().getStringList("drug-ids")) {

			String[] drugids = drugidraw.split(":");
			String drugid = drugids[0];

			if(event.getItem().getType().equals(Material.getMaterial(Integer.parseInt(drugid)))) {

				int i = random.nextInt(DrugEffect.values().length);
				DrugEffect effect = DrugEffect.values()[i];
				Player player = event.getPlayer();

				switch(effect) {

				case NAUSEA:
					this.inflict(new DrugEffectNausea(), player);
				case HALLUCINATION:
					this.inflict(new DrugEffectHallucination(), player);
				case ILLNESS:
					this.inflict(new DrugEffectIllness(), player);
				case DIZZINESS:
					this.inflict(new DrugEffectDizziness(), player);
				case DEATH:
					DrugEffectDeath.kill(player);

				}

			}

		}

	}

}
