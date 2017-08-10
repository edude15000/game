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
	protected User user;
	
	// Declare Label components for home menu
	private Label fishingLabel;
	private Label fishingXpLabel; 
	private Label cookingLabel;
	private Label cookingXpLabel;
	private Label miningLabel;
	private Label miningXpLabel;
	private Label smithingLabel;
	private Label smithingXpLabel;
	private Label herbloreXpLabel;
	private Label herbloreLabel;

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
	private Button combatStatsSelectButton;
	
	public void updateLevels() {
		fishingLabel.setText(Integer.toString(user.getLevel("Fishing")));
		fishingXpLabel.setText(Integer.toString(user.getLevelObject("Fishing").getXp()));
		cookingLabel.setText(Integer.toString(user.getLevel("Cooking")));
		cookingXpLabel.setText(Integer.toString(user.getLevelObject("Cooking").getXp()));
		miningLabel.setText(Integer.toString(user.getLevel("Mining")));
		miningXpLabel.setText(Integer.toString(user.getLevelObject("Mining").getXp()));
		smithingLabel.setText(Integer.toString(user.getLevel("Smithing")));
		smithingXpLabel.setText(Integer.toString(user.getLevelObject("Smithing").getXp()));
		herbloreLabel.setText(Integer.toString(user.getLevel("Herblore")));
		herbloreXpLabel.setText(Integer.toString(user.getLevelObject("Herblore").getXp()));
	}
	
    // Constructor to setup the GUI components
    public GUIWindow(String userName, String userClass) {
    	user = Play.startUser(userName, userClass, false);
    	
    	setLayout(new FlowLayout());
	    	// "super" Frame, which is a Container, sets its layout to FlowLayout to arrange
	        // the components from left-to-right, and flow to next row from top-to-bottom.
    	Panel status = new Panel();
    	Panel stats = new Panel();
    	Panel exp = new Panel();
    	Panel output = new Panel();
    	Panel options = new Panel();
    	
    	
    	// Main menu elements
    	fishingLabel = new Label ("Fishing Lvl:" + user.getLevel("Fishing")+"      "); 
    	stats.add(fishingLabel); 
    	fishingXpLabel = new Label ("Fishing XP:" + user.getLevelObject("Fishing").getXp()+"      "); 
    	exp.add(fishingXpLabel);
    	cookingLabel = new Label ("Cooking Lvl:" + user.getLevel("Cooking")+"      "); 
    	stats.add(cookingLabel); 
    	cookingXpLabel = new Label ("Cooking XP:" + user.getLevelObject("Cooking").getXp()+"      "); 
    	exp.add(cookingXpLabel);
    	miningLabel = new Label ("Mining Lvl:" + user.getLevel("Mining")+"      "); 
    	stats.add(miningLabel);  
    	miningXpLabel = new Label ("Mining XP:" + user.getLevelObject("Mining").getXp()+"      "); 
    	exp.add(miningXpLabel);
    	smithingLabel = new Label ("Smithing Lvl:" + user.getLevel("Smithing")+"      "); 
    	stats.add(smithingLabel);
    	smithingXpLabel = new Label ("Smithing XP:" + user.getLevelObject("Smithing").getXp()+"      "); 
    	exp.add(smithingXpLabel);
    	herbloreLabel = new Label ("Herblore Lvl:" + user.getLevel("Herblore")+"      "); 
    	stats.add(herbloreLabel);
    	herbloreXpLabel = new Label ("Herblore XP:"+ user.getLevelObject("Herblore").getXp()+"      "); 
    	exp.add(herbloreXpLabel);

    	
    	//Console window
    	if(!user.userName.isEmpty()){ // if no name is entered, skip to else statement
    		messages = new TextField("Welcome to miniRPG, "+user.userName+"!", 55); // construct the TextField component
    	}
    	else{
    		messages = new TextField("Welcome to miniRPG! You didn't introduce yourself; I'll call you Bobbert.",55);
    		user.userName = "Bobbert";
    	}
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
        combatStatsSelectButton = new Button(user.userClass+" Stats");
        options.add(combatSelectButton);
        options.add(combatStatsSelectButton);
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
        combatStatsSelectButton.addActionListener(this);
        
        addWindowListener(this);
        // "super" Frame (source object) fires WindowEvent.
        // "super" Frame adds "this" object as a WindowEvent listener.
        
        setTitle("miniRPG");  // "super" Frame sets its title
        setSize(650, 200);    // "super" Frame sets its initial window size
        
        setVisible(true); //"super" Frame shows
        
        add(status);
        add(stats);
        add(exp);
        add(output);
        add(options);
    }       

    //ActionEvent handler 
    @Override
    public void actionPerformed(ActionEvent evt) {
    	if(evt.getSource() == FishingSelectButton){
    		status = Fishing.fishing(user);
    		messages.setText(status);
    		messages.validate();
    		fishingLabel.setText("Fishing Lvl:" + user.getLevel("Fishing")); 
        	fishingXpLabel.setText("Fishing XP:" + user.getLevelObject("Fishing").getXp()); 
    	}
    	if(evt.getSource() == MiningSelectButton) {
    		status = Mining.mining(user);
    		messages.setText(status);
    		messages.validate();
    		miningLabel.setText("Mining Lvl:" + user.getLevel("Mining"));
    		miningXpLabel.setText("Mining XP:" + user.getLevelObject("Mining").getXp());
    	}
    	if(evt.getSource() == InventorySelectButton){
    		inventoryWindow frame = new inventoryWindow(user);
    		frame.setVisible(true);
    	}
    	if(evt.getSource() == combatStatsSelectButton){
    		combatStatsWindow frame = new combatStatsWindow(user);
    		frame.setVisible(true);
    	}
    	if(evt.getSource() == HospitalSelectButton){
    		hospitalWindow frame = new hospitalWindow(user);
    		frame.setVisible(true);
    	}
    	if(evt.getSource() == combatSelectButton){
    		//TODO: Remove the following line and make real combat
    		user.setCurrentHealth(user.getCurrentHealth()-5);
    	}
    	if(evt.getSource() == ShopSelectButton){
    		//TODO: Remove the following line and make real shop
    		user.setMoney(user.getMoney()+5);
    	}
    	if(evt.getSource() == QuitSelectButton){
    		System.exit(0);
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