package miniRPG;

import java.awt.*;  // Using Frame class in package java.awt
import java.awt.event.*;
import java.io.IOException;

/* 
 * The following code was based on a tutorial found at https://www3.ntu.edu.sg/home/ehchua/programming/java/j4a_gui.html
 */

// A GUI program is written as a subclass of Frame - the top-level container
// This subclass inherits all properties from Frame, e.g., title, icon, buttons, content-pane
public class GUIWindow extends Frame
	implements ActionListener, WindowListener {
    
	//Create player
	protected User user = Play.startUser();
	
	// Declare Label components for home menu
	private Label healthLabel;
	private Label currHealthLabel;
	private Label attackLabel;
	private Label defenseLabel;
	
	private Label fishingLabel;
	private Label fishingXpLabel; 
	private Label cookingLabel;
	private Label cookingXpLabel;
	private Label miningLabel;
	private Label miningXpLabel;
	private Label smithingLabel;
	private Label smithingXpLabel;
	private Label combatLabel;
	private Label combatXpLabel;

	private Label moneyLabel;

	// Declare console output
	private TextField messages;
	private String status; //for string to post to messages
	
	// Declare button options - home menu
	private Button combatSelectButton;
	private Button InventorySelectButton;
	private Button EquippedSelectButton;
	private Button ShopSelectButton;
	private Button HospitalSelectButton;
	private Button FishingSelectButton;
	private Button MiningSelectButton;
	private Button QuitSelectButton;
	
	// Declare menu options for combat - preliminary
	private TextField enemyCombatLevelEntry;
	private Button confirmEnemyLevelButton;
	private Button cancelBattleButton;
	
	// Declare menu UI for combat - in-combat
	private Label enemyName;
	private Label enemyHP;/*
 * 	
 *  private Label healthLabel; 		player health and total health
 *	private Label currHealthLabel; 	*/
	
	// Declare menu options for in-combat
	private Button useItemButton;
	private Button skipItemButton;
	private Button runButton;
	
	// Declare menu options for inventory
	private List inventoryList; //list of all inventory items
	private Button cancelInventoryButton;
	
	// Declare menu options for equipped items 
	private List equippedList; //list of all equipped items
	private Button cancelEquipmentButton;
	
	// Declare menu options for hospital
	private TextField healPurchaseAmountEntry;
	private Button confirmHealPurchaseButton;
	private Button cancelHospitalButton;

    // Constructor to setup the GUI components
    public GUIWindow() {
    	
    	setLayout(new FlowLayout());
	    	// "super" Frame, which is a Container, sets its layout to FlowLayout to arrange
	        // the components from left-to-right, and flow to next row from top-to-bottom.
    	Panel status = new Panel();
    	Panel stats = new Panel();
    	Panel exp = new Panel();
    	Panel output = new Panel();
    	Panel options = new Panel();
    	
    	
    	// Main menu elements
    	healthLabel = new Label("Total HP:" + user.getLevel("Health"));  // construct the Label component
        add(healthLabel); // "super" Frame container adds Label component
        
    	currHealthLabel = new Label ("Current HP:" + user.getCurrentHealth());
    	status.add(currHealthLabel);
    	attackLabel = new Label ("Attack Lvl:" + user.getAttack()); 
    	status.add(attackLabel);
    	defenseLabel = new Label ("Defense Lvl:" + user.getDefense()); 
    	status.add(defenseLabel); 
    	fishingLabel = new Label ("Fishing Lvl:" + user.getLevel("Fishing")); 
    	stats.add(fishingLabel); 
    	fishingXpLabel = new Label ("Fishing XP:" + user.getLevelObject("Fishing").getXp()); 
    	exp.add(fishingXpLabel);
    	cookingLabel = new Label ("Cooking Lvl:" + user.getLevel("Cooking")); 
    	stats.add(cookingLabel); 
    	cookingXpLabel = new Label ("Cooking XP:" + user.getLevelObject("Cooking").getXp()); 
    	exp.add(cookingXpLabel);
    	miningLabel = new Label ("Mining Lvl:" + user.getLevel("Mining")); 
    	stats.add(miningLabel);  
    	miningXpLabel = new Label ("Mining XP:" + user.getLevelObject("Mining").getXp()); 
    	exp.add(miningXpLabel);
    	smithingLabel = new Label ("Smithing Lvl:" + user.getLevel("Smithing")); 
    	stats.add(smithingLabel);
    	smithingXpLabel = new Label ("Smithing XP:" + user.getLevelObject("Smithing").getXp()); 
    	exp.add(smithingXpLabel);
    	combatLabel = new Label ("Combat Lvl:" + user.getLevel("Combat")); 
    	stats.add(combatLabel);  
    	combatXpLabel = new Label ("Combat XP:" + user.getLevelObject("Combat").getXp());
    	exp.add(combatXpLabel);
    	moneyLabel = new Label ("$" + user.getMoney());
    	status.add(moneyLabel);  
    	
    	//Console window
    	messages = new TextField("Status updates go here...", 55); // construct the TextField component
        messages.setEditable(false);       // set to read-only
        output.add(messages);              // "super" Frame container adds TextField component
        
        //Construct buttons
        combatSelectButton = new Button("Combat");
        InventorySelectButton = new Button("Inventory");
        EquippedSelectButton = new Button("Equipment"); 
        ShopSelectButton = new Button("Shop"); 
        HospitalSelectButton = new Button("Hospital");  
        FishingSelectButton = new Button("Fish");  
        MiningSelectButton = new Button("Mine");    
        QuitSelectButton  = new Button("Quit");
        options.add(combatSelectButton);
        options.add(InventorySelectButton);
        options.add(EquippedSelectButton);
        options.add(ShopSelectButton);
        options.add(HospitalSelectButton);
        options.add(FishingSelectButton);
        options.add(MiningSelectButton);
        options.add(QuitSelectButton);
        

        combatSelectButton.addActionListener(this);
        InventorySelectButton.addActionListener(this);
        EquippedSelectButton.addActionListener(this);
        ShopSelectButton.addActionListener(this);
        HospitalSelectButton.addActionListener(this);
        FishingSelectButton.addActionListener(this);
        MiningSelectButton.addActionListener(this);
        QuitSelectButton.addActionListener(this);
        
        addWindowListener(this);
        // "super" Frame (source object) fires WindowEvent.
        // "super" Frame adds "this" object as a WindowEvent listener.
        
        setTitle("miniRPG");  // "super" Frame sets its title
        setSize(500, 250);    // "super" Frame sets its initial window size
        
        setVisible(true); //"super" Frame shows
        
        add(status);
        add(stats);
        add(exp);
        add(output);
        add(options);
    }    

    
    // The entry main() method
    public static void main(String[] args) {
        // Invoke the constructor (to set up the GUI) by allocating an instance
    	new GUIWindow();
    }
    
    //ActionEvent handler 
    @Override
    public void actionPerformed(ActionEvent evt) {
    	if(evt.getSource() == FishingSelectButton){
    		status = Fishing.fishing(user);
    		messages.setText(status);
    		messages.validate();
    	}
    }
    
    /* WindowEvent handlers */
    // Called back upon clicking close-window button
    @Override
    public void windowClosing(WindowEvent evt) {
        System.exit(0);  // Terminate the program
    }
  
    // Not Used, but need to provide an empty body to compile.
    @Override public void windowOpened(WindowEvent evt) { }
    @Override public void windowClosed(WindowEvent evt) { }
    @Override public void windowIconified(WindowEvent evt) { }
    @Override public void windowDeiconified(WindowEvent evt) { }
    @Override public void windowActivated(WindowEvent evt) { }
    @Override public void windowDeactivated(WindowEvent evt) { }

}