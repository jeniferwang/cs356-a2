package cs356.a2.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import cs356.a2.users.Admin;

// Class that generates the basic layout of the Admin UI
public class AdminUI implements UserInterface {

	private static AdminUI instance;
	private Admin admin;
	private JFrame frame;
	private GridBagConstraints gbc;
	
	private DefaultMutableTreeNode root;
	private DefaultTreeModel model;
	
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
		leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JTree jtree = new JTree();
		root = new DefaultMutableTreeNode("Root");
		model = new DefaultTreeModel(root);
		jtree.setModel(model);
		TreeSelectionModel tsm = jtree.getSelectionModel();
		tsm.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		DefaultMutableTreeNode veg = new DefaultMutableTreeNode("Hello");
		System.out.print(veg.getPath().toString());
		
		leftPanel.add(jtree);
		
		((GridBagLayout) layout).setConstraints(leftPanel, gbc);
		
		// Popup menu
		JPopupMenu menu = new JPopupMenu("Popup");
		JMenuItem item1 = new JMenuItem("Add User");
		JMenuItem item2 = new JMenuItem("Add User Group");
		menu.add(item1);
		menu.add(item2);
		
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
		topRightPanel.add(openUserViewButton);
		
		JPanel bottomRightPanel = new JPanel();
		bottomRightPanel.setLayout(new GridLayout(2,2));
		
		JButton showUserButton = new JButton("Show User Total");
		JButton showGroupButton = new JButton("Show User Group Total");
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

	@Override
	public void showFrame() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

}
