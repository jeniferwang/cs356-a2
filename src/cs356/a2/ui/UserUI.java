package cs356.a2.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;

import cs356.a2.users.User;

public class UserUI implements UserInterface {

	private JFrame frame;
	private GridBagConstraints gbc;
	private User user;
	
	public UserUI(User user) {
		this.user = user;
		init();
		System.out.println(user.getName());
	}
	
	@Override
	public void init() {
		setFrame(700, 550);
		setLayout(new GridBagLayout());		
		showFrame();
	}

	@Override
	public void setFrame(int x, int y) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		
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
