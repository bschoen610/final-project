package library;

import java.util.Vector;

public class PlayerContainer extends AbstractBean implements java.io.Serializable {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 661069278365290816L;

	private Player currentPlayer;
	
	private Vector<Player> playerContainer; 	

	public PlayerContainer()
	{
		playerContainer = new Vector<Player>();
		//how we initialize the players into the game will determine how we fill this vector
		//we need to make sure order of players entered into the vector is the order of players sitting left to right
	}
	
	public void addPlayer(Player player)
	{
		playerContainer.add(player);
		//numPlayers - 1 is the greatest index of the list
		this.getListDataChangeSupport().fireIntervalAdded(this.getNumPlayers() - 1);
	}
	//May need a function that adds all players at once
	
	public void removePlayer(Player player)
	{
		playerContainer.remove(player);
		this.getListDataChangeSupport().fireIntervalRemoved(this.getNumPlayers() - 1);
	}
	
	//may need an insertPlayer at index function
	
	//nextPlayer will be used to circle through players during gamePlay and through player list for big and small blinds
	public Player nextPlayer(Player currentPlayer)
	{
		int currentIndex = playerContainer.indexOf(currentPlayer);
		if (currentIndex < playerContainer.size() - 1){
			return playerContainer.get(currentIndex + 1);
		}
		else{
			return playerContainer.get(0); 
		}
	
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	} 
	
	public Vector<Player> getPlayerContainer() {
		return playerContainer;
	}

	public void setPlayerContainer(Vector<Player> playerContainer) {
		this.playerContainer = playerContainer;
	}
	
	public Player getPlayer(int playerIndex)
	{
		return this.getPlayerContainer().get(playerIndex);
	}
	
	public int getNumPlayers()
	{
		return this.getPlayerContainer().size(); 
	}
}
