import java.util.Stack;

/**
 * Performs a DLS on several randomly generated instances of a sliding puzzle.
 *
 * @author Tim Bohne
 */
public class Main {

    // specifies the number of different random board configurations to be generated
    static final int NUMBER_OF_CONFIGURATIONS = 10;
    // specifies the limit for the DLS
    static final int DEPTH = 30;

    /**
     * Iterative deepening depth first search implementation.
     *
     * @param puzzle - instance of the sliding puzzle to be solved
     * @return whether or not the sliding puzzle instance has been solved successfully
     */
    public static boolean iterativeDeepeningDepthFirstSearch(SlidingPuzzle puzzle) {
        for (int i = 5; i < DEPTH; i++) {
            puzzle = new SlidingPuzzle(puzzle);
            puzzle.generateNeighbors();
            System.out.println("DEPTH: " + i);
            if (depthLimitedSearch(puzzle, i)) {
                System.out.println("SUCCESS at limit " + i);
                return true;
            }
        }
        return false;
    }

    /**
     * Depth limited search implementation.
     *
     * @param puzzle - instance of the sliding puzzle to be solved
     * @param depth  - the depth limit for the DLS
     * @return whether or not the sliding puzzle instance has been solved successfully
     */
    public static boolean depthLimitedSearch(SlidingPuzzle puzzle, int depth) {

        if (puzzle.problemSolved()) { return true; }

        Stack stack = new Stack<SlidingPuzzle>();
        stack.push(puzzle);

        while (!stack.isEmpty()) {

            SlidingPuzzle current = (SlidingPuzzle) stack.peek();
            SlidingPuzzle neighbor = current.getNeighbor();

            if (neighbor == null) {
                stack.pop();
            } else {
                neighbor.generateNeighbors();

                if (neighbor.problemSolved()) { return true; }

                if (!stack.contains(neighbor) && stack.size() < depth) {
                    stack.push(neighbor);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        for (int configuration = 0; configuration < NUMBER_OF_CONFIGURATIONS; configuration++) {
            System.out.println("#############################################");
            System.out.println("configuration: " + configuration);
            System.out.println("depth: " + DEPTH);
            SlidingPuzzle puzzle = new SlidingPuzzle();
            System.out.println(puzzle);
            System.out.println("solved: " + depthLimitedSearch(puzzle, DEPTH));
            System.out.println("#############################################");
        }
    }
}
