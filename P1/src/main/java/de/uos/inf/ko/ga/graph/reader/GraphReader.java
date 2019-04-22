package de.uos.inf.ko.ga.graph.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;

/**
 * Provides functionalities to read graphs from the file system.
 *
 * @author Tim Bohne
 */
public class GraphReader {

    /**
     * Reads the matrix that represents the graph from the file system.
     *
     * @param file - the file to read from
     * @return the generated matrix
     */
    public static int[][] readMatrix(File file) {

        int numberOfVertices = 0;
        int[][] adjacencyMatrix = new int[numberOfVertices][numberOfVertices];

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            numberOfVertices = Integer.parseInt(reader.readLine().split(" ")[1].trim());
            adjacencyMatrix = new int[numberOfVertices][numberOfVertices];
            reader.readLine();
            String line = reader.readLine();

            while (line != null) {
                int idx = Integer.parseInt(line.split(":")[0].trim());
                String[] row = line.split(":")[1].trim().split(" ");

                for (int i = 0; i < row.length; i++) {
                    if (!row[i].trim().equals("x")) {
                        adjacencyMatrix[idx][i] = Integer.parseInt(row[i].trim());
                    } else {
                        adjacencyMatrix[idx][i] = Integer.MIN_VALUE;
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return adjacencyMatrix;
    }

    /**
     * Adds the edges specified by the adjacency matrix to the given graph.
     *
     * @param adjacencyMatrix - the matrix containing the graph's edges
     * @param graph           - the graph the edges are added to
     */
    public static void addEdgesForGraph(int[][] adjacencyMatrix, Graph graph) {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] != Integer.MIN_VALUE) {
                    graph.addEdge(i, j, adjacencyMatrix[i][j]);
                }
            }
        }
    }

	/**
	 * Reads a directed graph from a file.
	 * @param f File to be read
	 * @return Directed graph
	 */
	public static Graph readDirectedGraph(File f) {
        DirectedGraphMatrix graph = new DirectedGraphMatrix();
        int[][] adjacencyMatrix = readMatrix(f);
        graph.addVertices(adjacencyMatrix.length);
        addEdgesForGraph(adjacencyMatrix, graph);
		return graph;
	}

	/**
	 * Reads an undirected graph from a file.
	 * @param f File to be read
	 * @return Undirected graph
	 */
	public static Graph readUndirectedGraph(File f) {
        UndirectedGraphMatrix graph = new UndirectedGraphMatrix();
        int[][] adjacencyMatrix = readMatrix(f);
        graph.addVertices(adjacencyMatrix.length);
        addEdgesForGraph(adjacencyMatrix, graph);
        return graph;
	}
}
