package de.uos.inf.ko.ga.graph.impl;

import java.util.*;

import de.uos.inf.ko.ga.graph.Graph;

/**
 * Implementation of a directed graph with a list representation of the edges.
 */
public class DirectedGraphList implements Graph {

    private List<Map<Integer, Double>> adjacencyList;

    public DirectedGraphList() {
        this.adjacencyList = new ArrayList<>();
    }

	@Override
	public void addEdge(int start, int end) {
        this.addEdge(start, end, 1.0);
	}

	@Override
	public void addEdge(int start, int end, double weight) {
	    if (start >= 0 && start < this.adjacencyList.size() && end < this.adjacencyList.size()) {
            this.adjacencyList.get(start).put(end, weight);
        }
	}

	@Override
	public void addVertex() {
        Map<Integer, Double> vertex = new HashMap<>();
		this.adjacencyList.add(vertex);
	}

	@Override
	public void addVertices(int n) {
		for (int i = 0; i < n; i++) {
            Map<Integer, Double> vertex = new HashMap<>();
            this.adjacencyList.add(vertex);
        }
	}

	@Override
	public List<Integer> getNeighbors(int v) {
        List<Integer> neighbors = this.getPredecessors(v);
        for (int successor : this.getSuccessors(v)) {
            if (!neighbors.contains(successor)) {
                neighbors.add(successor);
            }
        }
        return neighbors;
	}

	@Override
	public List<Integer> getPredecessors(int v) {
        ArrayList<Integer> predecessors = new ArrayList<>();
        for (int i = 0; i < this.adjacencyList.size(); i++) {
            if (i != v) {
                if (this.adjacencyList.get(i).keySet().contains(v)) {
                    predecessors.add(i);
                }
            }
        }
        return predecessors;
	}

	@Override
	public List<Integer> getSuccessors(int v) {
        ArrayList<Integer> successors = new ArrayList<>();
        for (int neighbor : this.adjacencyList.get(v).keySet()) {
            successors.add(neighbor);
        }
        return successors;
	}

	@Override
	public int getVertexCount() {
		return this.adjacencyList.size();
	}

	@Override
	public double getEdgeWeight(int start, int end) {
        if (hasEdge(start, end)) {
            return this.adjacencyList.get(start).get(end);
        }
        return Double.POSITIVE_INFINITY;
	}

	@Override
	public boolean hasEdge(int start, int end) {
        if (start < this.adjacencyList.size() && end < this.adjacencyList.size()) {
            return this.getSuccessors(start).contains(end);
        }
        return false;
	}

	@Override
	public void removeEdge(int start, int end) {
        if (this.hasEdge(start, end)) {
            this.adjacencyList.get(start).remove(end);
        }
	}

	@Override
	public void removeVertex() {
        if (!this.adjacencyList.isEmpty()) {
            List<Integer> predecessors = this.getPredecessors(this.getVertexCount() - 1);
            for (int pred : predecessors) {
                this.removeEdge(pred, this.getVertexCount() - 1);
            }
            this.adjacencyList.remove(this.getVertexCount() - 1);
        }
	}

	@Override
	public boolean isWeighted() {
        for (Map m : this.adjacencyList) {
            for (Object o : m.values()) {
                if ((double)o != 1.0) {
                    return true;
                }
            }
        }
        return false;
	}

	@Override
	public boolean isDirected() {
		return true;
	}
}
