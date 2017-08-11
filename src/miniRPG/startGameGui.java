package miniRPG;

// Using Frame class in package java.awt
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.CheckboxGroup;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

public class startGameGui extends Frame implements ActionListener, WindowListener, ItemListener {
	// UI Elements
	private Label nameLabel;
	private Label classLabel;
	private Label hardcoreModeLabel;
	private List classList;
	private Button cancelButton;
	private Button confirmButton;
	private TextField nameEntry;
	private CheckboxGroup checkGroup;
	private Checkbox noButton;
	private Checkbox yesButton;

	// Selection var for classList
	private String userClass;
	private String userName;
	private boolean hardcoreMode;

	public startGameGui() {
		setLayout(new FlowLayout());

		Panel name = new Panel();
		Panel classSelect = new Panel();
		Panel hardcoreSelect = new Panel();
		Panel buttons = new Panel();

		// Menu elements
		nameLabel = new Label("Enter your name:");
		nameEntry = new TextField("", 30);
		classLabel = new Label("Select a class:");
		classList = new List(7, false);
		hardcoreModeLabel = new Label("Hardcore mode? (Reset on death.)");

		yesButton = new Checkbox("Yes", checkGroup, false);
		noButton = new Checkbox("No", checkGroup, true);
		yesButton.setCheckboxGroup(checkGroup);
		noButton.setCheckboxGroup(checkGroup);
		noButton.setEnabled(false);
		
		classList.add("Barbarian (+20 ATK; -15 HP, -5 SPD)");
		classList.add("Knight (-5 ATK, +15 DEF; +20 HP)");
		classList.add("Thief (+10 ATK; +5% GP Gain, +5 SPD)");
		classList.add("Warrior (+10 ATK; +10 DEF");
		classList.add("Chicken Tender (Random Stats)");
		classList.select(0);
		userClass = "Barbarian";
		cancelButton = new Button("Quit");
		confirmButton = new Button("Start!");
		
		// Initialize hardcore
		hardcoreMode = false;

		// add listeners
		cancelButton.addActionListener(this);
		confirmButton.addActionListener(this);
		classList.addActionListener(this);
		noButton.addItemListener(this);
		yesButton.addItemListener(this);

		// classList Listener definition
		classList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ievt) {
				if (classList.getSelectedIndex() == 0) {
					userClass = "Barbarian";
				} else if (classList.getSelectedIndex() == 1) {
					userClass = "Knight";
				} else if (classList.getSelectedIndex() == 2) {
					userClass = "Thief";
				} else if (classList.getSelectedIndex() == 3) {
					userClass = "Warrior";
				} else if (classList.getSelectedIndex() == 4) {
					userClass = "Chicken Tender";
				} else {
					userClass = "Barbarian"; // error handling
				}
			}
		});

		// add elements
		name.add(nameLabel);
		name.add(nameEntry);
		classSelect.add(classLabel);
		classSelect.add(classList);
		hardcoreSelect.add(hardcoreModeLabel);
		hardcoreSelect.add(noButton);
		hardcoreSelect.add(yesButton);
		buttons.add(cancelButton);
		buttons.add(confirmButton);

		/* Window Setup */
		addWindowListener(this);
		// "super" Frame (source object) fires WindowEvent.
		// "super" Frame adds "this" object as a WindowEvent listener.
		setTitle("Configure your character");
		setSize(400, 300);
		setVisible(true);

		add(name);
		add(classSelect);
		add(hardcoreSelect);
		add(buttons);

	}

	// The entry main() method
	public static void main(String[] args) throws IOException {
		// Invoke the constructor (to set up the GUI) by allocating an instance
		User user = Play.loadData();
		if (user == null) {
			new startGameGui();
		} else {
			GUIWindow frame = new GUIWindow(user.userName, user.userClass, user.hardcoreMode);
			frame.setVisible(true);
		}
	}

	// ActionEvent handler
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == cancelButton) {
			System.exit(0);
		}
		if (evt.getSource() == confirmButton) {
			userName = nameEntry.getText();
			GUIWindow frame;
			try {
				frame = new GUIWindow(userName, userClass, hardcoreMode);
				frame.setVisible(true);
				this.setVisible(false);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	// Item event handlers
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getItemSelectable() == yesButton){
			hardcoreMode = true;
			noButton.setState(false);
			yesButton.setEnabled(false);
			noButton.setEnabled(true);
		}
		else{
			hardcoreMode = false;
			yesButton.setState(false);
			yesButton.setEnabled(true);
			noButton.setEnabled(false);
		}
	}

	/* WindowEvent handlers */
	// Called back upon clicking close-window button
	@Override
	public void windowClosing(WindowEvent evt) {
		System.exit(0); // Terminate the program
	}

	// Not Used, but need to provide an empty body to compile.
	@Override public void windowOpened(WindowEvent evt) {}
	@Override public void windowClosed(WindowEvent evt) {}
	@Override public void windowIconified(WindowEvent evt) {}
	@Override public void windowDeiconified(WindowEvent evt) {}
	@Override public void windowActivated(WindowEvent evt) {}
	@Override public void windowDeactivated(WindowEvent evt) {}
}