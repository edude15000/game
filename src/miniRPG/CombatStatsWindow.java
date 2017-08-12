package miniRPG;

// Using Frame class in package java.awt
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JProgressBar;

public class CombatStatsWindow extends Frame implements ActionListener, WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// UI Elements
	private Button closeButton;
	private Label attackLabel;
	private Label defenseLabel;
	private Label combatLabel;
	private Label combatXpLabel;
	private JProgressBar healthbar;

	// Accessible user
	private User user;

	// Constructor
	public CombatStatsWindow(User user) {

		// pass in user for use with the items
		this.user = user;

		// attempt to dissuade the player from opening multiple windows -- if they want
		// multiple, they can still get them, but they won't want them. Hopefully.
		this.setAlwaysOnTop(true);

		setLayout(new FlowLayout());
		// "super" Frame, which is a Container, sets its layout to FlowLayout to
		// arrange
		// the components from left-to-right, and flow to next row from
		// top-to-bottom.

		Panel healthPanel = new Panel();
		Panel stats = new Panel();
		Panel overall = new Panel();

		// Define elements
		attackLabel = new Label("Attack Lvl:" + user.getAttack());
		stats.add(attackLabel);
		defenseLabel = new Label("Defense Lvl:" + user.getDefense());
		stats.add(defenseLabel);
		combatLabel = new Label("Combat Lvl:" + user.getLevel("Combat"));
		overall.add(combatLabel);
		combatXpLabel = new Label("Combat XP:" + user.getLevelObject("Combat").getXp());
		overall.add(combatXpLabel);

		// Initialize healthbar
		healthbar = new JProgressBar(0, user.getTotalHealth());
		healthbar.setValue(user.getCurrentHealth());
		healthbar.setStringPainted(true);
		healthbar.setString(user.getCurrentHealth() + "/" + user.getTotalHealth() + " HP");
		healthPanel.add(healthbar);

		// Establish button
		closeButton = new Button("Close");
		closeButton.addActionListener(this);

		/* Window Configurations! */
		setTitle("Combat Stats");
		setSize(258, 190);
		setVisible(true);// Inventory window shows
		add(healthPanel);
		add(stats);
		add(overall);
		add(closeButton);
	}

	/* Actions! */
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == closeButton) {
			this.dispose();
		}
	}

	/* WindowEvent handlers */
	// Called back upon clicking close-window button
	@Override
	public void windowClosing(WindowEvent evt) {
		this.dispose();
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