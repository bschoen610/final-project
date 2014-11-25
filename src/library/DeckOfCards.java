package library;

import java.util.Random;
import java.util.Vector;

public class DeckOfCards extends AbstractBean {
	private Vector<Card> deckOfCards;
	private Random random; 
	
	public DeckOfCards()
	{
		random = new Random(); 
		
	}

	public Vector<Card> getDeckOfCards() {
		return deckOfCards;
	}

	
	//Fisher-Yates Shuffle
	//http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
	public void shuffle()
	{
		for( int i = deckOfCards.size() - 1; i > 0; i--)
		{
			int j = random.nextInt(i + 1);  
			swapCards(i, j);
		}
		
	}
	
	public void swapCards( int index1, int index2)
	{
		if (index1 == index2){
			return; 
		}
		else{
			Card tempCard = deckOfCards.get(index1);
			deckOfCards.set(index1, deckOfCards.get(index2));
			deckOfCards.set(index2, tempCard);
		}
	}
	
	
	//can be used for the dealer to deal cards and trash cards
	public Card removeTopCard()
	{
		Card topCard = deckOfCards.elementAt(0);
		deckOfCards.remove(topCard);
		return topCard; 
	}
	
	
	
	//In constructor create all 52 cards of different rank and suit. 

}
