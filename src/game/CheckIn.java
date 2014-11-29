package game;

public class CheckIn implements Message {
	public final String username; // probably username, currently talking to Eugene about it
	
	public CheckIn(String username) {
		this.username = username;
	}
}