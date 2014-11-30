package game;

import game.Game.Move;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public class Player {
	int bet;
	int balance;
	public Socket s;
	public String username;
	public Vector<Vector<Card>> hands = new Vector<Vector<Card>>(2);
	public boolean doubled;
	
	public Player(Socket s, String username) {
		this.s = s;
		this.username = username;
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
		if (this.doubled) return Move.STAY;
		
		ObjectOutputStream oos = null;
		
		try {
			oos = new ObjectOutputStream(this.s.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			oos.writeObject(new StartMove(this.username));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		try {
			oos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(this.s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MoveMessage mm = null;
		try {
			mm = (MoveMessage) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Vector<Card> firstHand = this.hands.firstElement();
		
		if (mm.move == Move.SPLIT
				&& (this.hands.size() > 1
						|| firstHand.size() > 2
						|| firstHand.firstElement().face != firstHand.lastElement().face)) {
			System.out.println("Split in invalid scenario, this shouldn't happen");
		}
		
		return mm.move;
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