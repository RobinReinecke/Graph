package Graph;

import java.util.*;

class Graph {

    /**
     * Store the nodes of this graph in a HashSet, because inserting the the element twice is forbidden.
     */
    private HashSet<Node> nodes = new HashSet<>();

    public HashSet<Node> getNodes() {
        return nodes;
    }

    /**
     * Adds node to graph if it is valid.
     *
     * @param name name of node to add
     * @return node if node added, null otherwise
     */
    public Node addNode(String name) {
        //if name is invalid
        if (name == null || name.trim().length() == 0) {
            return null;
        }

        //return existing node
        for (Node node : nodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }

        Node newNode = new Node(name);
        nodes.add(newNode);
        return newNode;
    }

    /**
     * Adds an edge to the graph if it is valid.
     *
     * @param edge edge to add
     * @return edge if edge added, null otherwise
     */
    public Edge addEdge(Edge edge) {
        //if edge is invalid
        if (edge == null || edge.getWeight() < 0) {
            return null;
        }

        Node firstNode = edge.getFirstNode();
        Node secondNode = edge.getSecondNode();

        //check if edge nodes exist in graph
        if (!(nodes.contains(firstNode) && nodes.contains(secondNode))
                //check if edge already exist or is loop
                || firstNode.hasEdge(edge) || secondNode.hasEdge(edge) || firstNode == secondNode) {
            return null;
        }

        firstNode.addEdge(edge);
        secondNode.addEdge(edge);
        return edge;
    }

    /**
     * Removes the given node.
     *
     * @param node node to remove
     * @return true if removed, false if it was not inside the graph
     */
    public boolean removeNode(Node node) {
        //if node is invalid
        if (node == null) {
            return false;
        }

        //check node exists in nodes
        if (nodes.remove(node)) {
            //remove edges from incident nodes
            for (Edge edge : node.getEdges()) {
                //get the node that is not the deleted node
                Node firstNode = edge.getFirstNode();
                if (firstNode == node) {
                    edge.getSecondNode().removeEdge(edge);
                } else {
                    firstNode.removeEdge(edge);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Removes the given edge.
     *
     * @param edge edge to remove
     * @return true if removed, false if it was not inside the graph
     */
    public boolean removeEdge(Edge edge) {
        //check if edge is valid
        if (edge == null) {
            return false;
        }

        Node firstNode = edge.getFirstNode();
        Node secondNode = edge.getSecondNode();

        //check if edge nodes exist in graph
        if (!(nodes.contains(firstNode) && nodes.contains(secondNode))) {
            return false;
        }

        firstNode.removeEdge(edge);
        secondNode.removeEdge(edge);
        return true;
    }

    /**
     * Checks whether the graph is connected or not.
     * This means that a path exists from any node to any other node.
     *
     * @return true if the graph is connected and false otherwise
     */
    public boolean checkConnectivity() {
        //check for first node, whether all other nodes are reachable
        for (Node node : nodes) {
            //make of copy of all node
            HashSet<Node> copyNodes = new HashSet<>(nodes);
            copyNodes.remove(node);

            //make a queue for BFS
            Queue<Node> nodeQueue = new LinkedList<>();
            nodeQueue.offer(node);

            //BFS loop
            while (!nodeQueue.isEmpty()) {
                Node currentNode = nodeQueue.peek();

                //check if there are still reachable nodes from currentNode
                for (Edge edge : currentNode.getEdges()) {
                    //get adjacent node from the edge
                    Node adjacentNode = edge.getFirstNode() == currentNode ? edge.getSecondNode() : edge.getFirstNode();

                    //if node is reachable
                    if (copyNodes.contains(adjacentNode)) {
                        nodeQueue.offer(adjacentNode);
                        copyNodes.remove(adjacentNode);
                    }
                }
                //remove the current node from queue
                nodeQueue.poll();
            }

            //if there are nodes left in the copy, they are unreachable from the current node
            if (copyNodes.size() > 0) {
                return false;
            }
            return true;
        }
        return true;
    }

    /**
     * @param firstNode the node where to start
     * @return every node mapped to the edge to reach it
     */
    // Rückgabewert auf genutzte Collection ändern!
    public HashMap<Node, Edge> calculateShortestPaths(Node firstNode) {
        if (firstNode == null) {
            return null;
        }

        //make of copy of all node
        HashSet<Node> copyNodes = new HashSet<>(nodes);

        //list with a pair of node and the distance
        //List because of sorting is easy and same values are allowed
        List<NodeDistance> distances = new ArrayList<>();
        //ad first node
        distances.add(new NodeDistance(firstNode, 0));

        //add all other nodes with maximum distance
        for (Node node : nodes) {
            if (!node.equals(firstNode)) {
                distances.add(new NodeDistance(node, Integer.MAX_VALUE));
            }
        }

        //Map of shortest paths. Map because of the fact, that adding the same key twice will override it
        HashMap<Node, Edge> shortestPaths = new HashMap<>();
        //add path to first node
        shortestPaths.put(firstNode, null);

        //loop over all nodes
        while (!copyNodes.isEmpty()) {
            //sort node distances
            distances.sort(NodeDistance::compareTo);
            //get and remove the node with the lowest distance
            NodeDistance smallestDistanceNodeDistance = distances.remove(0);
            Node smallestDistanceNode = smallestDistanceNodeDistance.getNode();

            //remove node from copy of all nodes, because we calculate the shortest path for the node now
            copyNodes.remove(smallestDistanceNode);

            //loop over the edges of the current node
            for (Edge edge : smallestDistanceNode.getEdges()) {
                //get adjacent node from the edge
                Node adjacentNode = edge.getFirstNode() == smallestDistanceNode ? edge.getSecondNode() : edge.getFirstNode();

                //if shortest path for this node isn't calculated yet
                if (copyNodes.contains(adjacentNode)) {
                    //calculate alternative distance
                    int alternativeDistance = smallestDistanceNodeDistance.getDistance() + edge.getWeight();

                    //search the NodeDistance object for the adjacentNode
                    for (NodeDistance nodeDistance : distances) {
                        Node currentNode = nodeDistance.getNode();

                        //if node found and distance is smaller than the current
                        if (currentNode.equals(adjacentNode) && nodeDistance.getDistance() > alternativeDistance) {
                            //save smallest distance
                            nodeDistance.setDistance(alternativeDistance);
                            //save the path to the node, override if path already exists
                            shortestPaths.put(currentNode, edge);
                            break;
                        }
                    }
                }
            }
        }
        return shortestPaths;
    }
}