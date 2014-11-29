package game;

import java.util.Vector;

public class StartGame implements Message {
	public final Vector<String> players;
	
	public StartGame(Vector<String> players) {
		this.players = players;
	}
}