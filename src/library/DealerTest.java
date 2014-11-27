package library;

import static org.junit.Assert.*;
import library.Card.Rank;
import library.Card.Suit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DealerTest {
	
	private Dealer dealer;
	private Player player1; 
	private Player player2;

	@Before
	public void setUp() throws Exception {
		dealer = new Dealer(); 
		player1 = new Player(); 
		player2 = new Player(); 
		dealer.setCurrentPlayer(player1);
	}

	@Test
	public void testDealToPlayer() {
		DeckOfCards deck = dealer.getDeck();
		Card tempCard = deck.getCard(0);
		dealer.dealToPlayer();
		
		assertEquals("Wrong Card", tempCard, player1.getCurrentHand().getCard(0));

		
	}

	@Test
	public void testDealOneCardToDealer() {
		DeckOfCards deck = dealer.getDeck();
		Card tempCard = deck.getCard(0);
		dealer.dealOneCardToDealer(true);
		
		assertEquals("Wrong Card", tempCard, dealer.getDealerHand().getCard(0));
	}

	@Test
	public void testAfterPlayerActionDealerAction() {
		DeckOfCards deck = dealer.getDeck();
		Card card1 = new Card(Rank.KING, Suit.CLUB);
		Card card2 = new Card(Rank.KING, Suit.DIAMOND);
		Card card3 = new Card(Rank.THREE, Suit.HEART);
		
		dealer.getDealerHand().addCard(card1);
		dealer.getDealerHand().addCard(card2);
		dealer.afterPlayerActionDealerAction(); 
		
		assertEquals("Wrong number of cards in dealer's hand", 2, dealer.getDealerHand().getNumCards());
		assertEquals("Incorrect Bust", false, dealer.getDealerHand().isBusted());
		
		dealer.getDealerHand().addCard(card3);
		System.out.println(dealer.getDealerHand().getLowestValue());
		
		assertEquals("Wrong number of cards in dealer's hand", 3, dealer.getDealerHand().getNumCards());
		assertEquals("Should have Busted", true, dealer.getDealerHand().isBusted());
		
	}

	@Test
	public void testDealToTable() {
		PlayerContainer playerContainer = new PlayerContainer(); 
		playerContainer.addPlayer(player1);
		playerContainer.addPlayer(player2);
		dealer.setPlayers(playerContainer);
		
		dealer.dealToTable();
		
		assertEquals("Player does not have two cards", 2, player1.getCurrentHand().getNumCards());
		assertEquals("Player does not have two cards", 2, player2.getCurrentHand().getNumCards());		
	}

	@Test
	public void testDealToDealer() {
		dealer.dealToDealer();
		assertEquals("Dealer does not have two cards", 2, dealer.getDealerHand().getNumCards());
	}

	@Test
	public void testEvaluateDeck() {
		dealer.dealOneCardToDealer(true);
		dealer.evaluateDeck();
		assertEquals("Should not have reconstructed deck", 259, dealer.getDeck().getNumCards());
		for(int i = 0; i < 250; i++)
		{
			dealer.dealOneCardToDealer(true);
		}
		dealer.evaluateDeck(); 
		assertEquals("Should have reconstructed deck", 260, dealer.getDeck().getNumCards());
	
	}

}
