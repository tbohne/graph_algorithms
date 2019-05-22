package de.uos.inf.ko.ga.graph.shortestpath;

import de.uos.inf.ko.ga.graph.Graph;

/**
 * Shortest-path computation with Floyd for determining the distances between
 * all pairs of vertices in a directed graph.
 *
 */
public class Floyd {

	/**
	 * Computes distances between all pairs of vertices.
	 * If a vertex is not reachable from another vertex, the corresponding entry
	 * of the distance matrix is Double.POSITIVE_INFINITY.
	 * @param graph Input graph
	 * @return Matrix of dimension n times n with entry d[i][j] being the distance from vertex i to vertex j
	 */
	public static double[][] shortestPaths(Graph graph) {
		throw new UnsupportedOperationException("method not implemented");
	}
}
