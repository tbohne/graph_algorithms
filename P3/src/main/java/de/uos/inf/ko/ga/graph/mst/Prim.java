package de.uos.inf.ko.ga.graph.mst;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;

import java.util.*;

public class Prim {


    public static int getCheapestNextVertex(ArrayList<Double> currentCosts, ArrayList<Integer> alreadyPartOfSpanningTree) {

        double min = Integer.MAX_VALUE;
        int cheapestVertex = -1;

        for (int vertex = 0; vertex < currentCosts.size(); vertex++) {
            if (!alreadyPartOfSpanningTree.contains(vertex) && currentCosts.get(vertex) < min) {
                min = currentCosts.get(vertex);
                cheapestVertex = vertex;
            }
        }
        return cheapestVertex;
    }

    public static void updateCurrentCosts(
        Graph graph, int vertexToBeAdded, ArrayList<Integer> alreadyPartOfSpanningTree, ArrayList<Double> currentCosts, ArrayList<Integer> predecessors
    ) {
        for (int vertex = 0; vertex < graph.getVertexCount(); vertex++) {

            if (graph.hasEdge(vertexToBeAdded, vertex) && !alreadyPartOfSpanningTree.contains(vertex)
                    && graph.getEdgeWeight(vertexToBeAdded, vertex) < currentCosts.get(vertex)) {

                predecessors.set(vertex, vertexToBeAdded);
                currentCosts.set(vertex, graph.getEdgeWeight(vertexToBeAdded, vertex));
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

		final Graph mst = new UndirectedGraphList();

        ArrayList<Integer> predecessors = new ArrayList<>();
        ArrayList<Double> currentCosts = new ArrayList<>();
        ArrayList<Integer> alreadyPartOfSpanningTree = new ArrayList<>();

        for (int i = 0; i < graph.getVertexCount(); i++) {
            currentCosts.add(Double.MAX_VALUE);
            predecessors.add(-1);
        }
        currentCosts.set(0, 0.0);

        while (alreadyPartOfSpanningTree.size() != graph.getVertexCount()) {
            int vertexToBeAdded = getCheapestNextVertex(currentCosts, alreadyPartOfSpanningTree);
            alreadyPartOfSpanningTree.add(vertexToBeAdded);
            updateCurrentCosts(graph, vertexToBeAdded, alreadyPartOfSpanningTree, currentCosts, predecessors);
        }

        mst.addVertices(graph.getVertexCount());
        for (int i = 1; i < graph.getVertexCount(); i++) {

            // input graph not connected --> return empty graph
            if (predecessors.get(i) == -1) { return new UndirectedGraphList(); }

//            System.out.println("edge: (" + predecessors.get(i) + ", " +  i + ")" + " - weight: " +  graph.getEdgeWeight(i, predecessors.get(i)));
            mst.addEdge(predecessors.get(i), i, graph.getEdgeWeight(i, predecessors.get(i)));
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
        Set<Integer> vertices = new HashSet<>();
        double[] currentCosts = new double[graph.getVertexCount()];
        int[] predecessors = new int[graph.getVertexCount()];

        PriorityQueue<Edge> heap = new PriorityQueue<>();

        for (int i = 0; i < graph.getVertexCount(); i++) {
            currentCosts[i] = Double.POSITIVE_INFINITY;
        }
        vertices.add(0);

        // add all neighbors of 0 to the heap
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
        final Graph mst = new UndirectedGraphList();
        mst.addVertices(graph.getVertexCount());
        for (Edge e : edges) {
            mst.addEdge(e.getVertexOne(), e.getVertexTwo(), e.getWeight());
        }
        return mst;
    }
}
