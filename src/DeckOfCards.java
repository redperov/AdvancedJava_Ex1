import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a deck of cards.
 */
public class DeckOfCards {

    /**
     * Max number of cards in the deck.
     */
    private static final int NUM_OF_CARDS = 52;

    /**
     * Generates random numbers.
     */
    private static final SecureRandom randomGenerator = new SecureRandom();

    /**
     * All the possible card faces.
     */
    private static final CardFacesEnum[] faces = {CardFacesEnum.ACE, CardFacesEnum.DEUCE,
            CardFacesEnum.THREE, CardFacesEnum.FOUR, CardFacesEnum.FIVE, CardFacesEnum.SIX,
            CardFacesEnum.SEVEN, CardFacesEnum.EIGHT, CardFacesEnum.NINE, CardFacesEnum.TEN,
            CardFacesEnum.JACK, CardFacesEnum.QUEEN, CardFacesEnum.KING};

    /**
     * All the possible card suits.
     */
    private static final CardSuitsEnum[] suits = {CardSuitsEnum.HEARTS, CardSuitsEnum.DIAMONDS,
            CardSuitsEnum.CLUBS, CardSuitsEnum.SPADES};

    /**
     * Holds all the current deck's cards.
     */
    private List<Card> deck;

    /**
     * The index of the current card that's being used in the deck.
     */
    private int currentCard;

    /**
     * Constructor.
     * @param isEmpty indicates whether the player wants to get an empty deck of cards.
     */
    public DeckOfCards(boolean isEmpty) {
        this.deck = new ArrayList<>();
        this.currentCard = 0;

        if (isEmpty) {
            return;
        }

        // Fill the deck with cards
        for (int i = 0; i < NUM_OF_CARDS; i++) {
            this.deck.add(new Card(faces[i % 13], suits[i / 13]));
        }
    }

    public List<Card> getDeck() {
        return this.deck;
    }

    /**
     * Shuffles the cards in the deck.
     */
    public void shuffle() {
        this.currentCard = 0;
        int second;

        for (int first = 0; first < this.deck.size(); first++) {
            second = randomGenerator.nextInt(NUM_OF_CARDS);

            // Swap between the first and second cards
            Card temp = this.deck.get(first);
            this.deck.set(first, this.deck.get(second));
            this.deck.set(second, temp);
        }
    }

    /**
     * Deals a single card.
     * @return card if the deck is not empty, null otherwise
     */
    public Card deal() {
        if (this.currentCard < this.deck.size()) {
            return this.deck.get(currentCard++);
        }
        return null;
    }

    /**
     * Adds a single card to the deck.
     * @param card card to add
     */
    public void add(Card card) {
        if (this.deck.size() < NUM_OF_CARDS) {
            this.deck.add(card);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 1; i <= this.deck.size(); i++) {
            stringBuilder.append(this.deck.get(i - 1));

            // Add a new line after every fourth card, or a comma otherwise
            if (i % 4 == 0) {
                stringBuilder.append("\n");
            }
            else {
                stringBuilder.append(",");
            }
        }
        stringBuilder.setLength(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }
}