package miniRPG;

public class Weapon extends Item {
	String weaponName;
	int attackBoost;
	String weaponType;
	int weaponRequiredLevel;
	int weaponValue;

	public Weapon(String weaponName, int attackBoost, String weaponType, int weaponRequiredLevel, int weaponValue) {
		super(weaponName, weaponType, weaponRequiredLevel, weaponValue);
		this.attackBoost = attackBoost;
	}

	public int getAttackBoost() {
		return attackBoost;
	}

	public void setAttackBoost(int attackBoost) {
		this.attackBoost = attackBoost;
	}

}
