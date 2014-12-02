package library;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public abstract class AbstractGameListener implements PropertyChangeListener, ListDataListener {
	protected GamePlay gamePlay; 
	protected PlayerContainer playerContainer; 
	protected Vector<Player> playerList = new Vector<Player>(); 
	
	
	//will need to add an argument that acts as a reference to the server 
	public AbstractGameListener(GamePlay gameState)
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
			Player tempPlayer =   (Player)source;
			//method for dealing with propertyChanges in players
			playerPropertyChange(evt, tempPlayer);
		}
		else if(source instanceof Hand){
			Hand tempHand = (Hand)source; 
			handPropertyChange(evt, tempHand);
		}
		else if(source == this.gamePlay.getDealer()){
			Dealer tempDealer = (Dealer)source; 
			dealerPropertyChange(evt, tempDealer);
		}
		else if(source == this.gamePlay){
			GamePlay tempGamePlay = (GamePlay)source; 
			gamePlayPropertyChange(evt, tempGamePlay);
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
	
	//ListData Events
	protected void playerAdded(ListDataEvent e)
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
		
		//This might tell the UI to show a player
		
	}
	
	protected void playerRemoved(ListDataEvent e)
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

	protected abstract void handCardAdded(ListDataEvent e, Hand hand);

	protected abstract void handCardRemoved(ListDataEvent e, Hand hand);

	//Property Change Events
	
	protected abstract void dealerPropertyChange(PropertyChangeEvent evt, Dealer tempDealer);
		
	protected abstract void deckPropertyChange(PropertyChangeEvent evt, DeckOfCards tempDeck);
	
	protected abstract void gamePlayPropertyChange(PropertyChangeEvent evt, GamePlay tempGamePlay); 
	
	protected abstract void playerPropertyChange(PropertyChangeEvent pce, Player player);

	protected abstract void handPropertyChange(PropertyChangeEvent pce, Hand hand);
	
	

}
