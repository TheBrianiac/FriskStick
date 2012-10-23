package friskstick.cops.drugs;

/**
 * The enum that contains more general drug effects, which hold more specific drug effects to apply.
 */
public enum GeneralDrugEffect {

	HIGH(DrugEffect.NAUSEA, DrugEffect.HALLUCINATION, DrugEffect.DIZZINESS);

	private DrugEffect[] drugEffects;

	private GeneralDrugEffect(DrugEffect... drugEffects) {

		this.drugEffects = drugEffects;

	}

	/**
	 * Get the {@link DrugEffect effects} associated with the general effects.
	 * 
	 * @return An array containing the effects
	 */
	public DrugEffect[] getEffects() {

		return drugEffects;

	}

}