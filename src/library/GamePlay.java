package library;

public class GamePlay extends AbstractBean {
	private int amountInPot;
	private StateOfRound currentState; 
	private Player currentPlayer; 
	private PlayerContainer playerContainer; 
	private Dealer dealer; 
	
	public enum StateOfRound{
		BETTING	(0, 'B'),
		PLAYER_ACTION(1, 'P'),
		DEALER_ACTION(2, 'D'), 
		COLLECTION(3, 'C');
		
		private final int stateValue; 
		private final Character stateSymbol; 
		
		@SuppressWarnings("unused")
		private static StateOfRound[] stateArray = {BETTING, PLAYER_ACTION, DEALER_ACTION, COLLECTION};  
		
		private StateOfRound(int stateValue, Character stateSymbol)
		{
			this.stateValue = stateValue; 
			this.stateSymbol = stateSymbol; 
		}

		public int getStateValue() {
			return stateValue;
		}

		public Character getStateSymbol() {
			return stateSymbol;
		}
		
		public StateOfRound next(StateOfRound currentState)
		{
			return values()[currentState.getStateValue() + 1];
		}
		
		
	}
	
	//add methods for creating players when players join the game
	//This will need to give the players the same dealer.
	
	public void addPlayer(Player newPlayer)
	{
		newPlayer.setDealer(this.getDealer());
		this.getPlayerContainer().addPlayer(newPlayer);
	}

	public int getAmountInPot() {
		return amountInPot;
	}

	public void setAmountInPot(int amountInPot) {
		this.amountInPot = amountInPot;
	}
	
	public StateOfRound getCurrentState() {
		return currentState;
	}

	public void setCurrentState(StateOfRound currentState) {
		this.currentState = currentState;
	}
	
	public void nextState(){
		//Tricking java is fun
		StateOfRound oldState = this.currentState;
		this.currentState = this.currentState.next(currentState);
		this.getPcs().firePropertyChange("currentState", oldState, this.getCurrentState());
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		Player oldPlayer = this.currentPlayer;
		this.currentPlayer = currentPlayer;
		this.dealer.setCurrentPlayer(currentPlayer);
		this.playerContainer.setCurrentPlayer(currentPlayer);
		this.getPcs().firePropertyChange("currentPlayer", oldPlayer, this.getCurrentPlayer());
		currentPlayer.setCurrent(true);
		for (int i = 0; i < playerContainer.getPlayerContainer().size(); i++)
		{
			if (playerContainer.getPlayerContainer().get(i) != currentPlayer)
			{
				playerContainer.getPlayerContainer().get(i).setCurrent(false);
			}
		}
	}

	public PlayerContainer getPlayerContainer() {
		return playerContainer;
	} 

	public void setPlayerContainer(PlayerContainer playerContainer) {
		this.playerContainer = playerContainer;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}
	
	public void nextPlayer(Player nextPlayer)
	{
		//need to make sure the servers have these libraries so everyone is in sync
		this.setCurrentPlayer(this.getPlayerContainer().nextPlayer(currentPlayer));
		
		//TODO this needs to be called whenever a player busts or a player stays
		//TODO this needs to tell the server to tell the next player its his turn
	}
	
	
	
	
	//method that goes through playerContainer and compares each players hands (possibly multiple) with the hand of the dealrs
	//This method will determine which players hands have lost (and thus that money is subtracted from their chipCount
	//this method will determine which players hands have won (and thus the money they bet on that hand is not subtracted from their chipcount and that same amount is added to their chipcount)
	//this method will determine which players hands pushed (and thus the chipcount does not change for this hand) 
	
	//this method should only be called if the dealer did not have a blackjack at the beginning of the round
	public void determineWinnersAndLosers()
	{
		//if dealer busts then everyone who has not busted wins
		
		//this may not be necessary
		dealer.getDealerHand().calculateHandValue();
		
		if (dealer.getDealerHand().isBusted() == true){
			dealerBustPlayerOutcome();
		}else{

			//if dealer has not busted then you compare
			dealerNoBustPlayerOutcome(); 
		}
	}

	private void dealerBustPlayerOutcome() 
	{
		for(int i = 0; i < this.getPlayerContainer().getPlayerContainer().size(); i++)
		{
			for(int j = 0; j < this.getPlayerContainer().getPlayerContainer().get(i).getHands().size(); j++)
			{
				Player currentPlayer = this.getPlayerContainer().getPlayerContainer().get(i);
				if (currentPlayer.getHands().get(j).isBusted() == false){
					//player wins his money 
					currentPlayer.setChipCount(currentPlayer.getChipCount() + currentPlayer.getHands().get(j).getCurrentBet());						
				}
				else{
					//player loses his money
					currentPlayer.setChipCount(currentPlayer.getChipCount() - currentPlayer.getHands().get(j).getCurrentBet());
				}
			}
		}
	}
	
	private void dealerNoBustPlayerOutcome()
	{
		int dealerValue = 0; 
		if (this.getDealer().getDealerHand().getHighestValue() > 21){
			dealerValue = this.getDealer().getDealerHand().getLowestValue();
		}
		else{
			dealerValue = this.getDealer().getDealerHand().getHighestValue();
		}
		for(int i = 0; i < this.getPlayerContainer().getPlayerContainer().size(); i++)
		{
			for(int j = 0; j < this.getPlayerContainer().getPlayerContainer().get(i).getHands().size(); j++)
			{
				Player currentPlayer = this.getPlayerContainer().getPlayerContainer().get(i);
				int currentPlayerValue = currentPlayer.getHands().get(j).getRealValue();
				if(currentPlayerValue > dealerValue){
					currentPlayer.setChipCount(currentPlayer.getChipCount() + currentPlayer.getHands().get(j).getCurrentBet());						
				}else if(currentPlayerValue == dealerValue){
					//this means the player has pushed with the dealer
					//nothing happens
				}else{
					currentPlayer.setChipCount(currentPlayer.getChipCount() - currentPlayer.getHands().get(j).getCurrentBet());
				}
			}
		}
		
	}
	
		
}
