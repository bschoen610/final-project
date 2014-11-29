package game;

public class CheckIn implements Message {
	public final String username;
	
	public CheckIn(String username) {
		this.username = username;
	}
}