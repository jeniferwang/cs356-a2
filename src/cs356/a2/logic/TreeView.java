package cs356.a2.logic;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import cs356.a2.users.User;
import cs356.a2.users.UserGroup;

public class TreeView extends JPanel {
	
	private DefaultMutableTreeNode rootNode;
	private DefaultMutableTreeNode parentNode;
    private DefaultTreeModel treeModel;
    private JTree tree;
    private TreePath parentPath;
    private String currentNode;
    private DefaultMutableTreeNode node;
    
    public TreeView() {
    	rootNode = new DefaultMutableTreeNode("Root");
		treeModel = new DefaultTreeModel(rootNode);
		tree = new JTree(treeModel);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setCellRenderer(new TreeRenderer());
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (node == null) {
					return;
				}
				Object nodeInfo = node.getUserObject();
				currentNode = (String) nodeInfo;
			}
			
		});
		
		setBackground(Color.WHITE);
		add(tree);
    }
    
	public DefaultMutableTreeNode addObject(String name, Object child) {
		parentNode = null;
		parentPath = tree.getSelectionPath();
		
		if (parentPath == null) {
			parentNode = rootNode;
		} else {
			parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
		}
	
		return addObject(parentNode, child, name, true);
	}
	
	public Object getParentGroup() {
		return parentNode;
	}
	
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, String name) {
		return addObject(parent, child, name, false);
	}
	
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, 
				String name, boolean shouldBeVisible) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(name);
		
		if (parent == null) {
			parent = rootNode;
		}
		
		if (child instanceof User) {
			childNode.setAllowsChildren(false);
			// TODO handle case for adding user in user
			treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
		} else if (child instanceof UserGroup) {
			childNode.setAllowsChildren(true);
			treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
		}
		
		if (parent == null) {
			parent = rootNode;
		}
		
		if (shouldBeVisible) {
			tree.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		
		return childNode;
	}

	// Returns the currently selected node
	public String getSelectedNode() {
		if (currentNode == null) {
			currentNode = "Root";
		}
		return currentNode;
	}

	// Checks if node allows children -> user group
	public boolean isUserGroup() {
		if (node.getAllowsChildren()) {
			return true;
		}
		return false;
	}
    
}
