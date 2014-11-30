package game;

public class Balance implements Message {
	public final double amt;
	public final String username;
	
	public Balance(String username, double balance) {
		this.username = username;
		this.amt = balance;
	}
}