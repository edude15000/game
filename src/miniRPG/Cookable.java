package miniRPG;

public class Cookable extends Item {
	String cookableName;
	int cookableXP;
	int cookableLevelRequired;
	int chanceToCook;
	Item newItem;
	int cookableValue;
	int userHealth;

	public Cookable(String cookableName, int cookableXP,
			int cookableLevelRequired, User user, int cookableValue) {
		super(cookableName, cookableName, cookableLevelRequired, cookableValue);
		this.cookableName = cookableName;
		this.cookableLevelRequired = cookableLevelRequired;
		this.cookableValue = cookableValue;
		this.cookableXP = cookableXP;
		this.userHealth = user.getTotalHealth();
		getChanceToCook(user.getLevel("Cooking"));
		getNewItem();
	}

	public void getNewItem() {
		if (this.cookableName.toLowerCase().equalsIgnoreCase("fish")) {
			newItem = new Consumable("Fish", ((int) (userHealth * .1)),
					"Consumable", 15, 0, 0);
		} else if (this.cookableName.toLowerCase().contains("chicken")) {
			newItem = new Consumable("Chicken", ((int) (userHealth * .2)),
					"Consumable", 20, 0, 0);
		} else if (this.cookableName.toLowerCase().contains("turkey leg")) {
			newItem = new Consumable("Turkey Leg", ((int) (userHealth * .25)),
					"Consumable", 25, 0, 0);
		} else if (this.cookableName.toLowerCase().contains("beef")) {
			newItem = new Consumable("Beef", ((int) (userHealth * .3)),
					"Consumable", 30, 0, 0);
		} else if (this.cookableName.toLowerCase().contains("shrimp")) {
			newItem = new Consumable("Shrimp", ((int) (userHealth * .1)),
					"Consumable", 10, 0, 0);
		} else if (this.cookableName.toLowerCase().contains("lobster")) {
			newItem = new Consumable("Lobster", ((int) (userHealth * .3)),
					"Consumable", 30, 0, 0);
		} else if (this.cookableName.toLowerCase().contains("shark")) {
			newItem = new Consumable("Shark", ((int) (userHealth * .4)),
					"Consumable", 75, 0, 0);
		} else if (this.cookableName.toLowerCase().contains("swordfish")) {
			newItem = new Consumable("Swordfish", ((int) (userHealth * .35)),
					"Consumable", 50, 0, 0);
		}
	}

	public void getChanceToCook(int userLevel) {
		if (userLevel < cookableLevelRequired) {
			chanceToCook = 0;
			return;
		}
		if (userLevel == cookableLevelRequired) {
			chanceToCook = 70;
			return;
		}
		for (int i = 1; i < 6; i++) {
			if (userLevel == cookableLevelRequired + i) {
				chanceToCook = 70 + (i * 5);
				return;
			}
		}
		chanceToCook = 100;
		return;
	}
}
