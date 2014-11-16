package cs356.a2.users;

import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

import cs356.a2.visitor.Visitor;

public class User extends Observable implements Users, Observer {

	private User user;
	private String userID;
	private String groupID;
	private ArrayList<User> followers;
	private ArrayList<User> followings;
	private ArrayList<String> newsFeed;

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	
	public String getGroupID() {
		return groupID;
	}
	
	// Add user
	public void addUser(User user, String userID, String groupID) {
		this.user = user;
		this.setUserID(userID);
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
		System.out.println("Following " + following.getUserID());
		System.out.println("T/F: " + userExists(followings, following));
		if(!userExists(followings, following)) {
			followings.add(following);
			setChanged();
			//notifyObservers(following.getUserID());
			System.out.println("Iner here");
			//user.addObserver(following);
		}
	}
	
	// Return a list of users this user is following
	public ArrayList<String> getFollowing() {
		ArrayList<String> list = new ArrayList<String>();
		for (User u : followings) {
			list.add(u.getUserID());
		}
		return list;
	}

	// Add a new message to news feed and notify followers
	public void addNews(String message) {
		newsFeed.add(user.userID + " : " + message);
		setChanged();
		notifyObservers(message);
	}
	
	public String getLatestMessage() {
		return newsFeed.get(newsFeed.size()-1);
	}
	
	// Checks if user is already being followed or is following
	public boolean userExists(ArrayList<User> followers, User user) {
		for (User u : followers) {
			if (u.equals(user)) {
				return true;
			}
		}
		return false;
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
