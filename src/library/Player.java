package library;

import java.util.Vector;

public class Player extends AbstractPlayer implements java.io.Serializable  { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7072489023475046711L;
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
		int oldChipCount = this.getChipCount();
		this.chipCount = chipCount;
		this.getPcs().firePropertyChange("chipCount",oldChipCount, this.getChipCount() );
	}
	
	public Hand getCurrentHand()
	{
		return currentHand;
	}

	//this needs to be called whenever you switch hands during a split
	public void setCurrentHand(Hand currentHand)
	{
		Hand oldHand = this.getCurrentHand();
		this.currentHand = currentHand;
		this.getPcs().firePropertyChange("currentHand", oldHand, this.getCurrentHand());
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
			
			this.getPcs().firePropertyChange("numHands", this.getNumHands() - 1, this.getNumHands());

		}else{
			throw new UnsupportedOperationException("You cannot split hands right now"); 
		}
		

	}

	public boolean canSplit() {
		return canSplit;
	}

	public void setCanSplit(boolean canSplit) {
		boolean oldCanSplit = this.canSplit;
		this.canSplit = canSplit;
		this.getPcs().firePropertyChange("canSplit", oldCanSplit, this.canSplit);
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
		if(isCurrent()){
			this.currentHand.setCurrentBet(this.currentHand.getCurrentBet() * 2);
			//TODO Need to deal one more card to player and move to next hand, if this was his last hand move to last player
			//After Bean
		}else{

		}
	}

	public void stay()
	{
		//TODO Bean Fire Property Changes OR State change
		//this is a state change right??
		//Really the crux of the issue left. How to know when to advance players, depends on numHands of currentPlayer(this)
		//Same with hit and double down and split - lots of things have the happen
		if(isCurrent()){

		}else{

		}
	}
	public void hit()
	{
		// signal the dealer to deal one card to the players current hand
		//signal to the server to signal to the gamePlay to hit for the currentPlayer
		// this.getGamePlay().hit(); 
		if (isCurrent()){
			dealer.dealToPlayer();
			//TODO Bean Fire Property Changes OR State change
		}else{

		}
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		Dealer oldDealer = this.getDealer(); 
		this.dealer = dealer;
		this.getPcs().firePropertyChange("dealer", oldDealer, this.getDealer());
	}

	public boolean isCurrent() {
		return isCurrent;
	}

	public void setCurrent(boolean isCurrent) {
		boolean oldCurrent = this.isCurrent;
		this.isCurrent = isCurrent;
		this.getPcs().firePropertyChange("isCurrent", oldCurrent, this.isCurrent());
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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



