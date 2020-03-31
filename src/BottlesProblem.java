

/**
 * this class represents bottles problem which converted to a graph
 * description : for instance we have bottle A with 5 litters and a bottle B with 3 litters we want to get the situation :
 * bottle A with 4 litters.
 * we can do the following: empty A or B, fill to the max A or B , or moving from A to B some litters.
 * this problem converted into a graph with situations, for instance if A=1 and B =2 we have
 * (1+1)*(2+1) situations (Vertexes) +1 because of the 0 , so here are the Vertexes: (0.0),(0,1),(0,2),(1,0),(1,1),(1,2)
 * the goal: to check weather if we can reach from a vertex to another by the instructions i depicted above. for each vertex we have 6 options
 * (a,b) - some litters in A and B - (a,0),(0,b),(A,b),(a,B),(a+b-min(a+b,B),min(a+b,B),(min(a+b,A),a+b-min(a+b,A))
 * we will use a matrix to depict all situations for example for A=1,B=2 (0.0),(0,1),(0,2),(1,0),(1,1),(1,2) for rows and (0.0),(0,1),(0,2),(1,0),(1,1),(1,2)
 * for cols. then we will return a boolean matrix where a cell with true value - there is a way from vertex to another.
 * in addition we have a function that will convert i and j each of them to "Ordered pair". suppose i will be converted to k,p
 * and j will be converted to d,f.
 * these are the steps: (suppose m,n are inputs for the bottles)
 * 1) open a matrix in size of [(m+1)(n+1)] [(m+1)(n+1)]
 * 2) iterating through the mat send each cell(mat[i][j]) to a function that will convert i and j to ordered pair.
 * 3) the function will check according to m,n the 6 options depicted above. if one of them exists the function will return true.
 * <p>
 * adding some functions:
 * 1)findWaysFW - an algorithm which gets a neighbour matrix and returns a matrix that will determine about each vertex if it can be reached from another vertex
 * summary of this algorithm : basically we run through all the vertexes and checks if there a way between two of those, if now maybe through another
 * vertex - mat[i,j] = mat[i,k] && mat[k,j] || mat[i,j],on the same hand we will make another matrix with all the vertexes courses.
 * isConnected - checks if the graph is connected - means we can reach any vertex in the graph.
 * numOfConnectingComponents - checks how many connecting components in the graph.
 * checkWay - checks if there is a way between two vertexes and returns the way (enter number not by modeling to bottles problem,by normal numbers 0,1,2,3... - instead of (0,0) -> 0)
 */


public class BottlesProblem {
    static int n = 5, m = 3;
    static int size = (m + 1) * (n + 1);
    static String ways[][] = new String[size][size];


    public static void main(String[] args) {

//        Boolean check [][] = {{true,false,false,true,false,true,false},
//                {false,true,true,false,true,false,true},
//                {false,true,true,false,true,false,true},
//                {true,false,false,true,false,true,false},
//                {false,true,true,false,true,false,true},
//                {true,false,false,true,false,true,false},
//                {false,true,true,false,true,false,true}
//        };

        Boolean neighbourMat[][] = bottlesProblem();
        System.out.println("Neighbour matrix");
        printAll(neighbourMat);
        Boolean checkConnections[][] = bottlesProblem();
        checkConnections = findWaysFW(checkConnections, neighbourMat);
        System.out.println("Courses matrix");
        printAll(checkConnections);
        System.out.println();
        printAll(ways);
        System.out.println();
        System.out.println("Graph connected : " + isConnected(checkConnections));
        System.out.println();
         System.out.println(checkWay(checkConnections, 6, 4));
        System.out.println("Num of connecting component : " + numOfConnectingComponents(checkConnections));

    }

    private static int numOfConnectingComponents(Boolean[][] mat) {
        int i, j;
        boolean anotherConnection=false; // to know if there is a new connection component
        int count = 0; // to count how many components
        int notEmpty = 0; // to know if its not the connection we already defined before.

        int arr[] = new int[size]; // putting all values to -1 because we need to mark 0 as well too.
        for (i = 0; i < size; i++) {
            arr[i] = -1;
        }


        for (i = 0; i < size; i++) {
            for (j = 0; j < size; j++) {
                if (mat[i][j]) {
                    if (arr[j] == -1) {
                        notEmpty++;
                        anotherConnection=true;
                    }
                    arr[j] = i;
                }
            }
            if (anotherConnection) {
                count++;
                anotherConnection=false;
            }
            if (notEmpty == size) {
                return count;
            }

        }

        return 0;
    }

    private static String checkWay(Boolean[][] mat, int i, int j) {
        String ans = "";
        if (mat[i][j]) { // if there is a way
            String vertex[] = ways[i][j].split(" -> ");
            for (int k = 0; k < vertex.length; k++) {
                // converting the ordered pair to k
                int p = Integer.parseInt(vertex[k].charAt(1) + "");
                int q = Integer.parseInt(vertex[k].charAt(3) + "");
                int num = (n + 1) * p + q;
                ans += num + " -> ";

            }
        } else return "There is no course between these vertexes";
        ans = ans.substring(0, ans.length() - 4); // cut last ->
        return ans;
    }

    private static boolean isConnected(Boolean[][] mat) {
        for (int i = 0; i < size; i++) {
            if (!mat[0][i]) {
                return false;
            }
        }
        return true;
    }

    private static Boolean[][] findWaysFW(Boolean[][] checkConnections, Boolean[][] neighbourMat) {
        int p, l, g, q;


        // O(n^3)
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    // p,i ordered pair of i
                    p = i / (n + 1);
                    l = i % (n + 1);
                    // g,q ordered pair of j
                    g = j / (n + 1);
                    q = j % (n + 1);

                    // checks if there is any vertex k that connects between i and j or they are already connected by one edge
                    checkConnections[i][j] = (checkConnections[i][k] && checkConnections[k][j]) || checkConnections[i][j];

                    if (neighbourMat[i][j] && ways[i][j] == null) { // means we already have a course by 1 edge
                        ways[i][j] = "(" + p + "," + l + ") -> (" + g + "," + q + ")";
                    } else if (checkConnections[i][k] && checkConnections[k][j] && !neighbourMat[i][j]) { // if there is  more then one  edge course
                        neighbourMat[i][j] = true;
                        if (ways[i][j] == null) {
                            ways[i][j] = ways[i][k] + " -> " + ways[k][j].substring(9);
                        } else {
                            ways[i][j] += ways[i][k] + " -> " + ways[k][j];
                        }
                    }


                }
            }


        }
        return checkConnections;
    }

    private static Boolean[][] bottlesProblem() {

        Boolean[][] mat = new Boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mat[i][j] = checkNeighbours(i, j);
            }
        }

        return mat;
    }

    private static boolean checkNeighbours(int i, int j) {

        // k ,p are ordered pair of i
        int k = i / (n + 1);
        int p = i % (n + 1);
        // d ,j are ordered pair of j
        int d = j / (n + 1);
        int f = j % (n + 1);

        // checking all possible 6 situations described above:

        if (k == d && p == f) {
            return true;
        } // staying on the same vertex.
        else if (k == d && f == n || d == m && f == p) {
            return true;
        } // filling first bottle to its max or filling second bottle to its max
        else if (k == d && f == 0 || p == f && d == 0) {
            return true;
        } // empty first bottle or empty second bottle
        else if (d == k + p - MIN(k + p, n) && f == MIN(k + p, n)) {
            return true;
        } // pouring from first bottle to second
        else if (d == MIN(k + p, m) && f == k + p - MIN(k + p, m)) {
            return true;
        } // pouring from second bottle to first


        return false;
    }

    private static int MIN(int i, int n) { // min method.
        return i < n ? i : n;
    }

    private static <T> void printAll(T[][] t) {
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                System.out.print(t[i][j] + ",");
            }
            System.out.println();
        }
    }
}
