package miniRPG;

public class Smeltable extends Item {
	String name;
	int requiredLevelToSmith;
	int value;

	public Smeltable(String name, int requiredLevelToSmith, int value) {
		super(name, name, requiredLevelToSmith, value);
		this.requiredLevelToSmith = requiredLevelToSmith;
		this.name = name;
		this.value = value;
	}
	
}
