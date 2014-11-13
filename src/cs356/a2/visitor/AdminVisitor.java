package cs356.a2.visitor;

import cs356.a2.users.User;
import cs356.a2.users.UserGroup;

public class AdminVisitor implements Visitor {
	
	private int totalUser, totalGroup, totalMessages;
	private double totalPositive;
	
	@Override
	public void visitUser(User user) {
		totalUser++;
	}
	
	public int getTotalUser() {
		return totalUser;
	}
	
	@Override
	public void visitUserGroups(UserGroup userGroup) {
		totalGroup++;
		
	}
	
	public int getTotalGroup() {
		return totalGroup;
	}
	
	@Override
	public void visitMessages() {
		totalMessages++;
		
	}
	
	public int getTotalMessages() {
		return totalMessages;
	}
	
	@Override
	public void visitPercentage() {
		totalPositive++;
		
	}
	
	public double getPositivePercentage() {
		return totalPositive/totalMessages;
	}

}
