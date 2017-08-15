package miniRPG;

import java.util.Random;

public class Fishing {

	public static String fishing(User user) {
		String result = "";
		if (new Random().nextInt(100) + 1 > 75) {
			int chance = new Random().nextInt(100) + 1;
			if (user.getLevel("Fishing") < 5) {
				user.itemList.add(new Cookable("Raw Shrimp", 100, 1, user, 5));
				user.getLevelObject("Fishing").gainXp(50);
				result = " You catch a shrimp and gain 50 XP!";
			} else if (user.getLevel("Fishing") < 10) {
				if (chance <= 25) {
					user.itemList
							.add(new Cookable("Raw Fish", 200, 5, user, 5));
					user.getLevelObject("Fishing").gainXp(100);
					result = " You catch a fish and gain 100 XP!";
				} else {
					user.itemList.add(new Cookable("Raw Shrimp", 100, 1, user,
							5));
					user.getLevelObject("Fishing").gainXp(50);
					result = " You catch a shrimp and gain 50 XP!";
				}
			} else if (user.getLevel("Fishing") < 15) {
				if (chance <= 15) {
					user.itemList.add(new Cookable("Raw Lobster", 400, 10,
							user, 5));
					user.getLevelObject("Fishing").gainXp(200);
					result = " You catch a lobster and gain 200 XP!";
				} else if (chance <= 50) {
					user.itemList
							.add(new Cookable("Raw Fish", 200, 5, user, 5));
					user.getLevelObject("Fishing").gainXp(100);
					result = " You catch a fish and gain 100 XP!";
				} else {
					user.itemList.add(new Cookable("Raw Shrimp", 100, 1, user,
							5));
					user.getLevelObject("Fishing").gainXp(50);
					result = " You catch a shrimp and gain 50 XP!";
				}
			} else if (user.getLevel("Fishing") < 20) {
				if (chance <= 15) {
					user.itemList.add(new Cookable("Raw Swordfish", 750, 15,
							user, 5));
					user.getLevelObject("Fishing").gainXp(400);
					result = " You catch a swordfish and gain 400 XP!";
				} else if (chance <= 50) {
					user.itemList.add(new Cookable("Raw Lobster", 400, 10,
							user, 5));
					user.getLevelObject("Fishing").gainXp(200);
					result = " You catch a lobster and gain 200 XP!";
				} else {
					user.itemList
							.add(new Cookable("Raw Fish", 200, 5, user, 5));
					user.getLevelObject("Fishing").gainXp(100);
					result = " You catch a fish and gain 100 XP!";
				}
			} else if (user.getLevel("Fishing") < 25) {
				if (chance <= 15) {
					user.getLevelObject("Fishing").gainXp(500);
					int coins = new Random().nextInt(100) + 1;
					user.setMoney(user.getMoney() + coins);
					result = " You catch a crate containing " + coins + " !";
				} else if (chance <= 50) {
					user.itemList.add(new Cookable("Raw Swordfish", 750, 15,
							user, 5));
					user.getLevelObject("Fishing").gainXp(400);
					result = " You catch a swordfish and gain 400 XP!";
				} else {
					user.itemList.add(new Cookable("Raw Lobster", 400, 10,
							user, 5));
					user.getLevelObject("Fishing").gainXp(200);
					result = " You catch a lobster and gain 200 XP!";
				}
			} else {
				if (chance <= 15) {
					user.itemList.add(new Cookable("Raw Shark", 1500, 20, user,
							5));
					user.getLevelObject("Fishing").gainXp(1000);
					result = " You catch a shark and gain 1000 XP!";
				} else if (chance <= 50) {
					user.getLevelObject("Fishing").gainXp(500);
					int coins = new Random().nextInt(100) + 1;
					user.setMoney(user.getMoney() + coins);
					result = " You catch a crate containing " + coins + " !";
				} else {
					user.itemList.add(new Cookable("Raw Swordfish", 750, 15,
							user, 5));
					user.getLevelObject("Fishing").gainXp(400);
					result = " You catch a swordfish and gain 400 XP!";
				}
			}
		} else {
			user.getLevelObject("Fishing").gainXp(35);
			result = " You fail to catch anything!";
		}
		return result;
	}
}
