package cs356.a2.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	private GridBagConstraints gbc;
	private User user;
	private GroupTree groupTree;
	private boolean firstClick = true;
	private DefaultListModel<String> followingListModel;
	private JList<String> followingView;
	private JPanel mainPanel;
	private ArrayList<String> followingList;
	private JPanel followingViewPanel;
	
	public UserUI(User user, GroupTree groupTree) {
		this.user = user;
		this.groupTree = groupTree;
		init();
	}
	
	@Override
	public void init() {
		setFrame(700, 550);
		setLayout(new GridBagLayout());		
		showFrame();
	}

	@Override
	public void setFrame(int x, int y) {
		frame = new JFrame();
		frame.setTitle(user.getUserID() + "'s Profile");
		frame.setSize(x, y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setLayout(LayoutManager layout) {
		frame.getContentPane().setLayout(layout);
		
		mainPanel = new JPanel();
		gbc = new GridBagConstraints();
		
		gbc.weightx = 0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		JTextField userIDTextField = new JTextField(20);
		userIDTextField.setText("Enter User ID");
		
		userIDTextField.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				/*Check if it is user's first time clicking on text field. If true,
				clear field; else, do nothing.*/
				if (firstClick) {
					userIDTextField.setText("");
					firstClick = false;
				}
			}
		});
		
		mainPanel.add(userIDTextField, gbc);
		
		gbc.gridx = 1;
		
		JButton followUserButton = new JButton("Follow User");
		followUserButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent ae) {
				String userIDInput = userIDTextField.getText();
				if (groupTree.userExistsInTree(userIDInput)) {
					User addFollowingUser = groupTree.getUserFromTree(userIDInput);
					user.addFollowing(addFollowingUser);
					setFollowingList();
					mainPanel.revalidate();
					mainPanel.repaint();
				} else {
					// TODO handle if user doesn't exist
				}
			}
		});
		
		mainPanel.add(followUserButton, gbc);
		
		gbc.weightx = 0;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel followingsLabel = new JLabel("Currently Following");
		followingViewPanel = new JPanel();
		followingView = null;
		followingListModel = new DefaultListModel<String>();
		
		setFollowingList();
		followingView = new JList<String>(followingListModel);
		
		followingViewPanel.add(followingsLabel);
		followingViewPanel.add(followingView);
		
		mainPanel.add(followingViewPanel, gbc);
		frame.add(mainPanel);
		
	}
	
	public void setFollowingList() {
		followingList = user.getFollowing();
		for(String s : followingList) {
			followingListModel.clear();
			followingListModel.addElement(s);
		}
		
		followingView = new JList<String>(followingListModel);
	}

	@Override
	public void showFrame() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
