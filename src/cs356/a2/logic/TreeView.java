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
		// Initializes tree and root node
		renderer = new TreeRenderer();
		rootGroup = new UserGroup();
		rootGroup.setGroup("Root", null);
		rootNode = new DefaultMutableTreeNode(rootGroup);
		treeModel = new DefaultTreeModel(rootNode);
		tree = new JTree(treeModel);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setCellRenderer(new TreeRenderer());

		// Listens if selected node's value is changed
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				parentPath = tree.getSelectionPath();
				parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
				if (node == null) {
					return;
				}
				Object thisNode = node.getUserObject();
				if (thisNode instanceof User) {
					currentNode = (User) thisNode;
				} else if (thisNode instanceof UserGroup) {
					currentNode = (UserGroup) thisNode;
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
			parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
		}

		return addObject(parentNode, child, name, true);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, String name) {
		return addObject(parent, child, name, false);
	}

	// Adds the User or UserGroup in a recursive call
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, String name, 
			boolean shouldBeVisible) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

		if (parent == null) {
			parent = rootNode;
		}

		if (parent.getAllowsChildren()) {
			if (child instanceof User && parent.getAllowsChildren()) {
				childNode.setAllowsChildren(false);
				treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
			} else if (child instanceof UserGroup) {
				childNode.setAllowsChildren(true);
				treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
			}
		}

		if (shouldBeVisible) {
			tree.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		return childNode;
	}
	
	// Returns the parent group node
	public Object getParentGroup() {
		return parentNode.getUserObject();
	}

	// Returns true if ID already exists in tree, else false
	public boolean nodeExists(String id) {
		User thisUser;
		UserGroup thisUserGroup;
		String thisID = null;
		Enumeration en = rootNode.breadthFirstEnumeration();
		while (en.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();
			Object thisNode = node.getUserObject();
			if (thisNode instanceof User) {
				thisUser = (User) thisNode;
				thisID = thisUser.getUserID();
			} else if (thisNode instanceof UserGroup) {
				thisUserGroup = (UserGroup) thisNode;
				thisID = thisUserGroup.getGroupID();
			}
			if (thisID.equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	// Checks on validation. Returns true if 1) all unique IDs and 2) no spaces
	public boolean isValidation() {
		User thisUser, thatUser;
		UserGroup thisUserGroup, thatUserGroup;
		String thisID = null;
		String thatID = null;
		int count;
		Enumeration enThis = rootNode.breadthFirstEnumeration();
		while (enThis.hasMoreElements()) {
			count = 0;
			DefaultMutableTreeNode nodeThis = (DefaultMutableTreeNode) enThis.nextElement();
			Object thisNodeObject = nodeThis.getUserObject();
			if (thisNodeObject instanceof User) {
				thisUser = (User) thisNodeObject;
				thisID = thisUser.getUserID();
			} else if (thisNodeObject instanceof UserGroup) {
				thisUserGroup = (UserGroup) thisNodeObject;
				thisID = thisUserGroup.getGroupID();
			}
			Enumeration enThat = rootNode.breadthFirstEnumeration();
			while (enThat.hasMoreElements()) {
				DefaultMutableTreeNode nodeThat = (DefaultMutableTreeNode) enThat.nextElement();
				Object thatNodeObject = nodeThat.getUserObject();
				if (thatNodeObject instanceof User) {
					thatUser = (User) thatNodeObject;
					thatID = thatUser.getUserID();
				} else if (thatNodeObject instanceof UserGroup) {
					thatUserGroup = (UserGroup) thatNodeObject;
					thatID = thatUserGroup.getGroupID();
				}
				if (thatID.equals(thisID)) {
					count++;
				}
			}
			if ((count > 1) || thisID.contains(" ")) {
				return false;
			}
		}
		return true;
	}

	// Returns the User from userID
	public User getUserFrom(String userID) {
		User thisUser = null;
		Enumeration en = rootNode.breadthFirstEnumeration();
		while (en.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();
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
