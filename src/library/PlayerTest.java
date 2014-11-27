package library;

import static org.junit.Assert.*;
import library.Card.Rank;
import library.Card.Suit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTest {

	private Player player1; 
	private Player player2;
	private GamePlay gamePlay;
	@Before
	public void setUp() throws Exception {
		
		 gamePlay = new GamePlay(); 
		 gamePlay.brandNewGameStarting(2);
		 player1 = gamePlay.getPlayer(0);
		 player2 = gamePlay.getPlayer(1); 
	
	}

	@Test
	public void testSplitHand() {
		Card card1 = new Card(Rank.KING, Suit.CLUB);
		Card card2 = new Card(Rank.KING, Suit.DIAMOND);
		Card card3 = new Card(Rank.THREE, Suit.HEART);	
		
		player1.getCurrentHand().addCard(card1);
		player1.getCurrentHand().addCard(card2);
		
		player1.determineCanSplit();
		//I never want to call setCurrent from a player. Make sure i always do it from gamePlay
		//leaving this line so i remember what to look for after done with unit testing
		player1.setCurrent(true);
		
		player1.splitHand(); 
		
		assertEquals("Player should have two hands", 2, player1.getNumHands());
		assertEquals("Hand 1 should be a king", Rank.KING, player1.getHand(0).getCard(0).getRank());
		assertEquals("Hand 2 should be a king", Rank.KING, player1.getHand(1).getCard(0).getRank());
	
		player1.getHands().remove(0);
		player1.getHand(0).addCard(card3);
		player1.determineCanSplit();
		boolean caughtException = false; 
		try {
			player1.splitHand();
		} catch (UnsupportedOperationException e) {
			caughtException = true; 
		}
		assertEquals("Caught exception should be true, split hand should not be possible", true, caughtException);
		assertEquals("Player one should only have 1 hand", 1, player1.getNumHands());
	}

	@Test
	public void testDetermineCanSplit() {
		Card card1 = new Card(Rank.KING, Suit.CLUB);
		Card card2 = new Card(Rank.KING, Suit.DIAMOND);
		Card card3 = new Card(Rank.THREE, Suit.HEART);	
		
		player1.getCurrentHand().addCard(card1);
		player1.getCurrentHand().addCard(card2);
		
		player1.determineCanSplit();
		assertEquals("Player should be able to split", true, player1.canSplit());
	
		player1.getCurrentHand().addCard(card3); 
		player1.determineCanSplit();
		assertEquals("Player should not be able to split", false, player1.canSplit());

		
	}

	@Test
	public void testDoubleDown() {
		
		player1.getCurrentHand().bet(500);
		//Same thing as above, this should never be called. It should ONLY be called in gamePlay.setCurrentPlayer
		player1.setCurrent(true);
		player1.doubleDown();
		
		assertEquals("Player should have 1000 in his pot", 1000, player1.getCurrentHand().getCurrentBet());
		
		
	
	}

}
