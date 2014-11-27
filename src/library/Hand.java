package library;

import java.util.Vector;

import library.Card.Rank;
import library.GamePlay.StateOfRound;


public class Hand {
	private Vector<Card> hand;
	private int highestValue;
	private int lowestValue; 
	private int realValue; 
	private boolean isBusted; 
	private int currentBet; 
	private boolean isBetPlaced;
	private AbstractPlayer player; 

	public Hand(AbstractPlayer player)
	{
		this.hand = new Vector<Card>();
		this.setBusted(false);
		isBetPlaced = false; 
		this.player = player; 
	}
	
	public Vector<Card> getHand() {
		return hand;
	}
	public void addCard(Card c)
	{		hand.add(c);
		calculateHandValue(); 
	}

	
	// blackjack math logic
	public void calculateHandValue()
	{
		
		int lowValue = 0;
		int highValue = 0;
		for (int i = 0; i < this.getHand().size(); i++)
		{
			if(this.getHand().get(i).getRank() == Rank.ACE){				
				lowValue = lowValue + 1;
				highValue = highValue + 11; 
			
			}else{
				lowValue = lowValue + this.getHand().get(i).getRank().getValue(); 
				highValue = highValue + this.getHand().get(i).getRank().getValue(); 
			}
			
		}
		
		this.setHighestValue(highValue);
		this.setLowestValue(lowValue);
		
		if (this.getLowestValue() > 21){
			this.setBusted(true);
		}
		else{
			this.setBusted(false);
		}
		findRealValue(); 

	}

	public int getHighestValue() {
		return highestValue;
	}

	public void setHighestValue(int highestValue) {
		this.highestValue = highestValue;
	}

	public int getLowestValue() {
		return lowestValue;
	}

	public void setLowestValue(int lowestValue) {
		this.lowestValue = lowestValue;
	}

	public boolean isBusted() {
		return isBusted;
	}

	public void setBusted(boolean isBusted) {
		this.isBusted = isBusted;
	}

	public int getCurrentBet() {
		return currentBet;
	}

	public void setCurrentBet(int currentBet) {
		this.currentBet = currentBet;
	}
	
	private void findRealValue(){
		int realValue = 0; 
		if (this.getHighestValue() > 21){
			realValue = this.getLowestValue();
		}
		else{
			realValue = this.getHighestValue();
		}
		this.realValue = realValue;
	}

	public int getRealValue() {
		return realValue;
	}
	public Card getCard(int index)
	{
		return this.getHand().get(index);
	}
	public int getNumCards()
	{
		return this.getHand().size(); 
	}
	
	public void bet(int betAmount) throws UnsupportedOperationException
	{
		//TODO Bean Fire Property Changes OR State change
		if(this.getPlayer().getGamePlay().getCurrentState() == StateOfRound.BETTING){
			if(!isBetPlaced){
				this.setCurrentBet(betAmount);
				isBetPlaced = true; 
			}
			else{
				throw new UnsupportedOperationException("Bet was previously placed");			
			}
		}else{
			throw new UnsupportedOperationException("The state of the game is not Betting");			
		}
	}
	
	public boolean isBetPlaced() {
		return isBetPlaced;
	}

	public void setBetPlaced(boolean isBetPlaced) {
		this.isBetPlaced = isBetPlaced;
	}

	public AbstractPlayer getPlayer() {
		return player;
	}

	public void setPlayer(AbstractPlayer player) {
		this.player = player;
	}


}
