package library;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
		dealer.getDealerHand().addCard(card4);
		gamePlay.setCurrentPlayer(player2);
		player2.getHand(0).addCard(card1);
		player2.getHand(0).addCard(card4);
		
		player2.setChipCount(100);
		player2.setCurrentHand(player2.getHand(0));
		player2.getCurrentHand().bet(50);
		player1.setChipCount(100);
		player1.setCurrentHand(player1.getHand(0));
		player1.getCurrentHand().bet(50);
		player1.setCurrentHand(player1.getHand(1)); 
		player1.getCurrentHand().bet(50);
		
		gamePlay.determineWinnersAndLosers();
		assertEquals("Player 1 should have 100 dollars ", 100, player1.getChipCount());
		assertEquals("Player 2 should have 100 dollars, he should push", 100, player2.getChipCount());
		
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
		dealer.getDealerHand().addCard(card4);
		gamePlay.setCurrentPlayer(player2);
		player2.getHand(0).addCard(card1);
		player2.getHand(0).addCard(card4);
		
		player2.setChipCount(100);
		player2.setCurrentHand(player2.getHand(0));
		player2.getCurrentHand().bet(50);
		player1.setChipCount(100);
		player1.setCurrentHand(player1.getHand(0));
		player1.getCurrentHand().bet(50);
		player1.setCurrentHand(player1.getHand(1)); 
		player1.getCurrentHand().bet(50);
		
		gamePlay.determineWinnersAndLosers();
		assertEquals("Player 1 should have 100 dollars ", 100, player1.getChipCount());
		assertEquals("Player 2 should have 100 dollars, he should push", 100, player2.getChipCount());
		
		gamePlay.roundIsOver();
		
		assertEquals("Player1 should have no hands", 0, player1.getNumHands());
		assertEquals("Dealer should not have a hand", null, dealer.getDealerHand());
		assertEquals("There should be no current Player", null,gamePlay.getCurrentPlayer());
		
	}

	@Test
	public void testBrandNewGameStarting() {
		gamePlay.brandNewGameStarting(2); 
		
		assertNotNull("Dealer player container is wrongly null", gamePlay.getDealer().getPlayers());
		assertNotNull("Gameplay player container is wrongly null", gamePlay.getPlayerContainer() );
		assertEquals("Dealer and GamePlay should have the same Player Container", true, gamePlay.getDealer().getPlayers() == gamePlay.getPlayerContainer() );
		assertEquals("Player Containers should have 2 player", 2, gamePlay.getPlayerContainer().getNumPlayers());
	}
	
	@Test
	public void testSerializedGamePlay(){
		gamePlay.brandNewGameStarting(2);
		gamePlay.getPlayer(0).setChipCount(1000);
		gamePlay.setCurrentPlayer(gamePlay.getPlayer(0));
		gamePlay.getDealer().dealToPlayer();
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(); 
		try {
			ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
			objectStream.writeObject(gamePlay);
			objectStream.close();
			byte[] byteArray = byteStream.toByteArray();
			byteStream.close(); 
			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
			ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
			try {
				GamePlay gamePlay2 = (GamePlay)objectInputStream.readObject();
				assertEquals("The gameplays should have the same numPlayers", gamePlay.getPlayerContainer().getNumPlayers(), gamePlay2.getPlayerContainer().getNumPlayers());
				assertEquals("The gamePlays player 1's should have the same card", gamePlay.getPlayer(0).getHand(0).getCard(0), gamePlay2.getPlayer(0).getHand(0).getCard(0));
				assertEquals("The gamePlays player 1's should have the same chip count", gamePlay.getPlayer(0).getChipCount(), gamePlay2.getPlayer(0).getChipCount());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
