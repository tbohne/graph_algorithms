package de.uos.inf.ko.ga.tsp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.*;

import org.junit.Test;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.reader.GraphReader;

public class TwoOptTest {

	private static final List<String> GRAPHS = Arrays.asList(
			"tsp_01.gra",
			"tsp_02.gra",
			"tsp_03.gra"
	);

    /**
     * Shuffles the given array.
     *
     * @param arr - array to be shuffled
     */
    private static void shuffleArray(int[] arr) {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }
    }

    /**
     * Creates an array containing the vertices of the specified graph.
     *
     * @param graph - graph to create vertex array for
     * @return created vertex array
     */
    private int[] createVertexArray(Graph graph) {
        int[] vertices = new int[graph.getVertexCount()];
        for (int i = 0; i < graph.getVertexCount(); i++) {
            vertices[i] = i;
        }
        return vertices;
    }

    /**
     * Returns the average tour lengths for the specified list of tour lengths.
     *
     * @param tourLengths - list of tour lengths to compute average length for
     * @return average tour length
     */
    private double getAverageTourLength(List<Double> tourLengths) {
        double sum = 0.0;
        for (double length : tourLengths) {
            sum += length;
        }
        return sum / tourLengths.size();
    }

    /**
     * Performs the two-opt test for the graphs in the resources directory.
     */
	@Test
	public void testRunTwoOptOnTestGraphs() {
		for (final String filename : GRAPHS) {
			final File fileGraph = new File("src/test/resources/" + filename);

            System.out.println("############################ " + filename + " ############################");

			try {
				Graph graph = GraphReader.readUndirectedGraph(fileGraph);
				assertNotNull(graph);

                int[] vertices = createVertexArray(graph);
                Tour bestTour = new Tour(graph, vertices);
                List<Double> tourLengths = new ArrayList();

                for (int i = 0; i < 100; i++) {
                    shuffleArray(vertices);
                    Tour tour = new Tour(graph, vertices);
                    tour = TwoOpt.iterativeTwoOpt(tour, false);
                    tourLengths.add(tour.getCosts());
                    if (tour.getCosts() < bestTour.getCosts()) {
                        bestTour = new Tour(tour);
                    }
                }

                System.out.println("best tour: " + bestTour);
                System.out.println("min tour length: " + Collections.min(tourLengths));
                System.out.println("max tour length: " + Collections.max(tourLengths));
                System.out.println("avg tour length: " + getAverageTourLength(tourLengths));

            } catch (Exception ex) {
                fail("caught an exception while computing TSP tours: " + ex);
            }
            System.out.println("####################################################################");
        }
	}
}
