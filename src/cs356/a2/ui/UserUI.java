package cs356.a2.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import cs356.a2.logic.TreeView;
import cs356.a2.users.User;

public class UserUI implements UserInterface {

	private JFrame frame;
	private User user;
	private boolean firstUserClick = true;
	private boolean firstMessageClick = true;
	private DefaultListModel<String> followingListModel, messageListModel;
	private JList<String> followingView, messageView;
	private JPanel mainPanel, followingListPanel, messageListPanel;
	private JLabel userLastUpdate;
	private ArrayList<User> usersFollowing;
	private TreeView jtree;
	private AdminUI adminUI;

	public UserUI(User user, TreeView jtree) {
		this.adminUI = AdminUI.getInstance();
		this.user = user;
		this.jtree = jtree;
		init();
	}

	@Override
	public void init() {
		setFrame(700, 650);
		setLayout(new FlowLayout());
		showFrame();
	}

	@Override
	public void setFrame(int x, int y) {
		frame = new JFrame();
		frame.setTitle(user.getUserID() + "'s Profile");
		frame.setSize(x, y);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void setLayout(LayoutManager layout) {
		frame.getContentPane().setLayout(layout);

		GridLayout mainGrid = new GridLayout(3, 1);
		GridLayout sideGrid = new GridLayout(2, 1);
		GridLayout infoGrid = new GridLayout(1, 2);
		
		mainPanel = new JPanel(mainGrid);
		JPanel topPanel = new JPanel(sideGrid);
		JPanel bottomPanel = new JPanel(sideGrid);
		JPanel updateInfoPanel = new JPanel(infoGrid);
		JPanel followingInputPanel = new JPanel();
		followingListPanel = new JPanel();
		JPanel messageInputPanel = new JPanel();
		messageListPanel = new JPanel();

		JLabel userCreationTime = new JLabel();
		userCreationTime.setText("User creation time: " + String.valueOf(user.getTimeStamp()));
		
		userLastUpdate = new JLabel();
		updateLastUpdateTime();
		
		updateInfoPanel.add(userCreationTime);
		updateInfoPanel.add(userLastUpdate);
		
		JTextField userIDTextField = new JTextField(20);
		userIDTextField.setText("Enter User ID");

		userIDTextField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				/*
				 * Check if it is user's first time clicking on text field. If
				 * true, clear field; else, do nothing.
				 */
				if (firstUserClick) {
					userIDTextField.setText("");
					firstUserClick = false;
				}
			}
		});

		JButton followUserButton = new JButton("Follow User");
		followUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String userIDInput = userIDTextField.getText();
				if (!userIDInput.equals("")) {
					if (jtree.nodeExists(userIDInput)) {
						User addFollowingUser = jtree.getUserFrom(userIDInput);
						user.addFollowing(addFollowingUser);
						refresh();
					} else {
						JOptionPane.showMessageDialog(frame, "Please input an existing user.", 
								"Warning: User Doesn't Exist", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Please input a user.", 
							"Warning: Empty Input", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JLabel followingsLabel = new JLabel("Currently Following: ");
		followingView = null;
		followingListModel = new DefaultListModel<String>();
		setFollowingList();
		followingView = new JList<String>(followingListModel);
		JScrollPane followingScroll = new JScrollPane(followingView);
		followingScroll.setPreferredSize(new Dimension(500, 100));

		followingInputPanel.add(userIDTextField);
		followingInputPanel.add(followUserButton);
		followingListPanel.add(followingsLabel);
		followingListPanel.add(followingScroll);

		JTextField messageTextField = new JTextField(20);
		messageTextField.setText("Enter a message");

		messageTextField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				/*
				 * Check if it is user's first time clicking on text field. If
				 * true, clear field; else, do nothing.
				 */
				if (firstMessageClick) {
					messageTextField.setText("");
					firstMessageClick = false;
				}
			}
		});

		JButton messageButton = new JButton("Post Message");
		messageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String userMessage = messageTextField.getText();
				if (!userMessage.equals("")) {
					user.addNews(userMessage);
					messageTextField.setText("");
					adminUI.setLastUpdated(user.getUserID());
				} else {
					JOptionPane.showMessageDialog(frame, "Please input a user.", 
							"Warning: Empty Input", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		messageView = null;
		JLabel messageLabel = new JLabel("News Feed :");
		messageListModel = new DefaultListModel<String>();
		setMessageList();
		messageView = new JList<String>(messageListModel);
		JScrollPane messageScroll = new JScrollPane(messageView);
		messageScroll.setPreferredSize(new Dimension(500, 100));

		messageInputPanel.add(messageTextField);
		messageInputPanel.add(messageButton);
		messageListPanel.add(messageLabel);
		messageListPanel.add(messageScroll);

		topPanel.add(followingInputPanel);
		topPanel.add(followingListPanel);
		bottomPanel.add(messageInputPanel);
		bottomPanel.add(messageListPanel);
		mainPanel.add(topPanel);
		mainPanel.add(bottomPanel);
		frame.add(mainPanel);
		
		mainPanel.add(topPanel);
		mainPanel.add(bottomPanel);
		mainPanel.add(updateInfoPanel);
		topPanel.add(followingInputPanel);
		topPanel.add(followingListPanel);
		bottomPanel.add(messageInputPanel);
		bottomPanel.add(messageListPanel);

	}

	public void updateLastUpdateTime() {
		userLastUpdate.setText("User last update time: " + String.valueOf(user.getLastUpdateTime()));
	}
	
	public void setFollowingList() {
		usersFollowing = user.getFollowing();
		followingListModel.clear();
		for (User u : usersFollowing) {
			followingListModel.addElement(u.getUserID());
		}

		followingView = new JList<String>(followingListModel);
	}

	public void setMessageList() {
		messageListModel.clear();
		ArrayList<String> messages;
		if (!(user.getLatestMessage() == null)) {
			messages = user.getLatestMessage();
			for (String message : messages) {
				messageListModel.addElement(message);
			}
		}
	}

	public void refresh() {
		setFollowingList();
		setMessageList();
		updateLastUpdateTime();
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public JFrame getFrame() {
		return frame;
	}

	public void refreshUI(UserUI userUI) {
		userUI.refresh();
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	@Override
	public void showFrame() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
