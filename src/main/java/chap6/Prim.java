package chap6;

import java.util.*;

/**
 * Author: baojianfeng
 * Date: 2018-03-29
 * Description: find a minimum spanning tree using Prim's algorithm
 */
public class Prim {
    private List<Graph.Vertex> vertexList;
    private List<Graph.Edge> edgeList;
    private Map<Graph.Vertex, List<Graph.Vertex>> adjVertexListMap;
    private Graph.Vertex start; // use the first vertex in the vertex list as the start vertex

    public Prim(Graph graph) {
        this.vertexList = graph.getVertexList();
        this.edgeList = graph.getEdgeList();
        this.adjVertexListMap = graph.getAdjVertexMap();
        start = vertexList.get(0);
    }

    /**
     * get minimum spanning tree using Prim's algorithm
     * Note: set vertex visited, means it is already in the MST,
     * and no need to update the key in the later stage
     * @return minimum spanning tree edge list
     */
    public List<Graph.Edge> getMiniSpanTree() {
        BinaryHeap<Graph.Vertex> binaryHeap = initPQ(vertexList);
        List<Graph.Edge> mst = new ArrayList<>();

        start.setVisited(true);
        List<Graph.Vertex> adjVertexList = adjVertexListMap.get(start);
        for (Graph.Vertex adjVertex : adjVertexList) {
            Graph.Edge edgeAdj = new Graph.Edge(start, adjVertex, 0.0);
            double key = 0.0;
            //TODO not efficient, should be improved
            for (Graph.Edge edge : edgeList) {
                if (edge.equals(edgeAdj)) {
                    key = edge.getWeight();
                    break;
                }
            }

            adjVertex.setKey(key);
            adjVertex.setPrev(start);
            binaryHeap.decreaseKey(adjVertex);
        }

        while (!binaryHeap.isEmpty()) {
            Graph.Vertex vertex = binaryHeap.deleteMin();
            // set current selected vertex to be visited
            vertex.setVisited(true);

            Graph.Edge edge = new Graph.Edge(vertex, vertex.prev, vertex.key);
            mst.add(edge);

            List<Graph.Vertex> vertexAdjList = adjVertexListMap.get(vertex);
            for (Graph.Vertex adjVertex : vertexAdjList) {
                Graph.Edge edgeAdj = new Graph.Edge(vertex, adjVertex, 0.0);
                double distance = 0.0;
                // TODO not efficient, should be improved
                for (Graph.Edge edge1 : edgeList) {
                    if (edge1.equals(edgeAdj)) {
                        distance = edge1.getWeight();
                        break;
                    }
                }

                // adjVertex should not be visited, means it is not in the MST.
                if (!adjVertex.visited && distance < adjVertex.key) {
                    adjVertex.setKey(distance);
                    adjVertex.setPrev(vertex);
                    binaryHeap.decreaseKey(adjVertex);
                }
            }
        }

        return mst;
    }

    /**
     * initialize a priority queue
     * @param list vertex list in the graph
     * @return binary heap
     */
    private BinaryHeap<Graph.Vertex> initPQ(List<Graph.Vertex> list) {
        BinaryHeap<Graph.Vertex> binaryHeap = new BinaryHeap<>();

        // start from i = 1, since the first vertex is the starting point
        for (int i = 1; i < list.size(); i++) {
            Graph.Vertex vertex = list.get(i);
            vertex.setKey(Double.MAX_VALUE); // set initial key value to positive infinity, prev is null at this time
            binaryHeap.insert(vertex);
        }

        return binaryHeap;
    }

}
