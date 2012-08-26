package friskstick.cops.drugs;

import org.bukkit.potion.PotionEffectType;

public enum DrugEffect{
	NAUSEA(10, 0, PotionEffectType.CONFUSION), 
	DIZZINESS(60, 2, PotionEffectType.CONFUSION),
	HALLUCINATION(20, 4, PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION),
	DEATH,
	ILLNESS(600, 0, PotionEffectType.POISON);
	
	private PotionEffectType[] types;
	private int power;
	private int duration;
	private DrugEffect(){}
	private DrugEffect(int duration, int power, PotionEffectType...types){
		this.types = types;
		this.duration = duration;
		this.power = power;
	}
	public PotionEffectType[] getTypes(){
		return types;
	}
	public int getDuration(){
		return duration;
	}
	public int getPower(){
		return power;
	}
}
