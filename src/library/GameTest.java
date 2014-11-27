package library;

import java.util.Map;
import java.util.TreeMap;

import library.GamePlay.StateOfRound;

public class GameTest {

	public static void main(String[] args) {
		
		GamePlay gamePlay = new GamePlay(); 
		gamePlay.brandNewGameStarting(2);
		Dealer dealer = gamePlay.getDealer(); 
		DeckOfCards deckOfCards = gamePlay.getDealer().getDeck();
		PlayerContainer playerContainer = gamePlay.getPlayerContainer();
		
		
		gamePlay.getPlayer(0).setUserName("Ben");
		gamePlay.getPlayer(1).setUserName("Adam");
		
		Player player1 = gamePlay.getPlayer(0);
		Player player2 = gamePlay.getPlayer(1);
				
		
		TreeMap<Card, Integer> deckTest = new TreeMap<Card, Integer>(); 
		
		for(int i = 0; i < 260; i++)
		{
			Card tempCard = deckOfCards.removeTopCard(); 
			if (deckTest.containsKey(tempCard)){
				deckTest.put(tempCard, deckTest.get(tempCard) + 1);
			}else{
				deckTest.put(tempCard, 1);
			}
		}
		for(Map.Entry<Card, Integer> card: deckTest.entrySet())
		{
			if(card.getValue() != 5){
				printMessage("Error on " + card.getKey());
			}
		}
		deckTest.clear(); 
		dealer.evaluateDeck();
		deckOfCards = dealer.getDeck(); 
		
		try {
			//player1.bet(100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		//gamePlay.setCurrentState(StateOfRound.next(gamePlay.getCurrentState()));
		
		try{
		//player2.bet(100);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		
		printMessage(deckOfCards.getCard(0).getRank() + " " + deckOfCards.getCard(0).getSuit());
		printMessage(deckOfCards.getCard(1).getRank() + " " + deckOfCards.getCard(1).getSuit());
		
		dealer.dealToTable();
		dealer.dealToDealer();
		printPlayerHand(gamePlay, 0, 0);
		printPlayerHand(gamePlay, 1, 0);
		printDealerHand(gamePlay);
		
		
		
		
		
		
		
		
		
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
	public static void printPlayerHand(GamePlay gamePlay, int playerIndex, int handIndex)
	{
		Hand playerHand = gamePlay.getPlayer(playerIndex).getHand(0);
		Player tempPlayer = gamePlay.getPlayer(playerIndex);
		for(int i = 0; i < playerHand.getNumCards(); i++)
		{
			System.out.println(tempPlayer.getUserName() + " " + playerHand.getCard(i).getRank() + " " + playerHand.getCard(i).getSuit());
		}
	}
	public static void printDealerHand(GamePlay gamePlay)
	{
		Dealer tempDealer = gamePlay.getDealer(); 
		Hand dealerHand = tempDealer.getDealerHand();
		for(int i = 0; i < dealerHand.getNumCards(); i++)
		{
			System.out.println("Dealer " + dealerHand.getCard(i).getRank()+ " " + dealerHand.getCard(i).getSuit());
		}
	}

}
