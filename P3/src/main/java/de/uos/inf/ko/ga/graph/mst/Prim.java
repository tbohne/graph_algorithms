package de.uos.inf.ko.ga.graph.mst;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;

import java.util.ArrayList;
import java.util.PriorityQueue;

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

            System.out.println("edge: (" + predecessors.get(i) + ", " +  i + ")" + " - weight: " +  graph.getEdgeWeight(i, predecessors.get(i)));
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

		final Graph mst = new UndirectedGraphList();

        ArrayList<Integer> alreadyPartOfSpanningTree = new ArrayList<>();
        PriorityQueue<Edge> heap = new PriorityQueue<>();
        ArrayList<Integer> predecessors = new ArrayList<>();

        alreadyPartOfSpanningTree.add(0);
        predecessors.add(-1);
        for (int vertex : graph.getNeighbors(0)) {
            heap.add(new Edge(0, vertex, graph.getEdgeWeight(0, vertex)));
        }

        while (alreadyPartOfSpanningTree.size() != graph.getVertexCount()) {

            // input graph not connected --> return empty graph
            if (heap.size() == 0) { return new UndirectedGraphList(); }

            Edge minCostEdge = heap.poll();
            alreadyPartOfSpanningTree.add(minCostEdge.getVertexTwo());
            predecessors.add(minCostEdge.getVertexOne());

            for (int vertex : graph.getNeighbors(minCostEdge.getVertexTwo())) {

                if (!alreadyPartOfSpanningTree.contains(vertex)) {

                    boolean hasEdge = false;
                    ArrayList<Edge> toBeRemoved = new ArrayList<>();
                    ArrayList<Edge> toBeAdded = new ArrayList<>();

                    for (Edge e : heap) {
                        if (e.getVertexTwo() == vertex) {
                            if (e.getWeight() > graph.getEdgeWeight(minCostEdge.getVertexTwo(), vertex)) {
                                toBeRemoved.add(e);
                                toBeAdded.add(new Edge(minCostEdge.getVertexTwo(), vertex, graph.getEdgeWeight(minCostEdge.getVertexTwo(), vertex)));
                            }
                            hasEdge = true;
                        }
                    }

                    for (Edge e : toBeRemoved) {
                        heap.remove(e);
                    }
                    for (Edge e : toBeAdded) {
                        heap.add(e);
                    }

                    if (!hasEdge) {
                        heap.add(new Edge(minCostEdge.getVertexTwo(), vertex, graph.getEdgeWeight(minCostEdge.getVertexTwo(), vertex)));
                    }
                }
            }
        }

        mst.addVertices(graph.getVertexCount());

        for (int i = 1; i < graph.getVertexCount(); i++) {
            System.out.println("edge (" + predecessors.get(i) + ", " + alreadyPartOfSpanningTree.get(i)
            + " -- weight: " + graph.getEdgeWeight(predecessors.get(i), alreadyPartOfSpanningTree.get(i)));
            mst.addEdge(predecessors.get(i), alreadyPartOfSpanningTree.get(i), graph.getEdgeWeight(predecessors.get(i), alreadyPartOfSpanningTree.get(i)));
        }

		return mst;
	}
}
