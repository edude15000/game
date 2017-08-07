package miniRPG;

public class Consumable extends Item {
	String consumableName;
	int healAmount;
	int consumableValue;

	public Consumable(String consumableName, int healAmount,
			String cosumableType, int consumableValue) {
		super(consumableName, consumableName, 1, consumableValue);
		this.healAmount = healAmount;
	}

	public int getHealAmount() {
		return healAmount;
	}

	public void setHealAmount(int healAmount) {
		this.healAmount = healAmount;
	}

}
