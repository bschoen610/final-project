package library;

import java.util.Vector;

public class Player extends AbstractBean {
	private Card card1; 
	private Card card2; 
	private Vector<Card> hand;
	private int chipCount; 
	private boolean canCheck; 
	private boolean canBet; 
	private boolean canRaise; 
	private boolean canCall; 
	

	public Player()
	{
		
	}

	public boolean getCanCheck() {
		return canCheck;
	}
	
	public void setCanCheck(boolean canCheck) {
		this.canCheck = canCheck;
	}

	public boolean getCanBet() {
		return canBet;
	}

	public void setCanBet(boolean canBet) {
		this.canBet = canBet;
	}

	public boolean getCanRaise() {
		return canRaise;
	}

	public void setCanRaise(boolean canRaise) {
		this.canRaise = canRaise;
	}

	public boolean getCanCall() {
		return canCall;
	}

	public void setCanCall(boolean canCall) {
		this.canCall = canCall;
	}
	
	public Vector<Card> getHand() {
		return hand;
	}

	public void setHand(Vector<Card> hand) {
		this.hand = hand;
	} 
	
	public void addToHand(Card card)
	{
		if (hand.size() >= 2){
			return; 
		}
		else{
			hand.add(card);
		}
	}


	public int getChipCount() {
		return chipCount;
	}

	public void setChipCount(int chipCount) {
		this.chipCount = chipCount;
	}
	
	public void check()
	{
		
	}
	public void fold()
	{
		
	}
	public void bet(int amount)
	{
	
	}
	public void call(int amount)
	{
		
	}
	public void raise(int amount)
	{
		
	}

	public Card getCard1() {
		return card1;
	}


	public void setCard1(Card card1) {
		this.card1 = card1;
	}


	public Card getCard2() {
		return card2;
	}


	public void setCard2(Card card2) {
		this.card2 = card2;
	}
}
