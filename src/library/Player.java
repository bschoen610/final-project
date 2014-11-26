package library;

import java.util.Vector;

public class Player extends AbstractBean {
	private Card card1; 
	private Card card2; 
	private Vector<Hand> hands;
	private int chipCount; 
	private boolean canSplit; 
	private Hand currentHand; 
	private Dealer dealer; 
	private boolean isCurrent; 
	private String userName; 
	
	//Will the player necessarily have to know his own playerContainer? I'm not sure yet
	
	

	public Player(String userName)
	{
		hands = new Vector<Hand>(); 
		Hand hand = new Hand(); 
		this.setCurrentHand(hand);
		this.getHands().add(hand);
		//to start
		chipCount = 1000; 
		this.setUserName(userName);
	}

	public Vector<Hand> getHands() {
		return hands;
	}

	public void setHand(Vector<Hand> hands) {
		this.hands = hands;
	} 
	
	int getChipCount() {
		return chipCount;
	}

	public void setChipCount(int chipCount) {
		this.chipCount = chipCount;
	}

	public Card getCard1() 
	{
		return card1;
	}

	public void setCard1(Card card1)
	{
		this.card1 = card1;
	}


	public Card getCard2() 
	{
		return card2;
	}


	public void setCard2(Card card2)
	{
		this.card2 = card2;
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
		//we also need to decide if we want to let players split if they hvae two face cards of different value, because they all have ten numerical points. This is completely up to us
		
		Hand splitHand = new Hand(); 
		Card splitCard = currentHand.getHand().get(1);
		currentHand.getHand().remove(1);
		splitHand.addCard(splitCard);
		this.getHands().add(splitHand);
		
		//Send message to server saying that a hand has been split
		//TODO server interface
		
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

	
	public void bet(int betAmount)
	{
		this.currentHand.setCurrentBet(betAmount);
		//TODO server interface
	}
	public void doubleDown()
	{
		this.currentHand.setCurrentBet(this.currentHand.getCurrentBet() * 2);
		//TODO server interface
	}
	
	public void stay()
	{
		//TODO signal the server to signal everyone that its the next players turn or the next hand's turn
		
	}
	public void hit()
	{
		//TODO signal the dealer to deal one card to the players current hand
		//signal to the server to signal to the gamePlay to hit for the currentPlayer
		
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
}



