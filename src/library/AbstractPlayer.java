package library;

public abstract class AbstractPlayer extends AbstractBean {
	private GamePlay gamePlay; 
	
	public GamePlay getGamePlay() {
		return gamePlay;
	}
	public void setGamePlay(GamePlay gamePlay) {
		this.gamePlay = gamePlay;
	}
}
