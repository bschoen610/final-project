package library;

public class GamePlay extends AbstractBean {
	private int amountInPot;
	private StateOfRound currentState; 
	
	public enum StateOfRound{
		PRE_FLOP(0, 'P'),
		FLOP(1, 'F'),
		TURN(2, 'T'), 
		RIVER(3, 'R');
		
		private final int stateValue; 
		private final Character stateSymbol; 
		
		@SuppressWarnings("unused")
		private static StateOfRound[] stateArray = {PRE_FLOP, FLOP, TURN, RIVER};  
		
		private StateOfRound(int stateValue, Character stateSymbol)
		{
			this.stateValue = stateValue; 
			this.stateSymbol = stateSymbol; 
		}

		public int getStateValue() {
			return stateValue;
		}

		public Character getStateSymbol() {
			return stateSymbol;
		}
		
		public StateOfRound next(StateOfRound currentState)
		{
			return values()[currentState.getStateValue() + 1];
		}
		
		
	}

	public int getAmountInPot() {
		return amountInPot;
	}

	public void setAmountInPot(int amountInPot) {
		this.amountInPot = amountInPot;
	}
	
	public StateOfRound getCurrentState() {
		return currentState;
	}

	public void setCurrentState(StateOfRound currentState) {
		this.currentState = currentState;
	}
	
	public void nextState(){
		//Tricking java is fun
		StateOfRound oldState = this.currentState;
		this.currentState = this.currentState.next(currentState);
		this.getPcs().firePropertyChange("currentState", oldState, this.getCurrentState());
	}

	
}
