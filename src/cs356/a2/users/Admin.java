package cs356.a2.users;

import cs356.a2.logic.DynamicJTree;
import cs356.a2.logic.GroupTree;
import cs356.a2.logic.TreeNode;
import cs356.a2.ui.UserUI;
import cs356.a2.visitor.AdminVisitor;

// Class that handles Admin roles
public class Admin{
	
//	private HashMap<Integer, String> users = new HashMap<Integer, String>();
	private User user;
	private UserGroup group;
	private GroupTree groupTree;
	private TreeNode root;
	private AdminVisitor adminVisitor;
	private DynamicJTree jtree = new DynamicJTree();;

	// Initialize mapping and setting "Root" group
	public Admin() {
		// Create a root group and set the root node
		group = new UserGroup();
		group.setGroup("Root", null);
		root = new TreeNode(group);
		groupTree = new GroupTree();
		groupTree.setTree(root.getValue(), group);
		jtree.addObject(group.getGroupID(), group);
		
		adminVisitor = new AdminVisitor();
		group.accept(adminVisitor);
	}
	
	// Add a new user
	public void addNewUser(String userID) {
		// Check if user exists
		if (groupTree.userExistsInTree(userID)) {
			// TODO handle if user id already exists
		} else {
			user = new User();
			group = groupTree.getUserGroupFromTree(jtree.getSelectedNode());
			user.addUser(user, userID, group.getGroupID());
			group.addUserToGroup(user);
			jtree.addObject(user.getUserID(), user);
			user.accept(adminVisitor);
		}
	}
	
	// Add a new user group
	public void addNewUserGroup(String groupID) {
		if (groupTree.groupExistsInTree(groupID)) {
			// TODO handle if user group already exists
		} else {
			UserGroup parentGroup = group;
			group = new UserGroup();
			if (jtree.getParentGroup() == null) {
				group.setGroup(groupID, parentGroup);
			} else {
				group.setGroup(groupID, groupTree.getUserGroupFromTree(jtree.getParentGroup().toString()));
			}
			jtree.addObject(group.getGroupID(), group);
			groupTree.setTree(group, group.getParentGroup());
			group.accept(adminVisitor);
		}
	}
	
	// Add user to the group
	public void addThisUserToGroup() {
		if (!groupTree.userExistsInTree(user.getUserID())) {
			group.addUserToGroup(user);
		}
	}
	
	public void getUserView(User user) {
		if (user != null) {
			UserUI newUserUI = new UserUI(user);
		}
	}
	
	public void getSelectedUser(String userName) {
		User user = groupTree.getUserFromTree(userName);
		getUserView(user);
	}
	
	public int getTotalUsers() {
		return adminVisitor.getTotalUser();
	}
	
	public int getTotalUserGroups() {
		return adminVisitor.getTotalGroup();
	}
	
	public int getTotalMessages() {
		return adminVisitor.getTotalMessages();
	}
	
	public double getTotalPositiveMessages() {
		return adminVisitor.getPositivePercentage();
	}

	public void setTree(DynamicJTree jtree) {
		this.jtree = jtree;
		
	}
}
