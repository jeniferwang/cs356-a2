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
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs356.a2.logic.GroupTree;
import cs356.a2.users.User;

public class UserUI implements UserInterface {

	private JFrame frame;
	private User user;
	private GroupTree groupTree;
	private boolean firstUserClick = true;
	private boolean firstMessageClick = true;
	private DefaultListModel<String> followingListModel, messageListModel;
	private JList<String> followingView, messageView;
	private JPanel mainPanel;
	private ArrayList<String> followingList, messageList;
	private JPanel followingListPanel, messageListPanel;
	private ArrayList<User> usersFollowing;
	
	public UserUI(User user, GroupTree groupTree) {
		this.user = user;
		this.groupTree = groupTree;
		init();
	}
	
	@Override
	public void init() {
		setFrame(700, 550);
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
		
		GridLayout mainGrid = new GridLayout(2,1);
		GridLayout gridSection = new GridLayout(1,2);
		
		mainPanel = new JPanel(mainGrid);
		JPanel topPanel = new JPanel(mainGrid);
		JPanel bottomPanel = new JPanel(mainGrid);
		JPanel followingInputPanel = new JPanel(gridSection);
		followingListPanel = new JPanel(new FlowLayout());
		JPanel messageInputPanel = new JPanel(gridSection);
		messageListPanel = new JPanel(new FlowLayout());
		
		mainPanel.add(topPanel);
		mainPanel.add(bottomPanel);
		topPanel.add(followingInputPanel);
		topPanel.add(followingListPanel);
		bottomPanel.add(messageInputPanel);
		bottomPanel.add(messageListPanel);
		
		JTextField userIDTextField = new JTextField(20);
		userIDTextField.setText("Enter User ID");
		
		userIDTextField.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				/*Check if it is user's first time clicking on text field. If true,
				clear field; else, do nothing.*/
				if (firstUserClick) {
					userIDTextField.setText("");
					firstUserClick = false;
				}
			}
		});
		
		JButton followUserButton = new JButton("Follow User");
		followUserButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent ae) {
				String userIDInput = userIDTextField.getText();
				if (!userIDInput.equals("")) {
					if (groupTree.userExistsInTree(userIDInput)) {
						User addFollowingUser = groupTree.getUserFromTree(userIDInput);
						user.addFollowing(addFollowingUser);
						refresh();
					} else {
						// TODO handle if user doesn't exist
					}
				} else {
					// TODO handle empty string
				}
			}
		});
		
		JLabel followingsLabel = new JLabel("Currently Following: ");
		followingView = null;
		followingListModel = new DefaultListModel<String>();
		setFollowingList();
		followingView = new JList<String>(followingListModel);
		
		followingInputPanel.add(userIDTextField);
		followingInputPanel.add(followUserButton);
		followingListPanel.add(followingsLabel);
		followingListPanel.add(followingView);
		
		JTextField messageTextField = new JTextField(20);
		messageTextField.setText("Enter a message");
		
		messageTextField.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				/*Check if it is user's first time clicking on text field. If true,
				clear field; else, do nothing.*/
				if (firstMessageClick) {
					messageTextField.setText("");
					firstMessageClick = false;
				}
			}
		});
		
		JButton messageButton = new JButton("Post Message");
		messageButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent ae) {
				String userMessage = messageTextField.getText();
				if (!userMessage.equals("")) {
					user.addNews(userMessage);
					messageTextField.setText("");
				} else {
					// TODO Handle case when user enters blank message
				}
			}
		});
		
		messageView = null;
		JLabel messageLabel = new JLabel("News Feed");
		messageListModel = new DefaultListModel<String>();
		setMessageList();
		messageView = new JList<String>(messageListModel);
		
		messageInputPanel.add(messageTextField);
		messageInputPanel.add(messageButton);
		messageListPanel.add(messageLabel);
		messageListPanel.add(messageView);
		
		topPanel.add(followingInputPanel);
		topPanel.add(followingListPanel);
		bottomPanel.add(messageInputPanel);
		bottomPanel.add(messageListPanel);
		mainPanel.add(topPanel);
		mainPanel.add(bottomPanel);
		frame.add(mainPanel);
		
	}
	
	public void setFollowingList() {
		usersFollowing = user.getFollowing();
		followingListModel.clear();
		for(User u : usersFollowing) {
			followingListModel.addElement(u.getUserID());
		}
		
		followingView = new JList<String>(followingListModel);
	}
	
	public void setMessageList() {
		messageListModel.clear();
		ArrayList<String> messages;
		if (!(user.getLatestMessage() == null)) {
			messages = user.getLatestMessage();
			for(String message : messages) {
				messageListModel.addElement(message);
			}
		}
	}
	
	public void refresh() {
		setFollowingList();
		setMessageList();
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
