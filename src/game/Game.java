package game;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
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
	
	HashMap<String, Socket> userSocketMap;
	Vector<Player> players = new Vector<Player>();
	Player dealer = new Player(-1);
	Deck deck;
	ServerSocket ss;
	
	public Game(HashMap<String, Socket> userSocketMap) {
		this.userSocketMap = userSocketMap;
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
				
				System.out.println(p.getSum(i));
			}
		}
	}
	
	private void dealerMove() {
		while (this.dealer.getSum(0) < 17) {
			this.dealer.addCard(this.deck.getCard(), 0);
		}
	}

	public void run() {
		for (Player p : this.players) {
			p.readBet();
		}

		this.deck = new Deck();
		
		for (Player p : this.players) {
			for (int i = 0; i < 2; ++i) {
				p.addCard(this.deck.getCard(), 0);
			}
		}
		
		for (int i = 0; i < 2; ++i) {
			this.dealer.addCard(this.deck.getCard(), 0);
		}
		
		for (Player p : this.players) {
			int sum = p.getSum(0);
			
			if (sum == 21) {
				System.out.println("Blackjack");
				p.changeBalance((int)(p.getBet() * 1.5));
				this.players.remove(p);
			} else {
				System.out.println(sum);
			}
		}
		
		boolean donePlaying = false;
		
		for (Player p : this.players) {
			this.processMove(p);
		}
		
		this.dealerMove();
				
		for (Player p : this.players) {
			for (int i = 0; i < p.hands.size(); ++i) {
				int sum = p.getSum(i);
				if (sum <= 21) {
					for (int j = 0; j < this.dealer.hands.size(); ++j) {
						int dealerSum = this.dealer.getSum(j);
						if (sum > dealerSum) {
							System.out.println("Player " + p.id + " wins with " + sum + " vs " + dealerSum);
							p.changeBalance(p.getBet());
							p.setBet(0);
						} else if (sum < dealerSum) {
							System.out.println("Player " + p.id + " loses with " + sum + " vs " + dealerSum);
							p.changeBalance(-p.getBet());
						} else {
							System.out.println("Player " + p.id + " pushes with " + sum);
						}
					}
				} else {
					System.out.println("Player " + p.id + " busts");
					p.changeBalance(-p.getBet());
				}
			}
			
			p.setBet(0);
			
			if (p.isBankrupt()) {
				System.out.println("bankrupt");
			}
		}
	}
}