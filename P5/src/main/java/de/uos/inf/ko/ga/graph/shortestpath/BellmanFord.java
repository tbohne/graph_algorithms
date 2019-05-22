package de.uos.inf.ko.ga.graph.shortestpath;

import de.uos.inf.ko.ga.graph.Graph;

/**
 * Shortest-path computation with Bellman-Ford for determining the distances
 * from a vertex to all other vertices in a directed graph.
 *
 */
public class BellmanFord {

	/**
	 * Computes distances to all vertices in a graph starting at a certain vertex.
	 * The distances returned is Double.POSITIVE_INFINITY if a vertex is not
	 * reachable from the start vertex.
	 * @param graph Input graph
	 * @param start Start vertex for the computation of the distances
	 * @return Array containing the distance d[v] from the start node for each vertex v
	 */
	public static double[] shortestPaths(Graph graph, int start) {

        double[] d = new double[graph.getVertexCount()];

        // init distances
        for (int i = 0; i < graph.getVertexCount(); i++) {
            d[i] = Double.POSITIVE_INFINITY;
        }
        d[start] = 0;

        // compute shortest paths
        for (int i = 1; i < graph.getVertexCount(); i++) {
            // iterate over all edges
            for (int src = 0; src < graph.getVertexCount(); src++) {
                for (int dest : graph.getSuccessors(src)) {
                    if (d[src] != Double.POSITIVE_INFINITY && d[src] + graph.getEdgeWeight(src, dest) < d[dest]) {
                        d[dest] = d[src] + graph.getEdgeWeight(src, dest);
                    }
                }
            }
        }
        return d;
	}
}
