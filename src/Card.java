public class Card {

    private final CardFacesEnum face;
    private final CardSuitsEnum suit;

    public Card(CardFacesEnum face, CardSuitsEnum suit) {
        this.face = face;
        this.suit = suit;
    }

    public CardFacesEnum getFace() {
        return this.face;
    }

    @Override
    public String toString() {
        return String.format("%s of %s", this.face.name(),
                this.suit.name());
    }
}
