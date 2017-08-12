package miniRPG;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

// Using Frame class in package java.awt
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;

public class deleteCharWindow extends Frame implements ActionListener, WindowListener {
    private Label dialogueText;
    private Label dialogueTextBottom;
    private Button discardButton;
    private Button cancelButton;

    private Panel buttons;

    private User user;

    public deleteCharWindow(User user) {
        this.user = user;

        dialogueText = new Label("YOU ARE ATTEMPTING TO DELETE YOUR ACCOUNT.");
        dialogueTextBottom = new Label("THIS WILL ERASE ALL PROGRESS.");
        discardButton = new Button("DISCARD");
        cancelButton = new Button("BACK");
        discardButton.addActionListener(this);
        cancelButton.addActionListener(this);

        buttons = new Panel();

        buttons.add(dialogueText);
        buttons.add(discardButton);
        buttons.add(cancelButton);
        buttons.add(dialogueTextBottom);
        this.add(buttons);

        /* Window Setup */
        addWindowListener(this);
        // "super" Frame (source object) fires WindowEvent.
        // "super" Frame adds "this" object as a WindowEvent listener.
        setTitle("DELETE ACCOUNT?");
        setSize(350, 130);
        setVisible(true);


    }
    // ActionEvent handlers
    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == discardButton){
            try {
                Play.deleteUser();
            } catch (IOException e) {
                System.out.println("ERR: No user found to save");
                e.printStackTrace();
            }
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
