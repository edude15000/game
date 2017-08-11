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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class inventoryWindow extends Frame implements ActionListener, WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// UI Elements
	private List inventoryList;
	private Button cancelInventoryButton;
	private Button confirmInventoryButton;
	private TextField messages;
	private Label moneyLabel;

	// Accessible vars
	private int selection;
	private User user;

	// Constructor
	public inventoryWindow(User user) {
		// pass in user for use with the items
		this.user = user;

		// attempt to dissuade the player from opening multiple inventories -- if they
		// want multiple, they can still get them, but they won't want them. Hopefully.
		this.setAlwaysOnTop(true);

		setLayout(new FlowLayout());
		// "super" Frame, which is a Container, sets its layout to FlowLayout to
		// arrange
		// the components from left-to-right, and flow to next row from
		// top-to-bottom.

		Panel inventory = new Panel();
		Panel money = new Panel();
		Panel textbox = new Panel();
		Panel buttons = new Panel();

		// Define elements
		// Establish button
		cancelInventoryButton = new Button("Cancel");
		confirmInventoryButton = new Button("Use");
		inventoryList = new java.awt.List(8);// init the list gui object to scroll, but only display 8 rows at a time

		messages = new TextField("Select an item, then click \"Use\".", 30);
		messages.setEditable(false);

		// add items to list
		for (int i = 0; i < user.getItemList().size(); i++) {
			inventoryList.add(user.getItemList().get(i).getName());
		}

		// Using an item listener, set the selection variable equal to the new selection
		inventoryList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ievt) {
				selection = inventoryList.getSelectedIndex();
			}
		});

		// Display UI elements
		inventory.add(inventoryList);
		buttons.add(cancelInventoryButton);
		buttons.add(confirmInventoryButton);
		textbox.add(messages);
		moneyLabel = new Label("$" + user.getMoney());
		money.add(moneyLabel);

		inventoryList.addActionListener(this);
		cancelInventoryButton.addActionListener(this);
		confirmInventoryButton.addActionListener(this);

		/* Window Configurations! */
		setTitle("Inventory");
		setSize(280, 280);
		setVisible(true);// Inventory window shows
		add(textbox);
		add(inventory);// add panel with list
		add(money);
		add(buttons);

		if (user.getItemList().size() == 0) {
			confirmInventoryButton.setEnabled(false);
		}
	}

	public void consumeItem(User user, int itemIndex) {
		user.useItem(itemIndex);
		inventoryList.removeAll();
		for (int i = 0; i < user.getItemList().size(); i++) {
			inventoryList.add(user.getItemList().get(i).getName());
		}

	}

	/* Actions! */
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == cancelInventoryButton) {
			this.dispose();
		}
		if (evt.getSource() == confirmInventoryButton) {
			try {
				consumeItem(this.user, this.selection);
				if (user.getItemList().size() == 0) {
					confirmInventoryButton.setEnabled(false);
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