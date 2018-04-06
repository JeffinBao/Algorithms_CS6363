package chap6;

import java.util.*;

/**
 * Author: baojianfeng
 * Date: 2018-03-20
 * Description: construct a graph from a csv file
 */
public class Graph {
    private List<Vertex> vertexList;
    private List<Edge> edgeList;
    private Map<Vertex, List<Vertex>> adjVertexMap;

    public Graph(List<Vertex> vertexList, List<Edge> edgeList, Map<Vertex, List<Vertex>> adjVertexMap) {
        this.vertexList = vertexList;
        this.edgeList = edgeList;
        this.adjVertexMap = adjVertexMap;
    }

    /**
     * check whether a graph is connected, using BSF
     * @return true if the graph is connected
     */
    public boolean connectedBSF() {
        // set to default value(false) before BSF operation, in order to make the vertex's initial status is not visited
        for (Vertex vertex : vertexList) {
            vertex.setVisited(false);
        }

        Vertex startVer = vertexList.get(0);
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(startVer);

        while (!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            vertex.setVisited(true);
            List<Vertex> adjList = adjVertexMap.get(vertex);
            for (Vertex ver : adjList) {
                if (!ver.getVisited()) // add not visited vertex to queue
                    queue.add(ver);
            }
        }

        for (Vertex vertex : vertexList) {
            if (!vertex.getVisited())
                return false;
        }

        return true;
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public Map<Vertex, List<Vertex>> getAdjVertexMap() {
        return adjVertexMap;
    }

    /**
     * Vertex class, used to store the name of a vertex
     */
    public static class Vertex implements Comparable<Vertex> {
        String name;
        Double key;  // distance between previous vertex and current vertex
        Vertex prev; // previous vertex of current vertex
        boolean visited; // whether current vertex is visited

        Vertex(String name) {
            this(name, null, null);
        }

        Vertex(String name, Double key, Vertex prev) {
            this.name = name;
            this.key = key;
            this.prev = prev;
        }

        public String getName() {
            return name;
        }

        /**
         * set the key(distance) of the current vertex
         * @param key distance to previous vertex
         */
        public void setKey(Double key) {
            this.key = key;
        }

        /**
         * get the distance of the current vertex to previous vertex
         * @return distance
         */
        public Double getKey() {
            return key;
        }

        /**
         * set the previous vertex of the current vertex
         * @param prev previous vertex
         */
        public void setPrev(Vertex prev) {
            this.prev = prev;
        }

        /**
         * get the previous vertex of the current vertex
         * @return previous vertex of the current vertex
         */
        public Vertex getPrev() {
            return prev;
        }

        /**
         * whether a vertex has been visited when checking connectivity
         * @param visited true if the vertex is visited
         */
        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        /**
         * get the status whether a vertex is visited
         * @return true if the vertex is visited
         */
        public boolean getVisited() {
            return visited;
        }

        /**
         * compare current vertex with vertex o
         * @param o vertex o
         * @return 1 if current vertex's key is greater than o's key
         *        -1 if current vertex's key is less than o's key
         *         0 if they are equal to each other
         */
        @Override
        public int compareTo(Vertex o) {
            double diff = this.key - o.key;
            if (diff > 0.0)
                return 1;
            else if (diff < 0.0)
                return -1;
            else
                return 0;
        }

        /**
         * The vertex with same name are considered the same.
         * @param obj obj
         * @return true if two vertex have the same name
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;

            if (!(obj instanceof Vertex))
                return false;

            Vertex vertex = (Vertex) obj;
            return this.name.equals(vertex.getName());
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

    /**
     * Edge class, used to store the distance between two vertices
     */
    public static class Edge implements Comparable<Edge> {
        Vertex start; // start vertex
        Vertex end;   // end vertex
        double weight;// distance between start and end vertex

        Edge(Vertex start, Vertex end, double weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        public Vertex getStart() {
            return start;
        }

        public Vertex getEnd() {
            return end;
        }

        public double getWeight() {
            return weight;
        }

        /**
         * compare the distance of two edges
         * @param o the edge to be compared
         * @return -1 if current edge's distance is less than the other edge's distance
         *         0  if current edge's distance is equal to the other edge's distance
         *         1  if current edge's distance is greater than the other edge's distance
         */
        @Override
        public int compareTo(Edge o) {
            double diff = this.weight - o.weight;
            if (diff < 0)
                return -1;
            else if (diff == 0)
                return 0;
            else
                return 1;
        }

        /**
         * override equals method, if two vertices are in reversed order,
         * they should also be recognised as same edge
         * @param obj obj
         * @return true if two edges are equal
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;

            if (!(obj instanceof Edge))
                return false;

            Edge e = (Edge) obj;

            return start.equals(e.getStart()) && end.equals(e.getEnd())
                    || start.equals(e.getEnd()) && end.equals(e.getStart());
        }
    }
}
