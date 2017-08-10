package miniRPG;

public class Consumable extends Item {
	String consumableName;
	int healAmount;
	int attackBoost;
	int defenseBoost;
	int consumableValue;
	String potionSize;
	String potionType;
	int potionRequiredLevel;
	String herbColor;
	String syrumColor;

	public Consumable(String consumableName, int healAmount, String cosumableType, int consumableValue, int attackBoost,
			int defenseBoost) {
		super(consumableName, consumableName, 1, consumableValue);
		this.healAmount = healAmount;
		this.attackBoost = attackBoost;
		this.defenseBoost = defenseBoost;
	}

	public int getHealAmount() {
		return healAmount;
	}

	public void setHealAmount(int healAmount) {
		this.healAmount = healAmount;
	}

}
