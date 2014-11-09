package cs356.a2.logic;

import java.util.ArrayList;

import cs356.a2.users.UserGroup;

public class TreeNode {
	
	private UserGroup value;
	private ArrayList<UserGroup> children;
	
	public TreeNode(UserGroup rootValue) {
		value = rootValue;
		children = new ArrayList<UserGroup>();
	}
	
	public UserGroup getValue() {
		return value;
	}
	
	public ArrayList<UserGroup> getChildrenList() {
		return children;
	}
	
	public void addChild(UserGroup userGroup) {
		children.add(userGroup);
	}

}
