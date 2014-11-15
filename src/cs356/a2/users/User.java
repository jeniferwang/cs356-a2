package cs356.a2.users;

import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

import cs356.a2.visitor.Visitor;

public class User extends Observable implements Users, Observer {

	private User user;
	private int userID;
	private String name;
	private int groupID;
	private ArrayList<User> followers;
	private ArrayList<User> followings;
	private ArrayList<String> newsFeed;

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
	public void addUser(User user, int id, String name) {
		this.user = user;
		this.setName(name);
		this.setGroupID(groupID);
		this.followers = new ArrayList<User>();
		this.followings = new ArrayList<User>();
		this.newsFeed = new ArrayList<String>();
	}
	
	// Add the users following this user
	public void addFollower(User follower) {
		if(userExists(followers, follower) == false) {
			followers.add(follower);
		}
	}
	
	// Add the user that this user is following
	public void addFollowing(User following) {
		if(userExists(followings, following) == false) {
			followings.add(following);
			user.addObserver(following);
		}
	}
	
	// Add a new message to news feed and notify followers
	public void addNews(String message) {
		newsFeed.add(user.name + " : " + message);
		setChanged();
		notifyObservers(message);
	}
	
	public String getLatestMessage() {
		return newsFeed.get(newsFeed.size()-1);
	}
	
	// Checks if user is already being followed or is following
	public boolean userExists(ArrayList<User> followers, User user) {
		if (followers.contains(user)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void update(Observable observable, Object object) {
		// Traverse through list and print all messages
		System.out.println("New Message! " + ((User) observable).getLatestMessage());
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visitUser(this);
	}

}
