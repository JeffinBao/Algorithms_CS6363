package chap6;

import java.io.*;
import java.util.*;

/**
 * Author: baojianfeng
 * Date: 2018-03-20
 * Description: implement Kruskals' algorithm.
 *              Two ways: 1. edges' distances are in ascending order.
 *                        2. edges' distances are in descending order.
 */
public class Kruskals {
    private Graph graph;
    private List<Graph.Vertex> vertexList;
    private List<Graph.Edge> edgeList;
    private Map<Graph.Vertex, List<Graph.Vertex>> adjVertexMap;

    public Kruskals(Graph graph) {
        this.graph = graph;
        vertexList = graph.getVertexList();
        edgeList = graph.getEdgeList();
        adjVertexMap = graph.getAdjVertexMap();
    }

    /**
     * get minimum spanning tree edges list,
     * the original edges are in ascending order
     * @return minimum spanning tree edges list
     */
    public List<Graph.Edge> getMiniSpanTreeAscend() {
        List<Graph.Edge> mst = new ArrayList<>();
        Collections.sort(edgeList); // sort the original edge list
        DisjSets ds = new DisjSets(vertexList.size()); // make set for every vertex

        for (Graph.Edge edge : edgeList) {
            // startIdx and endIdx are the index of start and end vertex of an edge
            int startIdx = vertexList.indexOf(edge.getStart());
            int endIdx = vertexList.indexOf(edge.getEnd());

            // after find operation , uSet and vSet are the set start and end vertext belong to
            int uSet = ds.find(startIdx);
            int vSet = ds.find(endIdx);

            if (uSet != vSet) {
                ds.union(uSet, vSet);
                mst.add(edge);
            }
        }

        return mst;

    }

    /**
     * get minimum spanning tree edges list,
     * the original edges are in descending order.
     * procedure: starts from the longest edge to the shortest edge,
     *            removes the edge which will not disconnected the graph
     * @return minimum spanning tree edges list
     */
    public List<Graph.Edge> getMiniSpanTreeDecend() {
        Collections.sort(edgeList);
        Collections.reverse(edgeList); // get the descending order(by distance) edge list

        for (int i = 0; i < edgeList.size(); ) {
            Graph.Edge edge = edgeList.remove(i);
            Graph.Vertex start = edge.getStart();
            Graph.Vertex end = edge.getEnd();

            // update adjacent list for each vertex if one edge is removed
            List<Graph.Vertex> adjStartList = adjVertexMap.get(start);
            adjStartList.remove(end);
            adjVertexMap.put(start, adjStartList);
            List<Graph.Vertex> adjEndList = adjVertexMap.get(end);
            adjEndList.remove(start);
            adjVertexMap.put(end, adjEndList);

            // if the removal of an edge causes the graph to be disconnected, add back the edge and adjacent vertices
            if (!graph.connectedBSF()) {
                edgeList.add(i, edge);

                // add back adjacent vertices
                adjStartList.add(end);
                adjVertexMap.put(start, adjStartList);
                adjEndList.add(start);
                adjVertexMap.put(end, adjEndList);

                i++; // i only increments by one when the edge is finally not removed!!!
            }
        }

        return edgeList;
    }

    public static void main(String[] args) {
        List<Graph.Edge> edgeList = new ArrayList<>();
        List<Graph.Vertex> vertexList = new ArrayList<>();
        Map<Graph.Vertex, List<Graph.Vertex>> adjVertexMap = new HashMap<>();

        String filePath = "";
        try {
            FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();

            while (line != null) {
                String[] strArr = line.split(",", 2);
                Graph.Vertex start = new Graph.Vertex(strArr[0]);
                // make sure that no two vertices with the same name added to vertexList
                // if a same name vertex already exists, retrieve that vertex and set it as 'start' vertex
                // it is very important, since we want to make sure that every name only matches to one vertex object,
                // in this way, when checking for connectivity(connectedBSF function in Graph.java) and setting a vertex
                // visited, all places(vertexList, adjVerMa[p) this vertex stored will get the updated vertex visit status.
                if (!vertexList.contains(start))
                    vertexList.add(start);
                else {
                    for (Graph.Vertex vertex : vertexList) {
                        if (vertex.equals(start)) {
                            start = vertex;
                            break;
                        }
                    }
                }

                List<Graph.Vertex> innerAdjVerList = new ArrayList<>(); // adjacent vertex list to the current 'start' vertex
                String[] placeDists = strArr[1].split(",");
                for (int i = 0; i < placeDists.length - 1; i += 2) {
                    Graph.Vertex end = new Graph.Vertex(placeDists[i]);
                    // make sure that no two vertices with the same name added to vertexList
                    // if a same name vertex already exists, retrieve that vertex and set it as 'end' vertex
                    if (!vertexList.contains(end))
                        vertexList.add(end);
                    else {
                        for (Graph.Vertex vertex : vertexList) {
                            if (vertex.equals(end)) {
                                end = vertex;
                                break;
                            }
                        }
                    }
                    innerAdjVerList.add(end);
                    Graph.Edge edge = new Graph.Edge(start, end, Double.valueOf(placeDists[i + 1]));
                    if (!edgeList.contains(edge))
                        edgeList.add(edge);
                }
                adjVertexMap.put(start, innerAdjVerList); // store the adjacent list to a specific 'start' vertex

                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Graph graph = new Graph(vertexList, edgeList, adjVertexMap);
        Kruskals kruskals = new Kruskals(graph);
        List<Graph.Edge> mst = kruskals.getMiniSpanTreeAscend();
//        List<Graph.Edge> mst = kruskals.getMiniSpanTreeDecend();

        double distSum = 0;
        for (Graph.Edge edge : mst) {
            System.out.println("start: " + edge.getStart().getName() + " end: " + edge.getEnd().getName() + " distance: " + edge.getWeight());
            distSum += edge.getWeight();
        }
        System.out.println("distance sum: " + distSum);
    }
}
