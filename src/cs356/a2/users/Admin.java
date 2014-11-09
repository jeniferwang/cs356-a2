package cs356.a2.users;

import java.util.HashMap;
import java.util.Random;

import cs356.a2.logic.GroupTree;
import cs356.a2.logic.TreeNode;

// Class that handles Admin roles

public class Admin {
	
	private HashMap<Integer, String> users = new HashMap<Integer, String>();
	private Random random;
	private User user;
	private UserGroup group;
	private int randomUserID, randomGroupID;
	private GroupTree groupTree;
	private TreeNode root;

	// Initialize mapping and setting "Root" group
	Admin() {
		random = new Random();
		randomGroupID = random.nextInt(500) + 1;
		group = new UserGroup();
		group.setGroup(randomGroupID, "Root", null);
		root = new TreeNode(group);
		groupTree = new GroupTree();
		groupTree.setTree(root.getValue(), group);
		
		// User group test case
		addNewUser("Bob");
		addNewUserGroup("Othello", group);
		addThisUserToGroup();
		groupTree.print(root);
	}
	
	// Add a new user
	public void addNewUser(String name) {
		randomUserID = random.nextInt(500) + 1;
		while (users.containsKey(randomUserID)) {
			randomUserID = random.nextInt(500) + 1;
		}
		user = new User();
		user.addUser(randomUserID, name);
	}
	
	// Add a new user group
	public void addNewUserGroup(String name, UserGroup parent) {
		randomGroupID = random.nextInt(500) + 1;
		while (groupTree.getTree().containsKey(randomGroupID)) {
			randomGroupID = random.nextInt(500) + 1;
		}
		group = new UserGroup();
		group.setGroup(randomGroupID, name, parent);
		groupTree.setTree(group, group.getParentGroup());
	}
	
	// Add user to the group
	public void addThisUserToGroup() {
		if (groupTree.userExistsInTree(user) == false) {
			group.addUserToGroup(user);
		}
	}
	
}
