public class Dijkstra {


    static int INFINITY = 9999, n = 9; //(number of vertexes),
    static int s = 0, d = 8, NILL = -1;


    // s - source vertex where to start
    // d - destination vertex

    // Function to find the shortest path
    // with minimum edges in a graph
    static void Dijkstra(int[][] Graph, int _n, int _s, int _d , boolean isWeight) {

        int i, j, u, v, count;
        int[] dist = new int[n]; // saving the distances between source vertex to each of the vertexes until the dest one

        // for weights on vertexes if we have it.
        int[] weights = {1, 1, 3, 1, 1, 1, 1, 1, 1};
        if(isWeight){
            // initialize the weights on vertexes with the edges - only if we need to
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    if (i!=j && Graph[i][j] != INFINITY) { // if its not the current vertex and if we have a neighbour
                        Graph[i][j] *= 2; // double the edge so at the end we will divide by two.
                        Graph[i][j] += weights[i] + weights[j]; // adding the weights of the vertexes to the edge's total weight from current vertex to its neighbour.
                    }
                }
            }
        }

        int[] Blackened = new int[n]; // means true of false if we visited in this vertex
        int[] pathlength = new int[n]; // saving the length of the path (edges length not cost)
        int[] parent = new int[n]; // saving the parent of each vertex , source vertex has no parent

        // The parent Of the source vertex is always equal to nill
        parent[_s] = NILL;

        // first, we initialize all distances to infinity.
        for (i = 0; i < n; i++)
            dist[i] = INFINITY;



        dist[_s] = 0;
        for (count = 0; count < n - 1; count++) {
            u = MinDistance(dist, Blackened);

            // if MinDistance() returns INFINITY, then the graph is not
            // connected and we have traversed all of the vertices in the
            // connected component of the source vertex, so it can reduce
            // the time complexity sometimes
            // In a directed graph, it means that the source vertex
            // is not a root
            if (u == INFINITY)
                break;
            else {

                // Mark the vertex as Blackened
                Blackened[u] = 1;
                for (v = 0; v < n; v++) {
                    //Graph[u][v] != 0 means its a neighbour.
                    if (Blackened[v] == 0 && Graph[u][v] != 0
                            && dist[u] + Graph[u][v] < dist[v]) {
                        parent[v] = u;
                        pathlength[v] = pathlength[parent[v]] + 1;
                        dist[v] = dist[u] + Graph[u][v];
                    } else if (Blackened[v] == 0 && Graph[u][v] != 0
                            && dist[u] + Graph[u][v] == dist[v]
                            && pathlength[u] + 1 < pathlength[v]) {
                        parent[v] = u;
                        pathlength[v] = pathlength[u] + 1;
                    }
                }
            }
        }

        // Printing the path // means we reached the dest and we have a course
        if (dist[_d] != INFINITY) {
            if(isWeight){
                dist[_d] += weights[_d] + weights[s];
                dist[_d]/=2;
                System.out.println("the cost to the dest vertex is " + dist[_d]);
            }
            else{
                System.out.println("the cost to the dest vertex is " + dist[_d]);
            }

            PrintPath(parent, _d);

        } else
            System.out.println("There is not path between vertex " +
                    _s + " to vertex " + _d);
    }

    static int MinDistance(int[] dist, int[] Blackened) {
        int min = INFINITY, min_index = -1, v;
        for (v = 0; v < n; v++)
            if (Blackened[v] == 0 && dist[v] < min) {
                min = dist[v];
                min_index = v;
            }
        return min == INFINITY ? INFINITY : min_index;
    }

    // Function to print the path
    static void PrintPath(int[] parent, int _d) {
        if (parent[_d] == NILL) {
            System.out.print(_d);
            return;
        }
        PrintPath(parent, parent[_d]);
        System.out.print("->" + _d);
    }

    // Driver Code
    public static void main(String[] args) {

        // INFINITY means that u and v are not neighbors.
        int[][] Graph = {{0, 2, 1, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY},
                {2, 0, INFINITY, INFINITY, 0, INFINITY, INFINITY, INFINITY, INFINITY},
                {1, INFINITY, 0, 3, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY},
                {INFINITY, INFINITY, 3, 0, 2, 7, 1, INFINITY, INFINITY},
                {INFINITY, 0, INFINITY, 2, 0, 4, INFINITY, INFINITY, INFINITY},
                {INFINITY, INFINITY, INFINITY, 7, 4, 0, 2, 2, INFINITY},
                {INFINITY, INFINITY, INFINITY, 1, INFINITY, 2, 0, 1, 4},
                {INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, 2, 1, 0, 9},
                {INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, 4, 9, 0}};
        Dijkstra(Graph, n, s, d,false);


// This co
    }
}