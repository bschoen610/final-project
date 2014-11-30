package game;

public class StartBet implements Message {
	public final String username;
	
	public StartBet(String username) {
		this.username = username;
	}
}