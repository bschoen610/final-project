package game;

import java.io.Serializable;
import java.util.Vector;

public class Hand implements Serializable {
	private static final long serialVersionUID = 1L;
	
	int bet;
	Vector<Card> cards;
	
	public Hand(int bet) {
		this.bet = bet;
		this.cards = new Vector<Card>();
	}
	
	public int getBet() {
		return this.bet;
	}
	
	public void setBet(int bet) {
		this.bet = bet;
	}
	
	public void addCard(Card card) {
		this.cards.add(card);
	}
	
	public void removeCard(int idx) {
		this.cards.remove(idx);
	}
	
	public int getSum() {
		int sum = 0;
		int aceCount = 0;
		
		for (Card card : this.cards) {
			if (card.num >= 9) {
				sum += 10;
			} else if (card.num == 0) {
				sum += 11;
				++aceCount;
			} else {
				sum += card.num + 1;
			}
		}
		
		while (aceCount > 0) sum -= 10;
		
		return sum;
	}
}