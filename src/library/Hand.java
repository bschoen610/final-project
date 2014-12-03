package library;

import java.util.Vector;

import library.Card.Rank;
import library.GamePlay.StateOfRound;


public class Hand extends AbstractBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1710360574880167790L;
	private Vector<Card> hand;
	private int highestValue = 0;
	private int lowestValue = 0; 
	private int realValue = 0; 
	private boolean isBusted; 
	private double currentBet = 0; 
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
	{		
		int oldNumCards =  this.getNumCards();
		hand.add(c);
		calculateHandValue(); 
		this.getPcs().firePropertyChange("numCards", oldNumCards, this.getNumCards());
		//changing a list so call fireIntervalAdded
		this.getListDataChangeSupport().fireIntervalAdded(this.getNumCards() - 1);
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
		int oldHighestValue = this.getHighestValue();
		this.highestValue = highestValue;
		this.getPcs().firePropertyChange("highestValue", oldHighestValue, this.getHighestValue());
	}

	public int getLowestValue() {
		return lowestValue;
	}

	public void setLowestValue(int lowestValue) {
		int oldLowestValue = this.getLowestValue();
		this.lowestValue = lowestValue;
		this.getPcs().firePropertyChange("lowestValue", oldLowestValue, this.getLowestValue());
	}

	public boolean isBusted() {
		return isBusted;
	}

	public void setBusted(boolean isBusted) {
		boolean oldBusted = this.isBusted;
		this.isBusted = isBusted;
		this.getPcs().firePropertyChange("isBusted", oldBusted, this.isBusted());
	}

	public double getCurrentBet() {
		return currentBet;
	}

	public void setCurrentBet(double currentBet) {
		double oldBet = this.getCurrentBet();
		this.currentBet = currentBet;
		this.getPcs().firePropertyChange("currentBet", oldBet, this.getCurrentBet());
	}
	
	private void findRealValue(){
		int oldRealValue = this.getRealValue();
		int realValue = 0; 
		if (this.getHighestValue() > 21){
			realValue = this.getLowestValue();
		}
		else{
			realValue = this.getHighestValue();
		}
		this.realValue = realValue;
		this.getPcs().firePropertyChange("realValue", oldRealValue, this.getRealValue());

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
		if(this.getPlayer().getGamePlay().getCurrentState() == StateOfRound.BETTING){
			if(!isBetPlaced){
				this.setCurrentBet(betAmount);
				isBetPlaced = true; 
				this.getPcs().firePropertyChange("currentBet", 0, betAmount);

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
		boolean oldIsBetPlaced = this.isBetPlaced();
		this.isBetPlaced = isBetPlaced;
		this.getPcs().firePropertyChange("isBetPlaced", oldIsBetPlaced, this.isBetPlaced());
	}

	public AbstractPlayer getPlayer() {
		return player;
	}

	public void setPlayer(AbstractPlayer player) {
		this.player = player;
	}


}
