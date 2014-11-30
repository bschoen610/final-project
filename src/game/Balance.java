package game;

public class Balance implements Message {
	public final int amt;
	public final String username;
	
	public Balance(String username, int amt) {
		this.username = username;
		this.amt = amt;
	}
}