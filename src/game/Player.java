package game;

import game.Game.Move;

import java.util.Scanner;
import java.util.Vector;

public class Player {
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
			System.out.println("Split in invalid scenario, this shouldn't happen");
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