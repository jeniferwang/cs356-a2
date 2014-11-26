package cs356.a2.users;

import cs356.a2.visitor.Visitor;

public interface Users {

	public void setTimeStamp();
	public long getTimeStamp();
	public void accept(Visitor visitor);

}
