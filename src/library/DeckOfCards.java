package library;

import java.util.Random;
import java.util.Vector;

import library.Card.Rank;
import library.Card.Suit;

public class DeckOfCards extends AbstractBean implements java.io.Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6902749635422700599L;
	private Vector<Card> deckOfCards;
	private Random random; 
	
	public DeckOfCards()
	{
		random = new Random();
		// create all cards that will be used in game, equal number of every card based on suit and rank 
		//we need to decide how many decks we want to play with, anywhere from 1-8
		deckOfCards = new Vector<Card>();
		int numberOfDecks = 5;
		for(int i = 0; i < numberOfDecks; i++)
		{
			for (int k = 0; k < 4; k++)
			{
				for(int j = 2; j < 15; j++)
				{
					Card card = new Card(Rank.getRank(j), Suit.getSuit(k));
					deckOfCards.add(card);
				}

			}
		}
		this.shuffle();
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
		int oldNumCards = this.getNumCards();
		Card topCard = deckOfCards.elementAt(0);
		deckOfCards.remove(topCard);
		this.getPcs().firePropertyChange("numCards", oldNumCards, this.getNumCards());
		
		return topCard; 
	}
	public Card getCard(int index)
	{
		return this.getDeckOfCards().get(index);
	}
	
	public int getNumCards()
	{
		return this.getDeckOfCards().size(); 
	}

}
