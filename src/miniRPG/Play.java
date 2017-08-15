package miniRPG;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
		user = startUser("test", "Chicken Tender", false);
		loopActions(user);
		sc.close();
	}

	public static User startUser(String userName, String userClass,
			boolean hardcore) {

		user = null;
		try {
			user = loadData();
			if (user == null) {
				user = new User(100, 100, 0, 10, 10, 10);
				user.userClass = userClass;
				user.userName = userName;
				user.hardcoreMode = hardcore;
				setUpUserInfo();
			}
		} catch (IOException e) {
		}
		return user;
	}

	public static void deleteUser() throws IOException {
		user = null;
		saveData(user);
		System.exit(0);
	}

	public static void setUpUserInfo() {
		if (user.getCurrentHealth() < 1) {
			user.setCurrentHealth(user.getTotalHealth());
		}
		if (user.userClass.equalsIgnoreCase("Barbarian")) {
			user.setAttack(user.getAttack() + 20);
			user.setSpeed(user.getSpeed() - 5);
			user.setTotalHealth(user.getTotalHealth() - 15);
			user.setCurrentHealth(user.getTotalHealth());
		} else if (user.userClass.equalsIgnoreCase("Knight")) {
			user.setAttack(user.getAttack() - 5);
			user.setDefense(user.getDefense() + 15);
			user.setTotalHealth(user.getTotalHealth() + 20);
			user.setCurrentHealth(user.getTotalHealth());
		} else if (user.userClass.equalsIgnoreCase("Thief")) {
			user.setAttack(user.getAttack() + 10);
			user.setSpeed(user.getSpeed() + 5);
		} else if (user.userClass.equalsIgnoreCase("Warrior")) {
			user.setAttack(user.getAttack() + 10);
			user.setDefense(user.getDefense() + 10);
		} else if (user.userClass.equalsIgnoreCase("Chicken Tender")) {
			user.setAttack(new Random().nextInt(21) + 5);
			user.setDefense(new Random().nextInt(21) + 5);
			user.setTotalHealth(new Random().nextInt(151) + 50);
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
		if (item.itemName.equalsIgnoreCase("Gold bar")) {
			System.out.println("Please choose a gem to make jewelry!");
			return;
		}
		Item item1 = null, item2 = null, item3 = null, item4 = null, item5 = null, item6 = null;
		if (item.itemName.toLowerCase().contains("sapphire")) {
			System.out
					.println("1 Lvl 1 Sapphire Ring (1 gold bar, 1 sapphire)");
			System.out
					.println("2 Lvl 3 Sapphire Necklace (2 gold bars, 1 sapphire)");
			item1 = new Jewelry("Sapphire Ring", 0, 0,
					(int) (user.getDefense() * .05), 50, "ring");
			item2 = new Jewelry("Sapphire Necklace", 0, 0,
					(int) (user.getDefense() * .05), 50, "necklace");
		} else if (item.itemName.toLowerCase().contains("emerald")) {
			System.out.println("1 Lvl 6 Emerald Ring (1 gold bar, 1 emerald)");
			System.out
					.println("2 Lvl 9 Emerald Necklace (2 gold bars, 1 emerald)");
			item1 = new Jewelry("Emerald Ring",
					(int) (user.getTotalHealth() * .05), 0, 0, 50, "ring");
			item2 = new Jewelry("Emerald Necklace",
					(int) (user.getTotalHealth() * .05), 0, 0, 50, "necklace");
		} else if (item.itemName.toLowerCase().contains("ruby")) {
			System.out.println("1 Lvl 12 Ruby Ring (1 gold bar, 1 ruby)");
			System.out.println("2 Lvl 15 Ruby Necklace (2 gold bars, 1 ruby)");
			item1 = new Jewelry("Ruby Ring", 0, (int) (user.getAttack() * .05),
					0, 50, "ring");
			item2 = new Jewelry("Ruby Necklace", 0,
					(int) (user.getAttack() * .05), 0, 50, "necklace");
		} else if (item.itemName.toLowerCase().contains("diamond")) {
			System.out.println("1 Lvl 18 Diamond Ring (1 gold bar, 1 diamond)");
			System.out
					.println("2 Lvl 21 Diamond Necklace (2 gold bars, 1 diamond)");
			item1 = new Jewelry("Diamond Ring",
					(int) (user.getTotalHealth() * .03),
					(int) (user.getAttack() * .03),
					(int) (user.getDefense() * .03), 50, "ring");
			item2 = new Jewelry("Diamond Necklace",
					(int) (user.getTotalHealth() * .03),
					(int) (user.getAttack() * .03),
					(int) (user.getDefense() * .03), 50, "necklace");
		} else if (item.itemName.toLowerCase().contains("bronze")) {
			System.out.println("1 Lvl 1 Bronze Boots (3 bronze bars)");
			System.out.println("2 Lvl 1 Bronze Helmet (3 bronze bars)");
			System.out.println("3 Lvl 1 Bronze Shield (3 bronze bars)");
			System.out.println("4 Lvl 1 Bronze Sword (5 bronze bars)");
			System.out.println("5 Lvl 1 Bronze Platebody (5 bronze bars)");
			System.out.println("6 Lvl 1 Bronze Platelegs (5 bronze bars)");
			item1 = new Armor("Bronze Boots",
					(int) Math.ceil(user.getDefense() * .05), "boots", 1, 50);
			item2 = new Armor("Bronze Helmet", (int) Math.ceil(user
					.getDefense() * .05), "helmet", 1, 50);
			item3 = new Armor("Bronze Shield", (int) Math.ceil(user
					.getDefense() * .05), "shield", 1, 50);
			item4 = new Weapon("Bronze Sword",
					(int) Math.ceil(user.getAttack() * .05), "sword", 1, 50);
			item5 = new Armor("Bronze Platebody", (int) Math.ceil(user
					.getDefense() * .05), "platebody", 1, 50);
			item6 = new Armor("Bronze Platelegs", (int) Math.ceil(user
					.getDefense() * .05), "platelegs", 1, 50);
		} else if (item.itemName.toLowerCase().contains("iron")) {
			System.out.println("1 Lvl 5 Iron Boots (3 iron bars)");
			System.out.println("2 Lvl 5 Iron Helmet (3 iron bars)");
			System.out.println("3 Lvl 5 Iron Shield (3 iron bars)");
			System.out.println("4 Lvl 5 Iron Sword (5 iron bars)");
			System.out.println("5 Lvl 5 Iron Platebody (5 iron bars)");
			System.out.println("6 Lvl 5 Iron Platelegs (5 iron bars)");
			item1 = new Armor("Iron Boots",
					(int) Math.ceil(user.getDefense() * .1), "boots", 5, 100);
			item2 = new Armor("Iron Helmet",
					(int) Math.ceil(user.getDefense() * .1), "helmet", 5, 100);
			item3 = new Armor("Iron Shield",
					(int) Math.ceil(user.getDefense() * .1), "shield", 5, 100);
			item4 = new Weapon("Iron Sword",
					(int) Math.ceil(user.getAttack() * .1), "sword", 5, 100);
			item5 = new Armor("Iron Platebody", (int) Math.ceil(user
					.getDefense() * .1), "platebody", 5, 100);
			item6 = new Armor("Iron Platelegs", (int) Math.ceil(user
					.getDefense() * .1), "platelegs", 5, 100);
		} else if (item.itemName.toLowerCase().contains("steel")) {
			System.out.println("1 Lvl 10 Steel Boots (3 steel bars)");
			System.out.println("2 Lvl 10 Steel Helmet (3 steel bars)");
			System.out.println("3 Lvl 10 Steel Shield (3 steel bars)");
			System.out.println("4 Lvl 10 Steel Sword (5 steel bars)");
			System.out.println("5 Lvl 10 Steel Platebody (5 steel bars)");
			System.out.println("6 Lvl 10 Steel Platelegs (5 steel bars)");
			item1 = new Armor("Steel Boots",
					(int) Math.ceil(user.getDefense() * .05), "boots", 10, 200);
			item2 = new Armor("Steel Helmet",
					(int) Math.ceil(user.getDefense() * .15), "helmet", 10, 200);
			item3 = new Armor("Steel Shield",
					(int) Math.ceil(user.getDefense() * .15), "shield", 10, 200);
			item4 = new Weapon("Steel Sword",
					(int) Math.ceil(user.getAttack() * .2), "sword", 10, 200);
			item5 = new Armor("Steel Platebody", (int) Math.ceil(user
					.getDefense() * .15), "platebody", 10, 200);
			item6 = new Armor("Steel Platelegs", (int) Math.ceil(user
					.getDefense() * .15), "platelegs", 10, 200);
		} else if (item.itemName.toLowerCase().contains("mithril")) {
			System.out.println("1 Lvl 15 Mithril Boots (3 mithril bars)");
			System.out.println("2 Lvl 15 Mithril Helmet (3 mithril bars)");
			System.out.println("3 Lvl 15 Mithril Shield (3 mithril bars)");
			System.out.println("4 Lvl 15 Mithril Sword (5 mithril bars)");
			System.out.println("5 Lvl 15 Mithril Platebody (5 mithril bars)");
			System.out.println("6 Lvl 15 Mithril Platelegs (5 mithril bars)");
			item1 = new Armor("Mithril Boots", (int) Math.ceil(user
					.getDefense() * .175), "boots", 15, 350);
			item2 = new Armor("Mithril Helmet", (int) Math.ceil(user
					.getDefense() * .175), "helmet", 15, 350);
			item3 = new Armor("Mithril Shield", (int) Math.ceil(user
					.getDefense() * .175), "shield", 15, 350);
			item4 = new Weapon("Mithril Sword", (int) Math.ceil(user
					.getAttack() * .25), "sword", 15, 350);
			item5 = new Armor("Mithril Platebody", (int) Math.ceil(user
					.getDefense() * .175), "platebody", 15, 350);
			item6 = new Armor("Mithril Platelegs", (int) Math.ceil(user
					.getDefense() * .175), "platelegs", 15, 350);
		} else if (item.itemName.toLowerCase().contains("adamantite")) {
			System.out.println("1 Lvl 20 Adamantite Boots (3 adamantite bars)");
			System.out
					.println("2 Lvl 20 Adamantite Helmet (3 adamantite bars)");
			System.out
					.println("3 Lvl 20 Adamantite Shield (3 adamantite bars)");
			System.out.println("4 Lvl 20 Adamantite Sword (5 adamantite bars)");
			System.out
					.println("5 Lvl 20 Adamantite Platebody (5 adamantite bars)");
			System.out
					.println("6 Lvl 20 Adamantite Platelegs (5 adamantite bars)");
			item1 = new Armor("Adamantite Boots", (int) Math.ceil(user
					.getDefense() * .2), "boots", 20, 500);
			item2 = new Armor("Adamantite Helmet", (int) Math.ceil(user
					.getDefense() * .2), "helmet", 20, 500);
			item3 = new Armor("Adamantite Shield", (int) Math.ceil(user
					.getDefense() * .2), "shield", 20, 500);
			item4 = new Weapon("Adamantite Sword", (int) Math.ceil(user
					.getAttack() * .3), "sword", 20, 500);
			item5 = new Armor("Adamantite Platebody", (int) Math.ceil(user
					.getDefense() * .2), "platebody", 20, 500);
			item6 = new Armor("Adamantite Platelegs", (int) Math.ceil(user
					.getDefense() * .2), "platelegs", 20, 500);
		} else if (item.itemName.toLowerCase().contains("rune")) {
			System.out.println("1 Lvl 25 Rune Boots (3 rune bars)");
			System.out.println("2 Lvl 25 Rune Helmet (3 rune bars)");
			System.out.println("3 Lvl 25 Rune Shield (3 rune bars)");
			System.out.println("4 Lvl 25 Rune Sword (5 rune bars)");
			System.out.println("5 Lvl 25 Rune Platebody (5 rune bars)");
			System.out.println("6 Lvl 25 Rune Platelegs (5 rune bars)");
			item1 = new Armor("Rune Boots",
					(int) Math.ceil(user.getDefense() * .25), "boots", 25, 1000);
			item2 = new Armor("Rune Helmet",
					(int) Math.ceil(user.getDefense() * .25), "helmet", 25,
					1000);
			item3 = new Armor("Rune Shield",
					(int) Math.ceil(user.getDefense() * .25), "shield", 25,
					1000);
			item4 = new Weapon("Rune Sword",
					(int) Math.ceil(user.getAttack() * .35), "sword", 25, 1000);
			item5 = new Armor("Rune Platebody", (int) Math.ceil(user
					.getDefense() * .25), "platebody", 25, 1000);
			item6 = new Armor("Rune Platelegs", (int) Math.ceil(user
					.getDefense() * .25), "platelegs", 25, 1000);
		}
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
		} else if (choice == 3) {
			if (item3 != null) {
				smelt(item3);
			}
		} else if (choice == 4) {
			if (item4 != null) {
				smelt(item4);
			}
		} else if (choice == 5) {
			if (item5 != null) {
				smelt(item5);
			}
		} else if (choice == 6) {
			if (item6 != null) {
				smelt(item6);
			}
		}
	}

	public static void smelt(Item i) {
		if (i.itemName.toLowerCase().contains("sapphire ring")
				&& getNumberOfItemByName("Gold Bar") >= 1
				&& user.getLevel("Smithing") >= 1) {
			user.itemList.remove(getItemByName("Sapphire"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Sapphire Ring!");
			user.getLevelObject("Smithing").gainXp(200);
		} else if (i.itemName.toLowerCase().contains("sapphire necklace")
				&& getNumberOfItemByName("Gold Bar") >= 2
				&& user.getLevel("Smithing") >= 3) {
			user.itemList.remove(getItemByName("Sapphire"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Sapphire Necklace!");
			user.getLevelObject("Smithing").gainXp(250);
		} else if (i.itemName.toLowerCase().contains("emerald ring")
				&& getNumberOfItemByName("Gold Bar") >= 1
				&& user.getLevel("Smithing") >= 6) {
			user.itemList.remove(getItemByName("Emerald"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Emerald Ring!");
			user.getLevelObject("Smithing").gainXp(400);
		} else if (i.itemName.toLowerCase().contains("emerald necklace")
				&& getNumberOfItemByName("Gold Bar") >= 2
				&& user.getLevel("Smithing") >= 9) {
			user.itemList.remove(getItemByName("Emerald"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Emerald Necklace!");
			user.getLevelObject("Smithing").gainXp(450);
		} else if (i.itemName.toLowerCase().contains("ruby ring")
				&& getNumberOfItemByName("Gold Bar") >= 1
				&& user.getLevel("Smithing") >= 12) {
			user.itemList.remove(getItemByName("Ruby"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Ruby Ring!");
			user.getLevelObject("Smithing").gainXp(750);
		} else if (i.itemName.toLowerCase().contains("ruby necklace")
				&& getNumberOfItemByName("Gold Bar") >= 2
				&& user.getLevel("Smithing") >= 15) {
			user.itemList.remove(getItemByName("Ruby"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Ruby Necklace!");
			user.getLevelObject("Smithing").gainXp(800);
		} else if (i.itemName.toLowerCase().contains("diamond ring")
				&& getNumberOfItemByName("Gold Bar") >= 1
				&& user.getLevel("Smithing") >= 18) {
			user.itemList.remove(getItemByName("Diamond"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Diamond Ring!");
			user.getLevelObject("Smithing").gainXp(1000);
		} else if (i.itemName.toLowerCase().contains("diamond necklace")
				&& getNumberOfItemByName("Gold Bar") >= 2
				&& user.getLevel("Smithing") >= 21) {
			user.itemList.remove(getItemByName("Diamond"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Diamond Necklace!");
			user.getLevelObject("Smithing").gainXp(1100);
		} else {
			String type = i.getName().substring(0, i.getName().indexOf(" "))
					.trim();
			if (!checkIfCanMakeArmor(i, type)) {
				System.out
						.println("You either do not have a high enough level or do not have enough required items to craft this!");
			}
		}
	}

	public static int getRightXP(String type) {
		int xp = 0;
		if (type.equalsIgnoreCase("bronze")) {
			xp = 75;
		} else if (type.equalsIgnoreCase("iron")) {
			xp = 150;
		} else if (type.equalsIgnoreCase("steel")) {
			xp = 350;
		} else if (type.equalsIgnoreCase("mithril")) {
			xp = 750;
		} else if (type.equalsIgnoreCase("adamantite")) {
			xp = 1000;
		} else if (type.equalsIgnoreCase("rune")) {
			xp = 1500;
		} else if (type.equalsIgnoreCase("Small HP Potion")) {
			xp = 150;
		} else if (type.equalsIgnoreCase("Medium HP Potion")) {
			xp = 250;
		} else if (type.equalsIgnoreCase("Large HP Potion")) {
			xp = 350;
		} else if (type.equalsIgnoreCase("Giant HP Potion")) {
			xp = 450;
		} else if (type.equalsIgnoreCase("Small Defence Potion")) {
			xp = 300;
		} else if (type.equalsIgnoreCase("Medium Defence Potion")) {
			xp = 400;
		} else if (type.equalsIgnoreCase("Large Defence Potion")) {
			xp = 500;
		} else if (type.equalsIgnoreCase("Giant Defence Potion")) {
			xp = 600;
		} else if (type.equalsIgnoreCase("Small Attack Potion")) {
			xp = 450;
		} else if (type.equalsIgnoreCase("Medium Attack Potion")) {
			xp = 550;
		} else if (type.equalsIgnoreCase("Large Attack Potion")) {
			xp = 650;
		} else if (type.equalsIgnoreCase("Giant Attack Potion")) {
			xp = 750;
		} else if (type.equalsIgnoreCase("Small Attack Potion")) {
			xp = 700;
		} else if (type.equalsIgnoreCase("Medium Attack Potion")) {
			xp = 800;
		} else if (type.equalsIgnoreCase("Large Attack Potion")) {
			xp = 900;
		} else if (type.equalsIgnoreCase("Giant Attack Potion")) {
			xp = 1000;
		}
		return xp;
	}

	public static boolean checkIfCanMakeArmor(Item i, String type) {
		if (i.itemName.toLowerCase().contains("boots")
				&& getNumberOfItemByName(type + " Bar") >= 3
				&& user.getLevel("Smithing") >= (getItemByName(type + " Bar").itemRequiredLevel)) {
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft " + type + " Boots!");

			user.getLevelObject("Smithing").gainXp(getRightXP(type));
			return true;
		} else if (i.itemName.toLowerCase().contains("helmet")
				&& getNumberOfItemByName(type + " Bar") >= 3
				&& user.getLevel("Smithing") >= (getItemByName(type + " Bar").itemRequiredLevel)) {
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a " + type + " Helmet!");
			user.getLevelObject("Smithing").gainXp(getRightXP(type));
			return true;
		} else if (i.itemName.toLowerCase().contains("shield")
				&& getNumberOfItemByName(type + " Bar") >= 3
				&& user.getLevel("Smithing") >= (getItemByName(type + " Bar").itemRequiredLevel)) {
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a " + type + " Shield!");
			user.getLevelObject("Smithing").gainXp(getRightXP(type));
			return true;
		} else if (i.itemName.toLowerCase().contains("sword")
				&& getNumberOfItemByName(type + " Bar") >= 5
				&& user.getLevel("Smithing") >= (getItemByName(type + " Bar").itemRequiredLevel)) {
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a " + type + " Sword!");
			user.getLevelObject("Smithing").gainXp(getRightXP(type));
			return true;
		} else if (i.itemName.toLowerCase().contains("platebody")
				&& getNumberOfItemByName(type + " Bar") >= 5
				&& user.getLevel("Smithing") >= (getItemByName(type + " Bar").itemRequiredLevel)) {
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a " + type
					+ " Platebody!");
			user.getLevelObject("Smithing").gainXp(getRightXP(type));
			return true;
		} else if (i.itemName.toLowerCase().contains("platelegs")
				&& getNumberOfItemByName(type + " Bar") >= 5
				&& user.getLevel("Smithing") >= (getItemByName(type + " Bar").itemRequiredLevel)) {
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.add(i);
			System.out
					.println("You successfully craft " + type + " Platelegs!");
			user.getLevelObject("Smithing").gainXp(getRightXP(type));
			return true;
		} else {
			return false;
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

	public static void potionList(Ingredient item) {
		if (item.itemType.contains("vial")
				|| item.itemType.contains("ingredient")) {
			System.out.println("Please choose a herb to make a potion!");
			return;
		}
		if (item.itemType.contains("herb")) {
			Item item1 = null, item2 = null, item3 = null, item4 = null;
			if (item.itemName.toLowerCase().contains("red")) {
				System.out
						.println("1 Lvl 1 Small HP Potion (1 Vial, 1 Red Syrum, 1 Red Herb)");
				System.out
						.println("2 Lvl 8 Medium HP Potion (1 Vial, 1 Blue Syrum, 1 Red Herb)");
				System.out
						.println("3 Lvl 20 Large HP Potion (1 Vial, 1 Green Syrum, 1 Red Herb)");
				System.out
						.println("4 Lvl 26 Giant HP Potion (1 Vial, 1 White Syrum, 1 Red Herb)");
				item1 = new Consumable("Small HP Potion",
						(int) (user.getTotalHealth() * .1), "potion", 1, 0, 0);
				((Consumable) item1).potionRequiredLevel = 1;
				((Consumable) item1).potionSize = "small";
				((Consumable) item1).potionType = "hp";
				((Consumable) item1).herbColor = "red";
				((Consumable) item1).syrumColor = "red";
				item2 = new Consumable("Medium HP Potion",
						(int) (user.getTotalHealth() * .25), "potion", 1, 0, 0);
				((Consumable) item2).potionRequiredLevel = 8;
				((Consumable) item2).potionSize = "medium";
				((Consumable) item2).potionType = "hp";
				((Consumable) item2).herbColor = "red";
				((Consumable) item2).syrumColor = "blue";
				item3 = new Consumable("Large HP Potion",
						(int) (user.getTotalHealth() * .35), "potion", 1, 0, 0);
				((Consumable) item3).potionRequiredLevel = 20;
				((Consumable) item3).potionSize = "large";
				((Consumable) item3).potionType = "hp";
				((Consumable) item3).herbColor = "red";
				((Consumable) item3).syrumColor = "green";
				item4 = new Consumable("Giant HP Potion",
						(int) (user.getTotalHealth() * .5), "potion", 1, 0, 0);
				((Consumable) item4).potionRequiredLevel = 26;
				((Consumable) item4).potionSize = "giant";
				((Consumable) item4).potionType = "hp";
				((Consumable) item4).herbColor = "red";
				((Consumable) item4).syrumColor = "white";
			} else if (item.itemName.toLowerCase().contains("blue")) {
				System.out
						.println("1 Lvl 2 Small Defence Potion (1 Vial, 1 Red Syrum, 1 Blue Herb)");
				System.out
						.println("2 Lvl 10 Medium Defence Potion (1 Vial, 1 Red Syrum, 1 Blue Herb)");
				System.out
						.println("3 Lvl 17 Large Defence Potion (1 Vial, 1 Red Syrum, 1 Blue Herb)");
				System.out
						.println("4 Lvl 23 Giant Defence Potion (1 Vial, 1 Red Syrum, 1 Blue Herb)");
				item1 = new Consumable("Small Defence Potion", 0, "potion", 1,
						0, (int) (user.getDefense() * .02));
				((Consumable) item1).potionRequiredLevel = 2;
				((Consumable) item1).potionSize = "small";
				((Consumable) item1).potionType = "defence";
				((Consumable) item1).herbColor = "blue";
				((Consumable) item1).syrumColor = "red";
				item2 = new Consumable("Medium Defence Potion", 0, "potion", 1,
						0, (int) (user.getDefense() * .05));
				((Consumable) item2).potionRequiredLevel = 10;
				((Consumable) item2).potionSize = "medium";
				((Consumable) item2).potionType = "defence";
				((Consumable) item2).herbColor = "blue";
				((Consumable) item2).syrumColor = "blue";
				item3 = new Consumable("Large Defence Potion", 0, "potion", 1,
						0, (int) (user.getDefense() * .1));
				((Consumable) item3).potionRequiredLevel = 17;
				((Consumable) item3).potionSize = "large";
				((Consumable) item3).potionType = "defence";
				((Consumable) item3).herbColor = "blue";
				((Consumable) item3).syrumColor = "green";
				item4 = new Consumable("Giant Defence Potion", 0, "potion", 1,
						0, (int) (user.getDefense() * .15));
				((Consumable) item4).potionRequiredLevel = 23;
				((Consumable) item4).potionSize = "giant";
				((Consumable) item4).potionType = "defence";
				((Consumable) item4).herbColor = "blue";
				((Consumable) item4).syrumColor = "white";
			} else if (item.itemName.toLowerCase().contains("green")) {
				System.out
						.println("1 Lvl 4 Small Attack Potion (1 Vial, 1 Red Syrum, 1 Green Herb)");
				System.out
						.println("2 Lvl 12 Medium Attack Potion (1 Vial, 1 Blue Syrum, 1 Green Herb)");
				System.out
						.println("3 Lvl 19 Large Attack Potion (1 Vial, 1 Green Syrum, 1 Green Herb)");
				System.out
						.println("4 Lvl 25 Giant Attack Potion (1 Vial, 1 White Syrum, 1 Green Herb)");
				item1 = new Consumable("Small Attack Potion", 0, "potion", 1,
						(int) (user.getAttack() * .02), 0);
				((Consumable) item1).potionRequiredLevel = 4;
				((Consumable) item1).potionSize = "small";
				((Consumable) item1).potionType = "attack";
				((Consumable) item1).herbColor = "green";
				((Consumable) item1).syrumColor = "red";
				item2 = new Consumable("Medium Attack Potion", 0, "potion", 1,
						(int) (user.getAttack() * .05), 0);
				((Consumable) item2).potionRequiredLevel = 12;
				((Consumable) item2).potionSize = "medium";
				((Consumable) item2).potionType = "attack";
				((Consumable) item2).herbColor = "green";
				((Consumable) item2).syrumColor = "blue";
				item3 = new Consumable("Large Attack Potion", 0, "potion", 1,
						(int) (user.getAttack() * .1), 0);
				((Consumable) item3).potionRequiredLevel = 19;
				((Consumable) item3).potionSize = "large";
				((Consumable) item3).potionType = "attack";
				((Consumable) item3).herbColor = "green";
				((Consumable) item3).syrumColor = "green";
				item4 = new Consumable("Giant Attack Potion", 0, "potion", 1,
						(int) (user.getAttack() * .15), 0);
				((Consumable) item4).potionRequiredLevel = 25;
				((Consumable) item4).potionSize = "giant";
				((Consumable) item4).potionType = "attack";
				((Consumable) item4).herbColor = "green";
				((Consumable) item4).syrumColor = "white";
			} else if (item.itemName.toLowerCase().contains("white")) {
				System.out
						.println("1 Lvl 8 Small Super Potion (1 Vial, 1 Red Syrum, 1 White Herb)");
				System.out
						.println("2 Lvl 16 Medium Super Potion (1 Vial, 1 Blue Syrum, 1 White Herb)");
				System.out
						.println("3 Lvl 24 Large Super Potion (1 Vial, 1 Green Syrum, 1 White Herb)");
				System.out
						.println("4 Lvl 29 Giant Super Potion (1 Vial, 1 White Syrum, 1 White Herb)");
				item1 = new Consumable("Small Super Potion",
						(int) (user.getTotalHealth() * .05), "potion", 1,
						(int) (user.getAttack() * .01),
						(int) (user.getDefense() * .01));
				((Consumable) item1).potionRequiredLevel = 8;
				((Consumable) item1).potionSize = "small";
				((Consumable) item1).potionType = "super";
				((Consumable) item1).herbColor = "white";
				((Consumable) item1).syrumColor = "red";
				item2 = new Consumable("Medium Super Potion",
						(int) (user.getTotalHealth() * .1), "potion", 1,
						(int) (user.getAttack() * .03),
						(int) (user.getDefense() * .03));
				((Consumable) item2).potionRequiredLevel = 16;
				((Consumable) item2).potionSize = "medium";
				((Consumable) item2).potionType = "super";
				((Consumable) item2).herbColor = "white";
				((Consumable) item2).syrumColor = "blue";
				item3 = new Consumable("Large Super Potion",
						(int) (user.getTotalHealth() * .15), "potion", 1,
						(int) (user.getAttack() * .07),
						(int) (user.getDefense() * .07));
				((Consumable) item3).potionRequiredLevel = 24;
				((Consumable) item3).potionSize = "large";
				((Consumable) item3).potionType = "super";
				((Consumable) item3).herbColor = "white";
				((Consumable) item3).syrumColor = "green";
				item4 = new Consumable("Giant Super Potion",
						(int) (user.getTotalHealth() * .2), "potion", 1,
						(int) (user.getAttack() * .010),
						(int) (user.getDefense() * .10));
				((Consumable) item4).potionRequiredLevel = 29;
				((Consumable) item4).potionSize = "giant";
				((Consumable) item4).potionType = "super";
				((Consumable) item4).herbColor = "white";
				((Consumable) item4).syrumColor = "white";
			}
			int choice = 0;
			choice = sc.nextInt();
			if (choice == 1) {
				if (item1 != null) {
					checkIfCanMakePotion(item1, ((Consumable) item1).herbColor,
							((Consumable) item1).syrumColor,
							((Consumable) item1).potionRequiredLevel,
							((Consumable) item1).potionSize,
							((Consumable) item1).potionType);
				}
			} else if (choice == 2) {
				if (item2 != null) {
					checkIfCanMakePotion(item2, ((Consumable) item2).herbColor,
							((Consumable) item2).syrumColor,
							((Consumable) item2).potionRequiredLevel,
							((Consumable) item2).potionSize,
							((Consumable) item2).potionType);
				}
			} else if (choice == 3) {
				if (item3 != null) {
					checkIfCanMakePotion(item3, ((Consumable) item3).herbColor,
							((Consumable) item3).syrumColor,
							((Consumable) item3).potionRequiredLevel,
							((Consumable) item3).potionSize,
							((Consumable) item3).potionType);
				}
			} else if (choice == 4) {
				if (item4 != null) {
					checkIfCanMakePotion(item4, ((Consumable) item4).herbColor,
							((Consumable) item4).syrumColor,
							((Consumable) item4).potionRequiredLevel,
							((Consumable) item4).potionSize,
							((Consumable) item4).potionType);
				}
			}
		}
	}

	public static void checkIfCanMakePotion(Item i, String herbColor,
			String syrumColor, int itemLevel, String itemSize, String potionType) {
		if (i.itemName.toLowerCase().contains(itemSize + " " + potionType)
				&& getNumberOfItemByName(syrumColor + " Syrum") >= 1
				&& getNumberOfItemByName("Vial") >= 1
				&& getNumberOfItemByName(herbColor + " Herb") >= 1
				&& user.getLevel("Herblore") >= itemLevel) {
			user.itemList.remove(getItemByName(syrumColor + " Syrum"));
			user.itemList.remove(getItemByName(herbColor + " Herb"));
			user.itemList.remove(getItemByName("Vial"));
			user.itemList.add(i);
			System.out.println("You successfully create a " + itemSize + " "
					+ potionType + " potion!");
			user.getLevelObject("Herblore").gainXp(getRightXP(i.itemName));
		} else {
			System.out
					.println("You either do not have a high enough level or do not have enough required items to create this potion!");
		}
	}

	public static void loopActions(User user) throws IOException {
		while (true) {
			sc = new Scanner(System.in);
			String choice = null;
			user.displayStats();
			System.out.println("What would you like to do?");
			System.out
					.println("Journal (j)\tCombat (c)\tEat All (z)\tInventory (i)\tEquipped Items (e)\tShop (s)"
							+ "\tHospital (h)\tFishing (f)\tMining (m)\tQuit (q)\tDelete User (d)");
			choice = sc.next();
			if (choice == null) {
				choice = sc.nextLine().toLowerCase();
			}
			if (choice.startsWith("j")) {
				journal();
			} else if (choice.startsWith("c")) {
				System.out
						.println("What level enemy do you want to fight? (Type 0 to exit): ");
				String level = sc.next();
				if (isInteger(level) && (Integer.parseInt(level) > 0)
						&& (Integer.parseInt(level) <= 30)) {
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
			} else if (choice.startsWith("z")) {
				eatAll();
			} else if (choice.startsWith("q")) {
				saveData(user);
				System.exit(0);
			} else if (choice.startsWith("d")) {
				System.out
						.println("Are you sure you want to delete your account? (y/n)");
				sc = new Scanner(System.in);
				choice = sc.next();
				if (choice.startsWith("y")) {
					deleteUser();
				}
			}
			if (user.getCurrentHealth() < 1) {
				user.setCurrentHealth(user.getTotalHealth());
			}
			user.checkForLevelUp();
			saveData(user);
		}
	}

	public static void journal() {
		System.out.println("1	Level Guide");
		System.out.println("Select option:");
		int choice = 0;
		try {
			choice = sc.nextInt();
		} catch (Exception e) {
			return;
		}
		if (choice == 1) {
			levelGuide();
		}
	}

	public static void levelGuide() {
		System.out.println("1	Combat");
		System.out.println("2	Fishing");
		System.out.println("3	Cooking");
		System.out.println("4	Mining");
		System.out.println("5	Smelting");
		System.out.println("6	Herblore");
		System.out.println("Select option:");
		int choice = 0;
		try {
			choice = sc.nextInt();
		} catch (Exception e) {
			return;
		}
		if (choice == 1) {
			System.out.println("Lvl COMBAT:");
			System.out.println("Lvl 1	Equip All Jewelry");
			System.out.println("Lvl 1	Equip Bronze Weapons + Armor");
			System.out.println("Lvl 5	Equip Iron Weapons + Armor");
			System.out.println("Lvl 10	Equip Steel Weapons + Armor");
			System.out.println("Lvl 15	Equip Mithril Weapons + Armor");
			System.out.println("Lvl 20	Equip Adamantite Weapons + Armor");
			System.out.println("Lvl 25	Equip Rune Weapons + Armor");
		} else if (choice == 2) {
			System.out.println("Lvl FISHING:");
			System.out.println("Lvl 1	Catch Shrimp");
			System.out.println("Lvl 5	Catch Fish");
			System.out.println("Lvl 10	Catch Lobster");
			System.out.println("Lvl 15	Catch Swordfish");
			System.out.println("Lvl 20	Catch Coin Crate");
			System.out.println("Lvl 25	Catch Shark");
		} else if (choice == 3) {
			System.out.println("Lvl COOKING:");
			System.out.println("Lvl 1	Cook Shrimp");
			System.out.println("Lvl 5	Cook Fish");
			System.out.println("Lvl 10	Cook Lobster");
			System.out.println("Lvl 15	Cook Swordfish");
			System.out.println("Lvl 20	Cook Shark");
		} else if (choice == 4) {
			System.out.println("Lvl MINING:");
			System.out.println("Lvl 1	Mine All Gems");
			System.out.println("Lvl 1	Mine Gold Ore");
			System.out.println("Lvl 1	Mine Bronze Ore");
			System.out.println("Lvl 5	Mine Iron Ore");
			System.out.println("Lvl 10	Mine Steel Ore");
			System.out.println("Lvl 15	Mine Mithril Ore");
			System.out.println("Lvl 20	Mine Adamantite Ore");
			System.out.println("Lvl 25	Mine Rune Ore");
		} else if (choice == 5) {
			System.out.println("Lvl SMITHING:");
			System.out.println("Lvl 1	Smith Gold Bar");
			System.out.println("Lvl 1	Smith Bronze Bar");
			System.out.println("Lvl 1	Smith Sapphire Ring");
			System.out.println("Lvl 3	Smith Sapphire Necklace");
			System.out.println("Lvl 5	Smith Iron Bar");
			System.out.println("Lvl 6	Smith Emerald Ring");
			System.out.println("Lvl 9	Smith Emerald Necklace");
			System.out.println("Lvl 10	Smith Steel Bar");
			System.out.println("Lvl 12	Smith Ruby Ring");
			System.out.println("Lvl 15	Smith Ruby Necklace");
			System.out.println("Lvl 15	Smith Mithril Bar");
			System.out.println("Lvl 18	Smith Diamond Ring");
			System.out.println("Lvl 20	Smith Adamantite Bar");
			System.out.println("Lvl 21	Smith Diamond Necklace");
			System.out.println("Lvl 25	Smith Rune Bar");
		} else if (choice == 6) {
			System.out.println("Lvl 1	Small HP Potion");
			System.out.println("Lvl 2	Small Defence Potion");
			System.out.println("Lvl 4	Small Attack Potion");
			System.out.println("Lvl 8	Small Super Potion");
			System.out.println("Lvl 8	Medium HP Potion");
			System.out.println("Lvl 10	Medium Defence Potion");
			System.out.println("Lvl 12	Medium Attack Potion");
			System.out.println("Lvl 16	Medium Super Potion");
			System.out.println("Lvl 17	Large Defence Potion");
			System.out.println("Lvl 19	Large Attack Potion");
			System.out.println("Lvl 20	Large HP Potion");
			System.out.println("Lvl 23	Giant Defence Potion");
			System.out.println("Lvl 24	Large Super Potion");
			System.out.println("Lvl 25	Large Attack Potion");
			System.out.println("Lvl 26	Giant HP Potion");
			System.out.println("Lvl 29	Giant Super Potion");
		}
		System.out.println();
		System.out.println("Press anything to return...");
		String choice2 = null;
		while (choice2 == null) {
			choice2 = sc.next();
		}
	}

	public static void eatAll() {
		List<Consumable> list = getListOfFood();
		String result = "";
		while (user.getCurrentHealth() < user.getTotalHealth()
				&& !list.isEmpty()) {
			if (user.getCurrentHealth() + list.get(0).getHealAmount() > user
					.getTotalHealth()) {
				break;
			}
			user.heal(list.get(0).getHealAmount());
			result += list.get(0).itemName + ", ";
			user.itemList.remove(list.get(0));
			list.remove(0);
		}
		if (result.isEmpty()) {
			System.out
					.println("You either don't have any useful food or are close to full HP already.");
		} else {
			result = result.trim().substring(0, result.length() - 2);
			System.out.println("You eat... " + result + "!");
		}
	}

	public static List<Consumable> getListOfFood() {
		List<Consumable> list = new ArrayList<>();
		for (Item i : user.itemList) {
			if (i instanceof Consumable) {
				list.add((Consumable) i);
			}
		}
		Collections.sort(list, new Comparator<Consumable>() {
			public int compare(Consumable o1, Consumable o2) {
				return o1.getHealAmount() - o2.getHealAmount();
			}
		});
		return list;
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
		int count = 1;
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
			choice -= 1;
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
			System.out.println("1	Vial\t\t\t50 Gold");
			System.out.println("2	Red Syrum\t\t50 Gold");
			System.out.println("3	Blue Syrum\t\t100 Gold");
			System.out.println("4	Green Syrum\t\t200 Gold");
			System.out.println("5	White Syrum\t\t500 Gold");
			System.out.println("6	Small HP Potion\t\t100 Gold");
			System.out.println("7	Medium HP Potion\t250 Gold");
			System.out.println("8	Large HP Potion\t\t500 Gold");
			Item i = null;
			int choice2 = 0;
			try {
				choice2 = sc.nextInt();
			} catch (Exception e) {
				return;
			}
			if (choice2 == 1) {
				i = new Ingredient("Vial", "vial", 1, 50);
			} else if (choice2 == 2) {
				i = new Ingredient("Red Syrum", "ingredient", 1, 50);
			} else if (choice2 == 3) {
				i = new Ingredient("Blue Syrum", "ingredient", 1, 100);
			} else if (choice2 == 4) {
				i = new Ingredient("Green Syrum", "ingredient", 1, 200);
			} else if (choice2 == 5) {
				i = new Ingredient("White Syrum", "ingredient", 1, 250);
			} else if (choice2 == 6) {
				i = new Consumable("Small Potion",
						((int) (user.getTotalHealth() * .1)), "consumable",
						100, 0, 0);
			} else if (choice2 == 7) {
				i = new Consumable("Medium Potion",
						((int) (user.getTotalHealth() * .25)), "consumable",
						250, 0, 0);
			} else if (choice2 == 8) {
				i = new Consumable("Large Potion",
						((int) (user.getTotalHealth() * .5)), "consumable",
						500, 0, 0);
			}
			System.out.println("How many do you want to buy?");
			choice2 = 0;
			try {
				choice2 = sc.nextInt();
				if (user.getMoney() < i.getItemValue() * choice2) {
					System.out
							.println("You do not have enough money to purchase this item!");
					return;
				}
				for (int j = 0; j < choice2; j++) {
					user.setMoney(user.getMoney() - (i.getItemValue()));
					user.itemList.add(i);
				}
			} catch (Exception e) {
				return;
			}
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
		int count = 1;
		System.out.println("Item list:");
		for (Item i : user.itemList) {
			if (i instanceof Cookable
					|| i.itemType.toLowerCase().equals("cookable")) {
				System.out.println(count + "\t" + i.getName()
						+ "\tRequired Level To COOK: " + i.getRequiredLevel());
			} else if (i instanceof Consumable
					|| i.itemType.toLowerCase().equals("consumable")) {
				Consumable c = (Consumable) i;
				if (c.herbColor != null) {
					System.out.println(count + "\t" + c.getName() + "\tHeals: "
							+ c.getHealAmount() + "\tAttack Bonus: "
							+ c.attackBoost + "\tDefense Bonus: "
							+ c.defenseBoost);
				} else {
					System.out.println(count + "\t" + c.getName() + "\tHeals: "
							+ c.getHealAmount());
				}
			} else if (i instanceof Weapon || i instanceof Armor
					|| i instanceof Jewelry
					|| i.itemType.toLowerCase().equals("armor")
					|| i.itemType.toLowerCase().equals("weapon")
					|| i.itemType.toLowerCase().equals("jewelry")) {
				String defDiff = "0", attDiff = "0", hpDiff = "0";
				if (i instanceof Weapon) {
					attDiff = String.valueOf(((Weapon) i).attackBoost);
				} else if (i instanceof Armor) {
					defDiff = String.valueOf(((Armor) i).defenseBoost);
				} else if (i instanceof Jewelry) {
					attDiff = String.valueOf(((Jewelry) i).attackBoost);
					defDiff = String.valueOf(((Jewelry) i).defenseBoost);
					hpDiff = String.valueOf(((Jewelry) i).hpBoost);
				}
				for (Item item : user.equippedItems) {
					if (item.itemType.toLowerCase().equals(i.itemType)) {
						if (item instanceof Weapon) {
							attDiff = String.valueOf(((Weapon) i)
									.getAttackBoost()
									- ((Weapon) item).getAttackBoost());
						} else if (item instanceof Armor) {
							defDiff = String.valueOf(((Armor) i)
									.getDefenseBoost()
									- ((Armor) item).getDefenseBoost());
						} else if (item instanceof Jewelry) {
							attDiff = String.valueOf(((Jewelry) i)
									.getAttackBoost()
									- ((Jewelry) item).getAttackBoost());
							defDiff = String.valueOf(((Jewelry) i)
									.getDefenseBoost()
									- ((Jewelry) item).getDefenseBoost());
							hpDiff = String.valueOf(((Jewelry) i).getHpBoost()
									- ((Jewelry) item).getHpBoost());
						}
						break;
					}
				}
				if (Integer.parseInt(attDiff) >= 0) {
					attDiff = "+" + attDiff;
				}
				if (Integer.parseInt(defDiff) >= 0) {
					defDiff = "+" + defDiff;
				}
				if (Integer.parseInt(hpDiff) >= 0) {
					hpDiff = "+" + hpDiff;
				}
				System.out.println(count + "\t" + i.getName() + " (" + hpDiff
						+ " HP, " + attDiff + " ATT, " + defDiff + " DEF)"
						+ "\tRequired Level To EQUIP: " + i.getRequiredLevel());
			} else if (i instanceof Ore) {
				System.out.println(count + "\t" + i.getName()
						+ "\tRequired Level to SMITH:" + i.getRequiredLevel());
			} else if (i instanceof Smeltable) {
				System.out.println(count + "\t" + i.getName());
			} else if (i instanceof Ingredient) {
				System.out.println(count + "\t" + i.getName());
			}
			count++;
		}
		System.out.print("Type a number to select item or a letter to cancel:");
		try {
			int choice = sc.nextInt();
			user.useItem(choice - 1);
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

	public static int determineFirst(int enemySpeed, int userSpeed) {
		if (enemySpeed == 0) {
			enemySpeed = 1;
		}
		double result = ((double) userSpeed / (double) enemySpeed);
		result += new Random().nextDouble() - new Random().nextDouble();
		if (result < 1) {
			return 1;
		}
		return 0;
	}

	public static void fight(Enemy enemy) throws IOException {
		int count = 0;
		int damage;
		count = determineFirst(enemy.enemySpeed, user.getSpeed());
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
				revertStats();
				return;
			}
			if (user.getCurrentHealth() <= 0) {
				if (!user.hardcoreMode) {
					int amountGold = (int) (user.getMoney() * 0.5);
					user.setMoney(user.getMoney() - amountGold);
					System.out.println("You have died! You lost " + amountGold
							+ " gold!");
					user.setCurrentHealth(user.getTotalHealth());
					revertStats();
					return;
				} else {
					System.out.println("You have died! AYYYY LMAO! (RIP)");
					deleteUser();
					return;
				}
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
						revertStats();
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
						if (enemy.enemyHp <= 0) {
							count++;
							break;
						}
						damage = damage(enemy.enemyAttack, user.getDefense());
						user.setCurrentHealth(user.getCurrentHealth() - damage);
						System.out.println(enemy.enemyName + " does " + damage
								+ " damage!");
					}
					count++;
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

	public static void revertStats() {
		user.statPotionUsed = false;
		if (user.preAttack != 0) {
			user.setAttack(user.preAttack);
		}
		if (user.preDefense != 0) {
			user.setDefense(user.preDefense);
		}
		user.preAttack = 0;
		user.preDefense = 0;
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
		} else {
			return new Random().nextInt(100) + 1 < 20;
		}
	}

	public static int damage(int attackerAtt, int defenderDef) {
		if (defenderDef == 0) {
			defenderDef = 1;
		}
		double totalAttackPower = (attackerAtt / defenderDef)
				+ new Random().nextDouble() - .5;
		double result = (Math.abs(totalAttackPower) * attackerAtt);
		return (int) result;
	}
}
