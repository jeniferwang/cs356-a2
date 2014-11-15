package cs356.a2.users;

import java.util.HashMap;
import java.util.Random;

import cs356.a2.logic.DynamicJTree;
import cs356.a2.logic.GroupTree;
import cs356.a2.logic.TreeNode;
import cs356.a2.ui.UserUI;
import cs356.a2.visitor.AdminVisitor;

// Class that handles Admin roles
public class Admin{
	
	private HashMap<Integer, String> users = new HashMap<Integer, String>();
	private Random random;
	private User user;
	private UserGroup group;
	private int randomUserID, randomGroupID;
	private GroupTree groupTree;
	private TreeNode root;
	private AdminVisitor adminVisitor;
	private DynamicJTree jtree = new DynamicJTree();;

	// Initialize mapping and setting "Root" group
	public Admin() {
		// Create a root group and set the root node
		random = new Random();
		randomGroupID = random.nextInt(500) + 1;
		group = new UserGroup();
		group.setGroup(randomGroupID, "Root", null);
		root = new TreeNode(group);
		groupTree = new GroupTree();
		groupTree.setTree(root.getValue(), group);
		jtree.addObject(group.getGroupName(), group);
		
		// Create AdminVisitor()
		adminVisitor = new AdminVisitor();
		group.accept(adminVisitor);
		
		// User group test case
//		addNewUser("Bob");
//		addNewUser("Bill");
//		addNewUser("Billy");
//		addNewUserGroup("Othello", group);
		addThisUserToGroup();
		groupTree.print(root);
	}
	
	// Add a new user
	public void addNewUser(String userName) {
		randomUserID = random.nextInt(500) + 1;
		while (users.containsKey(randomUserID)) {
			randomUserID = random.nextInt(500) + 1;
		}
		//String groupName = jtree.getSelectedUserGroup();
		user = new User();
		user.addUser(user, randomUserID, userName);
		group = groupTree.getUserGroup(jtree.getSelectedNode());
		group.addUserToGroup(user);
		//user.setGroupID(groupTree.getUserGroup(jtree.getParentGroup().toString()).getGroupID());
		jtree.addObject(user.getName(), user);
		user.accept(adminVisitor);
		
		System.out.println("New user created: " + userName);
	}
	
	// Add a new user group
	public void addNewUserGroup(String name) {
		randomGroupID = random.nextInt(500) + 1;
		while (groupTree.getTree().containsKey(randomGroupID)) {
			randomGroupID = random.nextInt(500) + 1;
		}
		UserGroup parentGroup = group;
		group = new UserGroup();
		if (jtree.getParentGroup() == null) {
			group.setGroup(randomGroupID, name, parentGroup);
		} else {
			group.setGroup(randomGroupID, name, groupTree.getUserGroup(jtree.getParentGroup().toString()));
		}
		jtree.addObject(group.getGroupName(), group);
		groupTree.setTree(group, group.getParentGroup());
		group.accept(adminVisitor);
	}
	
	// Add user to the group
	public void addThisUserToGroup() {
		if (groupTree.userExistsInTree(user) == false) {
			group.addUserToGroup(user);
		}
	}
	
	public void getUserView(User user) {
		UserUI newUserUI = new UserUI(user);
	}
	
	public void getSelectedUser(String userName) {
		User user = groupTree.getUser(userName);
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
