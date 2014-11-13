package cs356.a2.users;

import java.util.ArrayList;

import cs356.a2.visitor.Visitor;

public class User implements Users {

	private int userID;
	private String name;
	private int groupID;
	private ArrayList<Integer> followerIDs;
	private ArrayList<Integer> followingIDs;

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public int getUserID() {
		return userID;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	
	public int getGroupID() {
		return groupID;
	}
	
	// Add user
	public void addUser(int id, String name) {
		this.setUserID(id);
		this.setName(name);
		this.followerIDs = new ArrayList<Integer>();
		this.followingIDs = new ArrayList<Integer>();
	}
	
	// Add the users following this user
	public void addFollower(int followerID) {
		if(userExists(followerIDs, followerID) == false) {
			followerIDs.add(followerID);
		}
	}
	
	// Add the users that this user is following
	public void addFollowing(int followingID) {
		if(userExists(followingIDs, followingID) == false) {
			followingIDs.add(followingID);
		}
	}
	
	// Checks if user is already being followed or is following
	public boolean userExists(ArrayList<Integer> followers, int user) {
		if (followers.contains(user)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitUser(this);
	}

}
