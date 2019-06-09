package de.uos.inf.ko.ga.tsp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

	@Test
	public void testRunTwoOptOnTestGraphs() {
		for (final String filename : GRAPHS) {
			final File fileGraph = new File("src/test/resources/" + filename);

            System.out.println("############################ " + filename + " ############################");

			try {

				final Graph graph = GraphReader.readUndirectedGraph(fileGraph);
				assertNotNull(graph);
				
				// TODO: generate 100 random TSP tours and call TwoOpt.iterativeTwoOpt() on them

                for (int i = 0; i < 1; i++) {
                    int[] vertices = createVertexArray(graph);
                    shuffleArray(vertices);
                    Tour tour = new Tour(graph, vertices);
                    TwoOpt.iterativeTwoOpt(tour, true);
//                    System.out.println(t);
                }
				
				// TODO: output minimum, maximum, and average tour length

			} catch (Exception ex) {
				fail("caught an exception while computing TSP tours: " + ex);
			}

            System.out.println("####################################################################");
			break;
        }
	}
}
