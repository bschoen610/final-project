package library;

public class GameTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GamePlay gamePlay = new GamePlay(); 
		PlayerContainer playerContainer = new PlayerContainer(); 
		Dealer dealer = new Dealer(); 
		DeckOfCards deckOfCards = new DeckOfCards(); 
		
		dealer.setDeck(deckOfCards);
		
		printMessage(deckOfCards.getDeckOfCards().get(0).getRank() + " " + deckOfCards.getDeckOfCards().get(0).getSuit());
		printMessage(deckOfCards.getDeckOfCards().get(1).getRank() + " " + deckOfCards.getDeckOfCards().get(1).getSuit());
		
		
		gamePlay.setDealer(dealer);
		gamePlay.setPlayerContainer(playerContainer);
		
		Player playerA = new Player("Adam");
		Player playerB = new Player("Ben");
		
		gamePlay.addPlayer(playerA);
		gamePlay.addPlayer(playerB);
		
		playerA.bet(100);
		playerB.bet(200);
		
		
		
		
		
		
		
		
		
		
		
		
		
		//create the gamePlay
		//create the dealer
		//create deck of cards
		//add deck of cards to dealer
		//create the player container
		//add dealer and player container to the GamePlay		
		//create the players
		//add the players to the game
		
		//Play a round
		//Players place bets
		//Cards are dealt
		//check for dealer blackjack
		//playerAction around the table
		//dealerAction 
		//Collection And Payout
	
	}
	
	public static void printMessage(String string)
	{
		System.out.println(string);
	}

}
