/**
 * Project 2.5.11
 *
 * GameRunner Class for the Game of Nim
*/
public class GameRunner {
    public static void main(String[] args) {
        System.out.println("Welcome to the Game of Nim!");
        System.out.println("--- Your goal: don't be person to remove the last piece!");
        System.out.println("--- (if you would like to play against an AI, type \"Computer\" for Player 2!)\n");

        Board.populate();

        Game nim = new Game();
        nim.play();

    }
}
