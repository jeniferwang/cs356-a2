package cs356.a2.visitor;

import cs356.a2.users.User;
import cs356.a2.users.UserGroup;

public class AdminVisitor implements Visitor {

	private int totalUser, totalGroup, totalMessages;
	private double totalPositive;
	private String[] positiveWords = { "good", "nice", "wonderful", "happy" };

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

	// Splits the message into arrays and checks for positive messages
	@Override
	public void visitMessages(String messages) {
		boolean isPositive = false;
		String[] messageArray = messages.toLowerCase().split(" ");
		for (String s : messageArray) {
			for (String t : positiveWords) {
				if (s.equals(t)) {
					isPositive = true;
				}
			}
		}
		if (isPositive) {
			visitPercentage();
		}
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
		return totalPositive / totalMessages * 100;
	}

}
