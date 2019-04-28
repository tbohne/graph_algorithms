import java.util.Stack;

public class Main {

    public static void main(String[] args) {

        SlidingPuzzle puzzle = new SlidingPuzzle();
        System.out.println(puzzle);

    }

    public boolean depthLimitedSearch(SlidingPuzzle puzzle, int depth) {

        if (puzzle.problemSolved()) { return true; }

        Stack stack = new Stack<SlidingPuzzle>();
        stack.push(puzzle);

        while (!stack.isEmpty()) {

            SlidingPuzzle current = (SlidingPuzzle) stack.peek();
            SlidingPuzzle neighbor = current.getNeighbor();

        }

        return true;

    }

}
