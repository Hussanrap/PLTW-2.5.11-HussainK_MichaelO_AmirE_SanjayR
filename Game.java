import java.util.Random;
import java.util.Scanner;

public class Game {
    private String player1;
    private String player2;
    private String currentPlayer;
    private int currentPlayerNumber;
    private int score1;
    private int score2;
    private boolean playedGame;

    /**
     * Constructor initializes a new game and randomly selects the starting player.
     */
    public Game() {
        score1 = 0;
        score2 = 0;
    }

    /**
     * Main game loop where players take turns until the pile is empty.
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

        System.out.println("\n" + winner + " wins! " + notWinner+ " was forced to take the last piece.");
        updateScore(winner);

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
     * Switches to the next player.
     */
    private void switchPlayer() {
        currentPlayerNumber = (currentPlayerNumber == 1) ? 2 : 1;
        currentPlayer = (currentPlayerNumber == 1) ? player1 : player2;
    }


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
     * Updates the score for the winning player.
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
     * Resets and starts a new game.
     */
    private void playAgain() {
        Board.populate(); // Reinitialize the board
        currentPlayerNumber = new Random().nextInt(2) + 1;
        currentPlayer = (currentPlayerNumber == 1) ? player1 : player2;
        play();
    }
}
