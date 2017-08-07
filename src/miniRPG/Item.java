package miniRPG;

public abstract class Item {
	String itemName;
	String itemType;
	int itemRequiredLevel;
	int itemValue;

	public Item(String itemName, String itemType, int itemRequiredLevel, int itemValue) {
		this.itemName = itemName;
		this.itemType = itemType;
		this.itemRequiredLevel = itemRequiredLevel;
		this.itemValue = itemValue;
	}

	public int getItemValue() {
		return itemValue;
	}

	public void setItemValue(int itemValue) {
		this.itemValue = itemValue;
	}

	public String getName() {
		return itemName;
	}

	public void setName(String name) {
		this.itemName = name;
	}

	public String getType() {
		return itemType;
	}

	public void setType(String type) {
		this.itemType = type;
	}

	public int getRequiredLevel() {
		return itemRequiredLevel;
	}

	public void setRequiredLevel(int requiredLevel) {
		this.itemRequiredLevel = requiredLevel;
	}

}
