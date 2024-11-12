import java.util.Random;
import java.util.Scanner;

/**
 * The Game class represents the Game of Nim. It manages the players, board state, and game logic.
 * Players take turns removing pieces from a pile until one player is forced to take the last piece, resulting in a loss.
 * It also supports a "Computer" player that will always play optimally to win.
 */
public class Game {
    private String player1; // Name of Player 1
    private String player2; // Name of Player 2 or "Computer"
    private String currentPlayer; // The current player's name
    private int currentPlayerNumber; // Number representing the current player (1 or 2)
    private int score1; // Score for Player 1
    private int score2; // Score for Player 2
    private boolean playedGame; // Tracks if a game has been played

    /**
     * Constructor initializes a new game and initializes scores for both players.
     */
    public Game() {
        score1 = 0;
        score2 = 0;
    }

    /**
     * Main game loop where players take turns until the pile is empty.
     * Manages player turns, piece removal, win/loss conditions, and score updates.
     */
    public void play() {
        Scanner scanner = new Scanner(System.in);

        // Get player names
        if (!playedGame) {
            System.out.print("Enter Player 1's name: ");
            player1 = scanner.nextLine();
            System.out.print("Enter Player 2's name: ");
            player2 = scanner.nextLine();
        }

        // Randomly select the starting player
        currentPlayerNumber = new Random().nextInt(2) + 1;
        currentPlayer = (currentPlayerNumber == 1) ? player1 : player2;

        // Initialize board
        Board.populate();

        // Game loop
        while (!Board.isEmpty()) {
            Board.display();
            System.out.println(currentPlayer + "'s turn.");

            // Adjust maximum pieces based on the current pile size
            int maxPieces = (Board.getPileSize() == 1) ? 1 : Math.max(1, Board.getPileSize() / 2);
            int piecesTaken;

            if (currentPlayer.equalsIgnoreCase("Computer")) {
                piecesTaken = computeOptimalMove(Board.getPileSize(), maxPieces);
                System.out.println("Computer removed " +  piecesTaken + " pieces!");
            } else {
                piecesTaken = getUserInput("Enter the number of pieces to remove (1 to " + maxPieces + "): ", maxPieces, scanner, Board.getPileSize());
            }

            if (Board.removePieces(piecesTaken)) {
                // Switch to the next player
                switchPlayer();
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        // Announce the winner
        String winner = (currentPlayerNumber == 1) ? player1 : player2;
        String notWinner = (currentPlayerNumber == 1) ? player2 : player1;

        System.out.println("\n" + winner + " wins! " + notWinner + " was forced to take the last piece.");
        updateScore(winner);


        String trophy = "  ___________\n"
        + " '._==_==_=_.'\n"
        + " .-\\:      /-.'\n"
        + "| (|:.     |) |\n"
        + " '-|:.     |-'\n"
        + "   \\::.    /\n"
        + "    '::. .'\n"
        + "    _.' '._\n"
        + "   ` " + winner + " `\n"
        + "   \"\"\"\"\"\"\"\"\"";

        System.out.println(trophy);

        // Show scores
        displayScores();

        // Offer play again option
        System.out.print("Play again? (yes/no): ");
        if (scanner.next().equalsIgnoreCase("yes")) {
            playAgain();
        } else {
            System.out.println("Thank you for playing!");
        }
        scanner.close();
    }

    /**
     * Calculates the optimal move for the computer to guarantee a win.
     * @param pileSize the current number of pieces in the pile
     * @param maxPieces the maximum number of pieces the computer can take
     * @return the number of pieces the computer should take to win
     */
    private int computeOptimalMove(int pileSize, int maxPieces) {
        int targetSize = 1;
        while (targetSize - 1 < pileSize) {
            targetSize *= 2;
        }
        targetSize = (targetSize / 2) - 1;

        int piecesToTake = pileSize - targetSize;
        if (piecesToTake <= 0 || piecesToTake > maxPieces) {
            piecesToTake = Math.max(1, Math.min(maxPieces, pileSize / 2));
        }
        return piecesToTake;
    }

    /**
     * Switches the turn to the other player.
     */
    private void switchPlayer() {
        currentPlayerNumber = (currentPlayerNumber == 1) ? 2 : 1;
        currentPlayer = (currentPlayerNumber == 1) ? player1 : player2;
    }

    /**
     * Prompts the user for input on the number of pieces to remove.
     * Validates input to ensure it is within the allowed range.
     * @param message the prompt message for the user
     * @param max the maximum number of pieces the player can take
     * @param scanner the Scanner instance to read user input
     * @param pileSize the current pile size
     * @return the number of pieces the player chooses to take
     */
    private int getUserInput(String message, int max, Scanner scanner, int pileSize) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. " + message);
            scanner.next(); // Clear the invalid input
        }
        int input = scanner.nextInt();
        if (pileSize == 1 && input == 1) {
            return input;
        }
        while (input < 1 || input > max) {
            System.out.print("Invalid input. " + message);
            input = scanner.nextInt();
        }
        return input;
    }

    /**
     * Updates the score for the winning player after each game.
     * @param winner the name of the player who won the game
     */
    private void updateScore(String winner) {
        if (winner.equals(player1)) {
            score1++;
        } else {
            score2++;
        }
    }

    /**
     * Displays the current scores of both players.
     */
    private void displayScores() {
        System.out.println("\nCurrent Scores:");
        System.out.println(player1 + ": " + score1);
        System.out.println(player2 + ": " + score2);
        playedGame = true;
    }

    /**
     * Resets the game board and starts a new game.
     */
    private void playAgain() {
        Board.populate(); // Reinitialize the board
        currentPlayerNumber = new Random().nextInt(2) + 1;
        currentPlayer = (currentPlayerNumber == 1) ? player1 : player2;
        play();
    }
}
