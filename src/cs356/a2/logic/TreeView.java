package cs356.a2.logic;

import java.awt.Color;
import java.util.Enumeration;

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
import cs356.a2.users.Users;

public class TreeView extends JPanel {

	private DefaultMutableTreeNode rootNode;
	private DefaultMutableTreeNode parentNode;
	private DefaultTreeModel treeModel;
	private JTree tree;
	private TreePath parentPath;
	private Users currentNode;
	private User currentUserNode;
	private DefaultMutableTreeNode node;
	private TreeRenderer renderer;
	private UserGroup rootGroup, currentUserGroupNode;

	public TreeView() {
		renderer = new TreeRenderer();
		rootGroup = new UserGroup();
		rootGroup.setGroup("Root", null);
		rootNode = new DefaultMutableTreeNode(rootGroup);
		treeModel = new DefaultTreeModel(rootNode);
		tree = new JTree(treeModel);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setCellRenderer(new TreeRenderer());

		// Listens if selected node's value is changed
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				node = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();
				if (node == null) {
					return;
				}
				Object nodeInfo = node.getUserObject();
				if (nodeInfo instanceof User) {
					currentNode = (User) nodeInfo;
				} else if (nodeInfo instanceof UserGroup) {
					currentNode = (UserGroup) nodeInfo;
				}
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
			parentNode = (DefaultMutableTreeNode) (parentPath
					.getLastPathComponent());
		}

		return addObject(parentNode, child, name, true);
	}

	public Object getParentGroup() {
		return parentNode.getUserObject();
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
			Object child, String name) {
		return addObject(parent, child, name, false);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
			Object child, String name, boolean shouldBeVisible) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

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

	// Returns true if ID already exists in tree, else false
	public boolean nodeExists(String id) {
		User thisUser;
		UserGroup thisUserGroup;
		String thisID = null;
		Enumeration en = rootNode.breadthFirstEnumeration();
		while (en.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) en
					.nextElement();
			Object nodeInfo = node.getUserObject();
			if (nodeInfo instanceof User) {
				thisUser = (User) nodeInfo;
				thisID = thisUser.getUserID();
			} else if (nodeInfo instanceof UserGroup) {
				thisUserGroup = (UserGroup) nodeInfo;
				thisID = thisUserGroup.getGroupID();
			}
			if (thisID.equals(id)) {
				return true;
			}
		}
		return false;
	}

	// Returns the User from userID
	public User getUserFrom(String userID) {
		User thisUser = null;
		Enumeration en = rootNode.breadthFirstEnumeration();
		while (en.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) en
					.nextElement();
			Object nodeInfo = node.getUserObject();
			if (nodeInfo instanceof User) {
				thisUser = (User) nodeInfo;
				if (userID.equals(thisUser.getUserID())) {
					return thisUser;
				}
			}
		}
		return thisUser;
	}

	// Returns the currently selected node in User object
	public User getSelectedUserNode() {
		if (currentNode instanceof User) {
			currentUserNode = (User) currentNode;
		}
		return currentUserNode;
	}

	// Returns the currently selected node in UserGroup object
	public UserGroup getSelectedUserGroupNode() {
		if ((currentNode != null) && (currentNode instanceof UserGroup)) {
			currentUserGroupNode = (UserGroup) currentNode;
		} else {
			currentUserGroupNode = rootGroup;
		}
		return currentUserGroupNode;
	}

	// Checks if node allows children -> user group
	public boolean isUserGroup() {
		if (node.getAllowsChildren()) {
			return true;
		}
		return false;
	}

}
