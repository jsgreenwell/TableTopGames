import java.util.*;

/**
 * Go Fish card game implementation using Map-based Deck and ASCII art cards
 */
public class GoFish extends Game {
    private Deck deck;
    private List<Card> playerHand;
    private List<Card> computerHand;
    private int playerBooks;
    private int computerBooks;
    private List<String> playerBooksList;
    private List<String> computerBooksList;
    
    public GoFish() {
        this.deck = new Deck();
        this.playerHand = new ArrayList<>();
        this.computerHand = new ArrayList<>();
        this.playerBooks = 0;
        this.computerBooks = 0;
        this.playerBooksList = new ArrayList<>();
        this.computerBooksList = new ArrayList<>();
    }
    
    @Override
    public void playGame() {
        printGreeting();
        setupGame();
        
        while (!isGameOver()) {
            // Player turn
            if (!playerTurn()) {
                break; // Game over
            }
            
            if (isGameOver()) break;
            
            // Computer turn
            computerTurn();
        }
        
        showFinalResults();
    }
    
    @Override
    protected void printGreeting() {
        clearScreen();
        System.out.println(loadRules("gofish"));
        
        System.out.print("Press enter to continue: ");
        scan.nextLine();
        
        clearScreen();
        System.out.print("""
                ╔═══════════════════════════════════════╗
                ║                                       ║
                ║            🐟 GO FISH! 🐟             ║
                ║                                       ║
                ║     ♠ ♥ ♦ ♣  Card Game Fun  ♣ ♦ ♥ ♠     ║
                ║                                       ║
                ╚═══════════════════════════════════════╝
                
                Time to Go Fish!
                Press enter when you're ready to continue: """);
        
        scan.nextLine();
        clearScreen();
    }
    
    private void setupGame() {
        deck.shuffle();
        
        // Deal 7 cards to each player
        playerHand = deck.dealCards(7);
        computerHand = deck.dealCards(7);
        
        // Check for initial books
        checkForBooks(playerHand, playerBooksList, true);
        checkForBooks(computerHand, computerBooksList, false);
        
        System.out.println("Game setup complete! Each player has 7 cards.");
        System.out.println("Cards remaining in pond: " + deck.size());
        System.out.println();
    }
    
    private boolean playerTurn() {
        System.out.println("=== YOUR TURN ===");
        showPlayerHand();
        
        if (playerHand.isEmpty()) {
            System.out.println("You're out of cards! Drawing from pond...");
            if (deck.hasCards()) {
                playerHand.add(deck.dealCard());
            } else {
                return false; // Game over
            }
        }
        
        // Get player's request
        System.out.print("What rank do you want to ask for? (A, 2-10, J, Q, K): ");
        String requestedRank = scan.nextLine().toUpperCase().trim();
        
        // Validate input
        if (!isValidRank(requestedRank)) {
            System.out.println("Invalid rank! Try again.");
            return playerTurn();
        }
        
        // Check computer's hand
        List<Card> matchingCards = findCardsOfRank(computerHand, requestedRank);
        
        if (!matchingCards.isEmpty()) {
            System.out.println("Computer has " + matchingCards.size() + " card(s) of rank " + requestedRank + "!");
            
            // Transfer cards
            for (Card card : matchingCards) {
                computerHand.remove(card);
                playerHand.add(card);
            }
            
            checkForBooks(playerHand, playerBooksList, true);
            
            System.out.println("You get another turn!");
            System.out.println();
            return playerTurn(); // Player gets another turn
        } else {
            System.out.println("Computer says: 'Go Fish!'");
            
            if (deck.hasCards()) {
                Card drawnCard = deck.dealCard();
                playerHand.add(drawnCard);
                System.out.println("You drew: " + drawnCard);
                
                // Check if drawn card completes a book
                checkForBooks(playerHand, playerBooksList, true);
            } else {
                System.out.println("The pond is empty!");
            }
        }
        
        System.out.println();
        return true;
    }
    
    private void computerTurn() {
        System.out.println("=== COMPUTER'S TURN ===");
        
        if (computerHand.isEmpty()) {
            System.out.println("Computer is out of cards! Drawing from pond...");
            if (deck.hasCards()) {
                computerHand.add(deck.dealCard());
            } else {
                return; // Game over
            }
        }
        
        // Computer chooses a random rank from its hand
        Card.Rank randomRank = computerHand.get(getRandom(0, computerHand.size())).getRank();
        String requestedRank = randomRank.getSymbol();
        
        System.out.println("Computer asks: 'Do you have any " + requestedRank + "s?'");
        
        // Check player's hand
        List<Card> matchingCards = findCardsOfRank(playerHand, requestedRank);
        
        if (!matchingCards.isEmpty()) {
            System.out.println("You have " + matchingCards.size() + " card(s) of rank " + requestedRank + ".");
            System.out.println("You must give them to the computer.");
            
            // Transfer cards
            for (Card card : matchingCards) {
                playerHand.remove(card);
                computerHand.add(card);
            }
            
            checkForBooks(computerHand, computerBooksList, false);
            
            System.out.println("Computer gets another turn!");
            System.out.println();
            computerTurn(); // Computer gets another turn
        } else {
            System.out.println("You say: 'Go Fish!'");
            
            if (deck.hasCards()) {
                Card drawnCard = deck.dealCard();
                computerHand.add(drawnCard);
                System.out.println("Computer draws a card from the pond.");
                
                checkForBooks(computerHand, computerBooksList, false);
            } else {
                System.out.println("The pond is empty!");
            }
        }
        
        System.out.println();
    }
    
    private void showPlayerHand() {
        System.out.println("Your hand (" + playerHand.size() + " cards):");
        for (int i = 0; i < playerHand.size(); i++) {
            System.out.print(playerHand.get(i) + " ");
            if ((i + 1) % 13 == 0) System.out.println(); // New line every 13 cards
        }
        System.out.println();
        
        System.out.println("Your books: " + playerBooks + " (" + String.join(", ", playerBooksList) + ")");
        System.out.println("Computer books: " + computerBooks);
        System.out.println("Cards in pond: " + deck.size());
        System.out.println();
    }
    
    private List<Card> findCardsOfRank(List<Card> hand, String rank) {
        List<Card> matches = new ArrayList<>();
        for (Card card : hand) {
            if (card.getRank().getSymbol().equals(rank)) {
                matches.add(card);
            }
        }
        return matches;
    }
    
    private void checkForBooks(List<Card> hand, List<String> booksList, boolean isPlayer) {
        Map<Card.Rank, List<Card>> rankGroups = new HashMap<>();
        
        // Group cards by rank
        for (Card card : hand) {
            rankGroups.computeIfAbsent(card.getRank(), k -> new ArrayList<>()).add(card);
        }
        
        // Check for books (4 of a kind)
        for (Map.Entry<Card.Rank, List<Card>> entry : rankGroups.entrySet()) {
            if (entry.getValue().size() == 4) {
                Card.Rank rank = entry.getKey();
                List<Card> fourCards = entry.getValue();
                
                // Remove the four cards from hand
                hand.removeAll(fourCards);
                
                // Add to books
                if (isPlayer) {
                    playerBooks++;
                    playerBooksList.add(rank.getSymbol());
                    System.out.println("🎉 You completed a book of " + rank.getSymbol() + "s!");
                } else {
                    computerBooks++;
                    computerBooksList.add(rank.getSymbol());
                    System.out.println("Computer completed a book of " + rank.getSymbol() + "s.");
                }
            }
        }
    }
    
    private boolean isValidRank(String rank) {
        for (Card.Rank r : Card.Rank.values()) {
            if (r.getSymbol().equals(rank)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isGameOver() {
        // Game ends when all books are collected (13 total) or no more cards can be played
        int totalBooks = playerBooks + computerBooks;
        boolean noBooksLeft = totalBooks == 13;
        boolean noCardsLeft = !deck.hasCards() && (playerHand.isEmpty() || computerHand.isEmpty());
        
        return noBooksLeft || noCardsLeft;
    }
    
    private void showFinalResults() {
        clearScreen();
        System.out.println("🎮 GAME OVER! 🎮");
        System.out.println("================");
        System.out.println();
        
        System.out.println("Final Score:");
        System.out.println("Your books: " + playerBooks + " (" + String.join(", ", playerBooksList) + ")");
        System.out.println("Computer books: " + computerBooks + " (" + String.join(", ", computerBooksList) + ")");
        System.out.println();
        
        if (playerBooks > computerBooks) {
            System.out.println("🏆 CONGRATULATIONS! YOU WIN! 🏆");
        } else if (computerBooks > playerBooks) {
            System.out.println("💻 Computer wins! Better luck next time!");
        } else {
            System.out.println("🤝 It's a tie! Great game!");
        }
        
        System.out.println();
        System.out.println("Thanks for playing Go Fish!");
        System.out.print("Press Enter to return to main menu...");
        scan.nextLine();
    }
}
