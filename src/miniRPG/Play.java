package miniRPG;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;
import java.util.Scanner;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Play {
	static User user;
	static Scanner sc;

	public static void main(String[] args) throws IOException {
		sc = new Scanner(System.in);
		user = startUser();
		loopActions(user);
		sc.close();
	}

	public static User startUser() {
		user = null;
		try {
			user = loadData();
			if (user == null) {
				user = new User(100, 100, 0, 10, 10);
				setUpUserInfo();
			}
			if (user.getCurrentHealth() < 1) {
				user.setCurrentHealth(user.getTotalHealth());
			}
		} catch (IOException e) {
		}
		return user;
	}

	public static void setUpUserInfo() {
		String choice = null;
		while (choice == null || choice.isEmpty()) {
			System.out.println("What is your name?");
			choice = sc.nextLine();
		}
		user.userName = choice;
		int choice2 = 0;
		while (choice2 != 1 && choice2 != 2 && choice2 != 3 && choice2 != 4
				&& choice2 != 5) {
			System.out.println("What class do you want to be?");
			System.out.println("1 Barbarian (+25 Attack, -15 HP)");
			System.out.println("2 Knight (+15 Defence, +20 HP)");
			System.out.println("3 Thief (+10 Attack, +5% Gold from enemies)");
			System.out.println("4 Warrior (+10 Attack, +10 Defense");
			System.out.println("5 Chicken Tender (MYSTERY)");
			choice2 = sc.nextInt();
		}
		if (choice2 == 1) {
			user.userClass = "Barbarian";
			user.setAttack(user.getAttack() + 25);
			user.setTotalHealth(user.getTotalHealth() - 15);
			user.setCurrentHealth(user.getTotalHealth());
		} else if (choice2 == 2) {
			user.userClass = "Knight";
			user.setDefense(user.getDefense() + 15);
			user.setTotalHealth(user.getTotalHealth() + 20);
			user.setCurrentHealth(user.getTotalHealth());
		} else if (choice2 == 3) {
			user.userClass = "Thief";
			user.setAttack(user.getAttack() + 10);
		} else if (choice2 == 4) {
			user.userClass = "Warrior";
			user.setAttack(user.getAttack() + 10);
			user.setDefense(user.getDefense() + 10);
		} else if (choice2 == 5) {
			user.userClass = "Chicken Tender";
			user.setAttack(new Random().nextInt(21));
			user.setDefense(new Random().nextInt(21));
			user.setTotalHealth(new Random().nextInt(201));
			user.setCurrentHealth(user.getTotalHealth());
		}
	}

	public static User loadData() throws IOException {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Item.class,
				new InterfaceAdapter<Item>());
		BufferedReader br = new BufferedReader(new FileReader("Output.json"));
		JsonParser parser = new JsonParser();
		try {
			JsonObject json = parser.parse(br).getAsJsonObject();
			br.close();
			return gsonBuilder.create().fromJson(json, User.class);
		} catch (Exception e) {
		}
		return null;
	}

	public static void saveData(User user) throws IOException {
		try {
			Writer writer = new FileWriter("Output.json");
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Item.class,
					new InterfaceAdapter<Item>());
			gsonBuilder.setPrettyPrinting();
			gsonBuilder.create().toJson(user, writer);
			writer.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void smeltList(Smeltable item) {
		Item item1 = null, item2 = null;
		if (item.itemName.toLowerCase().contains("sapphire")) {
			System.out.println("1 Sapphire Ring (1 gold bar, 1 sapphire)");
			System.out.println("2 Sapphire Necklace (2 gold bars, 1 sapphire)");
			item1 = new Jewelry("Sapphire Ring", 0, 0,
					(int) (user.getDefense() * .05), 50);
			item2 = new Jewelry("Sapphire Necklace", 0, 0,
					(int) (user.getDefense() * .05), 50);
		} else if (item.itemName.toLowerCase().contains("emerald")) {
			System.out.println("1 Emerald Ring (1 gold bar, 1 emerald)");
			System.out.println("2 Emerald Necklace (2 gold bars, 1 emerald)");
			item1 = new Jewelry("Emerald Ring",
					(int) (user.getTotalHealth() * .05), 0, 0, 50);
			item2 = new Jewelry("Emerald Necklace",
					(int) (user.getTotalHealth() * .05), 0, 0, 50);
		} else if (item.itemName.toLowerCase().contains("ruby")) {
			System.out.println("1 Ruby Ring (1 gold bar, 1 ruby)");
			System.out.println("2 Ruby Necklace (2 gold bars, 1 ruby)");
			item1 = new Jewelry("Ruby Ring", 0, (int) (user.getAttack() * .05),
					0, 50);
			item2 = new Jewelry("Ruby Necklace", 0,
					(int) (user.getAttack() * .05), 0, 50);
		} else if (item.itemName.toLowerCase().contains("diamond")) {
			System.out.println("1 Diamond Ring (1 gold bar, 1 diamond)");
			System.out.println("2 Diamond Necklace (2 gold bars, 1 diamond)");
			item1 = new Jewelry("Diamond Ring",
					(int) (user.getTotalHealth() * .03),
					(int) (user.getAttack() * .03),
					(int) (user.getDefense() * .03), 50);
			item2 = new Jewelry("Diamond Necklace",
					(int) (user.getTotalHealth() * .03),
					(int) (user.getAttack() * .03),
					(int) (user.getDefense() * .03), 50);
		} else if (item.itemName.toLowerCase().contains("bronze")) {
			System.out.println("1 Bronze Helmet (1 gold bar, 1 diamond)");
		}

		// TODO : ARMOR / WEPS
		int choice = 0;
		choice = sc.nextInt();
		if (choice == 1) {
			if (item1 != null) {
				smelt(item1);
			}
		} else if (choice == 2) {
			if (item2 != null) {
				smelt(item2);
			}
		}
		return;
	}

	public static void smelt(Item i) {
		if (i.itemName.equalsIgnoreCase("Gold bar")) {
			System.out.println("Please choose a gem to make jewelry!");
			return;
		}
		if (i.itemName.toLowerCase().contains("sapphire ring")
				&& getNumberOfItemByName("Gold Bar") >= 1) {
			user.itemList.remove(getItemByName("Sapphire"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Sapphire Ring!");
			user.getLevelObject("Smithing").gainXp(200);
			// TODO everything else!
		} else {
			System.out
					.println("You do not have enough required items to craft this!");
		}

	}

	public static Item getItemByName(String name) {
		for (Item i : user.itemList) {
			if (i.itemName.equalsIgnoreCase(name)) {
				return i;
			}
		}
		return null;
	}

	public static void loopActions(User user) throws IOException {
		while (true) {
			String choice = null;
			user.displayStats();
			System.out.println("What would you like to do?");
			System.out
					.println("Combat (c)\tInventory (i)\tEquipped Items (e)\tShop (s)\tHospital (h)\tFishing (f)\tMining (m)\tQuit (q)");
			choice = sc.next();
			if (choice == null) {
				choice = sc.nextLine().toLowerCase();
			}
			if (choice.startsWith("c")) {
				System.out
						.println("What level enemy do you want to fight? (Type 0 to exit): ");
				String level = sc.next();
				if (isInteger(level) && (Integer.parseInt(level) > 0)
						&& (Integer.parseInt(level) < 30)) {
					fight(new Enemy(Integer.parseInt(level), user));
				}
			} else if (choice.startsWith("i")) {
				inventory();
			} else if (choice.startsWith("s")) {
				shop();
			} else if (choice.startsWith("h")) {
				hospital();
			} else if (choice.startsWith("f")) {
				System.out.println(Fishing.fishing(user));
			} else if (choice.startsWith("e")) {
				equippedItems();
			} else if (choice.startsWith("m")) {
				System.out.println(Mining.mining(user));
			} else if (choice.startsWith("q")) {
				saveData(user);
				System.exit(0);
			}
			user.checkForLevelUp();
			checkItemStats();
			saveData(user);
		}
	}

	public static void checkItemStats() {
		for (Item i : user.getEquippedItems()) {
			if (i.itemName.contains("Bronze")) {
				if (i instanceof Weapon) {
					((Weapon) i).setAttackBoost(((int) Math.ceil(user
							.getAttack() * .05)));
				} else if (i instanceof Armor) {
					((Armor) i).setDefenseBoost(((int) Math.ceil(user
							.getDefense() * .05)));
				}
			} else if (i.itemName.contains("Iron")) {
				if (i instanceof Weapon) {
					((Weapon) i).setAttackBoost(((int) Math.ceil(user
							.getAttack() * .1)));
				} else if (i instanceof Armor) {
					((Armor) i).setDefenseBoost(((int) Math.ceil(user
							.getDefense() * .1)));
				}
			} else if (i.itemName.contains("Steel")) {
				if (i instanceof Weapon) {
					((Weapon) i).setAttackBoost(((int) Math.ceil(user
							.getAttack() * .2)));
				} else if (i instanceof Armor) {
					((Armor) i).setDefenseBoost(((int) Math.ceil(user
							.getDefense() * .15)));
				}
			} else if (i.itemName.contains("Mithril")) {
				if (i instanceof Weapon) {
					((Weapon) i).setAttackBoost(((int) Math.ceil(user
							.getAttack() * .25)));
				} else if (i instanceof Armor) {
					((Armor) i).setDefenseBoost(((int) Math.ceil(user
							.getDefense() * .175)));
				}
			} else if (i.itemName.contains("Adamantite")) {
				if (i instanceof Weapon) {
					((Weapon) i).setAttackBoost(((int) Math.ceil(user
							.getAttack() * .3)));
				} else if (i instanceof Armor) {
					((Armor) i).setDefenseBoost(((int) Math.ceil(user
							.getDefense() * .2)));
				}
			} else if (i.itemName.contains("Rune")) {
				if (i instanceof Weapon) {
					((Weapon) i).setAttackBoost(((int) Math.ceil(user
							.getAttack() * .35)));
				} else if (i instanceof Armor) {
					((Armor) i).setDefenseBoost(((int) Math.ceil(user
							.getDefense() * .25)));
				}
			}
		}
	}

	public static int getNumberOfItemByName(String name) {
		int num = 0;
		for (Item item : user.getItemList()) {
			if (item.itemName.equalsIgnoreCase(name)) {
				num++;
			}
		}
		return num;
	}

	public static void equippedItems() {
		if (user.equippedItems.isEmpty()) {
			System.out.println("You have no equipped items!\n");
			return;
		}
		int count = 0;
		System.out.println("Equipped item list:");
		for (Item i : user.equippedItems) {
			if (i instanceof Armor) {
				System.out.println(count + "\t" + i.getName()
						+ "\tDEFENSE BONUS: " + ((Armor) i).getDefenseBoost());
			} else if (i instanceof Weapon) {
				System.out.println(count + "\t" + i.getName()
						+ "\tATTACK BONUS: " + ((Weapon) i).getAttackBoost());
			} else if (i instanceof Jewelry) {
				System.out.println(count + "\t" + i.getName() + "\tHP BONUS: "
						+ ((Jewelry) i).getHpBoost() + "\tATTACK BONUS: "
						+ ((Jewelry) i).getAttackBoost() + "\tDEFENSE BONUS: "
						+ ((Jewelry) i).getDefenseBoost());
			}
			count++;
		}
		System.out.print("Type a number to remove item or a letter to cancel:");
		try {
			int choice = sc.nextInt();
			user.removeEquippedItem(choice);
			System.out.println("You remove the "
					+ user.equippedItems.get(choice));
		} catch (Exception e) {
		}
	}

	public static void shop() {
		System.out.println("Would you like to buy or sell an item? (buy/sell)");
		String choice = sc.next();
		if (choice.toLowerCase().startsWith("s")) {
			if (user.itemList.isEmpty()) {
				System.out.println("You have no items!\n");
				return;
			}
			int count = 0;
			System.out.println("Item list:");
			for (Item i : user.itemList) {
				System.out.println(count + "\t" + i.getName() + "\tWORTH: "
						+ (i.getItemValue() / 2));
				count++;
			}
			System.out
					.print("Type a number to select item or a letter to cancel:");
			try {
				int choice2 = sc.nextInt();
				sellItem(user.itemList.get(choice2));
			} catch (Exception e) {
			}
		} else if (choice.toLowerCase().startsWith("b")) {
			// TODO : buy
		}
	}

	public static void sellItem(Item item) {
		user.itemList.remove(item);
		user.setMoney(user.getMoney() + (item.itemValue / 2));
		System.out.println("You sell the " + item.itemName);
	}

	public static void hospital() {
		if (user.getMoney() == 0) {
			System.out.println("You have no gold!\n");
			return;
		}
		System.out.println("You have " + user.getMoney() + " gold and "
				+ user.getCurrentHealth() + " HP...");
		System.out
				.print("The hospital charges 1 gold per hp to heal. How much would you like to spend? (Type 0 to exit): ");
		try {
			int choice = sc.nextInt();
			if (choice < 1) {
				return;
			}
			if (choice > user.getMoney()) {
				System.out
						.println("You do not have enough gold to heal this much.");
				return;
			} else {
				if (choice > (user.getTotalHealth() - user.getCurrentHealth())) {
					choice = user.getTotalHealth() - user.getCurrentHealth();
				}
				user.setMoney(user.getMoney() - choice);
				user.setCurrentHealth(user.getCurrentHealth() + choice);
				System.out.println("You have been healed for " + choice
						+ " HP!");
				return;
			}
		} catch (Exception e) {
		}
	}

	public static void inventory() {
		if (user.itemList.isEmpty()) {
			System.out.println("You have no items!\n");
			return;
		}
		int count = 0;
		System.out.println("Item list:");
		for (Item i : user.itemList) {
			if (i instanceof Cookable
					|| i.itemType.toLowerCase().equals("cookable")) {
				System.out.println(count + "\t" + i.getName()
						+ "\tRequired Level To COOK: " + i.getRequiredLevel());
			} else if (i instanceof Consumable
					|| i.itemType.toLowerCase().equals("consumable")) {
				Consumable c = (Consumable) i;
				System.out.println(count + "\t" + c.getName() + "\tHeals: "
						+ c.getHealAmount());
			} else if (i instanceof Weapon || i instanceof Armor
					|| i instanceof Jewelry
					|| i.itemType.toLowerCase().equals("armor")
					|| i.itemType.toLowerCase().equals("weapon")
					|| i.itemType.toLowerCase().equals("jewelry")) {
				System.out.println(count + "\t" + i.getName()
						+ "\tRequired Level To EQUIP: " + i.getRequiredLevel());
			} else if (i instanceof Ore) {
				System.out.println(count + "\t" + i.getName()
						+ "\tRequired Level to SMITH:" + i.getRequiredLevel());
			} else if (i instanceof Smeltable) {
				System.out.println(count + "\t" + i.getName());
			}
			count++;
		}
		System.out.print("Type a number to select item or a letter to cancel:");
		try {
			int choice = sc.nextInt();
			user.useItem(choice);
		} catch (Exception e) {
		}
	}

	public static boolean isInteger(String s) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), 10) < 0)
				return false;
		}
		return true;
	}

	public static void fight(Enemy enemy) {
		int count = 0;
		int damage;
		while (true) {
			if (enemy.enemyHp <= 0) {
				System.out.println(enemy.enemyName
						+ " has been killed! You have recieved :");
				int gold = 0;
				if (user.userClass.equals("Thief")) {
					gold = (int) (enemy.enemyMoney * 1.05);
				} else {
					gold = enemy.enemyMoney;
				}
				System.out.println(gold + " gold");
				user.setMoney(user.getMoney() + gold);
				System.out.println(enemy.enemyXp + " XP");
				user.getLevelObject("Combat").setXp(
						user.getLevelObject("Combat").getXp() + enemy.enemyXp);
				for (Item i : enemy.lootlist) {
					System.out.println(i.getName());
					user.itemList.add(i);
				}
				user.monstersKilled++;
				return;
			}
			if (user.getCurrentHealth() <= 0) {
				int amountGold = (int) (user.getMoney() * 0.5);
				user.setMoney(user.getMoney() - amountGold);
				System.out.println("You have died! You lost " + amountGold
						+ " gold!");
				user.setCurrentHealth(user.getTotalHealth());
				return;
			}
			if (count % 2 == 0) {
				System.out.println(enemy.enemyName + " HP: " + enemy.enemyHp);
				System.out.println("Your HP: " + user.getCurrentHealth());
				System.out
						.println("Use an item, run away, or autofight? (yes / no / run / af): ");
				String choice = sc.next();
				if (choice == null || choice.isEmpty()) {
					choice = sc.nextLine();
				}
				if (choice.toLowerCase().startsWith("y")
						|| choice.toLowerCase().startsWith("i")) {
					inventory();
				} else if (choice.toLowerCase().startsWith("r")) {
					if (runAway(
							user.getLevelObject("Combat").getLevelFromXP(
									user.getLevelObject("Combat").getXp()),
							enemy.enemyLevel)) {
						System.out.println("You successfully ran away!");
						return;
					} else {
						System.out.println("You fail to run away!");
					}
				} else if (choice.toLowerCase().startsWith("a")) {
					while (user.getCurrentHealth() > user.getTotalHealth() * .10
							&& enemy.enemyHp >= 0) {
						damage = damage(user.getAttack(), enemy.enemyDefense);
						enemy.enemyHp -= damage;
						System.out.println("You do " + damage + " damage!");
						damage = damage(enemy.enemyAttack, user.getDefense());
						user.setCurrentHealth(user.getCurrentHealth() - damage);
						System.out.println(enemy.enemyName + " does " + damage
								+ " damage!");
					}
				}
				if (!choice.toLowerCase().startsWith("a")
						&& !choice.toLowerCase().startsWith("y")) {
					damage = damage(user.getAttack(), enemy.enemyDefense);
					enemy.enemyHp -= damage;
					System.out.println("You do " + damage + " damage!");
				}
			} else {
				damage = damage(enemy.enemyAttack, user.getDefense());
				user.setCurrentHealth(user.getCurrentHealth() - damage);
				System.out.println(enemy.enemyName + " does " + damage
						+ " damage!");
			}
			count++;
		}
	}

	public static boolean runAway(int userLevel, int enemyLevel) {
		if (userLevel + 3 > enemyLevel) {
			return true;
		}
		if (userLevel + 2 > enemyLevel) {
			return new Random().nextInt(100) + 1 < 95;
		}
		if (userLevel + 1 > enemyLevel) {
			return new Random().nextInt(100) + 1 < 90;
		}
		if (userLevel == enemyLevel) {
			return new Random().nextInt(100) + 1 < 85;
		}
		if (userLevel + 1 < enemyLevel) {
			return new Random().nextInt(100) + 1 < 75;
		}
		if (userLevel + 2 < enemyLevel) {
			return new Random().nextInt(100) + 1 < 50;
		}
		if (userLevel + 3 < enemyLevel) {
			return new Random().nextInt(100) + 1 < 30;
		} else
			return new Random().nextInt(100) + 1 < 20;
	}

	public static int damage(int attackerAtt, int defenderDef) {
		int totalAttackPower = new Random().nextInt(attackerAtt + 10);
		int totalDefensePower = new Random().nextInt(defenderDef + 10);
		int totalHit = totalAttackPower - totalDefensePower;
		if (totalHit < 0) {
			totalHit = new Random().nextInt(5);
		}
		return totalHit;
	}
}
