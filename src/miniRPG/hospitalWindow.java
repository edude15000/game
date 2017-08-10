package miniRPG;

import java.awt.*;  // Using Frame class in package java.awt
import java.awt.event.*;
import javax.swing.JProgressBar;

public class hospitalWindow extends Frame
implements ActionListener, WindowListener {		
	// UI Elements
	private Button closeButton;
	private Label healQuestion;
	private TextField healAmount;
	private Label priceLabel;
	private Label healClarify;
	private Button confirmButton;
	private JProgressBar healthbar;
	private int confirmedHealAmount;

	// Accessible user
	private User user;
	
	// Constructor
	public hospitalWindow(User user){
		this.user = user;
	
		//attempt to dissuade the player from opening multiple windows -- if they want multiple, they can still get them, but they won't want them. Hopefully.
		this.setAlwaysOnTop(true);
		
		setLayout(new FlowLayout());
		// "super" Frame, which is a Container, sets its layout to FlowLayout to
		// arrange
		// the components from left-to-right, and flow to next row from
		// top-to-bottom.
	
		Panel healthBarPanel = new Panel();
		Panel question = new Panel();
		Panel clarify = new Panel();
		Panel healAmountPanel = new Panel();
		Panel buttons = new Panel();
		
		//Healthbar is a bulk of code...
		healthbar = new JProgressBar(0, user.getTotalHealth());
    	healthbar.setValue(user.getCurrentHealth());
    	healthbar.setStringPainted(true);
    	healthbar.setString(user.getCurrentHealth()+"/"+user.getTotalHealth()+" HP");
		
    	healClarify = new Label ("The Hospital charges $1 to heal 1 HP.");
		healQuestion = new Label("How much would you like to heal?");
		healAmount = new TextField ("0", 5);
		priceLabel = new Label("$"+user.getMoney());
		closeButton = new Button("Leave Hospital");
		confirmButton = new Button("Heal Up!");
		
		// add listeners
		closeButton.addActionListener(this);
		confirmButton.addActionListener(this);
		
		// add elements
		healthBarPanel.add(healthbar);
		
		clarify.add(healClarify);
		question.add(healQuestion);
		healAmountPanel.add(priceLabel);
		healAmountPanel.add(healAmount);
		
		
		buttons.add(confirmButton);
		buttons.add(closeButton);
		
		/* Window Configurations!*/
		setTitle("Hospital");
		setSize(300, 255);		
		setVisible(true);
		add(healthBarPanel);
		add(clarify);
		add(question);
		add(healAmountPanel);
		add(buttons);
	}
	
	
	/* Actions! */
	@Override
    public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == closeButton){
			this.dispose();
		}
		if(evt.getSource() == confirmButton){
			try{ // Try to initiate the heal. Will catch if letters are there.
				confirmedHealAmount = Integer.parseInt(healAmount.getText());
				if(confirmedHealAmount > user.getTotalHealth()-user.getCurrentHealth()){
					healQuestion.setText("You can't heal that much!");
				}
				else if(confirmedHealAmount>0){ // Heal and reset all labels on the window.
					if(confirmedHealAmount<=user.getMoney()){
						user.heal(confirmedHealAmount);
						user.setMoney(user.getMoney()-confirmedHealAmount);
						priceLabel.setText("$"+Integer.toString(user.getMoney()));
						// Update Health Bar
						healthbar.setValue(user.getCurrentHealth());
				    	healthbar.setStringPainted(true);
				    	healthbar.setString(user.getCurrentHealth()+"/"+user.getTotalHealth()+" HP");
				    	healQuestion.setText("Healed up! Heal for more?");
					}
					else{
						healQuestion.setText("Not enough money!");
					}
					}
				else{
					healQuestion.setText("Probably want more than that.");
				}
			}
			catch(NumberFormatException e){
				healQuestion.setText("Enter a valid number.");
			}
		}
	}
	
    /* WindowEvent handlers */
    // Called back upon clicking close-window button
    @Override
    public void windowClosing(WindowEvent evt) {
    	this.dispose();
    }
  
    // Not Used, but need to provide an empty body to compile.
    @Override public void windowOpened(WindowEvent evt) { }
    @Override public void windowClosed(WindowEvent evt) { }
    @Override public void windowIconified(WindowEvent evt) { }
    @Override public void windowDeiconified(WindowEvent evt) { }
    @Override public void windowActivated(WindowEvent evt) { }
    @Override public void windowDeactivated(WindowEvent evt) { }
}