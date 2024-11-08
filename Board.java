import java.util.Random;

/**
 * The Board class represents the pile of pieces for the Game of Nim.
 * It manages the pile size and provides methods to manipulate and display the pile.
 */
public class Board {
    private static int pileSize; // The current number of pieces in the pile
    private static final int minPile = 10; // Minimum starting pile size
    private static final int maxPile = 50; // Maximum starting pile size

    /**
     * Initializes the board with a random pile size between the specified minimum and maximum limits.
     */
    public static void populate() {
        Random random = new Random();
        pileSize = random.nextInt(maxPile - minPile + 1) + minPile;
    }

    /**
     * Displays the current number of pieces in the pile.
     */
    public static void display() {
        System.out.println("\nCurrent pile size: " + pileSize + " pieces");
    }

    /**
     * Removes a specified number of pieces from the pile, if valid.
     * The removal count must be between 1 and half the pile size, inclusive.
     * 
     * @param count the number of pieces to remove
     * @return true if the removal is successful; false if the count is invalid
     */
    public static boolean removePieces(int count) {
        if (pileSize == 1) {
            if (count == 1) {
                pileSize -= count;
                return true;
            }
        }
        if (count >= 1 && count <= pileSize / 2) {
            pileSize -= count;
            return true;
        }
        return false;
    }

    /**
     * Checks if the pile is empty, which indicates the end of the game.
     * 
     * @return true if the pile size is zero or less, false otherwise
     */
    public static boolean isEmpty() {
        return pileSize <= 0;
    }

    /**
     * Retrieves the current number of pieces in the pile.
     * 
     * @return the current pile size
     */
    public static int getPileSize() {
        return pileSize;
    }
}