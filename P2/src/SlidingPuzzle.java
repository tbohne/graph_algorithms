import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SlidingPuzzle {

    private final int DIMENSION = 3;

    private int[][] board;
    private ArrayList<SlidingPuzzle> neighbors;


    public SlidingPuzzle() {
        this.generateInitialBoard();
        this.neighbors = new ArrayList<>();
        this.generateNeighbors();
    }

    public SlidingPuzzle(SlidingPuzzle puzzle) {
        this.board = new int[DIMENSION][DIMENSION];
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                this.board[i][j] = puzzle.board[i][j];
            }
        }
        this.neighbors = new ArrayList<>();
    }

    public int[][] getBoard() {
        return this.board;
    }

    public void generateNeighbors() {

        int xCoordEmpty = -1;
        int yCoordEmpty = -1;
        // find empty pos
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (this.board[i][j] == 0) {
                    xCoordEmpty = j;
                    yCoordEmpty = i;
                }
            }
        }

        // above case
        if (yCoordEmpty - 1 >= 0) {
            SlidingPuzzle nbr = new SlidingPuzzle(this);
            nbr.board[yCoordEmpty][xCoordEmpty] = this.board[yCoordEmpty - 1][xCoordEmpty];
            nbr.board[yCoordEmpty - 1][xCoordEmpty] = 0;
            this.neighbors.add(nbr);
        }

        // below case
        if (yCoordEmpty + 1 < DIMENSION) {
            SlidingPuzzle nbr = new SlidingPuzzle(this);
            nbr.board[yCoordEmpty][xCoordEmpty] = this.board[yCoordEmpty + 1][xCoordEmpty];
            nbr.board[yCoordEmpty + 1][xCoordEmpty] = 0;
            this.neighbors.add(nbr);
        }

        // left case
        if (xCoordEmpty - 1 >= 0) {
            SlidingPuzzle nbr = new SlidingPuzzle(this);
            nbr.board[yCoordEmpty][xCoordEmpty] = this.board[yCoordEmpty][xCoordEmpty - 1];
            nbr.board[yCoordEmpty][xCoordEmpty - 1] = 0;
            this.neighbors.add(nbr);
        }

        // right case
        if (xCoordEmpty + 1 < DIMENSION) {
            SlidingPuzzle nbr = new SlidingPuzzle(this);
            nbr.board[yCoordEmpty][xCoordEmpty] = this.board[yCoordEmpty][xCoordEmpty + 1];
            nbr.board[yCoordEmpty][xCoordEmpty + 1] = 0;
            this.neighbors.add(nbr);
        }
    }

    public int getRandomNumber(ArrayList<Integer> poolOfNumbers) {
        Collections.shuffle(poolOfNumbers);
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
                } else {
                    this.board[i][j] = 0;
                }
            }
        }
    }

    public SlidingPuzzle getNeighbor() {

        if (this.neighbors.size() == 0) { return null; }

        SlidingPuzzle nbr = this.neighbors.get(0);
        this.neighbors.remove(0);
        return nbr;
    }

    public boolean problemSolved() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (this.board[i][j] != i * DIMENSION + j + 1) {
                    if (!(i == DIMENSION - 1 && j == DIMENSION - 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean equals(Object other) {
        if (other == null) { return false; }

        if (other instanceof SlidingPuzzle) {

            for (int i = 0; i < DIMENSION; i++) {
                for (int j = 0; j < DIMENSION; j++) {
                    if (((SlidingPuzzle) other).board[i][j] != this.board[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
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
