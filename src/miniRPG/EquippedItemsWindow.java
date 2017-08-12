package miniRPG;

// Using Frame class in package java.awt

import java.awt.*;
import java.awt.event.*;

public class EquippedItemsWindow extends Frame implements ActionListener, WindowListener {
	private static final long serialVersionUID = 1L;

	// UI Elements
	private List itemList;
	private Button cancelButton;
	private Button confirmButton;
	private Label moneyLabel;

	// Accessible vars
	private int selection;
	private User user;

	// Constructor
	public EquippedItemsWindow(User user) {
		// pass in user for use with the items
		this.user = user;

		setLayout(new FlowLayout());
		// "super" Frame, which is a Container, sets its layout to FlowLayout to
		// arrange
		// the components from left-to-right, and flow to next row from
		// top-to-bottom.

		Panel listPanel = new Panel();
		Panel buttons = new Panel();

		// Define elements
		// Establish button
		cancelButton = new Button("Close");
		confirmButton = new Button("Unequip");
		confirmButton.setEnabled(false); // activates when item is selected
		itemList = new List(8);   // init the list gui object to scroll, but only display 8 rows at a time

		// add items to list
		for (int i = 0; i < user.getEquippedItems().size(); i++) {
			itemList.add(user.getEquippedItems().get(i).getName());
		}

		// Using an item listener, set the selection variable equal to the new selection
		itemList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ievt) {
				selection = itemList.getSelectedIndex();
				confirmButton.setEnabled(true);
			}
		});

		// Display UI elements
		listPanel.add(itemList);
		buttons.add(cancelButton);
		buttons.add(confirmButton);

		itemList.addActionListener(this);
		cancelButton.addActionListener(this);
		confirmButton.addActionListener(this);

		/* Window Configurations! */
		setTitle("Equipped Items");
		setSize(280, 220);
		setVisible(true);// Inventory window shows
		add(listPanel);// add panel with list
		add(buttons);

		if (user.getEquippedItems().size() == 0) {
			confirmButton.setEnabled(false);
		}
	}

	public void consumeItem(User user, int itemIndex) {
		user.removeEquippedItem(itemIndex);
		itemList.removeAll();
		for (int i = 0; i < user.getEquippedItems().size(); i++) {
			itemList.add(user.getEquippedItems().get(i).getName());
		}
		confirmButton.setEnabled(false);

	}

	/* Actions! */
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == cancelButton) {
			this.dispose();
		}
		if (evt.getSource() == confirmButton) {
			try {
				consumeItem(user, this.selection);
				if (user.getEquippedItems().size() == 0) {
					confirmButton.setEnabled(false);
				}
			} catch (IndexOutOfBoundsException e) {
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