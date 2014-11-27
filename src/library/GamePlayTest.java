package library;

import static org.junit.Assert.*;
import library.Card.Rank;
import library.Card.Suit;
import library.GamePlay.StateOfRound;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GamePlayTest {
	private GamePlay gamePlay; 
	private StateOfRound currentState; 
	private PlayerContainer playerContainer; 
	private Dealer dealer; 
	private Player player1;
	private Player player2; 

	@Before
	public void setUp() throws Exception {
		gamePlay = new GamePlay(); 
		currentState = StateOfRound.BETTING;
		playerContainer = new PlayerContainer(); 
		dealer = new Dealer(); 
		gamePlay.setDealer(dealer);
		gamePlay.setPlayerContainer(playerContainer);
		gamePlay.setCurrentState(currentState);
		player1 = new Player();
		player2 = new Player(); 
	
	}

	@Test
	public void testAddPlayer() {
		gamePlay.addPlayer(player1);
		
		assertEquals("Game should have 1 player", 1, gamePlay.getPlayerContainer().getNumPlayers());
		gamePlay.addPlayer(player2);
		
		assertEquals("Game should have 2 players", 2, gamePlay.getPlayerContainer().getNumPlayers());
	}

	@Test
	public void testNextState() {
		
		gamePlay.nextState(); 
		assertEquals("Game should be in Player Action state", StateOfRound.PLAYER_ACTION, gamePlay.getCurrentState());
		gamePlay.nextState();
		gamePlay.nextState(); 
		assertEquals("Game should be in Collection state", StateOfRound.COLLECTION, gamePlay.getCurrentState());
		gamePlay.nextState(); 
		assertEquals("Game should be in Betting state", StateOfRound.BETTING, gamePlay.getCurrentState());
	
	}

	@Test
	public void testSetCurrentPlayer() {
		//bad practice to use a method thats being tested above, but i already tested that it works, so whatever
		gamePlay.addPlayer(player1);
		gamePlay.addPlayer(player2);
		gamePlay.setCurrentPlayer(player1);
		assertEquals("Player 1 should be the currentPlayer", player1, gamePlay.getCurrentPlayer());
		gamePlay.setCurrentPlayer(player2);
		assertEquals("Player 2 should be the currentPlayer", player2, gamePlay.getDealer().getCurrentPlayer());
		assertEquals("Player 2 should be the current Player", player2, gamePlay.getPlayerContainer().getCurrentPlayer());
		assertEquals("player 1 should not be the current Player", false, player1.isCurrent()); 
	
	}

	@Test
	public void testNextCurrentPlayer() {
		gamePlay.addPlayer(player1);
		gamePlay.addPlayer(player2);
		gamePlay.setCurrentPlayer(player1);
		gamePlay.nextCurrentPlayer();
		assertEquals("Player 2 should be the current Player", player2, gamePlay.getCurrentPlayer());
		gamePlay.nextCurrentPlayer();
		assertEquals("Player 1 should be the current Player", player1, gamePlay.getCurrentPlayer());
		
	}

	@Test
	public void testDetermineWinnersAndLosers() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public void testDealerBustPlayerOutcome(){
		gamePlay.addPlayer(player1);
		gamePlay.addPlayer(player2);
		dealer.getDealerHand().setBusted(true);
		player1.setChipCount(100);
		player1.getHand(0).setCurrentBet(50);
		player2.setChipCount(100);
		player2.getHand(0).setCurrentBet(50);
		player1.getHand(0).setBusted(true);
		player2.getHand(0).setBusted(false);
		gamePlay.dealerBustPlayerOutcome();
		assertEquals("Player 1 should not win money", 50, player1.getChipCount());
		assertEquals("Player 2 should have won money", 150, player2.getChipCount());
		
		
		
	}
	
	@Test
	public void testDealerNoBustPlayerOutcome(){
		gamePlay.addPlayer(player1);
		gamePlay.addPlayer(player2);
		Card card1 = new Card(Rank.KING, Suit.CLUB);
		Card card2 = new Card(Rank.KING, Suit.DIAMOND);
		Card card3 = new Card(Rank.THREE, Suit.HEART);
		Card card4 = new Card(Rank.NINE, Suit.DIAMOND);
		gamePlay.setCurrentPlayer(player1);
		player1.getHand(0).addCard(card1);
		player1.getHand(0).addCard(card2);
		player1.splitHand();
		player1.getHand(0).addCard(card3);
		player1.getHand(0).addCard(card4);
		player1.getHand(1).addCard(card2);
		dealer.getDealerHand().addCard(card1);
		
		player1.setChipCount(100);
		player1.setCurrentHand(player1.getHand(0));
		player1.getCurrentHand().bet(50);
		player1.setCurrentHand(player1.getHand(1)); 
		player1.getCurrentHand().bet(50);
		
		gamePlay.dealerNoBustPlayerOutcome();
		assertEquals("Player 1 should have 100 dollars ", 100, player1.getChipCount());
		
		
	}

	@Test
	public void testRoundIsOver() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testBrandNewGameStarting() {
		fail("Not yet implemented"); // TODO
	}

}
