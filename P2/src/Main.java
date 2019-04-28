import java.util.Stack;

public class Main {

    static final int NUMBER_OF_CONFIGURATIONS = 10;
    static final int DEPTH = 30;

    public static boolean iterativeDeepeningDepthFirstSearch(SlidingPuzzle puzzle) {
        for (int i = 5; i < 30; i++) {
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
