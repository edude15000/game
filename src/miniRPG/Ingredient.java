package miniRPG;

public class Ingredient extends Item {
	String ingredientName;
	String ingredientType;
	int ingredientRequiredLevel;
	int ingredientValue;

	public Ingredient(String ingredientName, String ingredientType, int ingredientRequiredLevel,
			int ingredientValue) {
		super(ingredientName, ingredientType, ingredientRequiredLevel, ingredientValue);
		this.ingredientName = ingredientName;
		this.ingredientRequiredLevel = ingredientRequiredLevel;
		this.ingredientType = ingredientType;
		this.ingredientValue = ingredientValue;
	}

}
