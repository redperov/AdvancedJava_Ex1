import javax.swing.*;
import java.util.List;

public class GameMain {

    private static final int GOAL = 21;
    private static final int ACE_ADDITION = 10;

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
            System.out.println(gameDeck);
            playerDeck.add(gameDeck.deal());
            computerDeck.add(gameDeck.deal());

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
            computerResult = computerTurn(computerDeck, gameDeck);
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
            String message = String.format("Your current deck is: %s\nThe score is: %s\n" +
                    "Would you like another card?", playerDeck, calculateDeckScore(playerDeck));
            playerResponse = JOptionPane.showConfirmDialog(null, message);

            if (playerResponse == 2) {
                // The player decided to quit the game
                return 0;
            }
            else if (playerResponse == 1) {
                // The player to finish his turn
                break;
            }

            Card dealtCard = gameDeck.deal();

            // Check if the game deck is out of cards
            if (dealtCard == null) {
                JOptionPane.showMessageDialog(null,
                        "The game deck is out of cards, You lost.");
                return 0;
            }

            playerDeck.add(dealtCard);
        }

        return calculateDeckScore(playerDeck);
    }

    private static int computerTurn(DeckOfCards computerDeck, DeckOfCards gameDeck) {
        int currScore = 0;

        while (currScore < GOAL) {
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
        message = String.format("Game over.\nYour deck: %s\nComputer deck: %s\n%s\n\n" +
                "Would you like to start a new game", playerDeck, computerDeck, result);
        int playerResponse = JOptionPane.showConfirmDialog(null, message);

        if (playerResponse == 1 || playerResponse == 2) {
            System.exit(0);
        }
    }

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
