package miniRPG;

import java.util.ArrayList;

public class Level {

	public ArrayList<Integer> xpList = new ArrayList<>();
	public int levelXp;
	public String levelName;
	public int levelBeforeLevelingUp;

	public Level(int levelXp, String levelName, int levelBeforeLevelingUp) {
		this.levelXp = levelXp;
		this.levelName = levelName;
		this.levelBeforeLevelingUp = levelBeforeLevelingUp;
		setXpList();
	}

	public String getName() {
		return levelName;
	}

	public void setName(String name) {
		this.levelName = name;
	}

	public int getXp() {
		return levelXp;
	}

	public void setXp(int xp) {
		this.levelXp = xp;
	}

	public void gainXp(int xp) {
		this.levelXp += xp;
	}

	public int getXPFromLevel(int level) {
		return xpList.get(level);
	}

	public int getXPUntilLevel(int level, int currentXP) {
		return getXPFromLevel(level) - currentXP;
	}

	public int getLevelFromXP(int xp) {
		int result = 1;
		for (int i = 2; i < 32; i++) {
			if (xpList.get(i) <= xp) {
				result = i;
			}
		}
		return result;
	}

	public void updateLevel() {
		int level = getLevelFromXP(getXp());
		setXp(getXPFromLevel(level + 1));
	}

	public void setXpList() {
		xpList.add(0);
		xpList.add(0);
		for (int i = 2; i < 31; i++) {
			xpList.add(50 * (int) Math.pow(i, 2.3));
		}
		xpList.add(Integer.MAX_VALUE);
	}

	public int getLevelBeforeLevelingUp() {
		return levelBeforeLevelingUp;
	}

	public void setLevelBeforeLevelingUp(int levelBeforeLevelingUp) {
		this.levelBeforeLevelingUp = levelBeforeLevelingUp;
	}

}
