package cs356.a2.users;

import cs356.a2.logic.TreeView;
import cs356.a2.ui.AdminUI;
import cs356.a2.ui.UserUI;
import cs356.a2.visitor.AdminVisitor;

// Class that handles Admin roles
public class Admin {

	private User user;
	private UserGroup group;
	private AdminVisitor adminVisitor;
	private TreeView jtree = new TreeView();

	// Initialize and accept AdminVisitor
	public Admin() {
		group = new UserGroup();
		adminVisitor = new AdminVisitor();
		group.accept(adminVisitor);
	}

	// Add a new user
	public void addNewUser(String userID) {
		// Check if user exists
		if (jtree.nodeExists(userID)) {
			throw new IllegalArgumentException("User already exists!"); 
		} else {
			user = new User();
			group = jtree.getSelectedUserGroupNode();
			user.addUser(user, userID, group.getGroupID());
			group.addUserToGroup(user);
			jtree.addObject(user.getUserID(), user);
			user.accept(adminVisitor);
		}
	}

	// Add a new user group
	public void addNewUserGroup(String groupID) {
		if (jtree.nodeExists(groupID)) {
			throw new IllegalArgumentException("User Group already exists!"); 
		} else {
			UserGroup parentGroup = group;
			group = new UserGroup();
			if (jtree.getParentGroup() == null) {
				group.setGroup(groupID, parentGroup);
			} else {
				group.setGroup(groupID, (UserGroup) jtree.getParentGroup());
			}
			jtree.addObject(group.getGroupID(), group);
			group.accept(adminVisitor);
		}
	}

	// Create User UI for User
	public void getSelectedUserView(User user) {
		if (user != null) {
			UserUI newUserUI = new UserUI(user, jtree);
			user.setUserUI(newUserUI);
		}
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

	public void setTree(TreeView jtree) {
		this.jtree = jtree;

	}
}
