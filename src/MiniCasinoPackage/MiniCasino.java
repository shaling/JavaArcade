package MiniCasinoPackage;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.geometry.Insets;


public class MiniCasino extends Application {
	
	
	private Stage primaryStage;
	private Scene mainMenuScene;
	private Scene blackJackScene;
	private GridPane blackjackMainRoot;
	private GridPane blackjackGameRoot;
	private HBox dealerPane;
	private HBox playerPane;
	private VBox hitStayButtonVBox;
	private VBox newHandAndExitButtons;
	private Button startButton;
	private Button hitButton;
	private Button stayButton;
	private Button newHandButton;
	private Button exitGameButton;
	private Button betSubmitButton;
	private Button restartButton;
	private TextField betTextField;
	private Label enterBetLabel;
	private Label resultLabel;
	private Label currentBetLabel;
    private Label currentBalanceLabel;
    private Label gameCountLabel;
    private Label endOfGameLabel;
	private static int blackjackHandCount = 1;
	private double blackjackCurrentBalance = 10;
	private double blackjackCurrentBet;
	private BlackJack blackjack;
	
	
	public static void main(String[] args) {
        
        launch(args);
        
    }  
	
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		
		GridPane mainMenuRoot = new GridPane();
	    mainMenuScene = new Scene(mainMenuRoot, 768, 512);
	    primaryStage.setScene(mainMenuScene);
	    primaryStage.setTitle("Mini Casino");
	    primaryStage.setResizable(false);
	    primaryStage.show();
	   
	    
	    //Setting main menu background image

	    Image casino = new Image("file:lib/FuturisticCasino.jpeg");
        BackgroundImage casinoBackground = new BackgroundImage(
                casino,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));	                
        
        Background background = new Background(casinoBackground);
        mainMenuRoot.setBackground(background);
        
        mainMenuRoot.getColumnConstraints().addAll(new ColumnConstraints(768));
        mainMenuRoot.getRowConstraints().add(new RowConstraints(171)); 
        
        Label casinoLabel = new Label("Welcome to the Mini-Casino!");
        casinoLabel.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 44px; -fx-text-fill: white; -fx-font-style: italic");
        
        Button playBlackJackButton = new Button("Play BlackJack");
        playBlackJackButton.setStyle("-fx-font-size: 15px; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: black;");
        playBlackJackButton.setMinSize(150, 60);
        playBlackJackButton.setMaxSize(150, 60);
        playBlackJackButton.setAlignment(Pos.CENTER);
        playBlackJackButton.setOnAction(event-> blackJackMenu() );
        
        Button playRouletteButton = new Button("Play Roulette");
        playRouletteButton.setStyle("-fx-font-size: 15px; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: black;");
        playRouletteButton.setMinSize(150, 60);
        playRouletteButton.setMaxSize(150, 60);
        playRouletteButton.setAlignment(Pos.CENTER);
        playRouletteButton.setOnAction(event-> rouletteGameMenu() );
        
        VBox menuButtonsVBox = new VBox(5);
        menuButtonsVBox.getChildren().addAll(playBlackJackButton, playRouletteButton);
        
        HBox menuScreenHBox = new HBox(30);
        menuScreenHBox.getChildren().addAll(casinoLabel, menuButtonsVBox);
        mainMenuRoot.add(menuScreenHBox, 0, 1);
	   
	    
	}
	
	/*****************BLACKJACK GAME*********************/
	
	
	private void blackJackMenu () {
		
		blackjackHandCount = 1;
		
		//Adding blackjack game nodes; setting scene
		//to blackjack game.
		
		blackjackMainRoot = new GridPane();
        blackJackScene = new Scene(blackjackMainRoot, 861, 404);
        primaryStage.setScene(blackJackScene);
	
		blackjack = new BlackJack();
		
		for (int i = 0; i < 4; i++ ) {
			
			blackjackMainRoot.addColumn(i);
			blackjackMainRoot.addRow(i);
			
		}
	
		//setting row and column constraints for BlackJack title screen
		ColumnConstraints cl1 = new ColumnConstraints();
	    ColumnConstraints cl2 = new ColumnConstraints();
	    ColumnConstraints cl3 = new ColumnConstraints();
	    RowConstraints rw1 = new RowConstraints();
	    RowConstraints rw2 = new RowConstraints();
	    RowConstraints rw3 = new RowConstraints();
	    cl1.setPrefWidth(400); 
        cl2.setPrefWidth(287);     
        cl3.setPrefWidth(287);  
        rw1.setPrefHeight(133);       
        rw2.setPrefHeight(133);       
        rw3.setPrefHeight(133); 
        blackjackMainRoot.getColumnConstraints().addAll(cl1, cl2, cl3);    
        blackjackMainRoot.getRowConstraints().addAll(rw1, rw2, rw3);
	 
		blackjackMainRoot.setVgap(100); 
	    blackjackMainRoot.setHgap(90);
	    blackjackMainRoot.setPadding( new Insets(5) );
	    blackjackMainRoot.setGridLinesVisible(false);
	    
	    //setting background image for BlackJack game
		 Image table = new Image("file:lib/BG.jpeg");

	        // Create a background image
	        BackgroundImage backgroundImage = new BackgroundImage(
	                table,
	                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
	                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));	                
	        
	        Background background = new Background(backgroundImage);
	        blackjackMainRoot.setBackground(background);
		
		//Blackjack menu nodes
		Label gameLabel = new Label("Blackjack");
	    gameLabel.setAlignment(Pos.CENTER_RIGHT);
	    gameLabel.setStyle("-fx-font-size: 60px; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: white;");
	    blackjackMainRoot.add(gameLabel, 0, 1);
	    
	    startButton = new Button("Begin");
	    startButton.setAlignment(Pos.CENTER);
	    startButton.setStyle("-fx-font-size: 20px; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: black;");
	    startButton.setMaxWidth(100);
	    startButton.setMinWidth(100);
	    startButton.setMaxHeight(40);
	    startButton.setMinHeight(40);
	    blackjackMainRoot.add(startButton, 1, 1);
	    
	    Image blackjackMenuImage = new Image ("file:lib/CardsFuturistic.png");
        ImageView blackjackMenuImageView = new ImageView(blackjackMenuImage);
        blackjackMenuImageView.setPreserveRatio(true);
        blackjackMenuImageView.setFitWidth(250);
        blackjackMainRoot.add(blackjackMenuImageView, 2, 1);
        
        //End of game menu nodes
        endOfGameLabel = new Label("You no longer have enough to bet!\n Game over.");
	    endOfGameLabel.setAlignment(Pos.CENTER);
	    endOfGameLabel.setStyle("-fx-font-size: 30px; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: white;");
	    restartButton = new Button("Play again");
	    restartButton.setMaxWidth(100);
	    restartButton.setMinWidth(100);
	    restartButton.setMaxHeight(60);
	    restartButton.setMinHeight(60);
		
		//Initialize bet pane; pane that will hold all betting operations at beginning of each hand
	    VBox betPane = new VBox();
	    betPane.setAlignment(Pos.CENTER);
	    enterBetLabel = new Label("Enter bet below (Min. $5) :");
	    enterBetLabel.setStyle("-fx-font-size: 20px; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: white;");
	    betTextField = new TextField();
        betTextField.setPromptText("Enter here.");
        betTextField.setMinWidth(100);
        betTextField.setMaxWidth(100);
        betTextField.setMinWidth(50);
        betTextField.setMaxWidth(50);
        betSubmitButton = new Button("Submit bet");
        betSubmitButton.setMinHeight(40);
        betSubmitButton.setMaxHeight(40);
        betSubmitButton.setMinWidth(100);
        betSubmitButton.setMaxWidth(100);
        betPane.getChildren().addAll(enterBetLabel, betTextField, betSubmitButton);
	    
	    //Game Pane which will be invisible at the beginning; hold dealer and player panes/labels, hit, stay, new hand & quit buttons
	    blackjackGameRoot = new GridPane();
	    blackjackGameRoot.setVgap(15); 
	    blackjackGameRoot.setHgap(35);
	    blackjackGameRoot.setMaxWidth(861); 
        blackjackGameRoot.setMinWidth(861); 
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(150); 
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(150); 
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPrefWidth(150); 
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPrefWidth(250); 
        blackjackGameRoot.getColumnConstraints().addAll(col1, col2, col3, col4);
        
        //Game count Label; displays the amount of hands played this round
        gameCountLabel = new Label ("Game count: " + blackjackHandCount);
        gameCountLabel.setStyle("-fx-font-size: 10px; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: white;");
        
	  
		//creating dealer pane which will hold dealer's card images
		dealerPane = new HBox(6);
		dealerPane.setAlignment(Pos.TOP_LEFT);
		dealerPane.setPrefHeight(100);
		Label dealerLabel = new Label ("Dealer:");
		dealerLabel.setStyle("-fx-font-size: 20px; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: white;");
		
		//creating player pane which will hold player's card images
		playerPane = new HBox(6);
		playerPane.setAlignment(Pos.BOTTOM_LEFT);
		playerPane.setPrefHeight(100); 
		Label playerLabel = new Label ("Player:");
		playerLabel.setStyle("-fx-font-size: 20px; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: white;");
        
        //Button which calls playerHit method
        hitButton = new Button("Hit");
        hitButton.setMinSize(50, 30);
        hitButton.setMaxSize(50, 30);
        
        //Overlay which will display current bet and current balance
        VBox betAndBalanceOverlay = new VBox(8);
        currentBetLabel = new Label("Current bet: " + blackjackCurrentBet);
        currentBalanceLabel = new Label("Current balance: " + blackjackCurrentBalance);
        currentBetLabel.setStyle("-fx-font-size: 15px; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: white;");
        currentBalanceLabel.setStyle("-fx-font-size: 15px; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: white;");
        betAndBalanceOverlay.getChildren().addAll(currentBetLabel, currentBalanceLabel);
        
        //Stay button; stops player's turn, begins dealer's turn
        stayButton = new Button("Stay");
        stayButton.setMinSize(50, 30);
        stayButton.setMaxSize(50, 30);
     
           //vbox to hold hit and stay buttons;
           hitStayButtonVBox = new VBox(10);
           hitStayButtonVBox.getChildren().addAll(hitButton, stayButton);
           hitStayButtonVBox.setAlignment(Pos.TOP_RIGHT);
      
           blackjackGameRoot.add(dealerLabel, 0, 0);
           blackjackGameRoot.add(dealerPane, 0, 1);
           blackjackGameRoot.add(playerLabel, 0, 2);
           blackjackGameRoot.add(playerPane, 0, 3);
           blackjackGameRoot.add(hitStayButtonVBox, 1, 2);
           blackjackGameRoot.add(gameCountLabel, 3, 0);
           blackjackGameRoot.add(betAndBalanceOverlay, 3, 1);
           blackjackGameRoot.add(betPane, 3, 2); 
           
        newHandButton = new Button("Deal again");   //play again and exit buttons; appear after each hand
        exitGameButton = new Button("Exit");
        newHandButton.setMinSize(80, 30);
        newHandButton.setMaxSize(80, 30);
        exitGameButton.setMinSize(80, 30);
        exitGameButton.setMaxSize(80, 30);
        
        newHandAndExitButtons = new VBox(5);
        newHandAndExitButtons.setAlignment(Pos.CENTER);
        newHandAndExitButtons.getChildren().addAll(newHandButton, exitGameButton);

	    startButton.setOnAction(event-> {
	    	
	    	blackjackMainRoot.getChildren().remove(startButton);
	    	blackjackMainRoot.getChildren().remove(gameLabel);
	    	blackjackMainRoot.getChildren().remove(blackjackMenuImageView);
	    	blackjackMainRoot.getChildren().add(blackjackGameRoot);
	    	blackjackMainRoot.getColumnConstraints().clear();
	    	blackjackMainRoot.getRowConstraints().clear();
	    
	    			blackJackRound();
        	
	    } );
        
    }
	
	private void blackJackRound () {
		
		playerPane.getChildren().clear();
		dealerPane.getChildren().clear();
		
		endOfRoundCheck();
		
		if (blackjackHandCount > 1) {
			
			enterBetLabel.setText("Enter next bet.");
			betSubmitButton.setDisable(false);
	        blackjack.newHand();
	        blackjackGameRoot.getChildren().remove(newHandAndExitButtons);  
	        
	    }
		
		playerPane.setVisible(false); //Hands are not visible until you place bet!
		dealerPane.setVisible(false);
		hitStayButtonVBox.setVisible(false);
	
		 betSubmitButton.setOnAction(event -> {
			 
			 String userInput = betTextField.getText().trim();
			 
			 
			 //Try catch to validate the user inputting bet (make sure it is a number and it is over $5 )
			 
			 try {
			        double betAmount = Double.parseDouble(userInput);
			        
			        // Check if the bet is less than 5
			        if (betAmount < 5) {
			            enterBetLabel.setText("Bet must be at least 5!");
			            return; 
			        }
			        
			        else if (betAmount > blackjackCurrentBalance) {
			        	enterBetLabel.setText("Bet cannot exceed balance!");
			        	return;
			        }
			 
			 enterBetLabel.setText("Bet submitted!");
			 betSubmitButton.setDisable(true);
	        	
			 	blackjackCurrentBet = betAmount;
	        	blackjackCurrentBalance-= blackjackCurrentBet;
	        	currentBetLabel.setText("Current Bet: " + blackjackCurrentBet);
	        	currentBalanceLabel.setText("Current Balance: " + blackjackCurrentBalance);
	        	gameCountLabel.setText("Game count: " + blackjackHandCount);
	        	playerPane.setVisible(true); 
	    		dealerPane.setVisible(true);
	    		hitStayButtonVBox.setVisible(true);
	    		hitButton.setDisable(false);
	    		stayButton.setDisable(false);
	    		
		 }  catch (NumberFormatException e) {
	    	        // Handle the case where the input is not a valid number
	    	        enterBetLabel.setText("Please enter a number.");
	    	    }
	      
	        });
		
		blackjackGameRoot.getChildren().remove(newHandButton);
		blackjackGameRoot.getChildren().remove(resultLabel);
		
		
		//draw dealer's hidden card
		Image Hidden = new Image ("file:lib/cards/BACK.png");
		ImageView imageview = new ImageView (Hidden);
		imageview.setFitWidth(80);
		imageview.setPreserveRatio(true);
		dealerPane.getChildren().add(imageview);
		
		//create dealer's first visible card 
		Image card = new Image (blackjack.dealerHand.get( blackjack.dealerHand.size() - 1 ).imagePath() );
		imageview = new ImageView (card);
		imageview.setFitWidth(80);
		imageview.setPreserveRatio(true);
		dealerPane.getChildren().add(imageview);
		
		
		//iterate through player's hand and draw card images
		for (int i = 0; i < blackjack.playerHand.size(); i++ ) {
			
			card = new Image (blackjack.playerHand.get(i).imagePath() );
			imageview = new ImageView (card);
			imageview.setFitWidth(80);
			imageview.setPreserveRatio(true);
			playerPane.getChildren().add(imageview);
			
		}
		
		hitButton.setOnAction(event -> {
	        
        	blackjack.playerHit();
        	updatePlayerHand(); });
		
		stayButton.setOnAction(stayEvent -> {
	        	
	        //when stay button is pressed, both buttons are disabled; dealer's turn begins
	        hitButton.setDisable(true);
	        stayButton.setDisable(true);
	        	
	          dealerTurn();
	          handleBet();
	          
	            //If statements determine which outcome label 
	            if (blackjack.determineWinner() == 1 ) {
	            	
	            	resultLabel = new Label ("You won!");
	            	resultLabel.setStyle("-fx-font-size: 20px; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: white;");
	            	blackjackGameRoot.add(resultLabel, 2, 2);
	            	
	            	if (blackjack.playerHasBlackJack() && !blackjack.dealerHasBlackJack() ) {
	            		
	            		currentBetLabel.setText("BLACKJACK! You win $" + blackjackCurrentBet*2.5);
	            		currentBalanceLabel.setText("New balance: $" + blackjackCurrentBalance);
	            	
	            	}
	            	
	            	else {
	            		
	            		currentBetLabel.setText("You win $" + blackjackCurrentBet*2);
		            	currentBalanceLabel.setText("New balance: $" + blackjackCurrentBalance);
	            		
	            	}
	            	
	            }
	            
	            else if (blackjack.determineWinner() == 2 ) {
	            	
	            	resultLabel = new Label ("You lose!");
	            	resultLabel.setStyle("-fx-font-size: 20px; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: white;");
	            	blackjackGameRoot.add(resultLabel, 2, 2);
	            	
	            	if ( !blackjack.playerHasBlackJack() && blackjack.dealerHasBlackJack()  ) {
	            		
	            		currentBetLabel.setText("Dealer has BLACKJACK! You lose.");
	            		currentBalanceLabel.setText("New balance: $" + blackjackCurrentBalance);
	            	
	            	}
	            	
	            	else {
	            		
	            	currentBetLabel.setText("You lost your $" + blackjackCurrentBet + " bet.");
	            	currentBalanceLabel.setText("New balance: $" + blackjackCurrentBalance);
	            	
	            	}
	            	
	            }
	            
	            else if (blackjack.determineWinner() == 3 ) {
	            	
	            	resultLabel = new Label ("It's a tie!");
	            	resultLabel.setStyle("-fx-font-size: 20px; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: white;");
	            	blackjackGameRoot.add(resultLabel, 2, 2);
	            	
	            	
	            	if ( blackjack.playerHasBlackJack() && blackjack.dealerHasBlackJack()  ) {
	            		
	            		currentBetLabel.setText("You both have BLACKJACK! It's a tie.");
	            		currentBalanceLabel.setText("New balance: $" + blackjackCurrentBalance);
	            	
	            	}
	            	
	            	else {
	            		
	            	currentBetLabel.setText("It's a tie!");
	            	currentBalanceLabel.setText("New balance: $" + blackjackCurrentBalance);
	            	
	            	}
	            }
	            
	            
	            
	            blackjackGameRoot.add(newHandAndExitButtons, 3, 3);
	            
	            newHandButton.setOnAction(event -> {
	            	
	            	blackjackHandCount++;
	            
	            
	            playerPane.getChildren().clear();
	            dealerPane.getChildren().clear();
	            blackJackRound();
	            
	            
	            });
	            
	            exitGameButton.setOnAction(event-> {
	            	
	            	//When exit button is pressed, scene is set to main menu
	            	//black jack root is reset, as well as the game values 
	            	primaryStage.setScene(mainMenuScene);
	            	blackjackMainRoot.getChildren().clear();
	            	blackjackCurrentBet = 0;
	            	blackjackCurrentBalance = 10;
	            	
	            	
	            });
	            
	        });
   
        }
   
	private void updatePlayerHand() {
		
	    // Clear existing cards
	    playerPane.getChildren().clear();
	    
	    // Draw updated hand
	    for (int i = 0; i < blackjack.playerHand.size(); i++) {
	    	
	    	if (blackjack.playerAceAdjustment() >= 21 ) {
	    		
	    		//if player's score is over 21, then the hit button will be disabled and stay button will be automatically clicked
	    		// this leads to the dealer's turn beginning
	    		disableHitButtonAndFireStayButton();
	    	
	    	}
	        BlackJack.Card card = blackjack.playerHand.get(i);
	        Image image = new Image(card.imagePath());
	        ImageView imageView = new ImageView(image);
	        imageView.setFitWidth(80);
	        imageView.setPreserveRatio(true);
	        playerPane.getChildren().add(imageView);
	        
	    }
	}
	
	private void dealerTurn() {
		
		dealerPane.getChildren().clear();

	    while (blackjack.dealerAceAdjustment() < 17) {
	    	
	        blackjack.dealerHit();
	        
	    }
	       
	        // draw all cards in the dealer's hand
	        for (BlackJack.Card card : blackjack.dealerHand) {
	        	
	            Image image = new Image(card.imagePath());
	            ImageView imageView = new ImageView(image);
	            
	            imageView.setFitWidth(80);
	            imageView.setPreserveRatio(true);
	            dealerPane.getChildren().add(imageView);
	            
	        }
	        
	    }
	
	private void handleBet () {
		
		if (blackjack.determineWinner() == 1) {
			
			if (blackjack.playerHasBlackJack() ) {
				
				blackjackCurrentBalance+=blackjackCurrentBet*2.5;
				
			}
			
			else {
				
			blackjackCurrentBalance+= blackjackCurrentBet*2;
			
			}
			
		}
		
		else if (blackjack.determineWinner() == 3) {
			
			blackjackCurrentBalance+= blackjackCurrentBet;
			
		}
		
	}
	
	private void endOfRoundCheck () {
		
		if (blackjackCurrentBalance < 5) {
		
		blackjackMainRoot.getChildren().remove(blackjackGameRoot);
		
		Label gamesPlayedThisRound;
		
		//If statements purely to get the grammar correct
		if (blackjackHandCount - 1 == 1) {
			
			gamesPlayedThisRound = new Label("You played " + (blackjackHandCount - 1) + " hand!");
			
		}
		
		else {
			
			gamesPlayedThisRound = new Label("You played " + (blackjackHandCount - 1) + " hands!");
			
		}
		
		gamesPlayedThisRound.setStyle("-fx-font-size: 20px; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: white;");
		
		//Adding all end of game nodes to the blackjack root
				blackjackMainRoot.add(endOfGameLabel, 0, 0);
				blackjackMainRoot.add(gamesPlayedThisRound, 0, 1);
				blackjackMainRoot.add(restartButton, 1, 0);
				blackjackMainRoot.add(exitGameButton, 1, 1);
				exitGameButton.setMinSize(100, 60);
		        exitGameButton.setMaxSize(100, 60);
		
		restartButton.setOnAction(event-> {
		
			//When game restarts values are reset and new hand is dealt
	
			blackjackMainRoot.getChildren().remove(endOfGameLabel);
			blackjackMainRoot.getChildren().remove(gamesPlayedThisRound);
			blackjackMainRoot.getChildren().remove(restartButton);
			blackjackMainRoot.getChildren().remove(exitGameButton);
			
			blackjackCurrentBet = 0;
			blackjackCurrentBalance = 10;
			blackjackHandCount = 1;
			
			currentBetLabel.setText("Current Bet: " + blackjackCurrentBet);
        	currentBalanceLabel.setText("Current Balance: " + blackjackCurrentBalance);
			
			blackjackMainRoot.getChildren().add(blackjackGameRoot);
			
			blackJackRound();
		
		});
		
		}
		
	}
	
	private void disableHitButtonAndFireStayButton() {
		
	    hitButton.setDisable(true);
	    stayButton.fire();
	    
	}
	
	
	//Method for accessing game count outside of the class (in Blackjack game logic class)
	public static int getBlackjackGameCount( ) {
		
		return blackjackHandCount;
		
	}
	
	
	
	/*****************ROULETTE GAME*********************/
	
	
	private int rouletteRoundCount = 1;
	private int chipCount = 3;
	private int beginningOfRoundChipCount;
	private Scene rouletteBoardScene;
	private Scene rouletteWheelScene;
	private AnchorPane rouletteBoardRoot;
	private AnchorPane rouletteWheelRoot;
	private VBox betsVBox;
	private ScrollPane betsWonScrollPane;
	private Button backToBoardButton;
	private Label invalidBetLabel;
	private Label chipCountLabel;
	private Label notEnoughBetsLabel;
	private Label betsWonCountLabel;
	private Label totalRoundWinningsLabel;
	//Will hold list of bets placed in each round
	private ArrayList<String> betList = new ArrayList<String>();
	//Will hold running total of bets placed during a given round
	private int betCount = 0;
	private int pocketValue;
	//Arrays hold pocket coordinates relative to ball bouncing animation; in order 0-36
	private double[]pocketXCoordinates = {10, -73, 108, -28, 84, -2, 125, -87, 56, -105, 19, 89, -60, 115, -94, 50, -39,
											124, -104, 66, -84, 97, -106, 38, -21, 118, -8, 122, -77, -97, 75, -100, 30, 
											-55, 125, -46, 105};
	private double[]pocketYCoordinates = {25, 223, 81, 32, 50, 255, 155, 77, 246, 155, 255, 226, 50, 192, 192, 31, 245,
											116, 114, 39, 208, 65, 135, 253, 252, 97, 27, 175, 63, 96, 237, 174, 27, 
											235, 136, 40, 210};	
	
	public void rouletteGameMenu () {
		
		AnchorPane rouletteMenuRoot = new AnchorPane();
		AnchorPane.setTopAnchor(rouletteMenuRoot, 0.0);
        AnchorPane.setBottomAnchor(rouletteMenuRoot, 0.0);
        AnchorPane.setLeftAnchor(rouletteMenuRoot, 0.0);
        AnchorPane.setRightAnchor(rouletteMenuRoot, 0.0); 
        
        Scene rouletteMenuScene = new Scene(rouletteMenuRoot, 874, 500);
        primaryStage.setScene(rouletteMenuScene);
        
      //Set background image to roulette board
    	Image rouletteMenuImage = new Image ("file:lib/roulettemenuimage.jpeg");
    	
    	BackgroundImage rouletteMenuBackgroundImage = new BackgroundImage(
                rouletteMenuImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));	
    	
    	Background rouletteMenuBackground = new Background (rouletteMenuBackgroundImage);
    	
    	rouletteMenuRoot.setBackground(rouletteMenuBackground);
    	
    	
    	Label rouletteMenuLabel = new Label("Welcome to Roulette");
    	rouletteMenuLabel.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 50px; -fx-text-fill: white; -fx-font-style: italic");
    	rouletteMenuLabel.setLayoutX(212);
    	rouletteMenuLabel.setLayoutY(0);
    	
    	Button playRouletteButton = new Button("Start game");
    	playRouletteButton.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 13px; -fx-text-fill: black; -fx-font-style: italic");
    	playRouletteButton.setLayoutX(700);
    	playRouletteButton.setLayoutY(20);
    	playRouletteButton.setPrefWidth(140);
    	playRouletteButton.setPrefHeight(50);
    	playRouletteButton.setOnAction(event -> rouletteBoard() );
    	
    	Button quitRouletteButton = new Button("Return to main menu");
    	quitRouletteButton.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 13px; -fx-text-fill: black; -fx-font-style: italic");
    	quitRouletteButton.setLayoutX(30);
    	quitRouletteButton.setLayoutY(20);
    	quitRouletteButton.setPrefWidth(140);
    	quitRouletteButton.setPrefHeight(50);
    	quitRouletteButton.setOnAction(event -> {
    		
    			
    			primaryStage.setScene(mainMenuScene); });
    	
    	rouletteMenuRoot.getChildren().addAll(rouletteMenuLabel, playRouletteButton, quitRouletteButton);
    	
    	
    	
	}
	
	
public void rouletteBoard () {
    	
    	if (rouletteRoundCount > 1) {
    		
    		betList.clear();
    		betCount = 0;
    		
    	}
    
    	beginningOfRoundChipCount = chipCount;
    	
    	
    	//Initialize roulette board root as an anchor pane with anchors at top left pixel
    	rouletteBoardRoot = new AnchorPane();
    	AnchorPane.setTopAnchor(rouletteBoardRoot, 0.0);
        AnchorPane.setBottomAnchor(rouletteBoardRoot, 0.0);
        AnchorPane.setLeftAnchor(rouletteBoardRoot, 0.0);
        AnchorPane.setRightAnchor(rouletteBoardRoot, 0.0); 
        
        rouletteBoardScene = new Scene(rouletteBoardRoot, 912, 580);
        primaryStage.setScene(rouletteBoardScene);
        primaryStage.setTitle("Roulette");
        primaryStage.show();

    	//Set background image to roulette board
    	Image rouletteBoard = new Image ("file:lib/board.jpeg");
    	
    	BackgroundImage rouletteBackground = new BackgroundImage(
                rouletteBoard,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));	
    	
    	Background rouletteBoardBackground = new Background (rouletteBackground);
    	
    	rouletteBoardRoot.setBackground(rouletteBoardBackground);
    	
    	Label instructionLabel = new Label("- Click on the board to place chips."
    									+ "\n- You must place at least 1/3 of your chips each round"
    									+ "\n- If you lose all of your chips, it's game over!");
    	
    	instructionLabel.setLayoutX(5);
    	instructionLabel.setLayoutY(470);
    	instructionLabel.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 12px; -fx-text-fill: white; -fx-font-style: italic");
    	rouletteBoardRoot.getChildren().add(instructionLabel);
    	
    	chipCountLabel = new Label("Chip count: " + chipCount);
    	chipCountLabel.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 18px; -fx-text-fill: white; -fx-font-style: italic");
    	chipCountLabel.setLayoutX(484);
    	chipCountLabel.setLayoutY(45);
    	rouletteBoardRoot.getChildren().add(chipCountLabel);
        
    	//VBox which will hold bet labels inside of the scroll pane below
        betsVBox = new VBox();
        
        //Creating scroll pane which will display bets to user before they submit them
        ScrollPane betListScrollPane = new ScrollPane(betsVBox);
        betListScrollPane.setLayoutX(594);
        betListScrollPane.setLayoutY(35);
        betListScrollPane.setMinWidth(204);
        betListScrollPane.setMaxWidth(204);
        betListScrollPane.setMinHeight(60);
        betListScrollPane.setMaxHeight(60);
        Label betListLabel = new Label("Bets placed:");
        betListLabel.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 18px; -fx-text-fill: white; -fx-font-style: italic");
        betListLabel.setLayoutX(590);
        betListLabel.setLayoutY(3);       
      
        //Adding nodes above to roulette board window 
        rouletteBoardRoot.getChildren().addAll(betListScrollPane, betListLabel);
        
        //Creating main buttons (1-36) for roulette board
        int rows = 3; 
        int cols = 12; 
        double buttonWidth = 47.7;
        double buttonHeight = 84; 
        double padding = 3; 
        double initialX = 192; 
        double initialY = 111; 
        
        for (int i = 0; i < rows; i++) {
        	
            for (int j = 0; j < cols; j++) {
            	
                int buttonNumber = (rows - i) + (rows * j);
                Button button = new Button(String.valueOf(buttonNumber));
                double x = initialX + j * (buttonWidth + padding);
                double y = initialY + i * (buttonHeight + padding);
                button.setLayoutX(x);
                button.setLayoutY(y);
                button.setPrefWidth(buttonWidth);
                button.setPrefHeight(buttonHeight);
                button.setOpacity(0);
                
                button.setOnAction(event -> placeBet(event));

                rouletteBoardRoot.getChildren().add(button);
                
            }
        }      
        
        //Creating zero button
        buttonWidth = 46;
        buttonHeight = 264;
        initialX = 144;
        initialY = 108;
        
        Button zeroButton = new Button("0");
        zeroButton.setPrefWidth(buttonWidth);
        zeroButton.setPrefHeight(buttonHeight);
        zeroButton.setLayoutX(initialX);
        zeroButton.setLayoutY(initialY);
        zeroButton.setOpacity(0);
        zeroButton.setOnAction(event -> placeBet(event));
        rouletteBoardRoot.getChildren().add(zeroButton);
        
        	//Creating 1st 12, 2nd 12, 3rd 12 buttons
        	buttonWidth = 201;
        	buttonHeight = 50; 
        	initialX = 192; 
        	initialY = 372; 
        
        	for (int i = 0 ; i < 3; i++ ) {
        		
        		Button button = new Button((i + 1) + "12");
        		button.setPrefWidth(buttonWidth);
        		button.setPrefHeight(buttonHeight);
                button.setLayoutX(initialX + i*(buttonWidth));
                button.setLayoutY(initialY);
                button.setOpacity(0);
                button.setOnAction(event -> placeBet(event));
                
                rouletteBoardRoot.getChildren().add(button);

        	}
        	
        	//Creating 1st 18 and 2nd 18 buttons
        	buttonWidth = 100.8;
        	buttonHeight = 50; 
        	initialX = 192; 
        	initialY = 422; 
        	
        	for (int i = 0; i < 2; i++ ) {
        		
        		Button button = new Button((i + 1) + "18");
        		button.setPrefWidth(buttonWidth);
        		button.setPrefHeight(buttonHeight);
                button.setLayoutX(initialX + 5*i*(buttonWidth));
                button.setLayoutY(initialY);
                button.setOpacity(0);
                button.setOnAction(event -> placeBet(event));
                
                rouletteBoardRoot.getChildren().add(button);
        	
        	}
        	
        	//Creating red and black buttons
        	initialX = 393.6; 
        	
        	for (int i = 0; i < 2; i++ ) {
        		
        		Button button;
        		
        		if (i == 0 ) {
        		
        			button = new Button("red");
        		
        		}
        		
        		else {
        			
        			button = new Button("black");
        	
        		}
        		
        		button.setPrefWidth(buttonWidth);
        		button.setPrefHeight(buttonHeight);
                button.setLayoutX(initialX + i*(buttonWidth));
                button.setLayoutY(initialY);
                button.setOpacity(0);
                button.setOnAction(event -> placeBet(event));

                rouletteBoardRoot.getChildren().add(button);
        		
        	}
        	
        	//Creating even and odd buttons
        	initialX = 292.8; 
    
        	for (int i = 0; i < 2; i++ ) {
        		
        		Button button;
        		
        		if (i == 0 ) {
        		
        			button = new Button("even");
        		
        		}
        		
        		else {
        			
        			button = new Button("odd");
        	
        		}
        		
        		button.setPrefWidth(buttonWidth);
        		button.setPrefHeight(buttonHeight);
                button.setLayoutX(initialX + 3*i*(buttonWidth));
                button.setLayoutY(initialY);
                button.setOpacity(0);
                button.setOnAction(event -> placeBet(event));
                
                rouletteBoardRoot.getChildren().add(button);
        		
        	}
        	
        	//Creating 2 to 1 buttons
        	buttonWidth = 54;
        	buttonHeight = 87; 
        	initialX = 796; 
        	initialY = 108; 
        
        	for (int i = 0 ; i < 3; i++ ) {
        		
        		Button button = new Button("221" + (i + 1));
        		button.setPrefWidth(buttonWidth);
        		button.setPrefHeight(buttonHeight);
                button.setLayoutX(initialX);
                button.setLayoutY(initialY + i*buttonHeight);
                button.setOpacity(0);
                button.setOnAction(event -> placeBet(event));
                
                rouletteBoardRoot.getChildren().add(button);
        		
        	}
        	
        	//Button that will submit all bets and begin roulette wheel spinning
        	Button spinWheel = new Button("Spin wheel");
        	spinWheel.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 18px; -fx-text-fill: black; -fx-font-style: italic");
        	spinWheel.setPrefWidth(200);
    		spinWheel.setPrefHeight(50);
            spinWheel.setLayoutX(190);
            spinWheel.setLayoutY(45);
            rouletteBoardRoot.getChildren().add(spinWheel);
            
            //When spin wheel button is clicked, window changes to roulette wheel scene
            spinWheel.setOnAction(actionEvent -> {
            	
            	if (betCount >= beginningOfRoundChipCount / 3 ) {
            	
            			rouletteWheel(); }
            	
            	else {
            		
            		notEnoughBetsLabel = new Label("You must place at least 1/3 of your chips on the board!");
            		notEnoughBetsLabel.setLayoutX(80);
            		notEnoughBetsLabel.setLayoutX(20);
            		notEnoughBetsLabel.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 18px; -fx-text-fill: white; -fx-font-style: italic");
            		rouletteBoardRoot.getChildren().add(notEnoughBetsLabel);
  
            	}
            	
            });
    
    }
    
    public void rouletteWheel () {
    	
    	rouletteRoundCount++;
    	
    	for (int i = 0 ; i < betList.size(); i++ ) {
    		
    		System.out.println( betList.get(i));
    		
    	}
    	
    	rouletteWheelRoot = new AnchorPane();
    	AnchorPane.setTopAnchor(rouletteWheelRoot, 0.0);
        AnchorPane.setBottomAnchor(rouletteWheelRoot, 0.0);
        AnchorPane.setLeftAnchor(rouletteWheelRoot, 0.0);
        AnchorPane.setRightAnchor(rouletteWheelRoot, 0.0); 
        
        
        rouletteWheelScene = new Scene(rouletteWheelRoot, 900.6, 600);
        primaryStage.setScene(rouletteWheelScene);
        
        backToBoardButton = new Button("Go back to board.");
        backToBoardButton.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 13px; -fx-text-fill: black; -fx-font-style: italic");
        backToBoardButton.setPrefHeight(60);
        backToBoardButton.setPrefWidth(200);
        backToBoardButton.setLayoutX(10);
        backToBoardButton.setLayoutY(10);
        backToBoardButton.setVisible(false);
        
        backToBoardButton.setOnAction(event -> { 
        	
        	if (chipCount > 0) {
        	
        	rouletteBoard();
        	
        	}
        	
        	else {
        		
        		endRouletteGameMenu();
        	}
        	
        
        }); 
        
        rouletteWheelRoot.getChildren().add(backToBoardButton);
        
        //Set background image to roulette wheel 
    	Image coolBackGround = new Image ("file:lib/roulettewheelbackground.jpeg");
    	
    	BackgroundImage rouletteWheelBackgroundImage = new BackgroundImage(
                coolBackGround,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));	
    	
    	Background rouletteWheelBackground = new Background (rouletteWheelBackgroundImage);
    	
    	Label spinningLabel = new Label("Spinning...");
    	spinningLabel.setLayoutX(160);
    	spinningLabel.setLayoutY(20);
    	spinningLabel.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 40px; -fx-text-fill: white; -fx-font-style: italic");
    	rouletteWheelRoot.getChildren().add(spinningLabel);
    	
    	
    	rouletteWheelRoot.setBackground(rouletteWheelBackground);
    	
    	 Image wheelImage = new Image("file:lib/roulettewheel.png");
         ImageView wheelImageView = new ImageView(wheelImage);
         wheelImageView.setFitWidth(400);
         wheelImageView.setFitHeight(400);
         wheelImageView.setLayoutX(50);
         wheelImageView.setLayoutY(100);
         
        rouletteWheelRoot.getChildren().add(wheelImageView);
        
        //Roulette wheel spinning animation
        RotateTransition rotation = new RotateTransition(Duration.seconds(4), wheelImageView);
        rotation.setByAngle(1080);
        rotation.setCycleCount(1);
        rotation.play();
        
        //roulette ball
        Circle ball = new Circle(10, Color.WHITE);
        rouletteWheelRoot.getChildren().add(ball);
        ball.setCenterX(240); 
        ball.setCenterY(160);

        // Creating animation to make the ball bounce
        TranslateTransition ballBounceAnimation = new TranslateTransition(Duration.seconds(0.40), ball);
        ballBounceAnimation.setByY(30); 
        ballBounceAnimation.setCycleCount(8); 
        ballBounceAnimation.setAutoReverse(true);
        
        //Random object to generate random pocket on spin
        Random rand = new Random();
        pocketValue = rand.nextInt(37);
        System.out.println("pocket:" + pocketValue);

        //Animation that places ball in correct pocket on the screen
        TranslateTransition ballToCorrectPocket = new TranslateTransition(Duration.seconds(.80), ball);
        ballToCorrectPocket.setToX(pocketXCoordinates[pocketValue]);
        ballToCorrectPocket.setToY(pocketYCoordinates[pocketValue]);

        ballBounceAnimation.setOnFinished(event -> { 
        	
        	ballToCorrectPocket.play();
        	rouletteWheelRoot.getChildren().remove(spinningLabel);
        	
        	});
        
        ballBounceAnimation.play();
        
        ballToCorrectPocket.setOnFinished(event -> {
        	
        	Label spinResultLabel = new Label("Spin outcome: " + pocketValue);
        	spinResultLabel.setLayoutX(500);
        	spinResultLabel.setLayoutY(140);
        	spinResultLabel.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 40px; -fx-text-fill: white; -fx-font-style: italic");
        	rouletteWheelRoot.getChildren().add(spinResultLabel);
        	processBets();
        	backToBoardButton.setVisible(true);
        	
        });
    	
    }
    
public void endRouletteGameMenu () {
	
		//Game over; values are reset 
		chipCount = 3;
		rouletteRoundCount = 1;
    	
    	AnchorPane endRouletteMenuRoot = new AnchorPane();
    	
    	AnchorPane.setTopAnchor(endRouletteMenuRoot, 0.0);
        AnchorPane.setBottomAnchor(endRouletteMenuRoot, 0.0);
        AnchorPane.setLeftAnchor(endRouletteMenuRoot, 0.0);
        AnchorPane.setRightAnchor(endRouletteMenuRoot, 0.0); 
        
        Scene endRouletteMenuScene = new Scene(endRouletteMenuRoot, 844, 500);
        primaryStage.setScene(endRouletteMenuScene);
        primaryStage.show();
        
        
    	Image rouletteEndMenuImage = new Image ("file:lib/endmenuroulette.jpeg");
    	
    	BackgroundImage rouletteEndMenuBackground = new BackgroundImage(
                rouletteEndMenuImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));	
    	
    	Background rouletteEndBackground = new Background (rouletteEndMenuBackground);
    	
    	endRouletteMenuRoot.setBackground(rouletteEndBackground);
    	
    	Label youLoseLabel = new Label ("You lost all of your chips.");
    	youLoseLabel.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 40px; -fx-text-fill: white; -fx-font-style: italic");
    	youLoseLabel.setLayoutX(210);
    	youLoseLabel.setLayoutY(100);
    	
    	Button goToRouletteMenuButton = new Button("Return to menu");
    	goToRouletteMenuButton.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 12px; -fx-text-fill: black; -fx-font-style: italic");
    	goToRouletteMenuButton.setLayoutX(340);
    	goToRouletteMenuButton.setLayoutY(180);
    	goToRouletteMenuButton.setPrefHeight(40);
    	goToRouletteMenuButton.setPrefWidth(140);
    	goToRouletteMenuButton.setOnAction(event -> rouletteGameMenu() );
    	
    	
    	endRouletteMenuRoot.getChildren().addAll(youLoseLabel, goToRouletteMenuButton);
    	
    }  
    
    public void placeBet(ActionEvent event) {
    	
        if ( isBetValid() ) {
        	
        	rouletteBoardRoot.getChildren().remove(notEnoughBetsLabel);
        	
        	updateChipCount();
        
            betCount++;
            Button clickedButton = (Button) event.getSource();
            String buttonText = clickedButton.getText();
            betList.add(buttonText);
            betsVBox.getChildren().add(new Label(betToString(betList.get(betCount - 1))));
            
        }
        
        else {
        	
        	invalidBetLabel = new Label("You do not have any more chips to bet!\nPress button to place bets and spin wheel.");
        	invalidBetLabel.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 10px; -fx-text-fill: white; -fx-font-style: italic");
        	invalidBetLabel.setLayoutX(10);
        	invalidBetLabel.setLayoutY(52);
        	rouletteBoardRoot.getChildren().add(invalidBetLabel);
        	
        } 
        
    }
    
    public void processBets () {
    	
    	int chipsWonAccumulator = 0;
    	
    	ArrayList <String> wonBets = new ArrayList<>();
    	
    	 for (String bet : betList) {
    		 
    	        try {
    	        	
    	            int numericBet = Integer.parseInt(bet);
    	            
    	            if (numericBet == pocketValue) {
    	            	
    	                wonBets.add(bet);
    	                chipsWonAccumulator = chipsWonAccumulator + 36;
    	                
    	            } else if ((pocketValue <= 12 && pocketValue > 0) && bet.equals("112")) {
    	                wonBets.add(bet);
    	                chipsWonAccumulator = chipsWonAccumulator + 3;
    	                
    	            } else if ((pocketValue <= 24 && pocketValue > 12) && bet.equals("212")) {
    	                wonBets.add(bet);
    	                chipsWonAccumulator = chipsWonAccumulator + 3;
    	                
    	            } else if ((pocketValue <= 36 && pocketValue > 24) && bet.equals("312")) {
    	                wonBets.add(bet);
    	                chipsWonAccumulator = chipsWonAccumulator + 3;
    	                
    	            } else if ((pocketValue <= 18 && pocketValue > 0) && bet.equals("118")) {
    	                wonBets.add(bet);
    	                chipsWonAccumulator+=2;
    	                
    	            } else if ((pocketValue <= 36 && pocketValue > 18) && bet.equals("218")) {
    	                wonBets.add(bet);
    	                chipsWonAccumulator+=2;
    	               
    	            } else if (bet.equals("2211") || bet.equals("2212")|| bet.equals("2212")) { 
    	            	
    	            	if (pocketValue % 3 == 1 && bet.equals("2213")) {
    	            		
    	            		wonBets.add(bet);
        	                chipsWonAccumulator+=2;
    	                
    	            } else if (pocketValue % 3 == 2 && bet.equals("2212")) {
    	            	
    	            		wonBets.add(bet);
    	            		chipsWonAccumulator+=2;
    	               
    	            } else if (pocketValue % 3 == 0 && bet.equals("2211")) {
    	            	
    	            	wonBets.add(bet);
    	                chipsWonAccumulator+=2;
    	                
    	            	}
    	            
    	            }
    	        }
    	        
    	         catch (NumberFormatException e) {
    	        	
    	        	if (( pocketValue % 2 == 0) && bet.equals("even") ) {
    	                wonBets.add(bet);
    	                chipsWonAccumulator+=2;
    	                
    	            } else if ((pocketValue % 2 != 0) && bet.equals("odd") ) {
    	                wonBets.add(bet);
    	                chipsWonAccumulator+=2;
    	            
    	            }
    	        	
    	        	if ( ( pocketValue == 2 || pocketValue == 4 || pocketValue == 6 || pocketValue == 8 ||
    	        			pocketValue == 10 || pocketValue == 11 || pocketValue == 13 || pocketValue == 15 || 
    	        			pocketValue == 17 || pocketValue == 20 || pocketValue == 22 || pocketValue == 24 || 
    	        			pocketValue == 26 || pocketValue == 28 || pocketValue == 29 || pocketValue == 31 || 
    	        			pocketValue == 33 || pocketValue == 35 ) && bet.equals("black") ) {
    	        		
    	        				wonBets.add(bet);
    	        				chipsWonAccumulator+=2;
    	        	}
    	        	
    	        	else if ( ( pocketValue == 1 || pocketValue == 3 || pocketValue == 5 || pocketValue == 7 ||
        	        		pocketValue == 9 || pocketValue == 12 || pocketValue == 14 || pocketValue == 16 || 
        	        		pocketValue == 18 || pocketValue == 19 || pocketValue == 21 || pocketValue == 23 || 
        	        		pocketValue == 25 || pocketValue == 27 || pocketValue == 30 || pocketValue == 32 ||
        	        		pocketValue == 34 || pocketValue == 36 ) && bet.equals("red") ) {
        	        		
        	        			wonBets.add(bet);
        	        			chipsWonAccumulator+=2;
        	        	}
    	        		
    	       }
    	        
    	 }
    	        
    	        if (wonBets.size() == 1) {
    	        
    	        	betsWonCountLabel = new Label ("You won " + wonBets.size() + " bet:");
    	        
    	        }
    	        
    	        else {
        	        
        	        betsWonCountLabel = new Label ("You won " + wonBets.size() + " bets");
        	        
        	    }
    	        
    	        if (wonBets.size() > 0) {
    	        
    	   
    	        	betsWonCountLabel.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 30px; -fx-text-fill: white; -fx-font-style: italic");
    	        	betsWonCountLabel.setLayoutX(460);
    	        	betsWonCountLabel.setLayoutY(220);
    	        	VBox betsWonVBox = new VBox();
    	        	betsWonScrollPane = new ScrollPane(betsWonVBox);
    	        	betsWonScrollPane.setPrefHeight(50);
    	        	betsWonScrollPane.setPrefWidth(200);
    	       		betsWonScrollPane.setLayoutX(660);
    	       		betsWonScrollPane.setLayoutY(230);
    	       		
    	       		if (chipsWonAccumulator == 1) {
    	       			
    	       			totalRoundWinningsLabel = new Label("Total winnings this round: " + chipsWonAccumulator + " chip");
    	       		
    	       		}
    	       		
    	       		else {
    	       			
    	       			totalRoundWinningsLabel = new Label("Total winnings this round: " + chipsWonAccumulator + " chips");
    	       		
    	       		}
    	       		
    	       		totalRoundWinningsLabel.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 20px; -fx-text-fill: white; -fx-font-style: italic");
    	       		totalRoundWinningsLabel.setLayoutX(460);
    	       		totalRoundWinningsLabel.setLayoutY(300);
    	       
    	       for (int i = 0; i < wonBets.size(); i++ ) {
    	    	   
    	    	   Label label = new Label (betToString( wonBets.get(i) ) );
    	    	   label.setStyle("-fx-font-family: 'Apple Chancery', cursive; -fx-font-size: 14px; -fx-text-fill: black; -fx-font-style: italic");	 
    	    	   betsWonVBox.getChildren().add( label );
    	       
    	       }
    	       
    	       	   rouletteWheelRoot.getChildren().addAll(betsWonCountLabel, betsWonScrollPane, totalRoundWinningsLabel);
    	       	   
    	       	   chipCount+=chipsWonAccumulator;
    
    	        
    	       }        
    	
    	System.out.println("Won bets:");
    	for (int j = 0; j < wonBets.size(); j++ ) {
    		
    		System.out.println(wonBets.get(j));
    		
    	}
    	
    	System.out.println("Placed bets:");
    	for (int j = 0; j < betList.size(); j++ ) {
    		
    		System.out.println(betList.get(j));
    		
    		}
    
    }
    
    //method determines if user has more chips to bet
    public boolean isBetValid () {
    	
    	if (chipCount == 0) {
    		
    		return false;
    		
    	}
    	
    	return true;
    	
    }
    
    //method decrements chipcount and changes the chip count label
    public void updateChipCount() {
    	
    	chipCount--;
    	
    	chipCountLabel.setText("Chip count: " + chipCount);
    	
    }
    
    
    //Method converts semi ambiguous bet text into readable text to appear on screen
    public String betToString (String str) {
    	
    	if (str.equals("red")) {
    		
    		return "Bet on red (1 to 1)";
    		
    	}
    	
    	else if (str.equals("black") ) {
    		
    		return "Bet on black (1 to 1)";
    	}
    	
    	else if (str.equals("even") ) {
    		
    		return "Bet on even (1 to 1)";
    	}
    	
    	else if (str.equals("odd") ) {
    		
    		return "Bet on odd (1 to 1)";
    	}
    	
    	
    	if (Integer.parseInt(str) == 0) {
    		
    		return "Bet on zero (35 to 1)";
    	}
    	
    	else if ( Integer.parseInt(str) < 37 ) {
    		
    		return "Straight bet: " + str + " (35 to 1)";
    	}
    	
    	else if ( Integer.parseInt(str) == 2211 ) {
    		
    		return "Two to One: First row (2 to 1)";
    	}
    	
    	else if ( Integer.parseInt(str) == 2212 ) {
    		
    		return "Two to One: Second row (2 to 1)";
    	}
    	
    	else if ( Integer.parseInt(str) == 2213 ) {
    		
    		return "Two to One: Third row (2 to 1)";
    	}
    	
		else if ( Integer.parseInt(str) == 118 ) {
    		
    		return "One to Eighteen (1 to 1)";
    		
    	}
    	
    	else if ( Integer.parseInt(str) == 218 ) {
    		
    		return "Nineteen to Thirty Six (1 to 1)";
    	}
    	
    	else if ( Integer.parseInt(str) == 112 ) {
    		
    		return "First Twelve (2 to 1)";
    	}
    	
		else if ( Integer.parseInt(str) == 212 ) {
    		
    		return "Second Twelve (2 to 1)";
    		
    	}
    	
    	else if ( Integer.parseInt(str) == 312 ) {
    		
    		return "Third Twelve (2 to 1)";
    	}
    	
    	return "";	
    	
    }

}	