package library;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class ClientGameListener extends AbstractGameListener {
	
	
	
	//will need to add an argument that acts as a reference to the server 
	public ClientGameListener(GamePlay gameState)
	{
		super(gameState);
	}
	
	@Override 
	protected void playerAdded(ListDataEvent e)
	{
		super.playerAdded(e); 
		//client showing new player
	}
	@Override
	protected void playerRemoved(ListDataEvent e)
	{
		super.playerRemoved(e);
		//client showing player removed
	}
	
	protected void playerPropertyChange(PropertyChangeEvent pce, Player player)
	{
		//This would make decisions based on the properties that player could change
		//basically a big if then else that compares name of pce.getPropertyName() and decides what to do for each
		String propertyName = pce.getPropertyName();
		
		if(propertyName.equals("numHands")){
			if((int)pce.getNewValue() > (int)pce.getOldValue()){
				Hand newHand = player.getHand(player.getNumHands() - 1);
				newHand.addPropertyChangeListener(this);
				newHand.addListDataChangeListener(this);
			}
		}
		
		//TODO add all of the else if's for types of property changes a player can fire
		
	}
	
	//TODO add propertyChange methods for all classes that could fire propertyCahgnes
	//these methods would be called in the propertyCahnge method under the else if for their specific class 
	//TODO gameplay itself raises some events, so need to add methods for that - gamePlayPropertyChange for instance
	//basically any class that calls firePropertyChange needs to be included here and in the propertyChange method of this class (it's own else if case)

	protected void handPropertyChange(PropertyChangeEvent pce, Hand hand)
	{
		
	}
	protected void handCardAdded(ListDataEvent e, Hand hand)
	{
		
	}
	protected void handCardRemoved(ListDataEvent e, Hand hand)
	{
		
	}
	
	protected void dealerPropertyChange(PropertyChangeEvent evt, Dealer tempDealer) {
		String propertyName = evt.getPropertyName();
		if(propertyName.equals("deck")){
			//This may not be necessary TODO 
			DeckOfCards newDeck = gamePlay.getDealer().getDeck();
			newDeck.addPropertyChangeListener(this);
			newDeck.addListDataChangeListener(this);
		}
		//Not sure if any of these are equal
		if(propertyName.equals("currentPlayer")){
			
		}
		if(propertyName.equals("playerContainer")){
			
		}
	}
	
	protected void deckPropertyChange(PropertyChangeEvent evt, DeckOfCards tempDeck)
	{
		String propertyName = evt.getPropertyName();
		if(propertyName.equals("numCards")){
			//
			
		}else{
			throw new IllegalArgumentException("Unexcepted firing of property change " + propertyName); 
		}
	}
	protected void gamePlayPropertyChange(PropertyChangeEvent evt, GamePlay tempGamePlay) 
	{
		String propertyName = evt.getPropertyName(); 
		//if else cases for gamePlay property changes 
	}
	
	//TODO add all removed and added methods for classes that can fire listEventChanges. weve already done playerContainer and Hand 
	//Basically any class that calls fireIntervalAdded or fireIntervalRemoved needs to be included here and in the intervalAdded and intervalRemoved cases (their own else if cases)
	

}
