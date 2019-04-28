import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Represents an instance of the sliding puzzle.
 *
 * @author Tim Bohne
 */
public class SlidingPuzzle {

    private final int DIMENSION = 3;

    private int[][] board;
    private ArrayList<SlidingPuzzle> neighbors;

    /**
     * Constructor
     */
    public SlidingPuzzle() {
        this.generateInitialBoard();
        this.neighbors = new ArrayList<>();
        this.generateNeighbors();
    }

    /**
     * Copy-Constructor
     *
     * @param puzzle - the sliding puzzle instance for which the board gets copied
     */
    public SlidingPuzzle(SlidingPuzzle puzzle) {
        this.board = new int[DIMENSION][DIMENSION];
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                this.board[i][j] = puzzle.board[i][j];
            }
        }
        this.neighbors = new ArrayList<>();
    }

    /**
     * Generates the neighbors for the current sliding puzzle board.
     */
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

    /**
     * Returns a random number from the specified pool of numbers
     * and removes it from the pool.
     *
     * @param poolOfNumbers - specified pool of numbers
     * @return a random number from the specified pool of numbers
     */
    public int getRandomNumber(ArrayList<Integer> poolOfNumbers) {
        Collections.shuffle(poolOfNumbers);
        int number = poolOfNumbers.get(0);
        poolOfNumbers.remove(0);
        return number;
    }

    /**
     * Generates a random initial board configuration for the sliding puzzle.
     */
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

    /**
     * Returns the current board.
     *
     * @return the board represented as two dimensional array
     */
    public int[][] getBoard() {
        return this.board;
    }

    /**
     * Returns the next neighbor and removes it from the list of remaining neighbors.
     *
     * @return the next neighbor
     */
    public SlidingPuzzle getNeighbor() {

        if (this.neighbors.size() == 0) { return null; }

        SlidingPuzzle nbr = this.neighbors.get(0);
        this.neighbors.remove(0);
        return nbr;
    }

    /**
     * Returns whether or not the current board configuration is the target configuration.
     *
     * @return true --> problem solved / false --> problem not yet solved
     */
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

    /**
     * Returns whether another instance of a sliding puzzle
     * equals this one in terms of the board configuration.
     *
     * @param other - the instance of a sliding puzzle this instance is compared to
     * @return whether or not the instances have an equal board configuration
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof SlidingPuzzle)) { return false; }

        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (((SlidingPuzzle) other).board[i][j] != this.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Visualized the instances board configuration.
     *
     * @return visualization of the board configuration
     */
    @Override
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
