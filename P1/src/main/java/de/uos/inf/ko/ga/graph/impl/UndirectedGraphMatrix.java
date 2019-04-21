package de.uos.inf.ko.ga.graph.impl;

import java.util.ArrayList;
import java.util.List;

import de.uos.inf.ko.ga.graph.Graph;

/**
 * Implementation of a undirected graph with a matrix representation of the edges.
 *
 * @author Tim Bohne
 */
public class UndirectedGraphMatrix implements Graph {

	List<List<Double>> adjacencyMatrix;

    /**
     * Constructor
     */
	public UndirectedGraphMatrix() {
	    this.adjacencyMatrix = new ArrayList<>();
    }

    /**
     * Method to add the edge from start to end to the graph.
     * Adding self-loops is not allowed.
     * @param start start vertex
     * @param end   end vertex
     */
	@Override
	public void addEdge(int start, int end) {
	    this.addEdge(start, end, 1.0);
	}

    /**
     * Method to add the edge with the given weight to the graph from start to end.
     * Adding self-loops is not allowed.
     * @param start  number of start vertex
     * @param end    number of end vertex
     * @param weight weight of edge
     */
	@Override
	public void addEdge(int start, int end, double weight) {
        if (start >= 0 && start < this.adjacencyMatrix.size() && end < this.adjacencyMatrix.size()) {
            this.adjacencyMatrix.get(start).set(end, weight);
            this.adjacencyMatrix.get(end).set(start, weight);
        }
	}

    /**
     * Method to add a vertex to the graph.
     */
	@Override
	public void addVertex() {
        for (List<Double> row : this.adjacencyMatrix) {
            row.add(Double.POSITIVE_INFINITY);
        }
        ArrayList<Double> newRow = new ArrayList<>();
        for (int i = 0; i <= this.adjacencyMatrix.size(); i++) {
            newRow.add(Double.POSITIVE_INFINITY);
        }
        this.adjacencyMatrix.add(newRow);
	}

    /**
     * Method to add multiple vertices to the graph.
     * @param n number of vertices to add
     */
	@Override
	public void addVertices(int n) {
        for (int i = 0; i < n; i++) {
            this.addVertex();
        }
	}

    /**
     * Returns all neighbors of the given vertex v (all vertices i with {i,v} in E or (i,v) or (v,i) in E).
     * @param v vertex whose neighbors shall be returned
     * @return List of vertices adjacent to v
     */
	@Override
	public List<Integer> getNeighbors(int v) {
		return this.getPredecessors(v);
	}

    /**
     * Returns a list containing all predecessors of v.
     * @param v vertex id
     * @return list containing all predecessors of v
     */
	@Override
	public List<Integer> getPredecessors(int v) {
        // col
        ArrayList<Integer> predecessors = new ArrayList<>();
        for (int i = 0; i < this.adjacencyMatrix.size(); i++) {
            if (this.adjacencyMatrix.get(i).get(v) < Double.POSITIVE_INFINITY) {
                predecessors.add(i);
            }
        }
        return predecessors;
	}

    /**
     * Returns a list containing all successors v.
     * @param v vertex id
     * @return list containing all edges starting in v
     */
	@Override
	public List<Integer> getSuccessors(int v) {
        // row
        ArrayList<Integer> successors = new ArrayList<>();
        for (int i = 0; i < this.adjacencyMatrix.size(); i++) {
            if (this.adjacencyMatrix.get(v).get(i) < Double.POSITIVE_INFINITY) {
                successors.add(i);
            }
        }
        return successors;
	}

    /**
     * Method to get the number of vertices.
     * @return number of vertices
     */
	@Override
	public int getVertexCount() {
		return this.adjacencyMatrix.size();
	}

    /**
     * Method to get the weight of the edge {start, end} / the arc (start, end).
     * @param start start vertex of edge / arc
     * @param end   end vertex of edge / arc
     * @return Double.POSITIVE_INFINITY, if the edge does not exist, c_{start, end} otherwise
     */
	@Override
	public double getEdgeWeight(int start, int end) {
        return this.adjacencyMatrix.get(start).get(end);
	}

    /**
     * Method to test whether the graph contains the edge {start, end} / the arc (start, end).
     * @param start start vertex of the edge
     * @param end   end vertex of the edge
     */
	@Override
	public boolean hasEdge(int start, int end) {
        if (start < this.adjacencyMatrix.size() && end < this.adjacencyMatrix.size()) {
            return this.getSuccessors(start).contains(end);
        }
        return false;
	}

    /**
     * Method to remove an edge from the graph, defined by the vertices start and end.
     * @param start start vertex of the edge
     * @param end   end vertex of the edge
     */
	@Override
	public void removeEdge(int start, int end) {
        this.adjacencyMatrix.get(start).set(end, Double.POSITIVE_INFINITY);
        this.adjacencyMatrix.get(end).set(start, Double.POSITIVE_INFINITY);
	}

    /**
     * Method to remove the last vertex from the graph.
     */
	@Override
	public void removeVertex() {
        if (!this.adjacencyMatrix.isEmpty()) {
            this.adjacencyMatrix.remove(this.adjacencyMatrix.size() - 1);
        }
	}

    /**
     * Returns whether the graph is weighted.
     * A graph is weighted if an edge with weight different from 1.0 has been added to the graph.
     * @return true if the graph is weighted
     */
	@Override
	public boolean isWeighted() {
        for (List<Double> row : this.adjacencyMatrix) {
            for (double entry : row) {
                if (entry != Double.POSITIVE_INFINITY && entry != 1.0) {
                    return true;
                }
            }
        }
        return false;
	}

    /**
     * Returns whether the graph is directed.
     * @return true if the graph is directed
     */
	@Override
	public boolean isDirected() {
		return false;
	}
}
