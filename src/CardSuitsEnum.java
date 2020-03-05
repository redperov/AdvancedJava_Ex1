public enum CardSuitsEnum {
    HEARTS("Hearts"),
    DIAMONDS("Diamonds"),
    CLUBS("Clubs"),
    SPADES("Spades");

    private String suitName;

    private CardSuitsEnum(String suitName) {
        this.suitName = suitName;
    }

    @Override
    public String toString() {
        return this.suitName;
    }
}
