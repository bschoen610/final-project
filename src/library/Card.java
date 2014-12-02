package library;

public class Card extends AbstractBean implements Comparable<Card>,java.io.Serializable {

	//http://sourceforge.net/p/jokera/code/ci/master/tree/src/mikejyg/playingCards/Card.java#l57
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3235968207830808445L;
	public String imagePath;
	
	public enum Suit{
		CLUB(0, 'C'), 
		DIAMOND(1, 'D'), 
		HEART(2, 'H'),
		SPADE(3, 'S');
		private final int intValue; 
		private final Character symbol; 
		
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
		
		static public Suit getSuit(int suit)
		{
			return suitArray[suit];
		}
		
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
		JACK(10, 'J'),
		QUEEN(10, 'Q'),
		KING(10, 'K'),
		ACE(11, 'A');
		
		private final int intValue; 
		private final Character symbol; 
		
		private Rank( int intValue, Character symbol)
		{
			this.intValue = intValue; 
			this.symbol = symbol; 
		}

		public int getValue() {
			return intValue;
		}

		public Character getSymbol() {
			return symbol;
		}
		
		//ace can hold the value of the first card or the greatest card
		//aces can count as 1 or 11
		private static final Rank[] rankArray = {null, ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE};
		
		public static Rank getRank(int intValue)
		{
			return rankArray[intValue];
		}
		
	}
	
	private Rank rank; 
	private Suit suit;
	private boolean faceUp;
	
	public Card()
	{
		this.rank = Rank.ACE;
		this.suit = Suit.SPADE;
		this.faceUp = true; 
		Character rankChar = rank.getSymbol();
		Character suitChar = suit.getSymbol();
		imagePath = getImagePath(rankChar, suitChar);
	}
	
	public Card(Rank rank, Suit suit)
	{
		this.rank = rank;
		this.suit = suit;
		Character rankChar = rank.getSymbol();
		Character suitChar = suit.getSymbol();
		imagePath = getImagePath(rankChar, suitChar);
	}
	
	private String getImagePath(Character rank, Character suit) {
		String fullRank = "";
		String fullSuit = "";
		
		if(rank.equals('2')){
			fullRank = "2";
		}
		else if(rank.equals('3')){
			fullRank = "3";
		}
		else if(rank.equals('4')){
			fullRank = "4";
		}
		else if(rank.equals('5')){
			fullRank = "5";
		}
		else if(rank.equals('6')){
			fullRank = "6";
		}
		else if(rank.equals('7')){
			fullRank = "7";
		}
		else if(rank.equals('8')){
			fullRank = "8";
		}
		else if(rank.equals('9')){
			fullRank = "9";
		}
		else if(rank.equals('T')){
			fullRank = "10";
		}
		else if(rank.equals('J')){
			fullRank = "jack";
		}
		else if(rank.equals('Q')){
			fullRank = "queen";
		}
		else if(rank.equals('K')){
			fullRank = "king";
		}
		else if(rank.equals('A')){
			fullRank = "ace";
		}
		
		if(suit.equals('C')){
			fullSuit = "clubs";
		}
		else if(suit.equals('D')){
			fullSuit = "diamonds";
		}
		else if(suit.equals('H')){
			fullSuit = "hearts";
		}
		else if(suit.equals('S')){
			fullSuit = "spades";
		}
		if(fullRank.equals("king") || fullRank.equals("queen") || fullRank.equals("jack")){
			// do this to get the cooler version of these cards
            return fullRank + "_of_" + fullSuit + "2.png";
		}
		else{
            return fullRank + "_of_" + fullSuit + ".png";
		}
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

	public boolean isFaceUp() {
		return faceUp;
	}

	public void setFaceUp(boolean faceUp) {
		this.faceUp = faceUp;
	}

}
