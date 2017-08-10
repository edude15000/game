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
				user = new User(100, 100, 10000, 10, 10, 10);
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
		while (choice2 != 1 && choice2 != 2 && choice2 != 3 && choice2 != 4 && choice2 != 5) {
			System.out.println("What class do you want to be?");
			System.out.println("1 Barbarian (+25 Attack, -15 HP)");
			System.out.println("2 Knight (+15 Defence, +20 HP)");
			System.out.println("3 Thief (+10 Attack, +5% Gold from enemies)");
			System.out.println("4 Warrior (+10 Attack, +10 Defense)");
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
		gsonBuilder.registerTypeAdapter(Item.class, new InterfaceAdapter<Item>());
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
			gsonBuilder.registerTypeAdapter(Item.class, new InterfaceAdapter<Item>());
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
			System.out.println("1 Lvl 1 Sapphire Ring (1 gold bar, 1 sapphire)");
			System.out.println("2 Lvl 3 Sapphire Necklace (2 gold bars, 1 sapphire)");
			item1 = new Jewelry("Sapphire Ring", 0, 0, (int) (user.getDefense() * .05), 50, "ring");
			item2 = new Jewelry("Sapphire Necklace", 0, 0, (int) (user.getDefense() * .05), 50, "necklace");
		} else if (item.itemName.toLowerCase().contains("emerald")) {
			System.out.println("1 Lvl 6 Emerald Ring (1 gold bar, 1 emerald)");
			System.out.println("2 Lvl 9 Emerald Necklace (2 gold bars, 1 emerald)");
			item1 = new Jewelry("Emerald Ring", (int) (user.getTotalHealth() * .05), 0, 0, 50, "ring");
			item2 = new Jewelry("Emerald Necklace", (int) (user.getTotalHealth() * .05), 0, 0, 50, "necklace");
		} else if (item.itemName.toLowerCase().contains("ruby")) {
			System.out.println("1 Lvl 12 Ruby Ring (1 gold bar, 1 ruby)");
			System.out.println("2 Lvl 15 Ruby Necklace (2 gold bars, 1 ruby)");
			item1 = new Jewelry("Ruby Ring", 0, (int) (user.getAttack() * .05), 0, 50, "ring");
			item2 = new Jewelry("Ruby Necklace", 0, (int) (user.getAttack() * .05), 0, 50, "necklace");
		} else if (item.itemName.toLowerCase().contains("diamond")) {
			System.out.println("1 Lvl 18 Diamond Ring (1 gold bar, 1 diamond)");
			System.out.println("2 Lvl 21 Diamond Necklace (2 gold bars, 1 diamond)");
			item1 = new Jewelry("Diamond Ring", (int) (user.getTotalHealth() * .03), (int) (user.getAttack() * .03),
					(int) (user.getDefense() * .03), 50, "ring");
			item2 = new Jewelry("Diamond Necklace", (int) (user.getTotalHealth() * .03), (int) (user.getAttack() * .03),
					(int) (user.getDefense() * .03), 50, "necklace");
		} else if (item.itemName.toLowerCase().contains("bronze")) {
			System.out.println("1 Lvl 1 Bronze Boots (3 bronze bars)");
			System.out.println("2 Lvl 1 Bronze Helmet (3 bronze bars)");
			System.out.println("3 Lvl 1 Bronze Shield (3 bronze bars)");
			System.out.println("4 Lvl 1 Bronze Sword (5 bronze bars)");
			System.out.println("5 Lvl 1 Bronze Platebody (5 bronze bars)");
			System.out.println("6 Lvl 1 Bronze Platelegs (5 bronze bars)");
			item1 = new Armor("Bronze Boots", (int) Math.ceil(user.getDefense() * .05), "boots", 1, 50);
			item2 = new Armor("Bronze Helmet", (int) Math.ceil(user.getDefense() * .05), "helmet", 1, 50);
			item3 = new Armor("Bronze Shield", (int) Math.ceil(user.getDefense() * .05), "shield", 1, 50);
			item4 = new Weapon("Bronze Sword", (int) Math.ceil(user.getAttack() * .05), "sword", 1, 50);
			item5 = new Armor("Bronze Platebody", (int) Math.ceil(user.getDefense() * .05), "platebody", 1, 50);
			item6 = new Armor("Bronze Platelegs", (int) Math.ceil(user.getDefense() * .05), "platelegs", 1, 50);
		} else if (item.itemName.toLowerCase().contains("iron")) {
			System.out.println("1 Lvl 5 Iron Boots (3 iron bars)");
			System.out.println("2 Lvl 5 Iron Helmet (3 iron bars)");
			System.out.println("3 Lvl 5 Iron Shield (3 iron bars)");
			System.out.println("4 Lvl 5 Iron Sword (5 iron bars)");
			System.out.println("5 Lvl 5 Iron Platebody (5 iron bars)");
			System.out.println("6 Lvl 5 Iron Platelegs (5 iron bars)");
			item1 = new Armor("Iron Boots", (int) Math.ceil(user.getDefense() * .1), "boots", 5, 100);
			item2 = new Armor("Iron Helmet", (int) Math.ceil(user.getDefense() * .1), "helmet", 5, 100);
			item3 = new Armor("Iron Shield", (int) Math.ceil(user.getDefense() * .1), "shield", 5, 100);
			item4 = new Weapon("Iron Sword", (int) Math.ceil(user.getAttack() * .1), "sword", 5, 100);
			item5 = new Armor("Iron Platebody", (int) Math.ceil(user.getDefense() * .1), "platebody", 5, 100);
			item6 = new Armor("Iron Platelegs", (int) Math.ceil(user.getDefense() * .1), "platelegs", 5, 100);
		} else if (item.itemName.toLowerCase().contains("steel")) {
			System.out.println("1 Lvl 10 Steel Boots (3 steel bars)");
			System.out.println("2 Lvl 10 Steel Helmet (3 steel bars)");
			System.out.println("3 Lvl 10 Steel Shield (3 steel bars)");
			System.out.println("4 Lvl 10 Steel Sword (5 steel bars)");
			System.out.println("5 Lvl 10 Steel Platebody (5 steel bars)");
			System.out.println("6 Lvl 10 Steel Platelegs (5 steel bars)");
			item1 = new Armor("Steel Boots", (int) Math.ceil(user.getDefense() * .05), "boots", 10, 200);
			item2 = new Armor("Steel Helmet", (int) Math.ceil(user.getDefense() * .15), "helmet", 10, 200);
			item3 = new Armor("Steel Shield", (int) Math.ceil(user.getDefense() * .15), "shield", 10, 200);
			item4 = new Weapon("Steel Sword", (int) Math.ceil(user.getAttack() * .2), "sword", 10, 200);
			item5 = new Armor("Steel Platebody", (int) Math.ceil(user.getDefense() * .15), "platebody", 10, 200);
			item6 = new Armor("Steel Platelegs", (int) Math.ceil(user.getDefense() * .15), "platelegs", 10, 200);
		} else if (item.itemName.toLowerCase().contains("mithril")) {
			System.out.println("1 Lvl 15 Mithril Boots (3 mithril bars)");
			System.out.println("2 Lvl 15 Mithril Helmet (3 mithril bars)");
			System.out.println("3 Lvl 15 Mithril Shield (3 mithril bars)");
			System.out.println("4 Lvl 15 Mithril Sword (5 mithril bars)");
			System.out.println("5 Lvl 15 Mithril Platebody (5 mithril bars)");
			System.out.println("6 Lvl 15 Mithril Platelegs (5 mithril bars)");
			item1 = new Armor("Mithril Boots", (int) Math.ceil(user.getDefense() * .175), "boots", 15, 350);
			item2 = new Armor("Mithril Helmet", (int) Math.ceil(user.getDefense() * .175), "helmet", 15, 350);
			item3 = new Armor("Mithril Shield", (int) Math.ceil(user.getDefense() * .175), "shield", 15, 350);
			item4 = new Weapon("Mithril Sword", (int) Math.ceil(user.getAttack() * .25), "sword", 15, 350);
			item5 = new Armor("Mithril Platebody", (int) Math.ceil(user.getDefense() * .175), "platebody", 15, 350);
			item6 = new Armor("Mithril Platelegs", (int) Math.ceil(user.getDefense() * .175), "platelegs", 15, 350);
		} else if (item.itemName.toLowerCase().contains("adamantite")) {
			System.out.println("1 Lvl 20 Adamantite Boots (3 adamantite bars)");
			System.out.println("2 Lvl 20 Adamantite Helmet (3 adamantite bars)");
			System.out.println("3 Lvl 20 Adamantite Shield (3 adamantite bars)");
			System.out.println("4 Lvl 20 Adamantite Sword (5 adamantite bars)");
			System.out.println("5 Lvl 20 Adamantite Platebody (5 adamantite bars)");
			System.out.println("6 Lvl 20 Adamantite Platelegs (5 adamantite bars)");
			item1 = new Armor("Adamantite Boots", (int) Math.ceil(user.getDefense() * .2), "boots", 20, 500);
			item2 = new Armor("Adamantite Helmet", (int) Math.ceil(user.getDefense() * .2), "helmet", 20, 500);
			item3 = new Armor("Adamantite Shield", (int) Math.ceil(user.getDefense() * .2), "shield", 20, 500);
			item4 = new Weapon("Adamantite Sword", (int) Math.ceil(user.getAttack() * .3), "sword", 20, 500);
			item5 = new Armor("Adamantite Platebody", (int) Math.ceil(user.getDefense() * .2), "platebody", 20, 500);
			item6 = new Armor("Adamantite Platelegs", (int) Math.ceil(user.getDefense() * .2), "platelegs", 20, 500);
		} else if (item.itemName.toLowerCase().contains("rune")) {
			System.out.println("1 Lvl 25 Rune Boots (3 rune bars)");
			System.out.println("2 Lvl 25 Rune Helmet (3 rune bars)");
			System.out.println("3 Lvl 25 Rune Shield (3 rune bars)");
			System.out.println("4 Lvl 25 Rune Sword (5 rune bars)");
			System.out.println("5 Lvl 25 Rune Platebody (5 rune bars)");
			System.out.println("6 Lvl 25 Rune Platelegs (5 rune bars)");
			item1 = new Armor("Rune Boots", (int) Math.ceil(user.getDefense() * .25), "boots", 25, 1000);
			item2 = new Armor("Rune Helmet", (int) Math.ceil(user.getDefense() * .25), "helmet", 25, 1000);
			item3 = new Armor("Rune Shield", (int) Math.ceil(user.getDefense() * .25), "shield", 25, 1000);
			item4 = new Weapon("Rune Sword", (int) Math.ceil(user.getAttack() * .35), "sword", 25, 1000);
			item5 = new Armor("Rune Platebody", (int) Math.ceil(user.getDefense() * .25), "platebody", 25, 1000);
			item6 = new Armor("Rune Platelegs", (int) Math.ceil(user.getDefense() * .25), "platelegs", 25, 1000);
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
		if (i.itemName.toLowerCase().contains("sapphire ring") && getNumberOfItemByName("Gold Bar") >= 1
				&& user.getLevel("Smithing") >= 1) {
			user.itemList.remove(getItemByName("Sapphire"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Sapphire Ring!");
			user.getLevelObject("Smithing").gainXp(200);
		} else if (i.itemName.toLowerCase().contains("sapphire necklace") && getNumberOfItemByName("Gold Bar") >= 2
				&& user.getLevel("Smithing") >= 3) {
			user.itemList.remove(getItemByName("Sapphire"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Sapphire Necklace!");
			user.getLevelObject("Smithing").gainXp(250);
		} else if (i.itemName.toLowerCase().contains("emerald ring") && getNumberOfItemByName("Gold Bar") >= 1
				&& user.getLevel("Smithing") >= 6) {
			user.itemList.remove(getItemByName("Emerald"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Emerald Ring!");
			user.getLevelObject("Smithing").gainXp(400);
		} else if (i.itemName.toLowerCase().contains("emerald necklace") && getNumberOfItemByName("Gold Bar") >= 2
				&& user.getLevel("Smithing") >= 9) {
			user.itemList.remove(getItemByName("Emerald"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Emerald Necklace!");
			user.getLevelObject("Smithing").gainXp(450);
		} else if (i.itemName.toLowerCase().contains("ruby ring") && getNumberOfItemByName("Gold Bar") >= 1
				&& user.getLevel("Smithing") >= 12) {
			user.itemList.remove(getItemByName("Ruby"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Ruby Ring!");
			user.getLevelObject("Smithing").gainXp(750);
		} else if (i.itemName.toLowerCase().contains("ruby necklace") && getNumberOfItemByName("Gold Bar") >= 2
				&& user.getLevel("Smithing") >= 15) {
			user.itemList.remove(getItemByName("Ruby"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Ruby Necklace!");
			user.getLevelObject("Smithing").gainXp(800);
		} else if (i.itemName.toLowerCase().contains("diamond ring") && getNumberOfItemByName("Gold Bar") >= 1
				&& user.getLevel("Smithing") >= 18) {
			user.itemList.remove(getItemByName("Diamond"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Diamond Ring!");
			user.getLevelObject("Smithing").gainXp(1000);
		} else if (i.itemName.toLowerCase().contains("diamond necklace") && getNumberOfItemByName("Gold Bar") >= 2
				&& user.getLevel("Smithing") >= 21) {
			user.itemList.remove(getItemByName("Diamond"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.remove(getItemByName("Gold Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a Diamond Necklace!");
			user.getLevelObject("Smithing").gainXp(1100);
		} else {
			String type = i.getName().substring(0, i.getName().indexOf(" ")).trim();
			if (!checkIfCanMakeArmor(i, type)) {
				System.out.println(
						"You either do not have a high enough level or do not have enough required items to craft this!");
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
		if (i.itemName.toLowerCase().contains("boots") && getNumberOfItemByName(type + " Bar") >= 3
				&& user.getLevel("Smithing") >= (getItemByName(type + " Bar").itemRequiredLevel)) {
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft " + type + " Boots!");

			user.getLevelObject("Smithing").gainXp(getRightXP(type));
			return true;
		} else if (i.itemName.toLowerCase().contains("helmet") && getNumberOfItemByName(type + " Bar") >= 3
				&& user.getLevel("Smithing") >= (getItemByName(type + " Bar").itemRequiredLevel)) {
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a " + type + " Helmet!");
			user.getLevelObject("Smithing").gainXp(getRightXP(type));
			return true;
		} else if (i.itemName.toLowerCase().contains("shield") && getNumberOfItemByName(type + " Bar") >= 3
				&& user.getLevel("Smithing") >= (getItemByName(type + " Bar").itemRequiredLevel)) {
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a " + type + " Shield!");
			user.getLevelObject("Smithing").gainXp(getRightXP(type));
			return true;
		} else if (i.itemName.toLowerCase().contains("sword") && getNumberOfItemByName(type + " Bar") >= 5
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
		} else if (i.itemName.toLowerCase().contains("platebody") && getNumberOfItemByName(type + " Bar") >= 5
				&& user.getLevel("Smithing") >= (getItemByName(type + " Bar").itemRequiredLevel)) {
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft a " + type + " Platebody!");
			user.getLevelObject("Smithing").gainXp(getRightXP(type));
			return true;
		} else if (i.itemName.toLowerCase().contains("platelegs") && getNumberOfItemByName(type + " Bar") >= 5
				&& user.getLevel("Smithing") >= (getItemByName(type + " Bar").itemRequiredLevel)) {
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.remove(getItemByName(type + " Bar"));
			user.itemList.add(i);
			System.out.println("You successfully craft " + type + " Platelegs!");
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
		if (item.itemType.contains("vial") || item.itemType.contains("ingredient")) {
			System.out.println("Please choose a herb to make a potion!");
			return;
		}
		if (item.itemType.contains("herb")) {
			Item item1 = null, item2 = null, item3 = null, item4 = null;
			if (item.itemName.toLowerCase().contains("red")) {
				System.out.println("1 Lvl 1 Small HP Potion (1 Vial, 1 Red Syrum, 1 Red Herb)");
				System.out.println("2 Lvl 8 Medium HP Potion (1 Vial, 1 Blue Syrum, 1 Red Herb)");
				System.out.println("3 Lvl 20 Large HP Potion (1 Vial, 1 Green Syrum, 1 Red Herb)");
				System.out.println("4 Lvl 26 Giant HP Potion (1 Vial, 1 White Syrum, 1 Red Herb)");
				item1 = new Consumable("Small HP Potion", (int) (user.getTotalHealth() * .1), "potion", 1, 0, 0);
				item2 = new Consumable("Medium HP Potion", (int) (user.getTotalHealth() * .25), "potion", 1, 0, 0);
				item3 = new Consumable("Large HP Potion", (int) (user.getTotalHealth() * .35), "potion", 1, 0, 0);
				item4 = new Consumable("Giant HP Potion", (int) (user.getTotalHealth() * .5), "potion", 1, 0, 0);
			} else if (item.itemName.toLowerCase().contains("blue")) {
				System.out.println("1 Lvl 2 Small Defence Potion (1 Vial, 1 Red Syrum, 1 Blue Herb)");
				System.out.println("2 Lvl 10 Medium Defence Potion (1 Vial, 1 Red Syrum, 1 Blue Herb)");
				System.out.println("3 Lvl 17 Large Defence Potion (1 Vial, 1 Red Syrum, 1 Blue Herb)");
				System.out.println("4 Lvl 23 Giant Defence Potion (1 Vial, 1 Red Syrum, 1 Blue Herb)");
				item1 = new Consumable("Small Defence Potion", 0, "potion", 1, 0, (int) (user.getDefense() * .02));
				item2 = new Consumable("Medium Defence Potion", 0, "potion", 1, 0, (int) (user.getDefense() * .05));
				item3 = new Consumable("Large Defence Potion", 0, "potion", 1, 0, (int) (user.getDefense() * .1));
				item4 = new Consumable("Giant Defence Potion", 0, "potion", 1, 0, (int) (user.getDefense() * .15));
			} else if (item.itemName.toLowerCase().contains("green")) {
				System.out.println("1 Lvl 4 Small Attack Potion (1 Vial, 1 Red Syrum, 1 Green Herb)");
				System.out.println("2 Lvl 12 Medium Attack Potion (1 Vial, 1 Blue Syrum, 1 Green Herb)");
				System.out.println("3 Lvl 19 Large Attack Potion (1 Vial, 1 Green Syrum, 1 Green Herb)");
				System.out.println("4 Lvl 25 Giant Attack Potion (1 Vial, 1 White Syrum, 1 Green Herb)");
				item1 = new Consumable("Small Attack Potion", 0, "potion", 1, (int) (user.getAttack() * .02), 0);
				item2 = new Consumable("Medium Attack Potion", 0, "potion", 1, (int) (user.getAttack() * .05), 0);
				item3 = new Consumable("Large Attack Potion", 0, "potion", 1, (int) (user.getAttack() * .1), 0);
				item4 = new Consumable("Giant Attack Potion", 0, "potion", 1, (int) (user.getAttack() * .15), 0);
			} else if (item.itemName.toLowerCase().contains("white")) {
				System.out.println("1 Lvl 8 Small Super Potion (1 Vial, 1 Red Syrum, 1 White Herb)");
				System.out.println("2 Lvl 16 Medium Super Potion (1 Vial, 1 Blue Syrum, 1 White Herb)");
				System.out.println("3 Lvl 24 Large Super Potion (1 Vial, 1 Green Syrum, 1 White Herb)");
				System.out.println("4 Lvl 29 Giant Super Potion (1 Vial, 1 White Syrum, 1 White Herb)");
				item1 = new Consumable("Small Super Potion", (int) (user.getTotalHealth() * .05), "potion", 1,
						(int) (user.getAttack() * .01), (int) (user.getDefense() * .01));
				item2 = new Consumable("Medium Super Potion", (int) (user.getTotalHealth() * .1), "potion", 1,
						(int) (user.getAttack() * .03), (int) (user.getDefense() * .03));
				item3 = new Consumable("Large Super Potion", (int) (user.getTotalHealth() * .15), "potion", 1,
						(int) (user.getAttack() * .07), (int) (user.getDefense() * .07));
				item4 = new Consumable("Giant Super Potion", (int) (user.getTotalHealth() * .2), "potion", 1,
						(int) (user.getAttack() * .010), (int) (user.getDefense() * .10));
			}
			int choice = 0;
			choice = sc.nextInt();
			if (choice == 1) {
				if (item1 != null) {
					if (item.itemName.toLowerCase().contains("red")) {
						checkIfCanMakePotion(item1, "Red", "Red", 1, "small", "hp");
					} else if (item.itemName.toLowerCase().contains("blue")) {
						checkIfCanMakePotion(item1, "Red", "Blue", 8, "medium", "hp");
					} else if (item.itemName.toLowerCase().contains("green")) {
						checkIfCanMakePotion(item1, "Red", "Green", 20, "large", "hp");
					} else if (item.itemName.toLowerCase().contains("white")) {
						checkIfCanMakePotion(item1, "Red", "White", 26, "giant", "hp");
					}
				}
			} else if (choice == 2) {
				if (item2 != null) {
					if (item.itemName.toLowerCase().contains("red")) {
						checkIfCanMakePotion(item2, "Blue", "Red", 2, "small", "defence");
					} else if (item.itemName.toLowerCase().contains("blue")) {
						checkIfCanMakePotion(item2, "Blue", "Blue", 10, "medium", "defence");
					} else if (item.itemName.toLowerCase().contains("green")) {
						checkIfCanMakePotion(item2, "Blue", "Green", 17, "large", "defence");
					} else if (item.itemName.toLowerCase().contains("white")) {
						checkIfCanMakePotion(item2, "Blue", "White", 23, "giant", "defence");
					}
				}
			} else if (choice == 3) {
				if (item3 != null) {
					if (item.itemName.toLowerCase().contains("red")) {
						checkIfCanMakePotion(item3, "Green", "Red", 4, "small", "attack");
					} else if (item.itemName.toLowerCase().contains("blue")) {
						checkIfCanMakePotion(item3, "Green", "Blue", 12, "medium", "attack");
					} else if (item.itemName.toLowerCase().contains("green")) {
						checkIfCanMakePotion(item3, "Green", "Green", 19, "large", "attack");
					} else if (item.itemName.toLowerCase().contains("white")) {
						checkIfCanMakePotion(item3, "Green", "White", 25, "giant", "attack");
					}
				}
			} else if (choice == 4) {
				if (item4 != null) {
					if (item.itemName.toLowerCase().contains("red")) {
						checkIfCanMakePotion(item4, "White", "Red", 8, "small", "super");
					} else if (item.itemName.toLowerCase().contains("blue")) {
						checkIfCanMakePotion(item4, "White", "Blue", 16, "medium", "super");
					} else if (item.itemName.toLowerCase().contains("green")) {
						checkIfCanMakePotion(item4, "White", "Green", 24, "large", "super");
					} else if (item.itemName.toLowerCase().contains("white")) {
						checkIfCanMakePotion(item4, "White", "White", 29, "giant", "super");
					}
				}
			}
		}
	}

	public static void checkIfCanMakePotion(Item i, String herbColor, String syrumColor, int itemLevel, String itemSize,
			String potionType) {
		if (i.itemName.toLowerCase().contains(itemSize + " " + potionType)
				&& getNumberOfItemByName(syrumColor + " Syrum") >= 1 && getNumberOfItemByName("Vial") >= 1
				&& getNumberOfItemByName(herbColor + " Herb") >= 1 && user.getLevel("Herblore") >= itemLevel) {
			user.itemList.remove(getItemByName(syrumColor + " Syrum"));
			user.itemList.remove(getItemByName(herbColor + " Herb"));
			user.itemList.remove(getItemByName("Vial"));
			user.itemList.add(i);
			System.out.println("You successfully create a " + itemSize + " " + potionType + " potion!");
			user.getLevelObject("Herblore").gainXp(getRightXP(i.itemName));
		} else {
			System.out.println(
					"You either do not have a high enough level or do not have enough required items to create this potion!");
		}
	}

	public static void loopActions(User user) throws IOException {
		while (true) {
			String choice = null;
			user.displayStats();
			System.out.println("What would you like to do?");
			System.out.println(
					"Combat (c)\tInventory (i)\tEquipped Items (e)\tShop (s)\tHospital (h)\tFishing (f)\tMining (m)\tQuit (q)");
			choice = sc.next();
			if (choice == null) {
				choice = sc.nextLine().toLowerCase();
			}
			if (choice.startsWith("c")) {
				System.out.println("What level enemy do you want to fight? (Type 0 to exit): ");
				String level = sc.next();
				if (isInteger(level) && (Integer.parseInt(level) > 0) && (Integer.parseInt(level) <= 30)) {
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
			saveData(user);
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
				System.out.println(count + "\t" + i.getName() + "\tDEFENSE BONUS: " + ((Armor) i).getDefenseBoost());
			} else if (i instanceof Weapon) {
				System.out.println(count + "\t" + i.getName() + "\tATTACK BONUS: " + ((Weapon) i).getAttackBoost());
			} else if (i instanceof Jewelry) {
				System.out.println(count + "\t" + i.getName() + "\tHP BONUS: " + ((Jewelry) i).getHpBoost()
						+ "\tATTACK BONUS: " + ((Jewelry) i).getAttackBoost() + "\tDEFENSE BONUS: "
						+ ((Jewelry) i).getDefenseBoost());
			}
			count++;
		}
		System.out.print("Type a number to remove item or a letter to cancel:");
		try {
			int choice = sc.nextInt();
			user.removeEquippedItem(choice);
			System.out.println("You remove the " + user.equippedItems.get(choice));
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
				System.out.println(count + "\t" + i.getName() + "\tWORTH: " + (i.getItemValue() / 2));
				count++;
			}
			System.out.print("Type a number to select item or a letter to cancel:");
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
			System.out.println("7	Medium HP Potion\t\t250 Gold");
			System.out.println("8	Large HP Potion\t\t500 Gold");
			Item i = null;
			int choice2 = 0;
			choice2 = sc.nextInt();
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
				i = new Consumable("Small Potion", ((int) (user.getTotalHealth() * .1)), "consumable", 100, 0, 0);
			} else if (choice2 == 7) {
				i = new Consumable("Medium Potion", ((int) (user.getTotalHealth() * .25)), "consumable", 250, 0, 0);
			} else if (choice2 == 8) {
				i = new Consumable("Large Potion", ((int) (user.getTotalHealth() * .5)), "consumable", 500, 0, 0);
			}
			System.out.println("How many do you want to buy?");
			choice2 = 0;
			choice2 = sc.nextInt();
			if (user.getMoney() < i.getItemValue() * choice2) {
				System.out.println("You do not have enough money to purchase this item!");
				return;
			}
			for (int j = 0; j < choice2; j++) {
				user.setMoney(user.getMoney() - (i.getItemValue()));
				user.itemList.add(i);
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
		System.out.println("You have " + user.getMoney() + " gold and " + user.getCurrentHealth() + " HP...");
		System.out.print(
				"The hospital charges 1 gold per hp to heal. How much would you like to spend? (Type 0 to exit): ");
		try {
			int choice = sc.nextInt();
			if (choice < 1) {
				return;
			}
			if (choice > user.getMoney()) {
				System.out.println("You do not have enough gold to heal this much.");
				return;
			} else {
				if (choice > (user.getTotalHealth() - user.getCurrentHealth())) {
					choice = user.getTotalHealth() - user.getCurrentHealth();
				}
				user.setMoney(user.getMoney() - choice);
				user.setCurrentHealth(user.getCurrentHealth() + choice);
				System.out.println("You have been healed for " + choice + " HP!");
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
			if (i instanceof Cookable || i.itemType.toLowerCase().equals("cookable")) {
				System.out.println(count + "\t" + i.getName() + "\tRequired Level To COOK: " + i.getRequiredLevel());
			} else if (i instanceof Consumable || i.itemType.toLowerCase().equals("consumable")) {
				Consumable c = (Consumable) i;
				System.out.println(count + "\t" + c.getName() + "\tHeals: " + c.getHealAmount());
			} else if (i instanceof Weapon || i instanceof Armor || i instanceof Jewelry
					|| i.itemType.toLowerCase().equals("armor") || i.itemType.toLowerCase().equals("weapon")
					|| i.itemType.toLowerCase().equals("jewelry")) {
				System.out.println(count + "\t" + i.getName() + "\tRequired Level To EQUIP: " + i.getRequiredLevel());
			} else if (i instanceof Ore) {
				System.out.println(count + "\t" + i.getName() + "\tRequired Level to SMITH:" + i.getRequiredLevel());
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

	public static int determineFirst(int enemySpeed, int userSpeed) {
		double result = ((double) userSpeed / (double) enemySpeed);
		result += new Random().nextDouble() - new Random().nextDouble();
		if (result < 1) {
			return 1;
		}
		return 0;
	}

	public static void fight(Enemy enemy) {
		int count = 0;
		int damage;
		count = determineFirst(enemy.enemySpeed, user.getSpeed());
		while (true) {
			if (enemy.enemyHp <= 0) {
				System.out.println(enemy.enemyName + " has been killed! You have recieved :");
				int gold = 0;
				if (user.userClass.equals("Thief")) {
					gold = (int) (enemy.enemyMoney * 1.05);
				} else {
					gold = enemy.enemyMoney;
				}
				System.out.println(gold + " gold");
				user.setMoney(user.getMoney() + gold);
				System.out.println(enemy.enemyXp + " XP");
				user.getLevelObject("Combat").setXp(user.getLevelObject("Combat").getXp() + enemy.enemyXp);
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
				System.out.println("You have died! You lost " + amountGold + " gold!");
				user.setCurrentHealth(user.getTotalHealth());
				return;
			}
			if (count % 2 == 0) {
				System.out.println(enemy.enemyName + " HP: " + enemy.enemyHp);
				System.out.println("Your HP: " + user.getCurrentHealth());
				System.out.println("Use an item, run away, or autofight? (yes / no / run / af): ");
				String choice = sc.next();
				if (choice == null || choice.isEmpty()) {
					choice = sc.nextLine();
				}
				if (choice.toLowerCase().startsWith("y") || choice.toLowerCase().startsWith("i")) {
					inventory();
				} else if (choice.toLowerCase().startsWith("r")) {
					if (runAway(user.getLevelObject("Combat").getLevelFromXP(user.getLevelObject("Combat").getXp()),
							enemy.enemyLevel)) {
						System.out.println("You successfully ran away!");
						return;
					} else {
						System.out.println("You fail to run away!");
					}
				} else if (choice.toLowerCase().startsWith("a")) {
					while (user.getCurrentHealth() > user.getTotalHealth() * .10 && enemy.enemyHp >= 0) {
						damage = damage(user.getAttack(), enemy.enemyDefense);
						enemy.enemyHp -= damage;
						System.out.println("You do " + damage + " damage!");
						if (enemy.enemyHp <= 0) {
							count++;
							break;
						}
						damage = damage(enemy.enemyAttack, user.getDefense());
						user.setCurrentHealth(user.getCurrentHealth() - damage);
						System.out.println(enemy.enemyName + " does " + damage + " damage!");
					}
					count++;
				}
				if (!choice.toLowerCase().startsWith("a") && !choice.toLowerCase().startsWith("y")) {
					damage = damage(user.getAttack(), enemy.enemyDefense);
					enemy.enemyHp -= damage;
					System.out.println("You do " + damage + " damage!");
				}
			} else {
				damage = damage(enemy.enemyAttack, user.getDefense());
				user.setCurrentHealth(user.getCurrentHealth() - damage);
				System.out.println(enemy.enemyName + " does " + damage + " damage!");
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
		double totalAttackPower = (attackerAtt / defenderDef) + new Random().nextDouble() - .5;
		double result = (Math.abs(totalAttackPower) * attackerAtt);
		return (int) result;
	}
}
