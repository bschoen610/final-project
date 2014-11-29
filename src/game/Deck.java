package game;

import game.Game;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

class Deck {
	Vector<Card> deck;
	
	public Deck() {
		this.deck = new Vector<Card>(Game.DECK_SIZE);
		
		for (int i = 0; i < Game.NUM_DECKS; ++i) {
			for (int suit = 0; suit < Game.NUM_SUITS; ++suit) {
				for (int face = 0; face < Game.NUM_FACES; ++face) {
					this.deck.add(new Card(suit, face));
				}
			}
		}
		
		Random shuffler = new Random();
	
		for (int i = 0; i < Game.SHUFFLE_COUNT; ++i) {
			int oldIdx = shuffler.nextInt(Game.DECK_SIZE);
			int newIdx = shuffler.nextInt(Game.DECK_SIZE);
			
			Collections.swap(this.deck, oldIdx, newIdx);
		}
	}
	
	public Card getCard() {
		Card card = this.deck.lastElement();
		this.deck.remove(this.deck.size() - 1);
		return card;
	}
}