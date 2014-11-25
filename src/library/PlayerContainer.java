package library;

import java.util.Vector;

public class PlayerContainer extends AbstractBean {
	private Player smallBlindPlayer; 
	private Player bigBlindPlayer; 
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
	}
	
	public void removePlayer(Player player)
	{
		playerContainer.remove(player);
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
	public Player getSmallBlindPlayer() {
		return smallBlindPlayer;
	}
	public void setSmallBlindPlayer(Player smallBlindPlayer) {
		this.smallBlindPlayer = smallBlindPlayer;
	}
	public Player getBigBlindPlayer() {
		return bigBlindPlayer;
	}
	public void setBigBlindPlayer(Player bigBlindPlayer) {
		this.bigBlindPlayer = bigBlindPlayer;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	} 
}
