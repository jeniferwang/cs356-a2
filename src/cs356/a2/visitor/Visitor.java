package cs356.a2.visitor;

import cs356.a2.users.User;
import cs356.a2.users.UserGroup;

public interface Visitor {

	public void visitUser(User user);
	public void visitUserGroups(UserGroup userGroup);
	public void visitMessages();
	public void visitPercentage();

}
