package miniRPG;

import java.awt.*;  // Using Frame class in package java.awt
import java.awt.event.*;
import javax.swing.JProgressBar;

public class startGameGui extends Frame
implements ActionListener, WindowListener {		
	// UI Elements
	private Label nameLabel;
	private Label classLabel;
	private List classList;
	private Button cancelButton;
	private Button confirmButton;
	private TextField nameEntry;
	
	// Selection var for classList
	private String userClass;
	private String userName;
	
	public startGameGui(){
		setLayout(new FlowLayout());
		
		Panel name = new Panel();
		Panel classSelect = new Panel();
		Panel buttons = new Panel();
		
		// Menu elements
		nameLabel = new Label ("Enter your name:");
		nameEntry = new TextField("",30);
		classLabel = new Label("Select a class:");
		classList = new List(7, false);
			classList.add("Barbarian (+25 ATK; -15 HP)");
			classList.add("Knight (+15 DEF; +20 HP");
			classList.add("Thief (+10 ATK; +5% GP Gain");
			classList.add("Warrior (+10 ATK; +10 DEF");
			classList.add("Chicken Tender (Random Stats)");
			classList.select(0);
			userClass = "Barbarian";
		cancelButton = new Button("Quit");
		confirmButton = new Button("Start!");
		
		// add listeners
		cancelButton.addActionListener(this);
		confirmButton.addActionListener(this);
		classList.addActionListener(this);
		
		// classList Listener definition
		classList.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ievt){
				if(classList.getSelectedIndex()==0){
					userClass = "Barbarian";
				}
				else if(classList.getSelectedIndex()==1){
					userClass = "Knight";
				}
				else if(classList.getSelectedIndex()==2){
					userClass = "Thief";
				}
				else if(classList.getSelectedIndex()==3){
					userClass = "Warrior";
				}
				else if(classList.getSelectedIndex()==4){
					userClass = "Chicken Tender";
				}
				else{
					userClass = "Barbarian"; // error handling
				}
			}
		});
		
		
		// add elements
		name.add(nameLabel);
		name.add(nameEntry);
		classSelect.add(classLabel);
		classSelect.add(classList);
		buttons.add(cancelButton);
		buttons.add(confirmButton);
		
		/* Window Setup */
		addWindowListener(this);
        // "super" Frame (source object) fires WindowEvent.
        // "super" Frame adds "this" object as a WindowEvent listener.
		setTitle("Configure your character");
		setSize(400,250);
		setVisible(true);
		
		add(name);
		add(classSelect);
		add(buttons);
	}
	
	// The entry main() method
    public static void main(String[] args) {
        // Invoke the constructor (to set up the GUI) by allocating an instance
    	new startGameGui();
    }
	
	
	//ActionEvent handler 
    @Override
    public void actionPerformed(ActionEvent evt) {
    	if(evt.getSource() == cancelButton){
    		System.exit(0);
    	}
    	if(evt.getSource() == confirmButton){
    		userName = nameEntry.getText();
    		GUIWindow frame = new GUIWindow(userName, userClass);
    		frame.setVisible(true);
    		this.setVisible(false);
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