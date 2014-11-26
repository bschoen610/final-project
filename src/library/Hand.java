package library;

import java.util.Vector;

import library.Card.Rank;

public class Hand {
	private Vector<Card> hand;
	private int highestValue;
	private int lowestValue; 
	private int realValue; 
	private boolean isBusted; 
	private int currentBet; 
	public Hand()
	{
		this.hand = new Vector<Card>();
		this.setBusted(false);
	}
	
	public Vector<Card> getHand() {
		return hand;
	}
	public void addCard(Card c)
	{
		hand.add(c);
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
	
	public void findRealValue(){
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


}
