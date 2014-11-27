package game;

import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Game {
	public static final int NUM_DECKS = 6;
	public static final int NUM_CARDS = 52;
	public static final int DECK_SIZE = NUM_DECKS * NUM_CARDS; // 6 deckz
	public static final int SHUFFLE_COUNT = 1024;
	
	public enum Move {
		HIT,
		STAY,
		DOUBLE,
		SPLIT,
	}
	
	class Player {
		int bet;
		int balance;
		int id;
		public Vector<Vector<Card>> hands = new Vector<Vector<Card>>(2);
		public boolean doubled;
		
		public Player(int ida) {
			this.id = ida;
			this.bet = 0;
			this.balance = 1000;
			this.doubled = false;
			this.addHand();
		}
		
		public void changeBalance(int amt) {
			this.balance += amt;
		}
		
		public boolean isBankrupt() {
			return this.balance <= 0;
		}
		
		public void addCard(Card card, int idx) {
			this.hands.elementAt(idx).add(card);
		}
		
		public void split() {
			Vector<Card> newHand = new Vector<Card>(2);
			newHand.add(this.hands.firstElement().lastElement());
			this.hands.add(newHand);
			this.hands.firstElement().remove(1);
			this.bet *= 2;
		}
		
		public void addHand() {
			this.hands.add(new Vector<Card>(2));
		}
		
		public int getSum(int idx) {
			int sum = 0;
			int aces = 0;
			
			for (Card card : this.hands.elementAt(idx)) {
				int value = card.face + 1;
				
				if (value > 10) {
					value = 10;
				} 
				
				if (value == 1) {
					value = 11;
					++aces;
				}
				
				sum += value;
			}
			
			for (int i = 0; sum > 21 && i < aces; ++i) {
				sum -= 10;
			}
			
			return sum;
		}
		
		public Move getMove() {
			System.out.print("What would you like to do, player " + this.id + "? ");
			
			for (Vector<Card> hand : this.hands) {
				System.out.print("(");
				
				for (Card card : hand) {
					System.out.print(card.face + 1 + ", ");
				}
				
				System.out.print(")");
			}
			
			Scanner s = new Scanner(System.in);
			int move = s.nextInt();
			
			Move ret;
			
			switch (move) {
			case 0:
				ret = Move.HIT;
				break;
			case 1:
				ret = Move.STAY;
				break;
			case 2:
				ret = Move.DOUBLE;
				break;
			case 3:
				ret = Move.SPLIT;
				break;
			default:
				ret = null;
				break;
			}
			
			Vector<Card> firstHand = this.hands.firstElement();
			
			if (ret == Move.SPLIT
					&& (this.hands.size() > 1
							|| firstHand.size() > 2
							|| firstHand.firstElement().face != firstHand.lastElement().face)) {
				System.out.println("Split in invalid scenario, this should be fixed");
			}
			
			if (doubled) ret = Move.STAY;
			
			return ret;
		}
		
		public void readBet() {/*
			System.out.print("How much would you like to bet? ");
			Scanner s = new Scanner(System.in);
			int bet = s.nextInt();
			this.bet += bet;*/
			this.bet = 1;
		}
		
		public int getBet() {
			return this.bet;
		}
		
		public void setBet(int bet) {
			this.bet = bet;
		}
	}
	
	class Card {
		public static final int NUM_SUITS = 4;
		public static final int NUM_FACES = 13;
		int suit;
		int face;
		
		public Card(int suit, int face) {
			this.suit = suit;
			this.face = face;
		}
	}
	
	class Deck {
		Vector<Card> deck;
		
		public Deck() {
			this.deck = new Vector<Card>(DECK_SIZE);
			
			for (int i = 0; i < NUM_DECKS; ++i) {
				for (int suit = 0; suit < Card.NUM_SUITS; ++suit) {
					for (int face = 0; face < Card.NUM_FACES; ++face) {
						this.deck.add(new Card(suit, face));
					}
				}
			}
			
			Random shuffler = new Random();
		
			for (int i = 0; i < SHUFFLE_COUNT; ++i) {
				int oldIdx = shuffler.nextInt(DECK_SIZE);
				int newIdx = shuffler.nextInt(DECK_SIZE);
				
				Collections.swap(this.deck, oldIdx, newIdx);
			}
		}
		
		public Card getCard() {
			Card card = this.deck.lastElement();
			this.deck.remove(this.deck.size() - 1);
			return card;
		}
	}
	
	Vector<Player> players = new Vector<Player>();
	Player dealer = new Player(-1);
	Deck deck;
	
	public Game() {		
		for (int i = 0; i < 2; ++i) {
			this.players.add(new Player(i));
		}

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
	
	public static void main(String[] args) {
		new Game();
	}
}