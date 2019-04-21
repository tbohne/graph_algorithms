package de.uos.inf.ko.ga.graph.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;

public class GraphReader {

	/**
	 * Reads a directed graph from a file.
	 * @param f File to be read
	 * @return Directed graph
	 */
	public static Graph readDirectedGraph(File f) {

        int numberOfVertices = 0;
        int[][] adjacencyMatrix = new int[numberOfVertices][numberOfVertices];

        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {

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

        DirectedGraphMatrix graph = new DirectedGraphMatrix();
        graph.addVertices(numberOfVertices);
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
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
		/* TODO: implement me! */
		return new UndirectedGraphMatrix();
	}

}
