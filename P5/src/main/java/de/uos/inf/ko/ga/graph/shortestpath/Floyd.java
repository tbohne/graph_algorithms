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

        double[][] d = new double[graph.getVertexCount()][graph.getVertexCount()];

        // init distance matrix
        for (int i = 0; i < graph.getVertexCount(); i++) {
            for (int j = 0; j < graph.getVertexCount(); j++) {
                d[i][j] = graph.getEdgeWeight(i, j);
            }
        }

        // compute shortest paths
        for (int i = 0; i < graph.getVertexCount(); i++) {
            for (int j = 0; j < graph.getVertexCount(); j++) {
                for (int k = 0; k < graph.getVertexCount(); k++) {
                    if (d[j][i] + d[i][k] < d[j][k]) {
                        d[j][k] = d[j][i] + d[i][k];
                    }
                }
            }
        }
        return d;
	}
}
