import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SlidingPuzzle {

    private final int DIMENSION = 3;

    private int[][] board;

    public SlidingPuzzle() {
        this.generateInitialBoard();
    }

    public int getRandomNumber(ArrayList<Integer> poolOfNumbers) {
        Collections.shuffle(poolOfNumbers);
        System.out.println(poolOfNumbers);
        int number = poolOfNumbers.get(0);
        poolOfNumbers.remove(0);
        return number;
    }

    public void generateInitialBoard() {

        this.board = new int[DIMENSION][DIMENSION];

        ArrayList<Integer> numbersToBeAssigned = new ArrayList<>();
        for (int i = 1; i < DIMENSION * DIMENSION; i++) {
            numbersToBeAssigned.add(i);
        }

        int emptyPos = new Random().nextInt(8);

        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (i * DIMENSION + j != emptyPos) {
                    this.board[i][j] = this.getRandomNumber(numbersToBeAssigned);
                }

            }
        }
    }

    public String toString() {
        String board = "";
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (this.board[i][j] == 0) {
                    board += "  ";
                } else {
                    board += this.board[i][j] + " ";
                }
            }
            board += "\n";
        }
        return board;
    }

}
