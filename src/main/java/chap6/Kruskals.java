package chap6;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public Kruskals(Graph graph) {
        this.graph = graph;
        vertexList = graph.getVertexList();
        edgeList = graph.getEdgeList();
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

    public static void main(String[] args) {
        List<Graph.Edge> edgeList = new ArrayList<>();
        List<Graph.Vertex> vertexList = new ArrayList<>();

        String filePath = "";
        try {
            FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();

            while (line != null) {
                String[] strArr = line.split(",", 2);
                Graph.Vertex start = new Graph.Vertex(strArr[0]);
                vertexList.add(start);

                String[] placeDists = strArr[1].split(",");
                for (int i = 0; i < placeDists.length - 1; i += 2) {
                    Graph.Edge edge = new Graph.Edge(start, new Graph.Vertex(placeDists[i]), Double.valueOf(placeDists[i + 1]));
                    if (!edgeList.contains(edge))
                        edgeList.add(edge);
                }

                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Graph graph = new Graph(vertexList, edgeList);
        Kruskals kruskals = new Kruskals(graph);
        List<Graph.Edge> mst = kruskals.getMiniSpanTreeAscend();

        double distSum = 0;
        for (Graph.Edge edge : mst) {
            System.out.println("start: " + edge.getStart().getName() + " end: " + edge.getEnd().getName() + " distance: " + edge.getWeight());
            distSum += edge.getWeight();
        }
        System.out.println("distance sum: " + distSum);
    }
}
