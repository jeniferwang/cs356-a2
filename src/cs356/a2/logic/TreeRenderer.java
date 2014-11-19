package cs356.a2.logic;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import cs356.a2.users.User;
import cs356.a2.users.UserGroup;

public class TreeRenderer extends DefaultTreeCellRenderer {
	
	private JPanel renderer;
	private JLabel nameLabel;
	
	public TreeRenderer() {
		renderer = new JPanel();
		nameLabel = new JLabel();
		renderer.add(nameLabel);
	}
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, 
			boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		
		Component returnValue = null;
		
		if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
			Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
			if (userObject instanceof User) {
				User user = (User) userObject;
				nameLabel.setText(user.getUserID());
				nameLabel.setIcon(UIManager.getIcon("FileView.fileIcon"));
			} else if (userObject instanceof UserGroup) {
				UserGroup userGroup = (UserGroup) userObject;
				nameLabel.setText(userGroup.getGroupID());
				nameLabel.setIcon(UIManager.getIcon("FileView.directoryIcon"));
			}
			if (selected) {
				nameLabel.setForeground(Color.RED);
			} else {
				nameLabel.setForeground(Color.BLACK);
			}
			renderer.setBackground(Color.WHITE);
			returnValue = renderer;
		}
		return returnValue;
		
//		if (value instanceof DefaultMutableTreeNode) {
//			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
//			if (node.getAllowsChildren()) {
//				setIcon(UIManager.getIcon("FileView.directoryIcon"));
//			} else {
//				setIcon(UIManager.getIcon("FileView.fileIcon"));
//			}
//		}
//		
//		return this;
		
	}

}
