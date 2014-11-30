package game;

public class StartMove implements Message {
	public final String username;
	
	public StartMove(String username) {
		this.username = username;
	}
}