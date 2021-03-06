package miniRPG;

public class Jewelry extends Item {
	String name;
	int hpBoost;
	int attackBoost;
	int defenseBoost;
	int value;
	String type;

	public Jewelry(String name, int hpBoost, int attackBoost, int defenseBoost, int value, String type) {
		super(name, type, 1, value);
		this.attackBoost = attackBoost;
		this.hpBoost = hpBoost;
		this.defenseBoost = defenseBoost;
		this.value = value;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHpBoost() {
		return hpBoost;
	}

	public void setHpBoost(int hpBoost) {
		this.hpBoost = hpBoost;
	}

	public int getAttackBoost() {
		return attackBoost;
	}

	public void setAttackBoost(int attackBoost) {
		this.attackBoost = attackBoost;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getDefenseBoost() {
		return defenseBoost;
	}

	public void setDefenseBoost(int defenseBoost) {
		this.defenseBoost = defenseBoost;
	}

}
