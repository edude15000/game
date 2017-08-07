package miniRPG;

public class Ore extends Item {
	String name;
	int levelRequiredToSmith;
	int value;
	Item newItem;
	int smithXp;

	public Ore(String name, int levelRequiredToSmith, int value, int smithXp) {
		super(name, name, levelRequiredToSmith, value);
		this.name = name;
		this.levelRequiredToSmith = levelRequiredToSmith;
		this.smithXp = smithXp;
		getNewItem();
	}

	public void getNewItem() {
		if (this.name.toLowerCase().contains("bronze ore")) {
			newItem = new Smeltable("Bronze bar", 1, 1);
		} else if (this.name.toLowerCase().contains("iron ore")) {
			newItem = new Smeltable("Iron bar", 5, 1);
		} else if (this.name.toLowerCase().contains("steel ore")) {
			newItem = new Smeltable("Steel bar", 10, 1);
		} else if (this.name.toLowerCase().contains("mithril ore")) {
			newItem = new Smeltable("Mithril bar", 15, 1);
		} else if (this.name.toLowerCase().contains("adamantite ore")) {
			newItem = new Smeltable("Adamantite bar", 20, 1);
		} else if (this.name.toLowerCase().contains("rune ore")) {
			newItem = new Smeltable("Rune Bar", 25, 1);
		}
	}
}
