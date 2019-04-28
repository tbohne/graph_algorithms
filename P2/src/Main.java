import java.util.Stack;

public class Main {

    public static void main(String[] args) {

        SlidingPuzzle puzzle = new SlidingPuzzle();
        System.out.println(puzzle);


        System.out.println(depthLimitedSearch(puzzle, 30));

    }

    public static boolean depthLimitedSearch(SlidingPuzzle puzzle, int depth) {

        if (puzzle.problemSolved()) { return true; }

        Stack stack = new Stack<SlidingPuzzle>();
        stack.push(puzzle);

        while (!stack.isEmpty()) {

            SlidingPuzzle current = (SlidingPuzzle) stack.peek();
            SlidingPuzzle neighbor = current.getNeighbor();

            System.out.println("##############");
            System.out.println(current);
            System.out.println(neighbor);
            System.out.println("##############");

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

}
