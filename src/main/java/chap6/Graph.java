package chap6;

import java.util.List;

/**
 * Author: baojianfeng
 * Date: 2018-03-20
 * Description: construct a graph from a csv file
 */
public class Graph {
    private List<Vertex> vertexList;
    private List<Edge> edgeList;

    public Graph(List<Vertex> vertexList, List<Edge> edgeList) {
        this.vertexList = vertexList;
        this.edgeList = edgeList;
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    /**
     * Vertex class, used to store the name of a vertex
     */
    public static class Vertex {
        String name;

        Vertex(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
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
