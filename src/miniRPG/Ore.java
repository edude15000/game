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
			newItem = new Smeltable("Bronze Bar", 1, 1);
		} else if (this.name.toLowerCase().contains("iron ore")) {
			newItem = new Smeltable("Iron Bar", 5, 1);
		} else if (this.name.toLowerCase().contains("steel ore")) {
			newItem = new Smeltable("Steel Bar", 10, 1);
		} else if (this.name.toLowerCase().contains("mithril ore")) {
			newItem = new Smeltable("Mithril Bar", 15, 1);
		} else if (this.name.toLowerCase().contains("adamantite ore")) {
			newItem = new Smeltable("Adamantite Bar", 20, 1);
		} else if (this.name.toLowerCase().contains("rune ore")) {
			newItem = new Smeltable("Rune Bar", 25, 1);
		} else if (this.name.toLowerCase().contains("gold ore")) {
			newItem = new Smeltable("Gold Bar", 1, 1);
		}
	}
}
