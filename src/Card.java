/**
 * Represents a playing card with ASCII art display
 */
public class Card {
    public enum Suit {
        HEARTS("♥"), DIAMONDS("♦"), CLUBS("♣"), SPADES("♠");
        
        private final String symbol;
        
        Suit(String symbol) {
            this.symbol = symbol;
        }
        
        public String getSymbol() {
            return symbol;
        }
    }
    
    public enum Rank {
        ACE("A", 1), TWO("2", 2), THREE("3", 3), FOUR("4", 4), FIVE("5", 5), 
        SIX("6", 6), SEVEN("7", 7), EIGHT("8", 8), NINE("9", 9), TEN("10", 10),
        JACK("J", 11), QUEEN("Q", 12), KING("K", 13);
        
        private final String symbol;
        private final int value;
        
        Rank(String symbol, int value) {
            this.symbol = symbol;
            this.value = value;
        }
        
        public String getSymbol() {
            return symbol;
        }
        
        public int getValue() {
            return value;
        }
    }
    
    private final Suit suit;
    private final Rank rank;
    
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    public Suit getSuit() {
        return suit;
    }
    
    public Rank getRank() {
        return rank;
    }
    
    /**
     * Returns ASCII art representation of the card
     */
    public String getAsciiArt() {
        String rankSymbol = rank.getSymbol();
        String suitSymbol = suit.getSymbol();
        
        // Handle 10 special case (two characters)
        if (rankSymbol.equals("10")) {
            return String.format("""
                ┌─────────┐
                │10       │
                │    %s    │
                │       10│
                └─────────┘""", suitSymbol);
        } else {
            return String.format("""
                ┌─────────┐
                │%s        │
                │    %s    │
                │        %s│
                └─────────┘""", rankSymbol, suitSymbol, rankSymbol);
        }
    }
    
    /**
     * Returns a simple string representation for console display
     */
    @Override
    public String toString() {
        return rank.getSymbol() + suit.getSymbol();
    }
    
    /**
     * Checks if this card has the same rank as another card
     */
    public boolean sameRank(Card other) {
        return this.rank == other.rank;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return suit == card.suit && rank == card.rank;
    }
    
    @Override
    public int hashCode() {
        return suit.hashCode() * 31 + rank.hashCode();
    }
}