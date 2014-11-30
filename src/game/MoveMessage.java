package game;

import game.Game;

public class MoveMessage implements Message {
	public final Game.Move move;
	
	public MoveMessage(Game.Move move) {
		this.move = move;
	}
}
