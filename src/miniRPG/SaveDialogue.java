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
	private Button cancelButton;
	
	private Panel buttons;
	
	private User user;
	
	public SaveDialogue(User user) {
		this.user = user;
		
		dialogueText = new Label("                    Save before exiting?                    ");
		saveButton   = new Button("Save");
		noSaveButton = new Button("Don't Save");
		cancelButton = new Button("Cancel");
		saveButton.addActionListener(this);
		noSaveButton.addActionListener(this);
		cancelButton.addActionListener(this);

		buttons = new Panel();
		
		buttons.add(dialogueText);
		buttons.add(saveButton);
		buttons.add(noSaveButton);
		buttons.add(cancelButton);
		this.add(buttons);
		
		/* Window Setup */
		addWindowListener(this);
		// "super" Frame (source object) fires WindowEvent.
		// "super" Frame adds "this" object as a WindowEvent listener.
		setTitle("Save");
		setSize(300, 110);
		setVisible(true);
		
		
	}
	// ActionEvent handlers
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == saveButton){
			try {
				Play.saveData(user);
			} catch (IOException e) {
				System.out.println("ERR: No user found to save");
				e.printStackTrace();
			}
			System.exit(0);
		}
		if(evt.getSource() == noSaveButton){
			System.exit(0);
		}
		if(evt.getSource() == cancelButton){
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
	@Override public void windowOpened(WindowEvent evt) {}
	@Override public void windowClosed(WindowEvent evt) {}
	@Override public void windowIconified(WindowEvent evt) {}
	@Override public void windowDeiconified(WindowEvent evt) {}
	@Override public void windowActivated(WindowEvent evt) {}
	@Override public void windowDeactivated(WindowEvent evt) {}
}