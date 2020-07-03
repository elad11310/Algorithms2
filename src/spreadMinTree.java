import com.sun.javafx.geom.Edge;

import java.awt.*;
import java.util.*;

/**
 * this class represents 3 algorithms for finding a spread minimal tree by given a graph (the graph should be connected)
 * input : graph with weights on edges.
 * output : minimal spread tree.
 * <p>
 * first algorithm - kruskal - we gonna sort the edges in ascending form (smallest first,largest last), then
 * we gonna take each time the minimum edge and check each time that we are not creating a circle(if we do , its not a tree),
 * we will use disjoint set data structure for that algorithm that every vertex is a set and every time we want to connect between two vertexes(2 sets),
 * we will check that they are not in the same set by asking who is your ancestor father(each set will have 1).
 * <p>
 * second algorithm is reverse kruskal - we will sort the edges in descending form(largest first,smallest last),then
 * we will delete each maximum edge (if we can ) , we will be able to delete the current edge if it doesnt make the graph unconnected.
 * to check if the graph stay connected or not we will use bfs that that will get the graph and the two vertexes of the edge,
 * and if by any course we reach the other vertex it means the graph stay connected(we have another way to reach this vertex)
 * and if not the current edge cannot be deleted.
 * <p>
 * third algorithm is prim - its similar to algorithm dijkstra.
 */

public class spreadMinTree {

    static class Edge {
        int v;
        int u;
        int weight;

        public Edge(int v, int u, int weight) {
            this.v = v;
            this.u = u;
            this.weight = weight;
        }

        public String toString(){
            return "(" + v + "," + u + ",w:" + weight+")";
        }
    }

    static class sortByW implements Comparator<Edge> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Edge a, Edge b) {
            // for ascending
            return a.weight - b.weight;

            // for descending
           // return b.weight - a.weight;
        }
    }


    static ArrayList<ArrayList<Integer>> Graph; // the graph.
    static Edge[] edges;
    static ArrayList<Edge> treeEdges;

    public static void main(String[] args) {

        // the graph
        Graph = new ArrayList<>();
        // the spread minimal tree
        treeEdges = new ArrayList<>();
        createGraph();
        createEdges();



       // findMinTreeReverseKruskal();
      //  printTree();


        findMinTreeKruskal();
        printTree();

    }

    private static void findMinTreeKruskal() {
        int count=0;
        int n = Graph.size();
        int k=0;
        // sorting the edges by descending order
        Arrays.sort(edges, new sortByW());

        DisJointSet m = new DisJointSet(Graph.size());
        m.makeSet();

        while(count<=n-1 && k<edges.length){
            // saving the current edge vertexes , the edge we want to add.
            int u = edges[k].u;
            int v = edges[k].v;

            if(m.union(u,v)){
                treeEdges.add(edges[k]);
                count++;
            }


            k++;
        }



    }

    private static void printTree() {

        for(Edge e: treeEdges){
            System.out.println(e);
        }
    }

    private static void findMinTreeReverseKruskal() {

        int count = 0;
        int n = Graph.size();
        int k = 0;

        // sorting the edges by descending order
        Arrays.sort(edges, new sortByW());

        // the condition for tree to have n-1 edges.
        while (count != n - 1 && k < edges.length) {
            // saving the current edge vertexes , the edge we want to delete.
            int u = edges[k].u;
            int v = edges[k].v;
            // delete them from the Graph and if the graph becomes un connected we will restore them.
            for (int i = 0; i < Graph.get(u).size(); i++) {
                if (Graph.get(u).get(i)== v) {
                    Graph.get(u).remove(i);
                    break;
                }
            }
            for (int i = 0; i < Graph.get(v).size(); i++) {
                if (Graph.get(v).get(i)== u) {
                    Graph.get(v).remove(i);
                    break;
                }
            }

            if (bfs(u, v)) {
                count++;


            } else {
                // restore the deleted edge
                Graph.get(u).add(v);
                Graph.get(v).add(u);
                treeEdges.add(edges[k]);

            }

            k++;
        }
    }

    private static boolean bfs(int u, int v) {
        // creating the colors arr for bfs
        int i;
        String[] color = new String[Graph.size()];
        // init the arr
        for (i = 0; i < color.length; i++) {
            color[i] = "White";
        }

        color[u] = "Grey";

        Queue<Integer> q = new LinkedList<>();
        q.add(u);

        while (!q.isEmpty()) {
            int current = q.poll();

            // iterating through the current neighbours
            for (i = 0; i < Graph.get(current).size(); i++) {
                int neighbour = Graph.get(current).get(i);

                // it means we reach v from another way so the graph is connected even tough we deleted an edge.
                if (neighbour == v) {
                    return true;
                }

                // checking if the neighbours are white, if so turn them to grey.
                if (color[neighbour].equals("White")) {
                    color[neighbour] = "Grey";
                }

                // entering next neighbour to the queue.
                if (!color[neighbour].equals("Black"))
                    q.add(neighbour);
            }
            // after we finished with the current vertex.
            color[current] = "Black";


        }
        return false;
    }

    private static void createEdges() {

        edges = new Edge[15];
        edges[0] = new Edge(0,2,1);
        edges[1] = new Edge(0,1,4);
        edges[2] = new Edge(2,3,2);
        edges[3] = new Edge(2,5,3);
        edges[4] = new Edge(5,6,5);
        edges[5] = new Edge(5,7,7);
        edges[6] = new Edge(5,3,15);
        edges[7] = new Edge(6,7,4);
        edges[8] = new Edge(7,8,2);
        edges[9] = new Edge(7,3,10);
        edges[10] = new Edge(8,3,8);
        edges[11] = new Edge(8,4,100);
        edges[12] = new Edge(4,3,6);
        edges[13] = new Edge(4,1,7);
        edges[14] = new Edge(1,3,1);


    }

    private static void createGraph() {
        ArrayList<Integer> v0 = new ArrayList<>();
        v0.add(1);
        v0.add(2);
        Graph.add(v0);
        ArrayList<Integer> v1 = new ArrayList<>();
        v1.add(0);
        v1.add(4);
        v1.add(3);
        Graph.add(v1);
        ArrayList<Integer> v2 = new ArrayList<>();
        v2.add(5);
        v2.add(3);
        v2.add(0);
        Graph.add(v2);
        ArrayList<Integer> v3 = new ArrayList<>();
        v3.add(2);
        v3.add(1);
        v3.add(4);
        v3.add(7);
        v3.add(8);
        v3.add(5);
        Graph.add(v3);
        ArrayList<Integer> v4 = new ArrayList<>();
        v4.add(1);
        v4.add(3);
        v4.add(8);
        Graph.add(v4);
        ArrayList<Integer> v5 = new ArrayList<>();
        v5.add(3);
        v5.add(2);
        v5.add(6);
        v5.add(7);
        Graph.add(v5);
        ArrayList<Integer> v6 = new ArrayList<>();
        v6.add(5);
        v6.add(7);
        Graph.add(v6);
        ArrayList<Integer> v7 = new ArrayList<>();
        v7.add(6);
        v7.add(3);
        v7.add(8);
        v7.add(5);
        Graph.add(v7);
        ArrayList<Integer> v8 = new ArrayList<>();
        v8.add(4);
        v8.add(3);
        v8.add(7);
        Graph.add(v8);


    }


}