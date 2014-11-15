package cs356.a2.logic;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class TreeRenderer extends DefaultTreeCellRenderer {
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, 
			boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		
		if (value instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			if (node.getAllowsChildren()) {
				setIcon(UIManager.getIcon("FileView.directoryIcon"));
			} else {
				setIcon(UIManager.getIcon("FileView.fileIcon"));
			}
		}
		
		return this;
		
	}

}
