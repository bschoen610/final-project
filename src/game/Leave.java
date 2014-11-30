package game;

public class Leave implements Message {
	public final String username;
	
	public Leave(String username) {
		this.username = username;
	}
}