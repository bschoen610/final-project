package library;

public abstract class AbstractPlayer extends AbstractBean {
	private GamePlay gamePlay; 
	
	public GamePlay getGamePlay() {
		return gamePlay;
	}
	public void setGamePlay(GamePlay gamePlay) {
		this.gamePlay = gamePlay;
	}
	
	//TODO add common functions between dealer and player to abstractPlayer
	//After Bean
}
