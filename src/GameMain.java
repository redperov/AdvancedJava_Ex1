import javax.swing.*;
import java.util.List;
import java.util.Random;

public class GameMain {

    /**
     * The goal score in the game.
     */
    private static final int GOAL = 21;

    /**
     * The value that can be added to an ace card.
     */
    private static final int ACE_ADDITION = 10;

    /**
     * The probability at which the computer takes cards in the final moves.
     */
    public static final double PROBABILITY = 0.7;

    public static void main(String[] args) {
        DeckOfCards gameDeck;
        DeckOfCards playerDeck;
        DeckOfCards computerDeck;
        int playerResult;
        int computerResult;

        while (true) {
            gameDeck = new DeckOfCards(false);
            playerDeck = new DeckOfCards(true);
            computerDeck = new DeckOfCards(true);

            // Shuffle the cards and deal a single card to each player
            gameDeck.shuffle();
            playerDeck.add(gameDeck.deal());
            computerDeck.add(gameDeck.deal());

            // Let the player choose its cards
            playerResult = playerTurn(playerDeck, gameDeck);

            // Check if the player decided to quit the game
            if (playerResult == 0) {
                break;
            }
            // If the player passed the GOAL score, the computer can be declared as winner
            // without continuing the game
            else if (playerResult > GOAL) {
                showWinnerMessage("computer", playerDeck, computerDeck);
                continue;
            }

            // Let the computer choose his cards
            computerResult = computerTurn(computerDeck, gameDeck);

            // Declare the winner
            String winner = decideWinner(playerResult, computerResult);
            showWinnerMessage(winner, playerDeck, computerDeck);
        }
    }

    /**
     * Allows the player to choose its moves.
     * @param playerDeck player's initial deck of cards
     * @return 0 if the player decided to quit the game, otherwise, the deck's score
     */
    private static int playerTurn(DeckOfCards playerDeck, DeckOfCards gameDeck) {
        int playerResponse;

        while (true) {
            String message = String.format("Your current deck is:\n%s\nThe score is: %s\n" +
                    "Would you like another card?", playerDeck, calculateDeckScore(playerDeck));
            playerResponse = JOptionPane.showConfirmDialog(null, message);

            if (playerResponse == JOptionPane.CANCEL_OPTION || playerResponse == JOptionPane.CLOSED_OPTION) {
                // The player decided to quit the game
                return 0;
            }
            else if (playerResponse == JOptionPane.NO_OPTION) {
                // The player decided to finish his turn
                break;
            }
            Card dealtCard = gameDeck.deal();

            // Check if the game deck is out of cards
            if (dealtCard == null) {
                JOptionPane.showMessageDialog(null,
                        "The game deck is out of cards, You lost.");
                return GOAL + 1;
            }
            playerDeck.add(dealtCard);
        }

        return calculateDeckScore(playerDeck);
    }

    /**
     * Allows the computer to choose its moves.
     * @param computerDeck computer's initial deck of cards
     * @return the deck's score
     */
    private static int computerTurn(DeckOfCards computerDeck, DeckOfCards gameDeck) {
        int currScore = 0;
        Random random = new Random();

        // Keep taking cards while the score is less than the (goal - 10),
        // and when the score is above that, start taking cards at a certain probability
        while ((currScore < GOAL - ACE_ADDITION || random.nextFloat() <= PROBABILITY) && currScore < GOAL) {
            Card dealtCard = gameDeck.deal();

            // Check if the game deck is out of cards
            if (dealtCard == null) {
                break;
            }
            computerDeck.add(dealtCard);
            currScore = calculateDeckScore(computerDeck);
        }

        return currScore;
    }

    /**
     * Calculates the score of the given deck of cards.
     * @param deckOfCards deck to calculate its score
     * @return deck score
     */
    private static int calculateDeckScore(DeckOfCards deckOfCards) {
        List<Card> deckList = deckOfCards.getDeck();
        int score = 0;
        int acesCounter = 0;

        // Sum all the cards values
        for (Card currentCard : deckList) {
            CardFacesEnum currentFace = currentCard.getFace();

            // If it's an Ace, consider it's value for now as 1, and at the end check if it
            // should be changed to 11
            if (CardFacesEnum.ACE.equals(currentFace)) {
                acesCounter++;
            }
            score += currentFace.getValue();
        }

        // Check if the aces values should be increased
        for (int i = acesCounter; i > 0; i--) {
            int diffToGoal = GOAL - score;

            // TODO check that the calculation is correct
            // If the difference from the current score to the goal is greater than 10,
            // change the current ace's value from 1 to 11
            if (score < GOAL && diffToGoal >= ACE_ADDITION ) {
                score += ACE_ADDITION;
            }
            else {
                break;
            }
        }

        return score;
    }

    /**
     * Shows the game's winner
     * @param winner name of the winner
     * @param playerDeck player's deck
     * @param computerDeck computer's deck
     */
    private static void showWinnerMessage(String winner, DeckOfCards playerDeck, DeckOfCards computerDeck) {
        String result;
        String message;

        if (winner.equals("computer")) {
            result = "You lost";
        }
        else if (winner.equals("player")) {
            result = "You won";
        }
        else {
            result = "It's a draw";
        }
        message = String.format("Game over.\nYour deck:\n%s\nYour score: %s\nComputer deck:\n%s\n" +
                "Computer score: %s\n\n%s\n\nWould you like to start a new game",
                playerDeck, calculateDeckScore(playerDeck), computerDeck, calculateDeckScore(computerDeck),
                result);
        int playerResponse = JOptionPane.showConfirmDialog(null, message);

        if (playerResponse == JOptionPane.NO_OPTION || playerResponse == JOptionPane.CANCEL_OPTION
                || playerResponse == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Decides the game's winner according to the given scores.
     * @param playerResult player's result
     * @param computerResult computer's result
     * @return winner's name
     */
    private static String decideWinner(int playerResult, int computerResult) {
        String winner;

        if (computerResult > GOAL) {
            winner = "player";
        }
        else if (playerResult == computerResult) {
            winner = "draw";
        }
        else if (playerResult > computerResult) {
            winner = "player";
        }
        else {
            winner = "computer";
        }

        return winner;
    }
}
