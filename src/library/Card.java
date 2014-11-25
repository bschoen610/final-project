package library;

public class Card extends AbstractBean implements Comparable<Card> {

	//http://sourceforge.net/p/jokera/code/ci/master/tree/src/mikejyg/playingCards/Card.java#l57
	
	public enum Suit{
		CLUB(0, 'C'), 
		DIAMOND(1, 'D'), 
		HEART(2, 'H'),
		SPADE(3, 'S');
		private final int intValue; 
		private final Character symbol; 
		
		@SuppressWarnings("unused")
		private static Suit[] suitArray = {SPADE, HEART, DIAMOND, CLUB};
		
		private Suit(int intValue, Character symbol)
		{
			this.intValue = intValue; 
			this.symbol = symbol; 
		}

		public int getIntValue() {
			return intValue;
		}

		public Character getSymbol() {
			return symbol;
		}
		
		//static public Suit getSuit(int suit)
		//{
			//return suitArray[suit];
		//}
		
	}
	
	public enum Rank{
		TWO(2, '2'),
		THREE(3, '3'),
		FOUR(4, '4'),
		FIVE(5, '5'),
		SIX(6, '6'),
		SEVEN(7, '7'),
		EIGHT(8, '8'),
		NINE(9, '9'),
		TEN(10, 'T'),
		JACK(11, 'J'),
		QUEEN(12, 'Q'),
		KING(13, 'K'),
		ACE(14, 'A');
		
		private final int intValue; 
		private final Character symbol; 
		
		private Rank( int intValue, Character symbol)
		{
			this.intValue = intValue; 
			this.symbol = symbol; 
		}

		public int getIntValue() {
			return intValue;
		}

		public Character getSymbol() {
			return symbol;
		}
		
		//ace can hold the value of the first card or the greatest card
		//aces can start a straight of ace, 2, 3, 4, 5 AND can end a straight of 10, jack, queen, king, ACE
		private static final Rank[] rankArray = {null, ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE};
		
		public static Rank getRank(int intValue)
		{
			return rankArray[intValue];
		}
		
	}
	
	private Rank rank; 
	private Suit suit;
	
	public Card()
	{
		this.rank = Rank.ACE;
		this.suit = Suit.SPADE;
	}
	
	public Card(Rank rank, Suit suit)
	{
		this.rank = rank; 
		this.suit = suit; 
	}
	@Override
	public int compareTo(Card tempCard) {
		int firstCompare = rank.compareTo(tempCard.rank);
		if (firstCompare != 0){
			return firstCompare; 
		}
		else{
			return suit.compareTo(tempCard.suit);
		}
		
	}
	
	public boolean equals(Object tempCard)
	{ 
		Card card2 = (Card) tempCard; 
		if(this.getRank() == card2.getRank() && this.getSuit() == card2.getSuit()){
			return true;
		}
		else{
			return false; 
		}
	}
	public Rank getRank() {
		return rank;
	}
	public Suit getSuit() {
		return suit;
	}

}
