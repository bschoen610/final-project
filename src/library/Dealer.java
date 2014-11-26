package library;

public class Dealer extends AbstractBean {
	private DeckOfCards deck;
	private PlayerContainer playerContainer; 
	private Player currentPlayer; 
	private Hand dealerHand; 	
	
	
	

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
	
	public void dealToSelf()
	{
		Card currentCard = deck.removeTopCard();
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
			dealToSelf(); 
		}

	}


}
