package cs356.a2.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import cs356.a2.logic.TreeView;
import cs356.a2.users.Admin;

// Class that generates the basic layout of the Admin UI
public class AdminUI implements UserInterface {

	private static AdminUI instance;
	private Admin admin;

	private JFrame frame;
	private GridBagConstraints gbc;
	private JPanel mainPanel;

	private String input, lastUpdatedUser;
	private TreeView jtree;
	private JLabel lastUpdated;

	private AdminUI() {
	};

	protected static AdminUI getInstance() {
		if (instance == null) {
			instance = new AdminUI();
		}
		return instance;
	}

	@Override
	public void init() {
		admin = new Admin();
		jtree = new TreeView();
		admin.setTree(jtree);
		setFrame(800, 650);
		setLayout(new GridBagLayout());
		showFrame();
	}

	@Override
	public void setFrame(int x, int y) {
		frame = new JFrame();
		frame.setSize(x, y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setLayout(LayoutManager layout) {
		frame.getContentPane().setLayout(layout);
		gbc = new GridBagConstraints();

		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;

		mainPanel = new JPanel();
		generateLeftSide(layout);
		generateRightSide(layout);
		
		frame.add(mainPanel);
	}

	// Generate content of left column
	public void generateLeftSide(LayoutManager layout) {
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(350, 500));
		leftPanel.setBackground(Color.WHITE);
		leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		leftPanel.add(jtree);

		((GridBagLayout) layout).setConstraints(leftPanel, gbc);

		// Popup menu
		JPopupMenu menu = new JPopupMenu("Popup");
		JMenuItem addUserItem = new JMenuItem("Add User");
		JMenuItem addGroupItem = new JMenuItem("Add User Group");

		addUserItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				input = (String) JOptionPane.showInputDialog(frame, "Enter User Name",
						"Create New User", JOptionPane.QUESTION_MESSAGE);
				if (input != null) {
					if (!input.equals("")) {
						admin.addNewUser(input);
					} else {
						JOptionPane.showMessageDialog(frame, "Please input a user name.", 
							"Warning: Empty Input", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		addGroupItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				input = JOptionPane.showInputDialog(frame, "Enter Group Name",
						"Create New User Group", JOptionPane.QUESTION_MESSAGE);
				if (input != null) {
					if (!input.equals("")) {
						admin.addNewUserGroup(input);
					} else {
						JOptionPane.showMessageDialog(frame, "Please input a user group.", 
								"Warning: Empty Input", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		menu.add(addUserItem);
		menu.add(addGroupItem);

		MouseListener mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if (SwingUtilities.isRightMouseButton(mouseEvent)) {
					menu.show(mouseEvent.getComponent(), mouseEvent.getX(),
							mouseEvent.getY());
				}
			}
		};

		leftPanel.addMouseListener(mouseListener);

		mainPanel.add(leftPanel);
	}

	// Generate right side column
	public void generateRightSide(LayoutManager layout) {
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(290, 500));
		rightPanel.setLayout(new GridLayout(2, 0));

		JPanel topRightPanel = new JPanel(new GridLayout(2, 0));

		JLabel tips = new JLabel("<html> Right click on TreeView to bring up 'Add User'<br/> "
				+ "and 'Add User Group' menu items. </html>");
		topRightPanel.add(tips);
		lastUpdated = new JLabel("Last updated user: ");
		

		JButton openUserViewButton = new JButton("Open User View");

		openUserViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (!jtree.isUserGroup()) {
					admin.getSelectedUserView(jtree.getSelectedUserNode());
				}
			}
		});

		topRightPanel.add(openUserViewButton);

		JPanel bottomRightPanel = new JPanel();
		bottomRightPanel.setLayout(new GridLayout(3, 2));

		JButton showUserButton = new JButton("Show User Total");

		showUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(frame, "Total number of users: "
						+ admin.getTotalUsers(), "Show User Total",
						JOptionPane.PLAIN_MESSAGE);
			}
		});

		JButton showGroupButton = new JButton("Show User Group Total");

		showGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(frame, "Total number of user groups: "
						+ admin.getTotalUserGroups(), "Show User Total", 
						JOptionPane.PLAIN_MESSAGE);
			}
		});

		JButton showMessagesButton = new JButton("Show Messages Total");
		showMessagesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(frame, "Total number of messages: "
						+ admin.getTotalMessages(), "Show Total Messages",
						JOptionPane.PLAIN_MESSAGE);
			}
		});

		JButton showPositiveButton = new JButton("Show Positive Percentage");
		showPositiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(frame, "Total number of positive messages: "
						+ admin.getTotalPositiveMessages() + "%", "Show Total Positive Messages",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		JButton showValidation = new JButton("Show Validation");
		showValidation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(frame, "ID Validation is: " + jtree.isValidation(),
						"Show ID Validation", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		JButton showLastUpdated = new JButton("Last Updated User");
		showLastUpdated.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(frame, "Last updated user: "
						+ lastUpdatedUser, "Show Last Updated User",
						JOptionPane.PLAIN_MESSAGE);
			}
		});

		bottomRightPanel.add(showUserButton);
		bottomRightPanel.add(showGroupButton);
		bottomRightPanel.add(showMessagesButton);
		bottomRightPanel.add(showPositiveButton);
		bottomRightPanel.add(showValidation);
		bottomRightPanel.add(showLastUpdated);

		rightPanel.add(topRightPanel);
		rightPanel.add(bottomRightPanel);

		gbc.weightx = 1.0;
		gbc.gridx = 2;
		gbc.gridy = 0;

		((GridBagLayout) layout).setConstraints(rightPanel, gbc);
		mainPanel.add(rightPanel);
	}

	@Override
	public void showFrame() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	// Returns the last updated user
	public void setLastUpdated(String userID) {
		lastUpdatedUser = userID;
	}

}
