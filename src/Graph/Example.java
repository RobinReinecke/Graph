package Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Example {
    public static void main(String[] args) {
        Graph graph = new Graph();

        /// read file into graph
        // Je nachdem wo das Programm ausgeführt wird, Pfad anpassen
        File file = new File("src/graph.data");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // iterate every line
            while ((line = reader.readLine()) != null) {
                String[] object = line.split(",");
                if (object.length != 3) {
                    System.err.println("\"" + line + "\" is not an edge!");
                }
                Node firstNode = graph.addNode(object[0]);
                Node secondNode = graph.addNode(object[1]);
                // add nodes and edges to graph

                graph.addEdge(new Edge(firstNode, secondNode, Integer.parseInt(object[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!graph.checkConnectivity()) {
            System.out.println("Check connectivity returned false, but graph is connected!");
        }

        System.out.println("Expected result:");
        System.out.println("Path to A: A");
        System.out.println("Path to B: A -> B");
        System.out.println("Path to C: A -> C");
        System.out.println("Path to D: A -> B -> D");
        System.out.println("Path to E: A -> B -> D -> E");
        System.out.println("Path to F: A -> B -> D -> F");

        // Hier kann eigener Code eingefügt werden, um die gefundenen
        // kürzesten Pfade auszugeben.
        HashSet<Node> nodes = graph.getNodes();

        Node nodeA = null;

        //get node A
        for (Node node : nodes) {
            if (node.getName().equals("A")) {
                nodeA = node;
            }
        }

        HashMap<Node, Edge> shortestPaths = graph.calculateShortestPaths(nodeA);
        System.out.println("\nResult:");
        System.out.println("Path to A: A");
        System.out.println("Path to B: " + pathTo("B", shortestPaths));
        System.out.println("Path to C: " + pathTo("C", shortestPaths));
        System.out.println("Path to D: " + pathTo("D", shortestPaths));
        System.out.println("Path to E: " + pathTo("E", shortestPaths));
        System.out.println("Path to F: " + pathTo("F", shortestPaths));

    }

    /**
     * Recursive get shortest paths to the passed node from the passed shortestPaths
     *
     * @param name          Node
     * @param shortestPaths shortest paths
     * @return String with shortest path
     */
    private static String pathTo(String name, HashMap<Node, Edge> shortestPaths) {
        //loop over shortest paths
        for (Map.Entry<Node, Edge> entry : shortestPaths.entrySet()) {
            Node node = entry.getKey();
            //search for correct node
            if (node.getName().equals(name)) {
                //get shortest path recursive
                Edge edge = entry.getValue();

                //start node
                if (edge == null) {
                    return name;
                }
                //get adjacent node
                Node adjacentNode = edge.getFirstNode() == node ? edge.getSecondNode() : edge.getFirstNode();

                return pathTo(adjacentNode.getName(), shortestPaths) + " -> " + name;
            }
        }
        return "";
    }
}