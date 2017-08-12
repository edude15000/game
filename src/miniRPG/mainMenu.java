package miniRPG;

// Using Frame class in package java.awt
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.*;
import java.io.IOException;
import java.security.Key;

/* 
 * The following code was based on a tutorial found at https://www3.ntu.edu.sg/home/ehchua/programming/java/j4a_gui.html
 */

// A GUI program is written as a subclass of Frame - the top-level container
// This subclass inherits all properties from Frame, e.g., title, icon, buttons, content-pane
public class mainMenu extends Frame implements ActionListener, WindowListener {
	private static final long serialVersionUID = 1L;

	// Create player
	protected User user;

	// Declare Label components for home menu
	private Label fishingLabel = new Label();
	private Label cookingLabel = new Label();
	private Label miningLabel = new Label();
	private Label smithingLabel = new Label();
	private Label herbloreLabel = new Label();

	// Declare console output
	private TextField messages;
	private String status; // for string to post to messages

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
	
	private SaveDialogue saveframe;
	private inventoryWindow inventoryframe;
	private hospitalWindow hospitalframe;
	private combatStatsWindow statsframe;
	private deleteCharWindow deleteCharFrame;
	

	private void updateLevels() {
		Level level = user.getLevelObject("Fishing");
		fishingLabel.setText("Fishing: (" + level.getLevelFromXP(level.getXp()) + ") " + level.getXp() + "/"
				+ level.getXPFromLevel(level.getLevelFromXP(level.getXp()) + 1));
		level = user.getLevelObject("Cooking");
		cookingLabel.setText("Cooking: (" + level.getLevelFromXP(level.getXp()) + ") " + level.getXp() + "/"
				+ level.getXPFromLevel(level.getLevelFromXP(level.getXp()) + 1));
		level = user.getLevelObject("Mining");
		miningLabel.setText("Mining: (" + level.getLevelFromXP(level.getXp()) + ") " + level.getXp() + "/"
				+ level.getXPFromLevel(level.getLevelFromXP(level.getXp()) + 1));
		level = user.getLevelObject("Smithing");
		smithingLabel.setText("Smithing: (" + level.getLevelFromXP(level.getXp()) + ") " + level.getXp() + "/"
				+ level.getXPFromLevel(level.getLevelFromXP(level.getXp()) + 1));
		level = user.getLevelObject("Herblore");
		herbloreLabel.setText("Herblore: (" + level.getLevelFromXP(level.getXp()) + ") " + level.getXp() + "/"
				+ level.getXPFromLevel(level.getLevelFromXP(level.getXp()) + 1));
		
	}

	// Constructor to setup the GUI components
	public mainMenu(String userName, String userClass, boolean hardcoreMode) throws IOException {
		user = Play.startUser(userName, userClass, hardcoreMode);
		Play.saveData(user);
		setLayout(new FlowLayout());
		// "super" Frame, which is a Container, sets its layout to FlowLayout to arrange
		// the components from left-to-right, and flow to next row from top-to-bottom.
		Panel status = new Panel();
		Panel stats = new Panel();
		Panel stats2 = new Panel();
		Panel exp = new Panel();
		Panel output = new Panel();
		Panel options = new Panel();

		// Main menu elements
		Level level = user.getLevelObject("Fishing");
		fishingLabel.setText("Fishing: (" + level.getLevelFromXP(level.getXp()) + ") " + level.getXp() + "/"
				+ level.getXPFromLevel(level.getLevelFromXP(level.getXp()) + 1));
		stats.add(fishingLabel);
		level = user.getLevelObject("Cooking");
		cookingLabel.setText("Cooking: (" + level.getLevelFromXP(level.getXp()) + ") " + level.getXp() + "/"
				+ level.getXPFromLevel(level.getLevelFromXP(level.getXp()) + 1));
		stats.add(cookingLabel);
		level = user.getLevelObject("Mining");
		miningLabel.setText("Mining: (" + level.getLevelFromXP(level.getXp()) + ") " + level.getXp() + "/"
				+ level.getXPFromLevel(level.getLevelFromXP(level.getXp()) + 1));
		stats2.add(miningLabel);
		level = user.getLevelObject("Smithing");
		smithingLabel.setText("Smithing: (" + level.getLevelFromXP(level.getXp()) + ") " + level.getXp() + "/"
				+ level.getXPFromLevel(level.getLevelFromXP(level.getXp()) + 1));
		stats2.add(smithingLabel);
		level = user.getLevelObject("Herblore");
		herbloreLabel.setText("Herblore: (" + level.getLevelFromXP(level.getXp()) + ") " + level.getXp() + "/"
				+ level.getXPFromLevel(level.getLevelFromXP(level.getXp()) + 1));
		stats.add(herbloreLabel);

		// Console window
		if (!user.userName.isEmpty()) { // if no name is entered, skip to else statement
			messages = new TextField("Welcome to miniRPG, " + user.userName + "!", 55); // construct the TextField
		} else {
			messages = new TextField("Welcome to miniRPG! You didn't introduce yourself; I'll call you Bobbert.", 55);
			user.userName = "Bobbert";
		}
		messages.setEditable(false); // set to read-only
		output.add(messages); // "super" Frame container adds TextField component

		// Construct buttons
		combatSelectButton = new Button("Combat");
		InventorySelectButton = new Button("Inventory");
		EquippedSelectButton = new Button("Equipment");
		ShopSelectButton = new Button("Shop");
		HospitalSelectButton = new Button("Hospital");
		FishingSelectButton = new Button("Fish");
		MiningSelectButton = new Button("Mine");
		QuitSelectButton = new Button("Quit");
		combatStatsSelectButton = new Button(user.userClass + " Stats");
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


		setTitle("miniRPG"); // "super" Frame sets its title
		setSize(590, 200); // "super" Frame sets its initial window size

		setVisible(true); // "super" Frame shows

		add(status);
		add(stats);
		add(stats2); // TODO : PRINT ON NEW LINE
		add(exp);
		add(output);
		add(options);
	}

	// ActionEvent handler
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == FishingSelectButton) {
			status = Fishing.fishing(user);
			messages.setText(status);
			messages.validate();
			Level level = user.getLevelObject("Fishing");
			fishingLabel.setText("Fishing: (" + level.getLevelFromXP(level.getXp()) + ") " + level.getXp() + "/"
					+ level.getXPFromLevel(level.getLevelFromXP(level.getXp()) + 1));
		}
		if (evt.getSource() == MiningSelectButton) {
			status = Mining.mining(user);
			messages.setText(status);
			messages.validate();
			miningLabel.setText("Mining Lvl:" + user.getLevel("Mining"));
			Level level = user.getLevelObject("Mining");
			miningLabel.setText("Mining: (" + level.getLevelFromXP(level.getXp()) + ") " + level.getXp() + "/"
					+ level.getXPFromLevel(level.getLevelFromXP(level.getXp()) + 1));
		}
		if (evt.getSource() == InventorySelectButton) {
			inventoryframe= new inventoryWindow(user);
			inventoryframe.setVisible(true);
		}
		if (evt.getSource() == combatStatsSelectButton) {
			statsframe = new combatStatsWindow(user);
			statsframe.setVisible(true);
		}
		if (evt.getSource() == HospitalSelectButton) {
			hospitalframe = new hospitalWindow(user);
			hospitalframe.setVisible(true);
		}
		if (evt.getSource() == combatSelectButton) {
			// TODO: Remove the following line and make real combat
			user.setCurrentHealth(user.getCurrentHealth() - 5);
		}
		if (evt.getSource() == ShopSelectButton) {
			// TODO: Remove the following line and make real shop
			user.setMoney(user.getMoney() + 5);
		}
		if (evt.getSource() == QuitSelectButton) {
			saveframe = new SaveDialogue(user);
		}
	}

	/* WindowEvent handlers */
	// Called back upon clicking close-window button
	@Override
	public void windowClosing(WindowEvent evt) {
		saveframe = new SaveDialogue(user);
	}

	// Not Used, but need to provide an empty body to compile.
	@Override	public void windowOpened(WindowEvent evt) {}
	@Override	public void windowClosed(WindowEvent evt) {}
	@Override	public void windowIconified(WindowEvent evt) {}
	@Override	public void windowDeiconified(WindowEvent evt) {}
	@Override
	public void windowActivated(WindowEvent evt) {
		try{
			saveframe.dispose();
		}
		catch(NullPointerException e) {}
		try{
			hospitalframe.dispose();
		}
		catch(NullPointerException e) {}
		try{
			inventoryframe.dispose();
		}
		catch(NullPointerException e) {}
		try{
			statsframe.dispose();
		}
		catch(NullPointerException e) {}
		try{
			deleteCharFrame.dispose();
		}
		catch(NullPointerException e) {}
		
		/* Update XP and Levels! (XP can be gained from Inventory window.) */
		this.updateLevels();
	}

	@Override	public void windowDeactivated(WindowEvent evt) {}

}