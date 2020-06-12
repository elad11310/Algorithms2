
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * this class represents BFS algorithm on graphs.
 * we will use ArrayList of ArrayLists as data structure for the graph.
 * this algorithm finds the following:
 * 1)checks if the graph is connected
 * 2)how many connecting components the specific graph has
 * 3)what are the vertexes in each connecting component
 * 4)shortest distance between 2 vertexes and ths course between them
 * 5)finding diameter of the graph
 * 6)finding a course in size of the diameter.
 * basically this algorithm uses 3 helping arrays. 1 for the colors(all vertexes starts with white color,grey if they have been recognized as a neighbours
 * and haven't cared it, and black when we done with this vertex).
 * 2 array is for saving the distances between the starting vertex to all others in graph.
 * 3 array is for saving the father of each vertex - will be useful later for finding the courses.
 * conclusions : in d array we have all the shortest paths from starting vertex to all others.(if there is)
 * through fathers array we can show the shortest paths.
 */

public class BFS {
    static ArrayList<ArrayList<Integer>> Graph;
    static ArrayList<ArrayList<Integer>> connectingComponents; // continue from here!!
    static int[] d;
    static String[] color;
    static int[] fathers;


    public static void main(String[] args) {
        int ind;
        //creating the graph - Array of Arrays.
        Graph = new ArrayList<>();
        // creating the array that will hold for us the connecting components arrays.
        connectingComponents = new ArrayList<>();
        createGrapth();
        initializeArrays();
        do {

            ind = isWhite();
            if (ind != -1)
                Bfs(ind);

        } while (ind != -1);
        // findPath(10, 8);
        System.out.println();
        System.out.println(isConnected());
        System.out.println();
        System.out.println("Connecting components : " + connectingComponents.size());
        System.out.println(Arrays.asList(connectingComponents));
        //findDiamVersion1();
        findDiamVersion2();

    }

    private static int findDiamVersion2() {
        int diam;
        // this algorithm finds the diameter in O(v+e)
        // we gonna take any vertex and invoke bfs. we gonna get the vertex with the maximum length from it.
        // then we take this vertex and invoke again to bfs and that's will be the diameter for sure.

        int randVertex = (int) (Math.random() * Graph.size());
        Bfs(randVertex);
        // now we will look for the maximum vertex from it and invoke bfs on it.
        int max = findMaxIndex();
        initializeArrays();

        Bfs(max);
        diam = findMax();
        // if we want an instance to course in size of diam - we will call to findPath with max and the index(vertex) with maximum value in d after
        //invoking to bfs in the second time
        System.out.println("Diam is : " + diam);
        findPath(max, findMaxIndex());
        return diam;
    }

    private static int findDiamVersion1() {
        // this algorithm finds diameter in the graph in O(v*(v+e))
        // we will invoke bfs for each vertex on the graph and then we will find the maximum length between it to the others.
        // and then save the result in max
        int diam = 0, max = 0;

        for (int i = 0; i < Graph.size(); i++) {
            initializeArrays();

            Bfs(i);
            diam = findMax();
            if (diam > max) {
                max = diam;
            }
        }
        System.out.println("Diam is : " + diam);
        return max;

    }

    private static int findMaxIndex() {
        int max = 0, maxInd = 0;
        for (int i = 0; i < d.length; i++) {
            if (d[i] > max) {
                max = d[i];
                maxInd = i;
            }
        }
        return maxInd;

    }

    private static int findMax() {
        // find max in arr
        int max = 0;
        for (int i : d) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    private static int isWhite() {
        for (int i = 0; i < color.length; i++) {
            if (color[i].equals("White")) {
                return i;
            }
        }
        // if we didnt find a white vertex.
        return -1;
    }

    private static void initializeArrays() {
        int i;
        // first initializing the arrays O(v)
        d = new int[Graph.size()];
        color = new String[Graph.size()];
        fathers = new int[Graph.size()];

        for (i = 0; i < Graph.size(); i++) {
            color[i] = "White";
            d[i] = Integer.MAX_VALUE;
            fathers[i] = Integer.MAX_VALUE;
        }
    }

    private static boolean isConnected() {
        // we have 3 ways to check if the graph connected after we invoked Bfs.
        // 1)to check in color array all vertexes are black
        // 2) to check in fathers array if there is only 1 vertex who doesnt have a father.
        // 3) to check in d array all the numbers are not Integer.MaxValue.
        // here we will use the 3 check


        for (int i : d) {
            if (i == Integer.MAX_VALUE)
                return false;
        }

        return true;
    }

    private static void findPath(int s, int e) {
        if (e == s) {
            System.out.print(s + "->");
        } else if (fathers[e] == Integer.MAX_VALUE) {
            System.out.println("There is no way between those vertexes");
            return;
        } else {
            findPath(s, fathers[e]);
            System.out.print(e + "->");
        }

    }

    private static void Bfs(int vertexToStart) {
        int i;
        // now we start from any vertex so we gonna turn it into grey,update its distance from itself to 0 and send it to the Queue.
        color[vertexToStart] = "Grey";
        d[vertexToStart] = 0;

        // now creating our Queue
        Queue<Integer> q = new LinkedList<>();
        q.add(vertexToStart);

        // for the connecting components.
        ArrayList<Integer> c = new ArrayList<>();
        c.add(vertexToStart);

        while (!q.isEmpty()) {
            int current = q.poll();
            // now checking the current vertex neighbours.O(deg(v))
            for (i = 0; i < Graph.get(current).size(); i++) {
                int neigbhour = Graph.get(current).get(i);
                // adding to the connecting component.
                if (!c.contains(neigbhour))
                    c.add(neigbhour);
                // checking if the neighbours are white, if so turn them to grey.
                if (color[neigbhour].equals("White")) {
                    color[neigbhour] = "Grey";
                }
                // updating the distance to the current distance +1;
                if (d[neigbhour] == Integer.MAX_VALUE)
                    d[neigbhour] = d[current] + 1;
                // updating father
                if (fathers[neigbhour] == Integer.MAX_VALUE && neigbhour != vertexToStart)
                    fathers[neigbhour] = current;

                // entering next neighbour to the queue.
                if (!color[neigbhour].equals("Black"))
                    q.add(neigbhour);
            }
            // after we finished with the current vertex.
            color[current] = "Black";
        }

        // a connecting component is added.
        connectingComponents.add(c);

    }

    private static void createGrapth() {
        ArrayList<Integer> v0 = new ArrayList<>();
        v0.add(1);
        v0.add(4);
        Graph.add(v0);
        ArrayList<Integer> v1 = new ArrayList<>();
        v1.add(0);
        v1.add(5);
        Graph.add(v1);
        ArrayList<Integer> v2 = new ArrayList<>();
        v2.add(5);
        v2.add(3);
        v2.add(6);
        Graph.add(v2);
        ArrayList<Integer> v3 = new ArrayList<>();
        v3.add(2);
        v3.add(7);
        Graph.add(v3);
        ArrayList<Integer> v4 = new ArrayList<>();
        v4.add(0);
        Graph.add(v4);
        ArrayList<Integer> v5 = new ArrayList<>();
        v5.add(1);
        v5.add(2);
        v5.add(6);
        Graph.add(v5);
        ArrayList<Integer> v6 = new ArrayList<>();
        v6.add(5);
        v6.add(2);
        v6.add(7);
        Graph.add(v6);
        ArrayList<Integer> v7 = new ArrayList<>();
        v7.add(6);
        v7.add(3);
        Graph.add(v7);

//        ArrayList<Integer> v8 = new ArrayList<>();
//        v8.add(9);
//        v8.add(10);
//        Graph.add(v8);
//        ArrayList<Integer> v9 = new ArrayList<>();
//        v9.add(8);
//        Graph.add(v9);
//        ArrayList<Integer> v10 = new ArrayList<>();
//        v10.add(8);
//        Graph.add(v10);

    }

}
