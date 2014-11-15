package cs356.a2.observer;

public interface Subject {
	
	public void addObserver(Observer observer);
	public void removeObserver(Observer observer);
	public void notify(Object object);

}
