package de.uos.inf.ko.ga.shortestpath;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.shortestpath.Floyd;
import de.uos.inf.ko.ga.graph.util.GraphGenerator;
import org.junit.Test;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.reader.GraphReader;
import de.uos.inf.ko.ga.graph.shortestpath.BellmanFord;

public class BellmanFordFloydTest {

	private static final List<String> GRAPHS = Arrays.asList(
			"floyd_01.gra",
			"floyd_02.gra",
			"floyd_03.gra"
	);

	private void printDistanceMatrix(double[][] distances) {
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances[i].length; j++) {
                if (distances[i][j] == Double.POSITIVE_INFINITY) {
                    System.out.print(" \u221e  ");
                } else {
                    System.out.print(distances[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    private boolean equalDistances(double[][] distancesOne, double[][] distancesTwo) {
        for (int row = 0; row < distancesOne.length; row++) {
            for (int col = 0; col < distancesOne[row].length; col++) {
                if (Math.abs(distancesOne[row][col] - distancesTwo[row][col]) > 0.01) {
                    return false;
                }
            }
        }
        return true;
    }

	/**
	 * Determine distances between all pairs of vertices with Bellman-Ford and Floyd
	 * and output the distances between all pairs.
	 */
	@Test
	public void testCompareBellmanFordAndFloyd() {
		for (final String filename : GRAPHS) {
			final File fileGraph = new File("src/test/resources/" + filename);
			
			try {
				final Graph graph = GraphReader.readDirectedGraph(fileGraph);
				assertNotNull(graph);

                System.out.println("### " + filename + " ##################################");
                double[][] distancesFloyd = Floyd.shortestPaths(graph);
                System.out.println("--- FLOYD ---");
                this.printDistanceMatrix(distancesFloyd);
                double[][] distancesBellmanFord = this.bellmanFordAllPairs(graph);
                System.out.println("--- BELLMAN-FORD ---");
                this.printDistanceMatrix(distancesBellmanFord);
                System.out.println();
                System.out.println("Both algorithms return the same results (distance matrices): "
                    + this.equalDistances(distancesFloyd, distancesBellmanFord));
                System.out.println("###################################################");

			} catch (Exception ex) {
				fail("caught an exception while computing shortest paths: " + ex);
			}
		}
	}
	
	/**
	 * Generate random graphs and report the running times of Bellman-Ford and Floyd.
	 */
	@Test
	public void testCompareRunningTimes() {
		final Random random = new Random();

		for (int n = 10; n < 500; n += 50) {

		    Graph graph = new UndirectedGraphMatrix();
            GraphGenerator.generateRandomGraph(graph, n, random, 0.25);

            System.out.println("--- " + n + " vertices ---");

            double startTime = System.currentTimeMillis();
            Floyd.shortestPaths(graph);
            System.out.println("runtime floyd: " + (System.currentTimeMillis() - startTime) / 1000.0);

            startTime = System.currentTimeMillis();
            this.bellmanFordAllPairs(graph);
            System.out.println("runtime bellman-ford: " + (System.currentTimeMillis() - startTime) / 1000.0);
		}
	}

	/**
	 * Executes Bellman-Ford with each vertex as the starting vertex.
	 * @param graph Input graph
	 * @return distance matrix with distances between all pairs of vertices
	 */
	private double[][] bellmanFordAllPairs(Graph graph) {
		final int n = graph.getVertexCount();
		
		double d[][] = new double[n][n];
		
		for (int u = 0; u < n; ++u) {
			d[u] = BellmanFord.shortestPaths(graph, u);
			assertNotNull(d[u]);
		}
		
		return d;
	}
}
