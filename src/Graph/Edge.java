package Graph;

class Edge {
    /**
     * First node
     */
    private Node firstNode;

    /**
     * Second node
     */
    private Node secondNode;

    /**
     * Weight of this edge
     */
    private int weight;

    public Edge(Node firstNode, Node secondNode, int weight) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.weight = weight;
    }

    /**
     * Get first node
     *
     * @return first node
     */
    public Node getFirstNode() {
        return firstNode;
    }

    /**
     * Get second node
     *
     * @return second node
     */
    public Node getSecondNode() {
        return secondNode;
    }

    /**
     * Get weight
     *
     * @return weight
     */
    public int getWeight() {
        return weight;
    }
}