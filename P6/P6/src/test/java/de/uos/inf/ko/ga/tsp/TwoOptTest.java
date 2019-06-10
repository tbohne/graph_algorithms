package de.uos.inf.ko.ga.tsp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.*;

import de.uos.inf.ko.ga.graph.impl.DirectedGraphList;
import de.uos.inf.ko.ga.graph.util.GraphGenerator;
import org.junit.Test;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.reader.GraphReader;

public class TwoOptTest {

	private static final List<String> GRAPHS = Arrays.asList(
			"tsp_01.gra",
			"tsp_02.gra",
			"tsp_03.gra"
	);

    private static void shuffleArray(int[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    private int[] createVertexArray(Graph graph) {
        int[] vertices = new int[graph.getVertexCount()];
        for (int i = 0; i < graph.getVertexCount(); i++) {
            vertices[i] = i;
        }
        return vertices;
    }

    private double getAverageTourLength(List<Double> tourLengths) {
        double sum = 0.0;
        for (double length : tourLengths) {
            sum += length;
        }
        return sum / tourLengths.size();
    }

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
                    tour = TwoOpt.iterativeTwoOpt(tour, true);
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
