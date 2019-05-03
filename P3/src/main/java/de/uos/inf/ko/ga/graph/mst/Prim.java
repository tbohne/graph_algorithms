package de.uos.inf.ko.ga.graph.mst;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;

import java.util.*;

public class Prim {

    /**
     * Returns the cheapest vertex to be added to the MST next (used in the list implementation).
     *
     * @param currentCosts - array containing the current costs to reach each vertex
     * @param vertices     - the list of vertices that are already part of the MST
     * @return vertex to be added to the MST next
     */
    public static int getCheapestNextVertex(double[] currentCosts, List<Integer> vertices) {

        double min = Integer.MAX_VALUE;
        int cheapestVertex = -1;

        for (int vertex = 0; vertex < currentCosts.length; vertex++) {
            if (!vertices.contains(vertex) && currentCosts[vertex] < min) {
                min = currentCosts[vertex];
                cheapestVertex = vertex;
            }
        }
        return cheapestVertex;
    }

    /**
     * Updates the current costs to reach each vertex (used in the list implementation).
     *
     * @param graph           - the original graph
     * @param vertexToBeAdded - the vertex to be added
     * @param vertices        - the list of vertices that are already part of the MST
     * @param currentCosts    - array containing the current costs to reach each vertex
     * @param predecessors    - array containing the predecessor of each vertex
     */
    public static void updateCurrentCosts(
        Graph graph, int vertexToBeAdded, List<Integer> vertices, double[] currentCosts, int[] predecessors
    ) {
        for (int vertex = 0; vertex < graph.getVertexCount(); vertex++) {

            if (graph.hasEdge(vertexToBeAdded, vertex) && !vertices.contains(vertex)
                && graph.getEdgeWeight(vertexToBeAdded, vertex) < currentCosts[vertex]) {

                predecessors[vertex] = vertexToBeAdded;
                currentCosts[vertex] = graph.getEdgeWeight(vertexToBeAdded, vertex);
            }
        }
    }

    /**
	 * Computes a minimum spanning tree of an undirected graph using Prim's algorithm.
	 * @param graph Input graph
	 * @return Minimum spanning tree of the input graph
	 */
	public static Graph minimumSpanningTreeList(Graph graph) {
		assert(graph != null);
		assert(!graph.isDirected());

        int[] predecessors = new int[graph.getVertexCount()];
        double[] currentCosts = new double[graph.getVertexCount()];
        List<Integer> vertices = new ArrayList<>();

        // init predecessors and costs
        for (int i = 0; i < graph.getVertexCount(); i++) {
            currentCosts[i] = Double.POSITIVE_INFINITY;
            predecessors[i] = -1;
        }
        currentCosts[0] = 0.0;

        while (vertices.size() < graph.getVertexCount()) {
            int vertexToBeAdded = getCheapestNextVertex(currentCosts, vertices);
            vertices.add(vertexToBeAdded);
            updateCurrentCosts(graph, vertexToBeAdded, vertices, currentCosts, predecessors);
        }

        Graph mst = new UndirectedGraphList();
        mst.addVertices(graph.getVertexCount());

        for (int i = 1; i < graph.getVertexCount(); i++) {
            // input graph not connected --> return empty graph
            if (predecessors[i] == -1) { return new UndirectedGraphList(); }

            mst.addEdge(predecessors[i], i, graph.getEdgeWeight(i, predecessors[i]));
        }
        return mst;
	}

    /**
     * Computes a minimum spanning tree of an undirected graph using Prim's algorithm.
     * @param graph Input graph
     * @return Minimum spanning tree of the input graph
     */
    public static Graph minimumSpanningTreeHeap(Graph graph) {
        assert(graph != null);
        assert(!graph.isDirected());

        List<Edge> edges = new ArrayList<>();
        List<Integer> vertices = new ArrayList<>();
        double[] currentCosts = new double[graph.getVertexCount()];
        int[] predecessors = new int[graph.getVertexCount()];
        PriorityQueue<Edge> heap = new PriorityQueue<>();

        // init costs
        for (int i = 0; i < graph.getVertexCount(); i++) {
            currentCosts[i] = Double.POSITIVE_INFINITY;
        }
        vertices.add(0);

        // add all neighbors of vertex 0 to the heap
        List<Integer> neighbors = graph.getNeighbors(0);
        for (int nbr : neighbors) {
            predecessors[nbr] = 0;
            currentCosts[nbr] = graph.getEdgeWeight(0, nbr);
            heap.add(new Edge(0, nbr, currentCosts[nbr]));
        }

        while (vertices.size() < graph.getVertexCount()) {

            if (heap.size() == 0) { return new UndirectedGraphList(); }

            Edge minCostEdge = heap.poll();
            int newVertex = minCostEdge.getVertexTwo();
            if (vertices.contains(newVertex)) { continue; }
            vertices.add(newVertex);
            edges.add(new Edge(predecessors[newVertex], newVertex, graph.getEdgeWeight(predecessors[newVertex], newVertex)));

            for (int nbr : graph.getNeighbors(newVertex)) {
                if (!vertices.contains(nbr)) {
                    if (graph.getEdgeWeight(newVertex, nbr) < currentCosts[nbr]) {
                        currentCosts[nbr] = graph.getEdgeWeight(newVertex, nbr);
                        predecessors[nbr] = newVertex;
                        Edge edge = new Edge(newVertex, nbr, currentCosts[nbr]);

                        heap.remove(edge);
                        heap.add(edge);
                    }
                }
            }
        }
        Graph mst = new UndirectedGraphList();
        mst.addVertices(graph.getVertexCount());
        for (Edge e : edges) {
            mst.addEdge(e.getVertexOne(), e.getVertexTwo(), e.getWeight());
        }
        return mst;
    }
}
