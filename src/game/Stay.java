package game;

public class Stay implements Message {
	public final String username;
	
	public Stay(String username) {
		this.username = username;
	}
}