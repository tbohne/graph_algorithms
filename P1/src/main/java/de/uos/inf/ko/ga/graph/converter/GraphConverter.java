package de.uos.inf.ko.ga.graph.converter;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;

/**
 * Methods for converting between list and matrix representations of graphs.
 *
 * @author Tim Bohne
 */
public class GraphConverter {

    /**
     * Adds the edges that are part of the input graph to the output graph.
     *
     * @param inputGraph  - the graph the edges are copied from
     * @param outputGraph - the graph the edges are copied to
     */
    public static void addEdgesFromMatrixToList(Graph inputGraph, Graph outputGraph) {
        for (int vertex = 0; vertex < inputGraph.getVertexCount(); vertex++) {
            for (int neighbor : inputGraph.getSuccessors(vertex)) {
                outputGraph.addEdge(vertex, neighbor, inputGraph.getEdgeWeight(vertex, neighbor));
            }
        }
    }

	/**
	 * Constructs a graph represented by adjacency lists.
	 * Outputs an instance of DirectedGraphList if the input graph is directed, and outputs an instance of UndirectedGraphList otherwise. 
	 * The weights of the edges of the new graph are the same as the ones of the input graph.
	 * @param graph Graph to be converted
	 * @return graph Graph represented by adjacency list
	 */
	public static Graph toList(Graph graph) {
		if (graph.isDirected()) {
		    DirectedGraphList dirGraphList = new DirectedGraphList();
		    dirGraphList.addVertices(graph.getVertexCount());
		    addEdgesFromMatrixToList(graph, dirGraphList);
			return dirGraphList;
		} else {
            UndirectedGraphList undirGraphList = new UndirectedGraphList();
            undirGraphList.addVertices(graph.getVertexCount());
            addEdgesFromMatrixToList(graph, undirGraphList);
            return undirGraphList;
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
		    DirectedGraphMatrix dirGraphMatrix = new DirectedGraphMatrix();
		    dirGraphMatrix.addVertices(graph.getVertexCount());
            addEdgesFromMatrixToList(graph, dirGraphMatrix);
			return dirGraphMatrix;
		} else {
            UndirectedGraphMatrix undirGraphMatrix = new UndirectedGraphMatrix();
            undirGraphMatrix.addVertices(graph.getVertexCount());
            addEdgesFromMatrixToList(graph, undirGraphMatrix);
            return undirGraphMatrix;
		}
	}
}
