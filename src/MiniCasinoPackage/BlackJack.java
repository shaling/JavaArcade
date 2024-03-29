package MiniCasinoPackage;
import java.util.ArrayList;
import java.util.Random;

import MiniCasinoPackage.BlackJack.Card;


/** This class holds all of the basic blackjack logic 
 * 
 */

public class BlackJack {
	
	private ArrayList<Card> deck;
	
	/**Player*/
	
	protected ArrayList<Card> playerHand;
	//Field holds player's score; sum of all card values
	private int playerScore;
	//Field holds player's ace count; important for game logic
	private int playerAceCount;

	/**Dealer*/
	
	protected ArrayList<Card> dealerHand;
	//Field holds dealer's score; sum of all card values
	private int dealerScore;
	//Field holds dealer's ace count; important for game logic
	private int dealerAceCount;

	
	public BlackJack () {
	
		newHand();
	
	}

//function begins game by creating deck and handling first deals
	public void newHand () {
	
		if (MiniCasino.getBlackjackGameCount() > 1) {
		
			playerHand.clear();
			dealerHand.clear();
		
		}
	
    playerScore = 0;
    dealerScore = 0;
    playerAceCount = 0;
    dealerAceCount = 0;
	
	//Calling method that builds (or rebuilds) the deck
	deck = createDeck();
	
	//Calling method that shuffles deck randomly
	deck = shuffleDeck(deck);

	//Deals out player's hand
	playerHand = dealPlayerHand();

	//Deals out dealer's hand
	dealerHand = dealDealerHand();

}


	//function handles creating the deck
	public ArrayList<Card> createDeck () {
	
		ArrayList<Card> deck = new ArrayList<>();
	
		String [] values = {"A", "2", "3", "4","5","6","7","8","9","10","J","Q","K"};
		String [] types = {"H","S","C","D"};
	
		for (int i = 0; i < values.length; i++) {
		
			for ( int j = 0; j< types.length; j++ ) {
			
				deck.add( new Card (values[i] ,types[j]) );
				
			}
		
		}
	
		return deck;

	}


	//Method randomly shuffles deck by swapping cards
	
	public ArrayList<Card> shuffleDeck(ArrayList<Card> deck) {
   
		Random random = new Random();
	
		int n = deck.size();
    
		for (int i = n - 1; i > 0; i--) {
       
			int j = random.nextInt(i + 1);
    	
			// Swap deck[i] with deck[j]
			Card temp = deck.get(i);
			deck.set(i, deck.get(j));
			deck.set(j, temp);
        
		}
    
    return deck;
	
	}


	//Method returns player's first hand
	public ArrayList<Card> dealPlayerHand() {
	
		ArrayList<Card> hand = new ArrayList<>();
		
		Card card = deck.remove( deck.size() - 1) ;
		playerScore+= card.getVal();
		
		if ( card.isAce() ) {
			
			playerAceCount++;
		}
		
		hand.add( card );
		
		card = deck.remove( deck.size() - 1) ;
		playerScore+= card.getVal();
		
		if ( card.isAce() ) {
			
			playerAceCount++;
			
		}
		
		hand.add( card );
		
		return hand;
		
		}

	//Method returns dealer's first hand
	public ArrayList<Card> dealDealerHand() {
		
		ArrayList<Card> hand = new ArrayList<>();
		
		Card card = deck.remove( deck.size() - 1);
		dealerScore+= card.getVal();
		
		if ( card.isAce() ) {
			
			dealerAceCount++;
		}
		
		
		hand.add( card );
		
		card = deck.remove( deck.size() - 1) ;
		dealerScore+= card.getVal();
		
		if ( card.isAce() ) {
			
			dealerAceCount++;
		}
		
		hand.add( card );
		
		return hand;

	}
	
	//Void method adds card from deck to players hand
	public void playerHit () {
		
		Card card = deck.remove(deck.size() - 1);
		playerScore+= card.getVal();
		
		if ( card.isAce() ) {
			
			playerAceCount++;
			
		}
		
		playerHand.add(card);
		
	}
	
	//Void method adds card from deck to dealers hand
	public void dealerHit () {
		
		Card card = deck.remove(deck.size() - 1);
		dealerScore+= card.getVal();
		
		if ( card.isAce() ) {
			
			dealerAceCount++;
			
		}
		
		dealerHand.add(card);
		
	}
	
	//method reduces score in accordance to when aces put player's score over 21
	public int playerAceAdjustment () {
	
	
		while (playerScore > 21 && playerAceCount > 0 ) {
		
			playerScore-=10;
			playerAceCount--;
		
		}
	
	return playerScore;
	
	}


	//method reduces score in accordance to when aces put dealer's score over 21
	public int dealerAceAdjustment () {
	
	while (dealerScore > 21 && dealerAceCount > 0 ) {
		
		dealerScore-=10;
		dealerAceCount--;
		
	}
	
	return dealerScore;
	
}

	public boolean playerHasBlackJack() {
	
		
		if ( playerAceAdjustment() == 21 && playerHand.size() == 2 ) {
		
		return true;
		
	}
	
		return false;
		
	}
	

	
	public boolean dealerHasBlackJack() {
	
		if ( dealerAceAdjustment() == 21 && dealerHand.size() == 2 ) {
		
			return true;
		}
	
		return false;
		
	}



	//determines winner; 1 is player win, 2 is dealer win, 3 is tie
	public int determineWinner () {
	
	/**The three outcomes of either the player or dealer having blackjack */
	
		if (!playerHasBlackJack() && dealerHasBlackJack() ) {
		
			return 2;
		
		}
	
		
		else if ( playerHasBlackJack() && !dealerHasBlackJack() ) {
		
			return 1;
		
		}
	
		else if ( playerHasBlackJack() && dealerHasBlackJack() ) {
		
			return 3;
		
		}
	/***********************************************************************/
			
	
	//Other conditions
	
	if (playerAceAdjustment() <=21 && playerAceAdjustment() > dealerAceAdjustment() ) {
		
		return 1;
	}
	
	else if (playerAceAdjustment() <=21 && dealerAceAdjustment() > 21) {
		
		return 1;
	}
	
	else if (dealerAceAdjustment() <= 21 && dealerAceAdjustment() > playerAceAdjustment() ) {
		
		return 2;
	}
	
	else if (dealerAceAdjustment() <= 21 && playerAceAdjustment() > 21 ) {
		
		return 2;
	}
	
	else if (dealerAceAdjustment() == playerAceAdjustment() && playerAceAdjustment()<=21) {
		
		return 3;
	}
	
	else if (dealerAceAdjustment() > 21 && playerAceAdjustment() > 21 ) {
		
		return 3;
	}
	
	return 3;
	
}

	
//Card class, which holds card type and value
	class Card {

		
		private String value;
		private String type;


		//Card constructor to initialize card with value and type
		public Card (String value, String type ) {
	
			this.value = value;
			this.type = type;
	
		}

		//method returns image path for drawing in GUI
		public String imagePath () {
	
			String str = "file:lib/cards/" + this.value + "-" + this.type + ".png";
			return str;
		
		}

		//Method determines whether the calling card is an ace or not
		public boolean isAce (){
	
			if (this.value.compareTo("A") == 0 ) {
		
				return true;
		
			}
	
			return false;
	
		}

	
		
		public int getVal () {
	
			if (this.value.equals("A") ) {
		
				return 11;
			
			}
	
			if (this.value.equals("J") || this.value.equals("Q") || this.value.equals("K") ) {
		
				return 10;
		
			}
	
		
			else {
		
				return Integer.parseInt( this.value );
	
			
			}
	
		}

	}

}
