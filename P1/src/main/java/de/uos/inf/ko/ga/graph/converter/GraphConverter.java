package de.uos.inf.ko.ga.graph.converter;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;

/**
 * Methods for converting between list and matrix representations of graphs.
 */
public class GraphConverter {

	/**
	 * Constructs a graph represented by adjacency lists.
	 * Outputs an instance of DirectedGraphList if the input graph is directed, and outputs an instance of UndirectedGraphList otherwise. 
	 * The weights of the edges of the new graph are the same as the ones of the input graph.
	 * @param graph Graph to be converted
	 * @return graph Graph represented by adjacency list
	 */
	public static Graph toList(Graph graph) {
		if (graph.isDirected()) {
		    DirectedGraphList adjacencyList = new DirectedGraphList();
		    adjacencyList.addVertices(graph.getVertexCount());
		    for (int vertex = 0; vertex < graph.getVertexCount(); vertex++) {
		        for (int neighbor : graph.getSuccessors(vertex)) {
                    adjacencyList.addEdge(vertex, neighbor, graph.getEdgeWeight(vertex, neighbor));
                }
            }
			return adjacencyList;

		} else {

		    // TODO: adjust for undirected

            UndirectedGraphList adjacencyList = new UndirectedGraphList();
            adjacencyList.addVertices(graph.getVertexCount());

            for (int vertex = 0; vertex < graph.getVertexCount(); vertex++) {
                for (int neighbor : graph.getNeighbors(vertex)) {
                    adjacencyList.addEdge(vertex, neighbor, graph.getEdgeWeight(vertex, neighbor));
                }
            }
            return adjacencyList;
		}
	}

	/**
	 * Constructs a graph represented by an adjacency matrix.
	 * Outputs an instance of DirectedGraphMatrix if the input graph is directed, and outputs an instance of UndirectedGraphMatrix otherwise.
	 * The weights of the edges of the new graph are the same as the ones of the input graph. 
	 * @param graph Graph to be converted
	 * @return graph Graph represented by an adjacency matrix
	 */
	public static Graph toMatrix(Graph graph) {
		if (graph.isDirected()) {
		    DirectedGraphMatrix adjacencyMatrix = new DirectedGraphMatrix();
		    adjacencyMatrix.addVertices(graph.getVertexCount());
            for (int vertex = 0; vertex < graph.getVertexCount(); vertex++) {
                for (int neighbor : graph.getSuccessors(vertex)) {
                    adjacencyMatrix.addEdge(vertex, neighbor, graph.getEdgeWeight(vertex, neighbor));
                }
            }
			return adjacencyMatrix;
		} else {
            UndirectedGraphMatrix adjacencyMatrix = new UndirectedGraphMatrix();
            adjacencyMatrix.addVertices(graph.getVertexCount());
            for (int vertex = 0; vertex < graph.getVertexCount(); vertex++) {
                for (int neighbor : graph.getNeighbors(vertex)) {
                    adjacencyMatrix.addEdge(vertex, neighbor, graph.getEdgeWeight(vertex, neighbor));
                }
            }
            return adjacencyMatrix;
		}
	}

}
