package miniRPG;

public class Armor extends Item {
	String armorName;
	int defenseBoost;
	String armorType;
	int armorRequiredLevel;
	int armorValue;

	public Armor(String armorName, int defenseBoost, String armorType, int armorRequiredLevel, int armorValue) {
		super(armorName, armorType, armorRequiredLevel, armorValue);
		this.defenseBoost = defenseBoost;
	}

	public int getDefenseBoost() {
		return defenseBoost;
	}

	public void setDefenseBoost(int defenseBoost) {
		this.defenseBoost = defenseBoost;
	}

}
