package game;

import java.util.HashMap;
import java.util.Vector;

public class Deal implements Message {
	public final HashMap<String, Vector<Card>> updates;
	
	public Deal(HashMap<String, Vector<Card>> updates) {
		this.updates = updates;
	}
}
