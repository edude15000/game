package miniRPG;

import java.util.Random;

public class Mining {

	public static String mining(User user) {
		String result = "";
		if ((new Random().nextInt(100) + 1) < 5) {
			int chance = new Random().nextInt(100) + 1;
			if (chance < 50) {
				user.itemList.add(new Smeltable("Sapphire", 1, 1));
				result += " You find a sapphire!";
			} else if (chance < 80) {
				user.itemList.add(new Smeltable("Emerald", 1, 1));
				result += " You find a emerald!";
			} else if (chance < 95) {
				user.itemList.add(new Smeltable("Ruby", 1, 1));
				result += " You find a ruby!";
			} else {
				user.itemList.add(new Smeltable("Diamond", 1, 1));
				result += " You find a diamond!";
			}
		}
		if (new Random().nextInt(100) + 1 > 75) {
			int chance = new Random().nextInt(100) + 1;
			if (user.getLevel("Mining") < 5) {
				user.itemList.add(new Ore("Bronze Ore", 1, 1, 50));
				user.getLevelObject("Mining").gainXp(50);

				result += " You mine bronze ore and gain 50 XP!";
			} else if (user.getLevel("Mining") < 10) {
				if (chance <= 25) {
					user.itemList.add(new Ore("Iron Ore", 5, 1, 100));
					user.getLevelObject("Mining").gainXp(100);
					result += " You mine iron ore and gain 100 XP!";
				} else {
					user.itemList.add(new Ore("Bronze Ore", 1, 1, 50));
					user.getLevelObject("Mining").gainXp(50);
					result += " You mine bronze ore and gain 50 XP!";
				}
			} else if (user.getLevel("Mining") < 15) {
				if (chance <= 15) {
					user.itemList.add(new Ore("Steel Ore", 10, 1, 200));
					user.getLevelObject("Mining").gainXp(200);
					result += " You mine steel ore and gain 200 XP!";
				} else if (chance <= 50) {
					user.itemList.add(new Ore("Iron Ore", 5, 1, 100));
					user.getLevelObject("Mining").gainXp(100);
					result += " You mine iron ore and gain 100 XP!";
				} else {
					user.itemList.add(new Ore("Bronze Ore", 1, 1, 50));
					user.getLevelObject("Mining").gainXp(50);
					result += " You mine bronze ore and gain 50 XP!";
				}
			} else if (user.getLevel("Mining") < 20) {
				if (chance <= 15) {
					user.itemList.add(new Ore("Mihtril Ore", 15, 1, 400));
					user.getLevelObject("Mining").gainXp(400);
					result += " You mine mithril ore and gain 400 XP!";
				} else if (chance <= 50) {
					user.itemList.add(new Ore("Steel Ore", 10, 1, 200));
					user.getLevelObject("Mining").gainXp(200);
					result += " You mine steel ore and gain 200 XP!";
				} else {
					user.itemList.add(new Ore("Iron Ore", 5, 1, 100));
					user.getLevelObject("Mining").gainXp(100);
					result += " You mine iron ore and gain 100 XP!";
				}
			} else if (user.getLevel("Mining") < 25) {
				if (chance <= 15) {
					user.itemList.add(new Ore("Adamantite Ore", 20, 1, 750));
					user.getLevelObject("Mining").gainXp(750);
					result += " You mine adamantite ore and gain 500 XP!";
				} else if (chance <= 50) {
					user.itemList.add(new Ore("Mihtril Ore", 15, 1, 400));
					user.getLevelObject("Mining").gainXp(400);
					result += " You mine mithril ore and gain 400 XP!";
				} else {
					user.itemList.add(new Ore("Steel Ore", 10, 1, 200));
					user.getLevelObject("Mining").gainXp(200);
					result += " You mine steel ore and gain 200 XP!";
				}
			} else {
				if (chance <= 15) {
					user.itemList.add(new Ore("Rune Ore", 25, 1, 1000));
					user.getLevelObject("Mining").gainXp(1000);
					result += " You mine rune ore and gain 1000 XP!";
				} else if (chance <= 50) {
					user.itemList.add(new Ore("Adamantite Ore", 20, 1, 750));
					user.getLevelObject("Mining").gainXp(750);
					result += " You mine adamantite ore and gain 500 XP!";
				} else {
					user.itemList.add(new Ore("Mihtril Ore", 15, 1, 400));
					user.getLevelObject("Mining").gainXp(400);
					result += " You mine mithril ore and gain 400 XP!";
				}
			}
		} else if (new Random().nextInt(100) + 1 < 20) {
			user.itemList.add(new Ore("Gold Ore", 1, 100, 250));
			user.getLevelObject("Mining").gainXp(250);
			result += " You mine gold ore and gain 250 XP!";
		} else {
			user.getLevelObject("Mining").gainXp(10);
			result += " You fail to mine anything!";
		}
		return result;
	}

}
