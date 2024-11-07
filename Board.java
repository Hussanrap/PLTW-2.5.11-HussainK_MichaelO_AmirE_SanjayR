import java.util.Random;

public class Board {
    private static int pileSize;
    private static final int MIN_PILE_SIZE = 10;
    private static final int MAX_PILE_SIZE = 50;

    /**
     * Initializes the board with a random pile size between 10 and 50 pieces.
     */
    public static void populate() {
        Random random = new Random();
        pileSize = random.nextInt(MAX_PILE_SIZE - MIN_PILE_SIZE + 1) + MIN_PILE_SIZE;
    }

    /**
     * Displays the current pile size.
     */
    public static void display() {
        System.out.println("\nCurrent pile size: " + pileSize + " pieces");
    }

    /**
     * Removes a given number of pieces from the pile.
     * @param count Number of pieces to remove.
     * @return True if removal is successful, false otherwise.
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
     * Checks if the pile is empty, indicating the game has ended.
     * @return True if the pile is empty, false otherwise.
     */
    public static boolean isEmpty() {
        return pileSize <= 0;
    }

    /**
     * Gets the current pile size.
     * @return Current number of pieces in the pile.
     */
    public static int getPileSize() {
        return pileSize;
    }
}