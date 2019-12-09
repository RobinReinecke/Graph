package Graph;

public class NodeDistance implements Comparable<NodeDistance> {
    /**
     * Distance
     */
    private int distance;

    /**
     * Node
     */
    private Node node;

    public NodeDistance(Node node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    /**
     * Get distance
     *
     * @return Distance
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Set distance
     *
     * @param distance new value
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Get node
     *
     * @return Node
     */
    public Node getNode() {
        return node;
    }

    /**
     * Sort NodeDistance by distance
     *
     * @param nodeDistance NodeDistance to compare to
     * @return compared distances
     */
    @Override
    public int compareTo(NodeDistance nodeDistance) {
        return Integer.compare(distance, nodeDistance.getDistance());
    }
}