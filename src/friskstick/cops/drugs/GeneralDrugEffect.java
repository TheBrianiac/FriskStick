package friskstick.cops.drugs;

public enum GeneralDrugEffect {

	HIGH(DrugEffect.NAUSEA, DrugEffect.HALLUCINATION, DrugEffect.DIZZINESS);

	private DrugEffect[] drugEffects;

	private GeneralDrugEffect(DrugEffect...drugEffects) {

		this.drugEffects = drugEffects;

	}

	public DrugEffect[] getEffects() {

		return drugEffects;

	}

}