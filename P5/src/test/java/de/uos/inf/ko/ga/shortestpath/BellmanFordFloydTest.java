package de.uos.inf.ko.ga.shortestpath;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
				
				// TODO: compute distances between all pairs with Bellman-Ford and Floyd, and then output the distance matrices
				
				// TODO: test whether the values from both distance matrix are equal (use tolerance for comparing floating-point values)

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
		final int n = 500;

		long totalTimeBellmanFord = 0; // TODO: track time with System.currentTimeMillis()
		long totalTimeFloyd = 0;

		for (int inst = 0; inst < 10; ++inst) {
			// TODO: generate some random graph and report the running times of both algorithms
		}
		
		// TODO: output some kind of report
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
