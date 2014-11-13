package cs356.a2.users;

import cs356.a2.visitor.Visitor;

// Element abstract class
public interface Users {
	
	public abstract void accept(Visitor visitor);
	
}
