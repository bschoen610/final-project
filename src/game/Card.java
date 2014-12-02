package game;

public class Card {
	int suit;
	int face;
	public String imagePath;
	
	public Card(int suit, int face) {
		this.suit = suit;
		this.face = face;
		imagePath = getImagePath(face, suit);
	}	
	
	private String getImagePath(int face, int suit) {
		// look at Ben's Card.java to see how I was going to assign each Card the correct image
		return "";
		
		/*
		if(rank == 11)){
			fullRank = "jack";
		}
		else if(rank == 12){
			fullRank = "queen";
		}
		else if(rank == 13){
			fullRank = "king";
		}
		else if(rank == 14){
			fullRank = "ace";
		}
		
		if(suit.equals('C')){
			fullSuit = "clubs";
		}
		else if(suit.equals('D')){
			fullSuit = "diamonds";
		}
		else if(suit.equals('H')){
			fullSuit = "hearts";
		}
		else if(suit.equals('S')){
			fullSuit = "spades";
		}
		if(fullRank.equals("king") || fullRank.equals("queen") || fullRank.equals("jack")){
			// do this to get the cooler version of these cards
            return fullRank + "_of_" + fullSuit + "2.png";
		}
		else{
            return fullRank + "_of_" + fullSuit + ".png";
		}
		*/
	}
}