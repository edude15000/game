package miniRPG;

import java.util.ArrayList;
import java.util.Random;

public class User {
	public String statusMessage = "";
	public int totalHealth;
	public int currentHealth;
	public String userName;
	public String userClass;
	public int money;
	public int attack;
	public int defense;
	public int speed;
	public int monstersKilled;
	public boolean hardcoreMode;
	public boolean statPotionUsed;
	public int preAttack, preDefense;
	public ArrayList<Item> itemList = new ArrayList<>();
	public ArrayList<Item> equippedItems = new ArrayList<>();
	public ArrayList<Level> levelList = new ArrayList<>();
	public ArrayList<Item> bankedItemList = new ArrayList<>();

	public User(int totalHealth, int currentHealth, int money, int attack,
			int defense, int speed) {
		this.totalHealth = totalHealth;
		this.currentHealth = currentHealth;
		this.money = money;
		this.attack = attack;
		this.defense = defense;
		this.speed = speed;
		monstersKilled = 0;
		statPotionUsed = false;
		levelList.add(new Level(0, "Combat", 1));
		levelList.add(new Level(0, "Mining", 1));
		levelList.add(new Level(0, "Smithing", 1));
		levelList.add(new Level(0, "Cooking", 1));
		levelList.add(new Level(0, "Fishing", 1));
		levelList.add(new Level(0, "Herblore", 1));
		// Add as needed
	}

	public void useItem(int spotInInventory) {
		Item item = itemList.get(spotInInventory);
		String name = item.itemName;
		if (item instanceof Cookable) {
			if (getLevel("Cooking") < ((Cookable) item).getRequiredLevel()) {
				System.out.println("You need level " + item.itemRequiredLevel
						+ " cooking to cook this item.");
				return;
			}
			ArrayList<Item> removeIndexes = new ArrayList<>();
			ArrayList<Item> addIndexes = new ArrayList<>();
			for (Item i : itemList) {
				if (i.itemName.equals(name)) {
					Cookable item2 = (Cookable) i;
					if (new Random().nextInt(100) + 1 <= item2.chanceToCook) {
						addIndexes.add(item2.newItem);
						getLevelObject("Cooking").gainXp(item2.cookableXP);
						System.out.println("You successfully cook the "
								+ item.itemName + ", giving you "
								+ item2.cookableXP + " XP!");
					} else {
						System.out.println("You burn the " + item.itemName);
					}
					removeIndexes.add(i);
				}
			}
			for (int i = 0; i < removeIndexes.size(); i++) {
				itemList.remove(removeIndexes.get(i));
			}
			for (int i = 0; i < addIndexes.size(); i++) {
				itemList.add(addIndexes.get(i));
			}
			return;
		} else if (item instanceof Consumable) {
			heal(((Consumable) item).getHealAmount());
			if (((Consumable) item).herbColor != null
					&& !((Consumable) item).herbColor.toLowerCase().equals(
							"red")) {
				if (statPotionUsed) {
					System.out
							.println("You have already used a stat boosting potion for this battle!");
					return;
				}
				preAttack = getAttack();
				preDefense = getDefense();
				heal(((Consumable) item).getHealAmount());
				setAttack(getAttack() + ((Consumable) item).attackBoost);
				setDefense(getDefense() + ((Consumable) item).defenseBoost);
				statPotionUsed = true;
				System.out.println("You consume the potion!");
			} else if (((Consumable) item).getHealAmount() > (getTotalHealth() - getCurrentHealth())) {
				System.out.println("You consume the " + item.getName()
						+ ", healing your HP fully!");
			} else {
				System.out.println("You consume the " + item.getName()
						+ ", healing you by " + ((Consumable) item).healAmount
						+ " HP!");
			}
		} else if (item instanceof Weapon || item instanceof Armor
				|| item instanceof Jewelry) {
			if (getLevel("Combat") < item.getRequiredLevel()) {
				System.out.println("You need level " + item.itemRequiredLevel
						+ " combat to equip this item.");
				return;
			}
			String type = item.getType();
			if (item instanceof Jewelry) {
				type = ((Jewelry) item).itemType;
			}
			for (Item i : equippedItems) {
				if (i.itemType.toLowerCase().equals(type)) {
					removeEquippedItem(i);
					break;
				}
			}
			equipBonus(item);
		} else if (item instanceof Ore) {
			if (getLevel("Smithing") < item.getRequiredLevel()) {
				System.out.println("You need level " + item.itemRequiredLevel
						+ " smithing to smith this item.");
				return;
			}
			ArrayList<Item> removeIndexes = new ArrayList<>();
			ArrayList<Item> addIndexes = new ArrayList<>();
			for (Item i : itemList) {
				if (i.itemName.equals(name)) {
					Ore ore = (Ore) item;
					Smeltable bar = (Smeltable) ore.newItem;
					if (new Random().nextInt(100) + 1 > 50) {
						addIndexes.add(bar);
						getLevelObject("Smithing").gainXp(ore.smithXp);
						System.out
								.println("You successfully smelt the metal, giving you "
										+ ore.smithXp + " XP!");
					} else {
						getLevelObject("Smithing").gainXp(30);
						System.out
								.println("You fail to smelt the bar, losing the ore.");
					}
					removeIndexes.add(i);
				}
			}
			for (int i = 0; i < removeIndexes.size(); i++) {
				itemList.remove(removeIndexes.get(i));
			}
			for (int i = 0; i < addIndexes.size(); i++) {
				itemList.add(addIndexes.get(i));
			}
			return;
		} else if (item instanceof Smeltable) {
			Play.smeltList((Smeltable) item);
			return;
		} else if (item instanceof Ingredient) {
			Play.potionList((Ingredient) item);
			return;
		}
		removeItem(spotInInventory);
	}

	public void removeEquippedItem(int i) {
		Item item = getEquippedItems().get(i);
		equippedItems.remove(item);
		itemList.add(item);
		if (item instanceof Weapon) {
			setAttack(getAttack() - ((Weapon) item).getAttackBoost());
		}
		if (item instanceof Armor) {
			setDefense(getDefense() - ((Armor) item).getDefenseBoost());
		}
		if (item instanceof Jewelry) {
			setAttack(getAttack() - ((Jewelry) item).getAttackBoost());
			setDefense(getDefense() - ((Jewelry) item).getDefenseBoost());
			setTotalHealth(getTotalHealth() - ((Jewelry) item).getHpBoost());
		}
	}

	public void removeEquippedItem(Item item) {
		equippedItems.remove(item);
		itemList.add(item);
		if (item instanceof Weapon) {
			setAttack(getAttack() - ((Weapon) item).getAttackBoost());
		}
		if (item instanceof Armor) {
			setDefense(getDefense() - ((Armor) item).getDefenseBoost());
		}
		if (item instanceof Jewelry) {
			setAttack(getAttack() - ((Jewelry) item).getAttackBoost());
			setDefense(getDefense() - ((Jewelry) item).getDefenseBoost());
			setTotalHealth(getTotalHealth() - ((Jewelry) item).getHpBoost());
		}
	}

	public void equipBonus(Item item) {
		equippedItems.add(item);
		if (item instanceof Weapon) {
			setAttack(getAttack() + ((Weapon) item).getAttackBoost());
		}
		if (item instanceof Armor) {
			setDefense(getDefense() + ((Armor) item).getDefenseBoost());
		}
		if (item instanceof Jewelry) {
			setAttack(getAttack() + ((Jewelry) item).getAttackBoost());
			setDefense(getDefense() + ((Jewelry) item).getDefenseBoost());
			setTotalHealth(getTotalHealth() + ((Jewelry) item).getHpBoost());
		}
	}

	public void removeItem(int spotInInventory) {
		itemList.remove(spotInInventory);
	}

	public void heal(int amount) {
		if (amount >= (totalHealth - currentHealth)) {
			currentHealth = totalHealth;
		} else {
			currentHealth += amount;
		}
	}

	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}

	public ArrayList<Item> getEquippedItems() {
		return equippedItems;
	}

	public void setEquippedItems(ArrayList<Item> equippedItems) {
		this.equippedItems = equippedItems;
	}

	public int getTotalHealth() {
		return totalHealth;
	}

	public void setTotalHealth(int totalHealth) {
		this.totalHealth = totalHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public void levelUp(Level l) {
		if (l.levelName.equals("Combat")) {
			setAttack(getAttack()
					+ (int) Math.pow(getLevel("Combat"), 1.1)
					+ (int) (new Random().nextDouble() * Math.pow(
							getLevel("Combat"), 1.05)));
			setDefense(getDefense()
					+ (int) Math.pow(getLevel("Combat"), 1.0005)
					+ (int) (new Random().nextDouble() * Math.pow(
							getLevel("Combat"), 1.0005)));
			setSpeed(getSpeed() + new Random().nextInt(10) + 5);
			setTotalHealth((int) ((getTotalHealth() + 163) + ((new Random()
					.nextInt(163) + 163) * (new Random().nextDouble()))));
			setCurrentHealth(getTotalHealth());
		}
		System.out.println("You have leveled up! You are now level "
				+ l.getLevelFromXP(l.getXp()) + " " + l.getName());
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void checkForLevelUp() {
		for (Level l : levelList) {
			if (l.getLevelFromXP(l.getXp()) > l.getLevelBeforeLevelingUp()) {
				l.setLevelBeforeLevelingUp(l.getLevelFromXP(l.getXp()));
				levelUp(l);
			}
		}
	}

	public Level getLevelObject(String level) {
		for (Level l : levelList) {
			if (l.levelName.equals(level)) {
				return l;
			}
		}
		return null;
	}

	public int getLevel(String level) {
		Level l = getLevelObject(level);
		if (l == null) {
			return 0;
		} else {
			return l.getLevelFromXP(l.getXp());
		}
	}

	public void displayStats() {
		System.out.println();
		System.out.println(userName.toUpperCase() + "\t"
				+ userClass.toUpperCase());
		System.out.println("Health: " + getCurrentHealth() + "/"
				+ getTotalHealth() + "\tMoney: " + getMoney() + "\tAttack: "
				+ getAttack() + "\tDefense: " + getDefense() + "\tSpeed: "
				+ getSpeed());
		for (Level level : this.levelList) {
			System.out
					.print(level.levelName
							+ ": ("
							+ level.getLevelFromXP(level.getXp())
							+ ") "
							+ level.getXp()
							+ "/"
							+ level.getXPFromLevel(level.getLevelFromXP(level
									.getXp()) + 1) + "\t");
		}
		System.out.println();
		String sword = null, shield = null, platebody = null, platelegs = null, helmet = null, boots = null, ring = null, necklace = null;
		for (Item i : equippedItems) {
			if (i.itemType.equals("sword")) {
				sword = i.itemName + " (+" + ((Weapon) i).attackBoost + " ATT)";
			} else if (i.itemType.equals("shield")) {
				shield = i.itemName + " (+" + ((Armor) i).defenseBoost
						+ " DEF)";
			} else if (i.itemType.equals("platebody")) {
				platebody = i.itemName + " (+" + ((Armor) i).defenseBoost
						+ " DEF)";
			} else if (i.itemType.equals("platelegs")) {
				platelegs = i.itemName + " (+" + ((Armor) i).defenseBoost
						+ " DEF)";
			} else if (i.itemType.equals("helmet")) {
				helmet = i.itemName + " (+" + ((Armor) i).defenseBoost
						+ " DEF)";
			} else if (i.itemType.equals("boots")) {
				boots = i.itemName + " (+" + ((Armor) i).defenseBoost + " DEF)";
			} else if (i.itemType.toLowerCase().contains("ring")) {
				if (i.itemName.toLowerCase().contains("sapphire")) {
					ring = i.itemName + " (+" + ((Jewelry) i).defenseBoost
							+ " DEF)";
				} else if (i.itemName.toLowerCase().contains("emerald")) {
					ring = i.itemName + " (+" + ((Jewelry) i).hpBoost + " HP)";
				} else if (i.itemName.toLowerCase().contains("ruby")) {
					ring = i.itemName + " (+" + ((Jewelry) i).attackBoost
							+ " ATT)";
				} else {
					ring = i.itemName + " (+" + ((Jewelry) i).attackBoost
							+ " ATT, +" + ((Jewelry) i).defenseBoost
							+ " DEF, +" + ((Jewelry) i).hpBoost + " HP)";
				}
			} else if (i.itemType.toLowerCase().contains("necklace")) {
				if (i.itemName.toLowerCase().contains("sapphire")) {
					necklace = i.itemName + " (+" + ((Jewelry) i).defenseBoost
							+ " DEF)";
				} else if (i.itemName.toLowerCase().contains("emerald")) {
					necklace = i.itemName + " (+" + ((Jewelry) i).hpBoost
							+ " HP)";
				} else if (i.itemName.toLowerCase().contains("ruby")) {
					necklace = i.itemName + " (+" + ((Jewelry) i).attackBoost
							+ " ATT)";
				} else {
					necklace = i.itemName + " (+" + ((Jewelry) i).attackBoost
							+ " ATT, +" + ((Jewelry) i).defenseBoost
							+ " DEF, +" + ((Jewelry) i).hpBoost + " HP)";
				}
			}
		}
		if (sword == null) {
			sword = "N/A";
		}
		if (shield == null) {
			shield = "N/A";
		}
		if (platebody == null) {
			platebody = "N/A";
		}
		if (platelegs == null) {
			platelegs = "N/A";
		}
		if (helmet == null) {
			helmet = "N/A";
		}
		if (boots == null) {
			boots = "N/A";
		}
		if (ring == null) {
			ring = "N/A";
		}
		if (necklace == null) {
			necklace = "N/A";
		}
		System.out.println("Sword: " + sword + "\tShield: " + shield
				+ "\tBoots: " + boots);
		System.out.println("Helmet: " + helmet + "\tPlatebody: " + platebody
				+ "\tPlatelegs: " + platelegs);
		System.out.println("Ring: " + ring + "\tNecklace: " + necklace);
	}
}
