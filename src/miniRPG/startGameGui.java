package miniRPG;

// Using Frame class in package java.awt
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
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

public class startGameGui extends Frame implements ActionListener, WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// UI Elements
	private Label nameLabel;
	private Label classLabel;
	private Label hardcoreModeLabel;
	private List classList;
	private Button cancelButton;
	private Button confirmButton;
	private TextField nameEntry;
	JRadioButton noButton;
	JRadioButton yesButton;

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
		hardcoreModeLabel = new Label("Hardcore mode? (reset on death)");

		yesButton = new JRadioButton("Yes");
		yesButton.setMnemonic(KeyEvent.VK_B);
		yesButton.setActionCommand("Yes");
		yesButton.setSelected(false);

		noButton = new JRadioButton("No");
		noButton.setMnemonic(KeyEvent.VK_B);
		noButton.setActionCommand("No");
		noButton.setSelected(true);

		ButtonGroup group = new ButtonGroup();
		group.add(noButton);
		group.add(yesButton);

		classList.add("Barbarian (+20 ATK; -15 HP, -5 SPD)");
		classList.add("Knight (-5 ATK, +15 DEF; +20 HP)");
		classList.add("Thief (+10 ATK; +5% GP Gain, +5 SPD)");
		classList.add("Warrior (+10 ATK; +10 DEF");
		classList.add("Chicken Tender (Random Stats)");
		classList.select(0);
		userClass = "Barbarian";
		cancelButton = new Button("Quit");
		confirmButton = new Button("Start!");

		hardcoreMode = false;

		// add listeners
		cancelButton.addActionListener(this);
		confirmButton.addActionListener(this);
		classList.addActionListener(this);
		noButton.addActionListener(this);
		yesButton.addActionListener(this);

		noButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (noButton.isSelected()) {
					hardcoreMode = false;
				} else {
					hardcoreMode = true;
				}
			}
		});

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

	/* WindowEvent handlers */
	// Called back upon clicking close-window button
	@Override
	public void windowClosing(WindowEvent evt) {
		System.exit(0); // Terminate the program
	}

	// Not Used, but need to provide an empty body to compile.
	@Override
	public void windowOpened(WindowEvent evt) {
	}

	@Override
	public void windowClosed(WindowEvent evt) {
	}

	@Override
	public void windowIconified(WindowEvent evt) {
	}

	@Override
	public void windowDeiconified(WindowEvent evt) {
	}

	@Override
	public void windowActivated(WindowEvent evt) {
	}

	@Override
	public void windowDeactivated(WindowEvent evt) {
	}

}