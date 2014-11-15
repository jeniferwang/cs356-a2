package cs356.a2.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs356.a2.users.User;

public class UserUI implements UserInterface {

	private JFrame frame;
	private GridBagConstraints gbc;
	private User user;
	
	public UserUI(User user) {
		this.user = user;
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
		gbc = new GridBagConstraints();
		
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		generateTop(layout);
		generateBottom(layout);
	}

	private void generateBottom(LayoutManager layout) {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(390, 500));
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JTextField userIDTextField = new JTextField();
		JButton followUserButton = new JButton("Follow User");
		followUserButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent ae) {
				String userIDInput = userIDTextField.getText();
				
				//user.addFollowing(following);
			}
		});
		
		bottomPanel.add(userIDTextField);
		frame.add(bottomPanel);
	}

	private void generateTop(LayoutManager layout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showFrame() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
