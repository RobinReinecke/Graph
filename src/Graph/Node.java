package Graph;

import java.util.HashSet;

class Node {
    /**
     * Store the edges of this node in a HashSet, because inserting the the element twice is forbidden.
     */
    private HashSet<Edge> edges = new HashSet<>();

    private String name;

    public Node(String name) {
        this.name = name;
    }

    /**
     * Get name of Node
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get edges for this node.
     *
     * @return edges HashSet
     */
    public HashSet<Edge> getEdges() {
        return edges;
    }

    /**
     * Add edge to the node
     *
     * @param edge Edge to add
     * @return true if add was successful, false if not
     */
    public boolean addEdge(Edge edge) {
        //if edge is invalid
        if (edge == null) {
            return false;
        }
        return edges.add(edge);
    }

    /**
     * Check if node has the edge
     *
     * @param edge edge to check
     * @return true if it has the edge
     */
    public boolean hasEdge(Edge edge) {
        Node firstNode = edge.getFirstNode();
        Node secondNode = edge.getSecondNode();

        for (Edge currentEdge : edges) {
            Node currentFirstNode = currentEdge.getFirstNode();
            Node currentSecondNode = currentEdge.getSecondNode();
            if ((currentFirstNode == firstNode && currentSecondNode == secondNode)
                    || currentFirstNode == secondNode && currentSecondNode == firstNode) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove the edge from the node
     *
     * @param edge edge to remove
     * @return true if remove was successful
     */
    public boolean removeEdge(Edge edge) {
        return edges.remove(edge);
    }
}