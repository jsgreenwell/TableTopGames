import java.util.*;

/**
 * Represents a deck of playing cards using a Map for storage
 * The Map uses Integer keys for positions and Card values
 */
public class Deck {
    private Map<Integer, Card> cards;
    private List<Integer> availablePositions;
    private Random random;
    
    public Deck() {
        this.cards = new HashMap<>();
        this.availablePositions = new ArrayList<>();
        this.random = new Random();
        initializeDeck();
    }
    
    /**
     * Initialize a standard 52-card deck using a Map
     */
    private void initializeDeck() {
        cards.clear();
        availablePositions.clear();
        
        int position = 0;
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.put(position, new Card(suit, rank));
                availablePositions.add(position);
                position++;
            }
        }
    }
    
    /**
     * Shuffle the deck by reorganizing the map positions
     */
    public void shuffle() {
        // Create a new map with shuffled positions
        Map<Integer, Card> shuffledCards = new HashMap<>();
        List<Card> cardList = new ArrayList<>(cards.values());
        Collections.shuffle(cardList, random);
        
        // Rebuild the map with shuffled cards
        for (int i = 0; i < cardList.size(); i++) {
            shuffledCards.put(i, cardList.get(i));
        }
        
        this.cards = shuffledCards;
        
        // Reset available positions
        availablePositions.clear();
        for (int i = 0; i < cards.size(); i++) {
            availablePositions.add(i);
        }
    }
    
    /**
     * Deal a card from the deck (remove from available positions)
     */
    public Card dealCard() {
        if (availablePositions.isEmpty()) {
            return null; // No more cards to deal
        }
        
        // Get a random position from available positions
        int randomIndex = random.nextInt(availablePositions.size());
        int position = availablePositions.remove(randomIndex);
        
        return cards.get(position);
    }
    
    /**
     * Check if there are more cards to deal
     */
    public boolean hasCards() {
        return !availablePositions.isEmpty();
    }
    
    /**
     * Get the number of cards remaining in the deck
     */
    public int size() {
        return availablePositions.size();
    }
    
    /**
     * Reset the deck to a full 52-card deck
     */
    public void reset() {
        initializeDeck();
        shuffle();
    }
    
    /**
     * Get all cards in the deck (for debugging/testing)
     */
    public Map<Integer, Card> getAllCards() {
        return new HashMap<>(cards);
    }
    
    /**
     * Deal multiple cards at once
     */
    public List<Card> dealCards(int count) {
        List<Card> dealtCards = new ArrayList<>();
        for (int i = 0; i < count && hasCards(); i++) {
            Card card = dealCard();
            if (card != null) {
                dealtCards.add(card);
            }
        }
        return dealtCards;
    }
}