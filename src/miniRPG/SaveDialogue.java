package miniRPG;

// Using Frame class in package java.awt
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JProgressBar;

public class SaveDialogue extends Frame implements ActionListener, WindowListener {
	private Label dialogueText;
	private Button saveButton;
	private Button noSaveButton;
	
	private Panel buttonPanel;
	private Panel textPanel;
	
	private User user;
	
	public SaveDialogue(User user) {
		this.user = user;
		
		dialogueText = new Label("Would you like to save before exiting?");
		saveButton = new Button("Save");
		noSaveButton = new Button("Don't Save");
		saveButton.addActionListener(this);
		noSaveButton.addActionListener(this);
		
		buttonPanel = new Panel();
		textPanel = new Panel();
		
		textPanel.add(dialogueText);
		buttonPanel.add(saveButton);
		buttonPanel.add(noSaveButton);
		
		/* Window Setup */
		addWindowListener(this);
		// "super" Frame (source object) fires WindowEvent.
		// "super" Frame adds "this" object as a WindowEvent listener.
		setTitle("Quit without saving?");
		setSize(100, 50);
		setVisible(true);

		add(textPanel);
		add(buttonPanel);
	}
	// ActionEvent handlers
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == saveButton){
			try {
				Play.saveData(user);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
			System.exit(0);
		}
		if(evt.getSource() == noSaveButton){
			System.exit(0);
		}
	}
	
	
	
	
	
	
	/* WindowEvent handlers */
	// Called back upon clicking close-window button
	@Override
	public void windowClosing(WindowEvent evt) { //do nothing. pick an option, dammit.
	}

	// Not Used, but need to provide an empty body to compile.
	@Override public void windowOpened(WindowEvent evt) {}
	@Override public void windowClosed(WindowEvent evt) {}
	@Override public void windowIconified(WindowEvent evt) {}
	@Override public void windowDeiconified(WindowEvent evt) {}
	@Override public void windowActivated(WindowEvent evt) {}
	@Override public void windowDeactivated(WindowEvent evt) {}
}