package library;

public class Dealer extends AbstractPlayer {
	private DeckOfCards deck;
	private PlayerContainer playerContainer; 
	private Player currentPlayer; 
	private Hand dealerHand; 	
	
	
	
	public Dealer()
	{
		DeckOfCards deck = new DeckOfCards(); 
		this.setDeck(deck);
		dealerHand = new Hand(this); 
		currentPlayer = null; 
	}
	public DeckOfCards getDeck() {
		return deck;
	}

	public void setDeck(DeckOfCards deck) {
		this.deck = deck;
	}
	
	public void dealToPlayer()
	{
		Card currentCard = deck.removeTopCard();
		currentPlayer.getCurrentHand().addCard(currentCard);		
	}
	
	public void dealOneCardToDealer(boolean faceUpOrDown)
	{
		Card currentCard = deck.removeTopCard();
		currentCard.setFaceUp(faceUpOrDown);
		this.getDealerHand().addCard(currentCard);
	}

	public PlayerContainer getPlayers() {
		return playerContainer;
	}

	public void setPlayers(PlayerContainer players) {
		this.playerContainer = players;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Hand getDealerHand() {
		return dealerHand;
	}

	public void setDealerHand(Hand dealerHand) {
		this.dealerHand = dealerHand;
	}
	
	
	//we can automate how the dealer will play every single time
	public void afterPlayerActionDealerAction()
	{
		this.getDealerHand().calculateHandValue();
		while(this.getDealerHand().isBusted() == false && this.getDealerHand().getHighestValue() < 17)
		{
			dealOneCardToDealer(true); 
		}

	}
	
	public void dealToTable()
	{
		for (int i = 0; i < playerContainer.getPlayerContainer().size(); i++)
		{
			currentPlayer = playerContainer.getPlayer(i);
			dealToPlayer(); 
			dealToPlayer();
		}
		
		//reset current player to first player left of dealer
		currentPlayer = playerContainer.getPlayer(0);
	}
	
	public void dealToDealer()
	{
		dealOneCardToDealer(true);
		dealOneCardToDealer(false); 
	}
	
	public void evaluateDeck()
	{
		if (this.getDeck().getDeckOfCards().size() < 52){
			//build new deck (deck constructor will make the 260 cards)
			this.setDeck(new DeckOfCards());
		}else{
			//the deck stays the same
		}
	}


}
