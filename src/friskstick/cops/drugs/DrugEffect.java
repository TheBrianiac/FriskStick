package friskstick.cops.drugs;

import org.bukkit.potion.PotionEffectType;

/**
 * The enum that contains different drug effects, which hold potion effects to apply to the player.
 */
public enum DrugEffect {

	NAUSEA(200, 0, PotionEffectType.CONFUSION), 
	DIZZINESS(1200, 2, PotionEffectType.CONFUSION), 
	HALLUCINATION(400, 4, PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION), 
	DEATH, 
	ILLNESS(1200, 0, PotionEffectType.POISON);

	private PotionEffectType[] types;
	private int power;
	private int duration;

	private DrugEffect() {

	}

	private DrugEffect(int duration, int power, PotionEffectType... types) {

		this.types = types;
		this.duration = duration;
		this.power = power;

	}

	/**
	 * Get the potion effects involoved with the drug effect.
	 * 
	 * @return An array holding all potion effects involved
	 */
	public PotionEffectType[] getTypes() {

		return types;

	}

	/**
	 * Get the amount of time that the potion effect lasts for.
	 * 
	 * @return The amount of time that the potion effect lasts for (in ticks)
	 */
	public int getDuration() {

		return duration;

	}

	/**
	 * Get the power of the potion effect.
	 * 
	 * @return The power of the potion effect
	 */
	public int getPower() {

		return power;

	}

}
