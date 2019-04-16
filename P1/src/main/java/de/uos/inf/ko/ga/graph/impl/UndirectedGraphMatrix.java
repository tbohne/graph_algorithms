package de.uos.inf.ko.ga.graph.impl;

import java.util.ArrayList;
import java.util.List;

import de.uos.inf.ko.ga.graph.Graph;

/**
 * Implementation of a undirected graph with a matrix representation of the edges.
 *
 */
public class UndirectedGraphMatrix implements Graph {

	List<List<Double>> adjacencyMatrix;

	public UndirectedGraphMatrix() {
	    this.adjacencyMatrix = new ArrayList<>();
    }

	@Override
	public void addEdge(int start, int end) {
	    this.addEdge(start, end, 1.0);
	}

	@Override
	public void addEdge(int start, int end, double weight) {
        if (start >= 0 && start < this.adjacencyMatrix.size() && end < this.adjacencyMatrix.size()) {
            this.adjacencyMatrix.get(start).set(end, weight);
            this.adjacencyMatrix.get(end).set(start, weight);
        }
	}

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

	@Override
	public void addVertices(int n) {
        for (int i = 0; i < n; i++) {
            this.addVertex();
        }
	}

	@Override
	public List<Integer> getNeighbors(int v) {
		return this.getPredecessors(v);
	}

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

	@Override
	public int getVertexCount() {
		return this.adjacencyMatrix.size();
	}

	@Override
	public double getEdgeWeight(int start, int end) {
        return this.adjacencyMatrix.get(start).get(end);
	}

	@Override
	public boolean hasEdge(int start, int end) {
        if (start < this.adjacencyMatrix.size() && end < this.adjacencyMatrix.size()) {
            return this.getSuccessors(start).contains(end);
        }
        return false;
	}

	@Override
	public void removeEdge(int start, int end) {
        this.adjacencyMatrix.get(start).set(end, Double.POSITIVE_INFINITY);
        this.adjacencyMatrix.get(end).set(start, Double.POSITIVE_INFINITY);
	}

	@Override
	public void removeVertex() {
        if (!this.adjacencyMatrix.isEmpty()) {
            this.adjacencyMatrix.remove(this.adjacencyMatrix.size() - 1);
        }
	}

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

	@Override
	public boolean isDirected() {
		return false;
	}
}
