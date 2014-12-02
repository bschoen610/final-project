package library;

import game.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class ServerGameListener extends AbstractGameListener {
	
	
	
	//will need to add an argument that acts as a reference to the server 
	public ServerGameListener(GamePlay gameState)
	{
		super(gameState);
	}
	

	@Override 
	protected void playerAdded(ListDataEvent e)
	{
		super.playerAdded(e); 
		//server sending message "CheckIn"
		//create checkin message. 
		//send the message with sendAll
		//somebody needs to write a method that runs in the client that listens for this method - the program that runs in the client package 
		//The socket that connects every client to the server - we will be sending messages from the server through that socket to any given client
		//any given client needs to read the message, this will create an object in the client - a balance, a bet, a checkin, basically any data transfer object
		//Once the client has the object and has cast it to the correct type of data transfer (bet, move, etc.) the client will call a method from my library 
		//This will change the state of the game that is being represented in the client. Every client will get the same message and do the same thing
		//every client needs a copy of the state of the game
		//the method in my class will change the state of the game, and will raise an event (firePropertyChange or fireListEventChange)
		//these methods will call methods in ClientGameListener - specific based on what event was raised
		//Client game listener will change the GUI
		//For Example: When the User is allowed to make a move, the GUI will allow the user to click on hit, stay, bet, etc... 
		//The action of this, should be for the client to send a message to the server. 
		//the server needs a thread that's listening to messages for each client - one thread per client 
		//the server thread will decode the message, get an object, understand what the object means, and make the appropriate call to the library package
		//When the library changes the state of the game, it will raise an event (fireProprty change or fireListEventChange)
		//These methods will call methods in serverGameListener - specific based on what event was raised
		//ServerGameLiseter will respond to the changes in state of the game, by sending messages to the client -
		//THESE MESSAGES ARE THE MESSAGES THAT START THIS COMMENT. EXAMPLE "CheckIn". 
		
		
		//TODO THIS IS THE ARCHITECURE FOR EVERYTHING 
		//The client will have to listen to the stream of messages. This will be done in the client package
		//will be a giant if else for any possible message, and this will tell the UI what to do
		//Or, we could turn these messages into method lass in MY library. Then, these methods in the library package would raise events 
		//that will be handled by the client game listener. The client game listener is going to make calls on the UI (The GUI). 
		//The client game listener will need knowledge of GUI so it can update specifics J components. 
		
		
	}
	@Override
	protected void playerRemoved(ListDataEvent e)
	{
		super.playerRemoved(e);
		//server sending message "Leave" 
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
	
	
	private void sendToAll(Message m) {
		for (Player p : playerContainer.getPlayerContainer()) {
			ObjectOutputStream oos = null;
			
			try {
				oos = new ObjectOutputStream(p.getSocket().getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				oos.writeObject(m);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				oos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
