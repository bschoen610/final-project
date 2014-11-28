package library;

public class Dealer extends AbstractPlayer implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5227374921561663498L;
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
		DeckOfCards oldDeck = this.getDeck(); 
		this.deck = deck;
		this.getPcs().firePropertyChange("deck", oldDeck, this.getDeck());
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

	public void setPlayerContainer(PlayerContainer players) {
		PlayerContainer oldPlayerContainer = this.getPlayers();
		this.playerContainer = players;
		this.getPcs().firePropertyChange("playerContainer", oldPlayerContainer, this.getPlayers());
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		//I setCurrentPlayer for everyone in the GamePlay class. Would i need to do the PCS stuff in every method that GamePlay.setCurrentPlayer calls and then not have to deal with it in the GamePlay method? 
		//Or otherway around, or do it for both. 
		Player oldCurrentPlayer = this.getCurrentPlayer();
		this.currentPlayer = currentPlayer;
		this.getPcs().firePropertyChange("currentPlayer", oldCurrentPlayer, this.getCurrentPlayer());
	}

	public Hand getDealerHand() {
		return dealerHand;
	}

	public void setDealerHand(Hand dealerHand) {
		Hand oldHand = this.getDealerHand();
		this.dealerHand = dealerHand;
		this.getPcs().firePropertyChange("dealerHand", oldHand, this.getDealerHand());
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
			this.setCurrentPlayer(playerContainer.getPlayer(i));
			dealToPlayer(); 
			dealToPlayer();
		}
		
		//reset current player to first player left of dealer
		this.setCurrentPlayer(playerContainer.getPlayer(0));
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
