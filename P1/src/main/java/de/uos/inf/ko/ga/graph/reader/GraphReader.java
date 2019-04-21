package de.uos.inf.ko.ga.graph.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;

public class GraphReader {

    public static int[][] readMatrix(File file) {

        int numberOfVertices = 0;
        int[][] adjacencyMatrix = new int[numberOfVertices][numberOfVertices];

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            reader.readLine();
            numberOfVertices = Integer.parseInt(reader.readLine().split(" ")[1].trim());
            adjacencyMatrix = new int[numberOfVertices][numberOfVertices];

            String s = reader.readLine();

            while (s.length() > 0) {
                s = reader.readLine();
                if (s == null) { break; }
                int idx = Integer.parseInt(s.split(":")[0].trim());
                String[] arr = s.split(":")[1].trim().split(" ");

                for (int i = 0; i < arr.length; i++) {
                    if (!arr[i].trim().equals("x")) {
                        adjacencyMatrix[idx][i] = Integer.parseInt(arr[i].trim());
                    } else {
                        adjacencyMatrix[idx][i] = Integer.MIN_VALUE;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return adjacencyMatrix;
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
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] != Integer.MIN_VALUE) {
                    graph.addEdge(i, j, adjacencyMatrix[i][j]);
                }
            }
        }
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
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] != Integer.MIN_VALUE) {
                    graph.addEdge(i, j, adjacencyMatrix[i][j]);
                }
            }
        }
        return graph;
	}
}
