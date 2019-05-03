package de.uos.inf.ko.ga.graph.mst;

public class Edge implements Comparable<Edge> {

    private int vertexOne;
    private int vertexTwo;
    private double weight;

    public Edge(int vertexOne, int vertexTwo, double weight) {
        this.vertexOne = vertexOne;
        this.vertexTwo = vertexTwo;
        this.weight = weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getVertexOne() {
        return this.vertexOne;
    }

    public int getVertexTwo() {
        return this.vertexTwo;
    }

    public double getWeight() {
        return this.weight;
    }

    public int compareTo(Edge s) {
        if (this.weight < s.weight) {
            return -1;
        } else if (s.weight < this.weight) {
            return 1;
        }
        return 0;
    }
}
