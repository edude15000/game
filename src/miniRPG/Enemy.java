package miniRPG;

import java.util.ArrayList;
import java.util.Random;

public class Enemy {
	String enemyName;
	int enemyAttack, enemyDefense, enemyLevel;
	int enemyXp, enemyMoney, enemyHp;
	ArrayList<Item> lootlist = new ArrayList<>();
	int userHealth;

	public Enemy(int enemyLevel, User user) {
		userHealth = user.getTotalHealth();
		this.enemyLevel = enemyLevel;
		getName();
		randomlyCreateHP(enemyLevel);
		randomlyCreateXP(enemyLevel);
		randomlyCreateLootList(enemyLevel, user);
		randomlyCreateStats(enemyLevel);
	}

	private void getName() {
		if (enemyLevel < 2) {
			if (new Random().nextInt(100) + 1 > 75) {
				this.enemyName = "Giant Fish";
			} else {
				this.enemyName = "Rat";
			}
		} else if (enemyLevel < 5) {
			if (new Random().nextInt(100) + 1 > 75) {
				this.enemyName = "Rabid Chicken";
			} else {
				this.enemyName = "Giant Spider";
			}
		} else if (enemyLevel < 8) {
			if (new Random().nextInt(100) + 1 > 75) {
				this.enemyName = "Angry Cow";
			} else {
				this.enemyName = "Wild Dog";
			}
		} else if (enemyLevel < 12) {
			if (new Random().nextInt(100) + 1 > 75) {
				this.enemyName = "Mutated Turkey";
			} else {
				this.enemyName = "Giant Bee";
			}
		} else if (enemyLevel < 15) {
			this.enemyName = "Minotaur";
		} else if (enemyLevel < 18) {
			this.enemyName = "Troll";
		} else if (enemyLevel < 21) {
			this.enemyName = "Dark Knight";
		} else if (enemyLevel < 25) {
			this.enemyName = "Giant Scorpion";
		} else {
			this.enemyName = "Ankou";
		}
	}

	private void randomlyCreateHP(int level) {
		this.enemyHp = new Random().nextInt(40 + (10 * level))
				+ (int) Math.pow(level * 10, 1.6);
	}

	private void randomlyCreateXP(int level) {
		this.enemyXp = new Random().nextInt(35 * level) + (level * 20);
	}

	void randomlyCreateStats(int level) {
		this.enemyAttack = new Random().nextInt(12 * level) + (level / 2);
		this.enemyDefense = new Random().nextInt(12 * level) + (level / 2);
	}

	void randomlyCreateLootList(int level, User user) {
		this.enemyMoney = new Random().nextInt(25 * level) + (level * 5);
		if ((new Random().nextInt(100) + 1) < 5) {
			int chance2 = new Random().nextInt(100) + 1;
			if (chance2 < 50) {
				lootlist.add(new Smeltable("Sapphire", 1, 1));
			} else if (chance2 < 80) {
				lootlist.add(new Smeltable("Emerald", 1, 1));
			} else if (chance2 < 95) {
				lootlist.add(new Smeltable("Ruby", 1, 1));
			} else {
				lootlist.add(new Smeltable("Diamond", 1, 1));
			}

		}
		int chance;
		if ((new Random().nextInt(100) + 1) > 80) {
			Item item;
			chance = new Random().nextInt(100) + 1;
			if (chance > 75) {
				item = new Weapon("sword", 0, "sword", 1, 1);
			} else if (chance > 60) {
				item = new Armor("shield", 0, "shield", 1, 1);
			} else if (chance > 45) {
				item = new Armor("helmet", 0, "helmet", 1, 1);
			} else if (chance > 30) {
				item = new Armor("platebody", 0, "platebody", 1, 1);
			} else if (chance > 15) {
				item = new Armor("platelegs", 0, "platelegs", 1, 1);
			} else {
				item = new Armor("boots", 0, "boots", 1, 1);
			}
			if (level < 5) {
				item.setName("Bronze " + item.getName());
				item.setRequiredLevel(1);
				item.setItemValue(50);
				if (item.itemName.contains("sword")) {
					((Weapon) item).setAttackBoost(((int) Math.ceil(user
							.getAttack() * .05)));
				} else {
					((Armor) item).setDefenseBoost(((int) Math.ceil(user
							.getDefense() * .05)));
				}
			} else if (level < 10) {
				item.setName("Iron " + item.getName());
				item.setRequiredLevel(3);
				item.setItemValue(100);
				if (item.itemName.contains("sword")) {
					((Weapon) item).setAttackBoost(((int) Math.ceil(user
							.getAttack() * .1)));
				} else {
					((Armor) item).setDefenseBoost(((int) Math.ceil(user
							.getDefense() * .1)));
				}
			} else if (level < 15) {
				item.setName("Steel " + item.getName());
				item.setRequiredLevel(1);
				item.setItemValue(200);
				if (item.itemName.contains("sword")) {
					((Weapon) item).setAttackBoost(((int) Math.ceil(user
							.getAttack() * .2)));
				} else {
					((Armor) item).setDefenseBoost(((int) Math.ceil(user
							.getDefense() * .15)));
				}
			} else if (level < 20) {
				item.setName("Mithril " + item.getName());
				item.setRequiredLevel(1);
				item.setItemValue(350);
				if (item.itemName.contains("sword")) {
					((Weapon) item).setAttackBoost(((int) Math.ceil(user
							.getAttack() * .25)));
				} else {
					((Armor) item).setDefenseBoost(((int) Math.ceil(user
							.getDefense() * .175)));
				}
			} else if (level < 25) {
				item.setName("Adamantite " + item.getName());
				item.setRequiredLevel(1);
				item.setItemValue(500);
				if (item.itemName.contains("sword")) {
					((Weapon) item).setAttackBoost(((int) Math.ceil(user
							.getAttack() * .3)));
				} else {
					((Armor) item).setDefenseBoost(((int) Math.ceil(user
							.getDefense() * .2)));
				}
			} else if (level >= 25) {
				item.setName("Rune " + item.getName());
				item.setRequiredLevel(1);
				item.setItemValue(1000);
				if (item.itemName.contains("sword")) {
					((Weapon) item).setAttackBoost(((int) Math.ceil(user
							.getAttack() * .35)));
				} else {
					((Armor) item).setDefenseBoost(((int) Math.ceil(user
							.getDefense() * .25)));
				}
			}
			lootlist.add(item);
		}
		if (this.enemyName.equals("Giant Fish")) {
			lootlist.add(new Cookable("Raw Fish", 75, 1, user, 5));
		} else if (this.enemyName.equals("Rabid Chicken")) {
			lootlist.add(new Cookable("Raw Chicken", 150, 5, user, 5));
		} else if (this.enemyName.equals("Mutated Turkey")) {
			lootlist.add(new Cookable("Raw Turkey Leg", 300, 10, user, 5));
		} else if (this.enemyName.equals("Angry Cow")) {
			lootlist.add(new Cookable("Raw Beef", 600, 15, user, 5));
		}
		if (new Random().nextInt(100) + 1 > 75) {
			chance = new Random().nextInt(100) + 1;
			if (chance > 75) {
				lootlist.add(new Consumable("Potato",
						((int) (userHealth * .05)), "consumable", 1));
			} else if (chance > 50) {
				lootlist.add(new Consumable("Small Potion",
						((int) (userHealth * .1)), "consumable", 10));
			} else if (chance > 40) {
				lootlist.add(new Consumable("Fish", ((int) (userHealth * .15)),
						"consumable", 15));
			} else if (chance > 30) {
				lootlist.add(new Consumable("Chicken",
						((int) (userHealth * .2)), "consumable", 20));
			} else if (chance > 20) {
				lootlist.add(new Consumable("Turkey Leg",
						((int) (userHealth * .25)), "consumable", 25));
			} else if (chance > 10) {
				lootlist.add(new Consumable("Beef", ((int) (userHealth * .3)),
						"consumable", 30));
			} else if (chance > 2) {
				lootlist.add(new Consumable("Large Potion",
						((int) (userHealth * .5)), "consumable", 50));
			} else {
				lootlist.add(new Consumable("Max Potion", userHealth,
						"consumable", 100));
			}
		}
	}
}
