package de.uos.inf.ko.ga.graph.mst;

public class Edge implements Comparable<Edge> {

    private int vertexOne;
    private int vertexTwo;
    private double weight;

    /**
     * Constructor
     *
     * @param vertexOne - the edge's first vertex
     * @param vertexTwo - the edge's second vertex
     * @param weight    - the edge's weight
     */
    public Edge(int vertexOne, int vertexTwo, double weight) {
        this.vertexOne = vertexOne;
        this.vertexTwo = vertexTwo;
        this.weight = weight;
    }

    /**
     * Sets the edge's weight
     *
     * @param weight - the edge's weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Returns the first vertex of the edge.
     *
     * @return edge's first vertex
     */
    public int getVertexOne() {
        return this.vertexOne;
    }

    /**
     * Returns the second vertex of the edge.
     *
     * @return edge's second vertex
     */
    public int getVertexTwo() {
        return this.vertexTwo;
    }

    /**
     * Returns the weight of the edge.
     *
     * @return the edge's weight
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * Enables comparability for edges.
     *
     * @param other - the edge to compare with
     * @return 0 --> equal, -1 --> this edge smaller, 1 --> other edge smaller
     */
    @Override
    public int compareTo(Edge other) {
        if (this.weight < other.weight) {
            return -1;
        } else if (other.weight < this.weight) {
            return 1;
        }
        return 0;
    }
}
