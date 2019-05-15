package de.uos.inf.ko.ga.graph.shortestpath;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphList;

public class Dijkstra {

    private static final double EPS = 0.0001;

    /**
     * Computes distances of all vertices in a graph starting at a certain vertex.
     * The distances returned is Double.POSITIVE_INFINITY if a vertex is not
     * reachable from the start vertex.
     *
     * @param graph Input graph
     * @param start Start vertex for the computation of the distances
     * @return Array containing the distance d[v] from the start node for each vertex v
     */
    public static double[] shortestPaths(Graph graph, int start) {
        final int n = graph.getVertexCount();
        final double dist[] = new double[n];

        PriorityQueue<Integer> queue = new PriorityQueue<>(n - 1, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (Math.abs(dist[o1] - dist[o2]) <= EPS)
                    return 0;
                return dist[o1] < dist[o2] ? -1 : 1;
            }
        });
        for (int i = 0; i < n; i++) {
            if (i == start) {
                dist[i] = 0;
            } else {
                dist[i] = graph.getEdgeWeight(start, i);
            }
            queue.add(i);
        }

        while (!queue.isEmpty()) {
            int i = queue.poll();
            for (final int j : graph.getSuccessors(i)) {
                if (queue.contains(j)) {
                    double update = Math.min(dist[j], dist[i] + graph.getEdgeWeight(i, j));
                    if (update < dist[j]) {
                        queue.remove(j);
                        dist[j] = update;
                        queue.add(j);
                    }
                }
            }
        }

        return dist;
    }

    /**
     * Constructs a directed shortest-path tree from the distances of a shortest-path computation.
     * This method determines the edges used by shortest paths between the start vertex and
     * the remaining vertices. At first, it adds all edges outgoing from the start vertex, and
     * then does this for the newly visited vertices, and so on.
     *
     * @param graph Input graph
     * @param start Start vertex
     * @param dist  distances of the vertices from the start vertex
     * @return Graph containing edges of the shortest paths starting at the start vertex
     */
    public static Graph shortestPathGraphFromDistances(Graph graph, int start, double dist[]) {
        final int n = graph.getVertexCount();

        /* construct new graph with the same set of vertices */
        final Graph shortestPathTree = new DirectedGraphList();
        shortestPathTree.addVertices(n);

        /* queue containing the vertices that have been seen yet */
        final boolean seen[] = new boolean[n];
        final Queue<Integer> queue = new LinkedList<>();

        /* add the start vertex to the queue */
        seen[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            final int u = queue.poll();

            /* determine the neighbors of 'u' that are reachable from 'start' via 'u' in a shortest path */
            for (final int v : graph.getSuccessors(u)) {
                if (!seen[v]) {
                    /* test whether the value dist[v] obtained its value from the value dist[u] */
                    if (Math.abs(dist[u] + graph.getEdgeWeight(u, v) - dist[v]) <= EPS) {
                        seen[v] = true;
                        shortestPathTree.addEdge(u, v, graph.getEdgeWeight(u, v));
                        queue.add(v);
                    }
                }
            }
        }

        return shortestPathTree;
    }

}
