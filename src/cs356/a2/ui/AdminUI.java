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

import cs356.a2.logic.DynamicJTree;
import cs356.a2.users.Admin;

// Class that generates the basic layout of the Admin UI
public class AdminUI implements UserInterface, ActionListener {

	private static AdminUI instance;
	private Admin admin;
	private JFrame frame;
	private GridBagConstraints gbc;
	
	private JButton showUserButton;
	private JButton showGroupButton;
	private String input;
	
	private DynamicJTree jtree;
	
	private AdminUI() {};
	
	protected static AdminUI getInstance() {
		if (instance == null) {
			instance = new AdminUI();
		}
		return instance;
	}
	
	@Override
	public void init() {
		admin = new Admin();
		jtree = new DynamicJTree();
		admin.setTree(jtree);
		setFrame(700, 550);
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
		
		generateLeftSide(layout);
		generateRightSide(layout);
	}
	
	public void generateLeftSide(LayoutManager layout) {
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(390, 500));
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
				input = JOptionPane.showInputDialog(frame, "Enter User Name", 
						"Create New User", JOptionPane.QUESTION_MESSAGE);
				if (input != null) {
					admin.addNewUser(input);
				}
			}
		});
		
		addGroupItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				input = JOptionPane.showInputDialog(frame, "Enter Group Name", 
						"Create New User Group", JOptionPane.QUESTION_MESSAGE);
				if (input != null) {
					admin.addNewUserGroup(input);
				}
			}
		});
		
		menu.add(addUserItem);
		menu.add(addGroupItem);
		
		MouseListener mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if (SwingUtilities.isRightMouseButton(mouseEvent)) {
					menu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
			    }
			}
		};
		
		leftPanel.addMouseListener(mouseListener);
		
		frame.add(leftPanel);
	}
	
	public void generateRightSide(LayoutManager layout) {
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(290, 500));
		rightPanel.setLayout(new GridLayout(2,0));
		
		JPanel topRightPanel = new JPanel(new GridLayout(2,0));
		
		JLabel tips = new JLabel("Some tips");
		topRightPanel.add(tips);
		
		JButton openUserViewButton = new JButton("Open User View");
		
		openUserViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (!jtree.isUserGroup()) {
					admin.getSelectedUser(jtree.getSelectedNode());
				}
			}
		});
		
		topRightPanel.add(openUserViewButton);
		
		JPanel bottomRightPanel = new JPanel();
		bottomRightPanel.setLayout(new GridLayout(2,2));
		
		showUserButton = new JButton("Show User Total");
		
		showUserButton.addActionListener(this);
		
		showGroupButton = new JButton("Show User Group Total");
		
		showGroupButton.addActionListener(this);
		
		JButton showMessagesButton = new JButton("Show Messages Total");
		JButton showPositiveButton = new JButton("Show Positive Percentage");
		
		bottomRightPanel.add(showUserButton);
		bottomRightPanel.add(showGroupButton);
		bottomRightPanel.add(showMessagesButton);
		bottomRightPanel.add(showPositiveButton);
		
		rightPanel.add(topRightPanel);
		rightPanel.add(bottomRightPanel);
		
		gbc.weightx = 1.0;
		gbc.gridx = 2;
		gbc.gridy = 0;
		
		((GridBagLayout) layout).setConstraints(rightPanel, gbc);
		frame.add(rightPanel);
	}
	
	// Change to anonymous listener!
	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if (src == showUserButton) {
			JOptionPane.showMessageDialog(frame,
			"Total number of users : " + admin.getTotalUsers(), 
			"Show User Total", JOptionPane.PLAIN_MESSAGE);
		} else if (src == showGroupButton){
			JOptionPane.showMessageDialog(frame,
			"Total number of user groups : " + admin.getTotalUserGroups(), 
			"Show User Total", JOptionPane.PLAIN_MESSAGE);
		}
	}

	@Override
	public void showFrame() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
