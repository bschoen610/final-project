package game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Game implements Runnable {
	public static final int NUM_SUITS = 4;
	public static final int NUM_FACES = 13;
	public static final int NUM_DECKS = 6;
	public static final int NUM_CARDS = NUM_SUITS * NUM_FACES;
	public static final int DECK_SIZE = NUM_DECKS * NUM_CARDS;
	public static final int SHUFFLE_COUNT = 1024;
	public static final int MAX_PLAYERS = 6;
	public static final int MILLIS_PER_SEC = 1000;
	public static final int JOIN_TIME_SEC = 10;
	public static final int JOIN_TIME = JOIN_TIME_SEC * MILLIS_PER_SEC;
	
	public enum Move {
		HIT,
		STAY,
		DOUBLE,
		SPLIT,
	}
	
	HashMap<String, Player> players;
	Player dealer = new Player(null, "_DEALER");
	Deck deck;
	ServerSocket ss;
	
	public Game(HashMap<String, Socket> playerSockets) {
		this.players = new HashMap<String, Player>();
		for (Map.Entry<String, Socket> e : playerSockets.entrySet()) {
			this.players.put(e.getKey(), new Player(e.getValue(), e.getKey()));
		}
	}
		
	private void processMove(Player p) {
		for (int i = 0; i < p.hands.size(); ++i) {
			boolean donePlaying = false;
			
			while (! donePlaying) {
				switch (p.getMove()) {
				case HIT:
					p.addCard(this.deck.getCard(), i);
					donePlaying = p.getSum(i) >= 21;
					break;
				case STAY:
					donePlaying = true;
					break;
				case DOUBLE:
					donePlaying = true;
					p.addCard(this.deck.getCard(), i);
					p.setBet(p.getBet() * 2);
					p.doubled = true;
					break;
				case SPLIT:
					p.split();
					donePlaying = p.getSum(i) > 21;
					break;
				default:
					break;
				}
				
				sendToAll(new Deal(this.getHandsMap()));
			}
		}
	}
	
	private void dealerMove() {
		while (this.dealer.getSum(0) < 17) {
			this.dealer.addCard(this.deck.getCard(), 0);
		}
	}

	public void run() {
		Vector<String> usernames = new Vector<String>(this.players.size());
		
		for (String un : this.players.keySet()) {
			usernames.add(un);
		}
		
		sendToAll(new StartGame(usernames));
		
		for (Map.Entry<String, Player> e : this.players.entrySet()) {
			StartBet sb = new StartBet(e.getKey());
			sendToAll(sb);
			
			ObjectInputStream ois = null;
			
			try {
				ois = new ObjectInputStream(e.getValue().s.getInputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				Bet b = (Bet) ois.readObject();
			} catch (ClassNotFoundException | IOException e1) {
				e1.printStackTrace();
			}
		}

		this.deck = new Deck();
		
		for (Player p : this.players.values()) {
			for (int i = 0; i < 2; ++i) {
				p.addCard(this.deck.getCard(), 0);
			}
		}
		
		
		for (int i = 0; i < 2; ++i) {
			this.dealer.addCard(this.deck.getCard(), 0);
		}
		
		HashMap<String, Vector<Card>> updates = this.getHandsMap();
		
		updates.put("_DEALER", this.dealer.hands.firstElement());
		
		Deal d = new Deal(updates);
		
		sendToAll(d);
		
		for (Map.Entry<String, Player> e : this.players.entrySet()) {
			Player p = e.getValue();
			int sum = e.getValue().getSum(0);
			
			if (sum == 21) {
				// inform group of win
				p.changeBalance((int)(p.getBet() * 1.5));
				sendToAll(new Stay(e.getKey()));
				p.doubled = true;
			}
		}
		
		boolean donePlaying = false;
		
		for (Player p : this.players.values()) {
			this.processMove(p);
		}
		
		this.dealerMove();
				
		for (Player p : this.players.values()) {
			for (int i = 0; i < p.hands.size(); ++i) {
				int sum = p.getSum(i);
				if (sum <= 21) {
					for (int j = 0; j < this.dealer.hands.size(); ++j) {
						int dealerSum = this.dealer.getSum(j);
						if (sum > dealerSum) {
							// player wins
							p.changeBalance(p.getBet());
						} else if (sum < dealerSum) {
							// player loses
							p.changeBalance(-p.getBet());
						} else {
							// player pushes
						}
					}
				} else {
					p.changeBalance(-p.getBet());
				}
			}
			
			sendToAll(new Balance(p.username, p.balance));
			
			p.setBet(0);
			
			if (p.isBankrupt()) {
				sendToAll(new Leave(p.username));
				this.players.remove(p);
			}
		}
	}
	
	private void sendToAll(Message m) {
		for (Player p : this.players.values()) {
			ObjectOutputStream oos = null;
			
			try {
				oos = new ObjectOutputStream(p.s.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				oos.writeObject(m);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				oos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private HashMap<String, Vector<Card>> getHandsMap() {
		HashMap<String, Vector<Card>> updates = new HashMap<String, Vector<Card>>();
		
		for (Map.Entry<String, Player> e : this.players.entrySet()) {
			updates.put(e.getKey(), e.getValue().hands.firstElement());
		}
		
		return updates;
	}
}