package library;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class GameListener implements PropertyChangeListener, ListDataListener {
	GamePlay gamePlay; 
	PlayerContainer playerContainer; 
	Vector<Player> playerList = new Vector<Player>(); 
	
	
	//will need to add an argument that acts as a reference to the server 
	public GameListener(GamePlay gameState)
	{
		this.gamePlay = gameState; 
		this.gamePlay.addPropertyChangeListener(this);
		this.gamePlay.addListDataChangeListener(this);
		
		playerContainer = this.gamePlay.getPlayerContainer();
		playerContainer.addListDataChangeListener(this);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Object source = evt.getSource(); 
		//true if source is coming from player
		if(source instanceof Player){
			Player tempPlayer = (Player)source;
			//method for dealing with propertyChanges in players
			playerPropertyChange(evt, tempPlayer);
		}
		else if(source instanceof Hand){
			Hand tempHand = (Hand)source; 
			handPropertyChange(evt, tempHand);
		}
		else if(source == this.gamePlay.getDealer()){
			
		}
		
		//TODO add all of the possible else if's for Class's that could fire property changes
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		Object source = e.getSource(); 
		if(source == playerContainer){
			//method for handling adding player to playerContainer
			playerAdded(e);
		}
		
		//TODO add all of the possible else if's for class's that could fire intervalAdded changes
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		Object source = e.getSource();
		if(source == playerContainer){
			playerRemoved(e);
		}
		
		//TODO add all of the possible else if's for class's that could fire intervalRemoved changes
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
			//May not ever raise this event
		
	}
	
	private void playerAdded(ListDataEvent e)
	{
		
		//whenever a player is added you have to add this gameListener object as a listener to events that a player could raise
		for(int i = e.getIndex0(); i <= e.getIndex1(); i++)
		{
			//would send a check in message for every new player, potentially more than one at a time
			Player tempPlayer = playerContainer.getPlayer(i); 
			//tempPlayer.addListDataChangeListener(this);
			tempPlayer.addPropertyChangeListener(this);
			playerList.add(tempPlayer);
			
		}
	}
	
	private void playerRemoved(ListDataEvent e)
	{
		//would send a leave message for players leaving
		for(int i = e.getIndex0(); i <= e.getIndex1(); i++)
		{
			Player tempPlayer = playerContainer.getPlayer(i); 
			//tempPlayer.removeListDataChangeListener(this);
			tempPlayer.removePropertyChangeListener(this);
			playerList.remove(tempPlayer);
			
		}
	}
	
	private void playerPropertyChange(PropertyChangeEvent pce, Player player)
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

	private void handPropertyChange(PropertyChangeEvent pce, Hand hand)
	{
		
	}
	private void handCardAdded(ListDataEvent e, Hand hand)
	{
		
	}
	private void handCardRemoved(ListDataEvent e, Hand hand)
	{
		
	}
	
	//TODO add all removed and added methods for classes that can fire listEventChanges. weve already done playerContainer and Hand 
	//Basically any class that calls fireIntervalAdded or fireIntervalRemoved needs to be included here and in the intervalAdded and intervalRemoved cases (their own else if cases)
	

}
