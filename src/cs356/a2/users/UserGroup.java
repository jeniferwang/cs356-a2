package cs356.a2.users;

import java.util.ArrayList;

import cs356.a2.visitor.Visitor;

public class UserGroup implements Users {

	private int groupID;
	private String groupName;
	private UserGroup parentGroup;
	private ArrayList<User> userList;
	
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	
	public int getGroupID() {
		return groupID;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setParentGroup(UserGroup parentGroup) {
		this.parentGroup = parentGroup;
	}
	
	public UserGroup getParentGroup() {
		return parentGroup;
	}
	
	// Sets a new group
	public void setGroup(int id, String name, UserGroup parent) {
		userList = new ArrayList<User>();
		setGroupID(id);
		setGroupName(name);
		setParentGroup(parent);
	}
	
	public void addUserToGroup(User user) {
		userList.add(user);
	}
	
	// Checks if this user exists in this group
	public boolean userExists(User user) {
		for (User u : userList) {
			if (u.equals(user)) {
				return true;
			}
		}
		return false;
	}
	
	//Returns the user in the user group
	public User getUser(String user) {
		for (User u : userList) {
			System.out.println("User in list: " + u.getName());
			if(u.getName().equals(user)) {
				return u;
			}
		}
		return null;
	}
	
	// Returns the group's user list
	public ArrayList<User> getUserList() {
		return userList;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitUserGroups(this);
	}	
	
}
