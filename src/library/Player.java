package library;

import java.util.Vector;

public class Player extends AbstractPlayer { 
	private Vector<Hand> hands;
	private int chipCount; 
	private boolean canSplit; 
	private Hand currentHand; 
	private Dealer dealer; 
	//For the GUI, if you are not isCurrent then your options following the betting stage are disabled
	//or hidden
	private boolean isCurrent; 
	private String userName; 
	
	//Will the player necessarily have to know his own playerContainer? I'm not sure yet
	
	

	public Player()
	{
		hands = new Vector<Hand>(); 
		Hand hand = new Hand(this); 
		this.setCurrentHand(hand);
		this.getHands().add(hand);
		//to start
		chipCount = 1000; 
		isCurrent = false; 
		canSplit = false; 
		
	}

	public Vector<Hand> getHands() {
		return hands;
	}

	public void setHands(Vector<Hand> hands) {
		this.hands = hands;
	} 
	
	int getChipCount() {
		return chipCount;
	}

	public void setChipCount(int chipCount) {
		this.chipCount = chipCount;
	}
	
	public Hand getCurrentHand()
	{
		return currentHand;
	}

	//this needs to be called whenever you switch hands during a split
	public void setCurrentHand(Hand currentHand)
	{
		this.currentHand = currentHand;
	}

	public void splitHand()
	{
		//With the GUI we will need to make this option impossible if the player does not have a hand with two of the same numerical cards
		//we also need to decide if we want to let players split if they have two face cards of different value, because they all have ten numerical points. This is completely up to us
		determineCanSplit();
		if(canSplit() && isCurrent()){
			Hand splitHand = new Hand(this); 
			Card splitCard = currentHand.getHand().get(1);
			currentHand.getHand().remove(1);
			splitHand.addCard(splitCard);
			this.getHands().add(splitHand);
			
			//TODO Bean Fire Property Changes OR State change

		}else{
			throw new UnsupportedOperationException("You cannot split hands right now"); 
		}
		

	}

	public boolean canSplit() {
		return canSplit;
	}

	public void setCanSplit(boolean canSplit) {
		this.canSplit = canSplit;
	}
	
	public void determineCanSplit()
	{
		if (currentHand.getHand().size() != 2)
		{
			this.setCanSplit(false);
		}
		else{
			//this is the if statement i was talking about above, where king and queen could be split versus just king and king
			if(currentHand.getHand().get(0).getRank() == currentHand.getHand().get(1).getRank()){
				this.setCanSplit(true);
			} 
			else{
				this.setCanSplit(false); 
			}
		}
	}
	public void doubleDown()
	{
		if(isCurrentPlayer()){
			this.currentHand.setCurrentBet(this.currentHand.getCurrentBet() * 2);
			//TODO Bean Fire Property Changes OR State change

		}else{

		}
	}

	public void stay()
	{
		//TODO Bean Fire Property Changes OR State change
		if(isCurrentPlayer()){

		}else{

		}
	}
	public void hit()
	{
		// signal the dealer to deal one card to the players current hand
		//signal to the server to signal to the gamePlay to hit for the currentPlayer
		// this.getGamePlay().hit(); 
		if (isCurrentPlayer()){
			dealer.dealToPlayer();
			//TODO Bean Fire Property Changes OR State change
		}else{

		}
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public boolean isCurrent() {
		return isCurrent;
	}

	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public boolean isCurrentPlayer()
	{
		if (this.getGamePlay().getCurrentPlayer() == this){
			return true;
		}else{
			return false; 
		}
	}

	
	
	public Hand getHand(int handIndex)
	{
		return this.getHands().get(handIndex);
	}
	
	public int getNumHands()
	{
		return this.getHands().size(); 
	}
}



