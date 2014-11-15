package cs356.a2.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import cs356.a2.users.User;
import cs356.a2.users.UserGroup;

public class GroupTree {

	private HashMap<UserGroup, TreeNode> collectionMap;
//	private UserGroup userGroup;
//	private User user;
	
	public GroupTree() {
		collectionMap = new HashMap<UserGroup, TreeNode>();
	}
	
	public void setTree(UserGroup value, UserGroup parent) {
		TreeNode node = new TreeNode(value);
		collectionMap.put(value, node);
		if (parent != null) {
			collectionMap.get(parent).addChild(value);
		}
				
	}
	
	public HashMap<UserGroup, TreeNode> getTree() {
		return collectionMap;
	}
	
	// Checks if user exists in tree
	public boolean userExistsInTree(String user) {
		Iterator<Entry<UserGroup, TreeNode>> itr = collectionMap.entrySet().iterator();
		Entry<UserGroup, TreeNode> entry;
		while (itr.hasNext()) {
			entry = itr.next();
			ArrayList<User> userList = entry.getKey().getUserList();
			for (User u : userList) {
				if (u.getUserID().equals(user)) {
					return true;
				}
			}
		}
		return false;
	}
	
	// Checks if user group exists in tree
	public boolean groupExistsInTree(String group) {
		Iterator<Entry<UserGroup, TreeNode>> itr = collectionMap.entrySet().iterator();
		Entry<UserGroup, TreeNode> entry;
		while (itr.hasNext()) {
			entry = itr.next();
			String id = entry.getKey().getGroupID();
			if (id.equals(group)) {
				return true;
			}
		}
		return false;
	}
	
	// Return user group of selected node
	public UserGroup getUserGroupFromTree(String userGroupName) {
		Iterator<UserGroup> itr = collectionMap.keySet().iterator();
		while (itr.hasNext()) {
			UserGroup thisUserGroup = itr.next();
			if (thisUserGroup.getGroupID().equals(userGroupName)) {
				return thisUserGroup;
			}
		}
		return null;
	}
	
	// Return user of selected node
	public User getUserFromTree(String user) {
		Iterator<Entry<UserGroup, TreeNode>> itr = collectionMap.entrySet().iterator();
		Entry<UserGroup, TreeNode> entry;
		while (itr.hasNext()) {
			entry = itr.next();
			ArrayList<User> userList = entry.getKey().getUserList();
			for (User u : userList) {
				if (u.getUserID().equals(user)) {
					return u;
				}
			}
		}
		return null;
	}
	
	// Print Test
	public void print(TreeNode root) {
		Iterator<Entry<UserGroup, TreeNode>> itr = collectionMap.entrySet().iterator();
		Entry<UserGroup, TreeNode> entry;
		while (itr.hasNext()) {
		    entry = itr.next();
		    String id = entry.getKey().getGroupID();
		    String parentName = "";
		    System.out.println("Group ID: " + id + "    ");
		    if(entry.getValue().getValue().getParentGroup() != null) {
		    	parentName = entry.getValue().getValue().getParentGroup().getGroupID();
		    	System.out.println("Group Parent: " + parentName);
		    } else {
		    	parentName = "None";
		    	System.out.println("Group Parent: " + parentName);
		    }
		    System.out.println("---------------");
		}
	}
}
