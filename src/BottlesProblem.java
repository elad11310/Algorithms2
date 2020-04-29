import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.PriorityQueue;
import java.util.Scanner;

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
 * printCourse - checks if there is a way between two vertexes and returns the way ,invoke it with true so it will return a course in the bottle problem style, invoke with false-
 * it will return a course in just numbers of vertexes.
 * <p>
 * adding more functions:(these functions not connected to the bottle problem directly, we relay on that we got already a neighbours matrix with
 * values on the edges.
 * findCheapestWay - gets a neighbours matrix and returns a matrix with cheapest way(in value,not a course) between vertexes (if exists).
 * bottlesProblemCheapestWay - like findCheapestWay but in the bottle problem graph style
 * <p>
 * another problem we gonna deal with - a graph that the weights are on the vertexes and not on the edges. we get as input a matrix that will describe the neighbours
 * marked by ones . and another arr for the weights that should be on vertexes and then we convert it to the problem we had before - finding cheapest ways between vertexes
 * by the weights on the edges. so we gonna convert the weights from vertexes to the edges. we gonna face a problem that when we calculate the distance besides the vertexes
 * that on the end of both sides each one counts twice which is wrong so we gonna add the vertexes from the end so all vertexes eventually counts twice and then divide by two.
 * <p>
 * findCheapestWayVertex - will make the text depicted above. 1) convert. 2)FW finding all the cheapest ways. 3)fixing the problem that all vertexes except those in the end
 * count twice.
 * <p>
 * if we are not interested in all the vertexes cheapest ways but only from one vertex - all the cheapest ways to the rest of the vertexes,
 * we will use algorithm Dijkstra.(Disadvantage of Dijkstra is usability to take care of negative weights).
 * <p>
 * Negative weights : in undirected graph its enough if we have 1 negative weight on any edge then we have(or can have) negative circle. in O(m) we can know that.
 * (running through all the weights of the edges in the graph).
 * <p>
 * in directed graph we will lunch FW algorithm and if we have negative weights on the diagonal it means we have a negative circle.
 * if we want to show the negative circle we go to the course matrix at this point.
 * <p>
 * <p>
 * diameter(koter) in the graph is sub sequence of the arr(arr of the edges weight) with the maximum value.
 * for know we show 3 ways to find it - full search in O(n**3) , dynamic search in O(n**2)
 * full search- in full search we gonna split the arr to all its sub cells it means : [0,0],[0,1],,,,[0,n]
 *                                                                                          [1,1],[1,2],,,[1,n]
 *                                                                                              .
 *                                                                                                   .
 *                                                                                                      .
 *                                                                                                           .
 *                                                                                                               [n,n]
 * so we just need to take care of the triangle above the diagonal becuase its should be a continuous sequence ( not 1,3,5 for instance). so O(n(n-1)/2) -> O(n**2)
 * so O(n**2) to find all the sub sequences * O(n) to calculate the sum so till now O(n**3)
 * Dynamic search - open matrix - putting in the diagonal the arr values and then use : mat[i,j] = mat[i,j-1] +mat [j,j] or mat[i,j] = mat[i-1,j] +mat [i,i]
 * need to be check what is faster way (mat [i,i] or mat [j,j] could be replaced to arr[j] or arr[i]) maybe access to arr is faster then mat - need to be check).
 * <p>
 * patient greedy algorithm - O(n)
 * if we want to find the smallest sum of sub arr , we will use patient algorithm with a few changes-
 * we will switch the sign of very variable in the arr (O(n)), call findDiameterGreedy and on the result we put a negative sign.
 * <p>
 * findDiameterFullSearch
 * findDiameterDynamic
 * findDiameterGreedy (also called best)
 *
 *
 * now we will see some algorithms to find the diameter in circled graph which means we get a circled arr.
 * for instance {-6,3-10,100,-18,20,-2} - the diameter is 105 we start summing from index 6 until index 4 20+(-2)+(-6)+3+(-10)+100 = 105.
 * the maximum edges we can sum is until 1 before the index we started from, because its not allowed to sum an edge twice.
 *
 * full search - now i can be greater then j for example arr[6,4] must be count. before where the graph wasn't a cycled graph a[6,4] wasn't allowed because
 * it was'nt a proper sub arr, now it is.
 *
 * [0,0],[0,1],,,,[0,n]
 * [1,1].......[1,n][1,0]
 * [2,2].......[2,n][2,0][2,1]
 * [i,i].......[i,n][i,0]....[i,i-1]
 * findDiameterCircledGraphFullSearch - O(n**2) to find all sub arr and multiply by another O(n) to sum all the numbers in the current sub arr so total O(n**3).
 *
 *  another algorithm (best for my opinion) to duplicate the arr and to use findDiameterGreedy(O(n)) but with a restriction to search for parts that are no longer then
 *  the size of the original arr. duplicateArrFindDiameter total: O(n)
 *
 *
 *  another algorithm is based on symmetric that as follow : Diameter = Max(best(A) , sum(A) -- best(-A)) -- will be +. O(n)
 *  findDiameterSymmetric
 *
 *
 * new subject - gas station problem. we have a circle with points. each point is a gas station where we can fill gas.
 * now we want to check if we can start from a certain point and close a circle. between point to point we have a cost.
 * so we basically have 2  arr's , 1 for the gas station (each variable represents num of liters we can fill),second arr is for the costs between
 * point to point. is there a way to close a circle in O(n)? the idea is (if a means gas stations and b means costs) to check if
 * a1>b1 and then a1+a2>b1+b2 and .... why not to make sum of first array(Gas statiosn) and sum of second array(costs) and then to see if sum(a) > sum(b)?
 * because the total sum might be greater but in the way we might have a course where the fuel we have at the moment is not enough to get from ai to ai+1.
 * gasStationProblem
 */


// for Dijkstra algorithm
class Node {
    boolean isConnected;
    boolean isVisited;
    int cost; // total cost till this node
    int edge; // the edge connecting between the node to its neighbour

    public Node(boolean isConnected, boolean isVisited, int cost, int edge) {
        this.isConnected = isConnected;
        this.isVisited = isVisited;
        this.cost = cost;
        this.edge = edge;
    }
}


public class BottlesProblem {
    static int n = 2, m = 1;
    static int size = (m + 1) * (n + 1);
    //static int size = 6;
    static String ways[][] = new String[size][size]; // for all the ways between vertexes
    static String[][] cheapestWays = new String[size][size]; // for all the cheapest ways between vertexes.


    public static void main(String[] args) {



        int arr1[] =  {12,1,3,3,4,8};
        int arr2 [] ={6,4,2,9,3,5};
        System.out.println(gasStationProblem(arr1,arr2));

        //        Integer mat[][] = {{1, 10000, 1, 1, 10000, 10000},
//                {1, 1, 1, 1, 1, 10000},
//                {1, 10000, 1, 10000, 1, 1},
//                {1, 1, 10000, 1, 10000, 1},
//                {10000, 1, 1, 1, 1, 1},
//                {10000, 10000, 1, 1, 10000, 1}};
//
//        mat = findCheapestWay(mat);
//        printAll(mat);
//        System.out.println("---------");
//        printAll(cheapestWays);
//        try {
//            Node[] arr = Dijkstra(0);
//            for (Node s : arr) {
//                if (s != null)
//                    System.out.print(s.cost + " ");
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }


        // input matrix with edges value
//        Integer mat[][] = {{0, 1, 10000, 3},
//                {1, 0, 11, 10000},
//                {10000, 11, 0, 5},
//                {3, 10000, 5, 0}};

//        Integer mat [][] = {{0,9,5,10000,10000,10000,10000},
//                {9,0,10000,2,10000,10000,10000},
//                {5,10000,0,1,10000,10000,10000},
//                {10000,2,1,0,13,2,10000},
//                {10000,10000,10000,13,0,10000,6},
//                {10000,10000,10000,2,10000,0,4},
//                {10000,10000,10000,10000,6,4,0}};
//
//        mat = findCheapestWay(mat);
//        printAll(mat);
//        System.out.println("----------");
//        printAll(cheapestWays);


//        Boolean check[][] = {{true, false, false, true, false, true, false},
//                {false, true, true, false, true, false, true},
//                {false, true, true, false, true, false, true},
//                {true, false, false, true, false, true, false},
//                {false, true, true, false, true, false, true},
//                {true, false, false, true, false, true, false},
//                {false, true, true, false, true, false, true}
//        };
//
//        Boolean check2[][] = {{true, false, false, true, false, true, false},
//                {false, true, true, false, true, false, true},
//                {false, true, true, false, true, false, true},
//                {true, false, false, true, false, true, false},
//                {false, true, true, false, true, false, true},
//                {true, false, false, true, false, true, false},
//                {false, true, true, false, true, false, true}
//        };

//        Integer temp[][] = bottlesProblemCheapestWay();
//        System.out.println("Neighbour matrix");
//        printAll(temp);
//        temp = findCheapestWay(temp, true);
//        System.out.println("---------");
//        printAll(cheapestWays);

//
//        Boolean checkConnections[][] = bottlesProblem();
//        System.out.println("Neighbour matrix");
//        printAll(checkConnections);
//        checkConnections = findWaysFW(checkConnections);
//        //Boolean[][] checkConnections = findWaysFW(check, check2);
//        System.out.println("Courses matrix");
//        printAll(checkConnections);
//        System.out.println();
//        printAll(ways);
//        System.out.println();
//        System.out.println("Graph connected : " + isConnected(checkConnections));
//        System.out.println();
//        System.out.println(printCourse(checkConnections, 0, 4, true));
//        System.out.println("Num of connecting component : " + numOfConnectingComponents(checkConnections));

    }


    private static int duplicateArrFindDiameter(int [] weights){
        int newArr[] = new int [weights.length*2];
        int j=0;
        // duplicate the original arr to the new arr
        for(int i=0;i<newArr.length;i++){
            if(j==weights.length){
                j=0;
            }
            newArr[i]=weights[j];
            j++;
        }

        int ans = findDiameterGreedy(newArr,true,true);
        return  1;

    } /// suspended for now

    private static boolean gasStationProblem(int [] gasStations, int [] costs){
        int totalSumGasStations=0, totalSumCost=0;
        for(int i=0;i<gasStations.length;i++){
            totalSumGasStations+= gasStations[i];
            totalSumCost+= costs[i];
            if(totalSumGasStations<totalSumCost){
                return false;
            }
        }
        return true;
    }
    private static  int findDiameterSymmetric(int [] weights){
        return MAX(findDiameterGreedy(weights,true,false),Sum(weights,0,weights.length-1)-findDiameterGreedy(weights,false,false));
    }
    private static int findDiameterCircledGraphFullSearch(int [] weights){
        int sum = 0, maxSum = 0, numOfEdges=0,count;
        for (int i = 0; i < weights.length; i++) {
            int j=i;
            count=0;
            while(count!=weights.length) {
                sum = SumCircle(weights, i, j);
                if (sum > maxSum) {
                    maxSum = sum;
                    numOfEdges = Math.abs(i - j) + 1;


                }
                j++;
                count++;

            }

            }
        System.out.println("The diameter is " + maxSum + " and in num of edges :" + numOfEdges);
        return maxSum;
    }
    private static int findDiameterGreedy(int[] weights,boolean isPositiveSum,boolean isDuplicate) {
        int current = 0, max = 0 , numOfEdges=0;

        // if we want the smallest sum instead of highest
        if(!isPositiveSum){
            changeSign(weights);
        }

        for (int i = 0; i < weights.length; i++) {


            current += weights[i];
            numOfEdges++;
            if (current < 0) {
                current = 0;
                numOfEdges=0;
                continue;
            }

            if (current > max) {
                max = current;

            }



        }
        if(!isPositiveSum){
            max*=-1;
        }
       // System.out.println("The diameter is " + max + " and in num of edges :" + numOfEdges);

        return max;
    }

    private static void changeSign(int[] weights) {
       for(int i=0;i<weights.length;i++){
           weights[i]*=-1;
       }
    }

    private static int findDiameterDynamic(int[] weights) {
        int i, j, max = 0, numOfEdges = 0;
        int dynamicMat[][] = new int[weights.length][weights.length];
        // now putting the values from the weights arr into the diagonal O(n)
        for (i = 0; i < weights.length; i++) {
            dynamicMat[i][i] = weights[i];
            // starting looking for max from now
            if (weights[i] > max) {
                max = weights[i];
                numOfEdges = 1;
            }
        }
        //long startTime = System.nanoTime();
        long start = System.currentTimeMillis();
        // now first algorithm  mat[i,j] = mat[i,j-1] +mat [j,j] O(n**2)
        for (i = 0; i < dynamicMat.length - 1; i++) {
            for (j = i + 1; j < dynamicMat[i].length; j++) {
                dynamicMat[i][j] = dynamicMat[i][j - 1] + dynamicMat[j][j];
                if (dynamicMat[i][j] > max) {
                    max = dynamicMat[i][j];
                    numOfEdges = Math.abs(i - j) + 1;
                }
            }
        }
        //long endTime   = System.nanoTime();
        long end = System.currentTimeMillis();

        System.out.println("The diameter is " + max + " and in num of edges :" + numOfEdges);
        System.out.println(end - start);
        // now second algorithm  mat[i,j] = mat[i-1,j] +mat [i,i] O(n**2)

        for (i = 1; i < dynamicMat.length; i++) {
            for (j = 0; j < i; j++) {
                dynamicMat[i][j] = dynamicMat[i - 1][j] + dynamicMat[i][i];
                if (dynamicMat[i][j] > max) {
                    max = dynamicMat[i][j];
                    numOfEdges = Math.abs(i - j) + 1;
                }
            }
        }
        System.out.println("The diameter is " + max + " and in num of edges :" + numOfEdges);

        // now third algorithm  mat[i,j] = mat[i-1,j] + weights[i] O(n**2)

        for (i = 1; i < dynamicMat.length; i++) {
            for (j = 0; j < i; j++) {
                dynamicMat[i][j] = dynamicMat[i - 1][j] + weights[i];
                if (dynamicMat[i][j] > max) {
                    max = dynamicMat[i][j];
                    numOfEdges = Math.abs(i - j) + 1;
                }
            }
        }

        System.out.println("The diameter is " + max + " and in num of edges :" + numOfEdges);


        // now forth algorithm  mat[i,j] = mat[i,j-1] +weights[j] O(n**2)
        for (i = 0; i < dynamicMat.length - 1; i++) {
            for (j = i + 1; j < dynamicMat[i].length; j++) {
                dynamicMat[i][j] = dynamicMat[i][j - 1] + weights[j];
                if (dynamicMat[i][j] > max) {
                    max = dynamicMat[i][j];
                    numOfEdges = Math.abs(i - j) + 1;
                }
            }
        }
        System.out.println("The diameter is " + max + " and in num of edges :" + numOfEdges);

        return max;
    }

    private static int findDiameterFullSearch(int[] weights) {
        int sum = 0, maxSum = 0, numOfEdges = 0;
        for (int i = 0; i < weights.length; i++) {
            for (int j = i; j < weights.length; j++) {
                sum = Sum(weights, i, j);
                if (sum > maxSum) {
                    maxSum = sum;
                    numOfEdges = Math.abs(i - j) + 1;
                }

            }
        }
        System.out.println("The diameter is " + maxSum + " and in num of edges :" + numOfEdges);
        return maxSum;
    }

    private static int Sum(int[] arr, int i, int j) {
        int sum = 0;
        for (int k = i; k <= j; k++) {
            sum += arr[k];
        }
        return sum;
    }

    private static int SumCircle(int[] arr, int i, int j) {
        int sum = 0;
        int subArr = Math.abs(i-j)+1;
        while(subArr>0) {
            if(i>arr.length-1){
                i=0;
            }
            sum+=arr[i];
            i++;
            subArr--;
        }

        return sum;
    }

    private static Integer[][] findCheapestWayVertex(Integer[][] mat, int[] arr) {
        // step 1: convert the weights on vertexes to weights on edges. O(n**2)
        int i, j;

        System.out.println("After converting");
        for (i = 0; i < mat.length; i++) {
            for (j = 0; j < mat[0].length; j++) {
                // if its not diagnoal
                if (i != j && mat[i][j] != 10000) {
                    mat[i][j] = arr[i] + arr[j];
                }
            }
        }

        printAll(mat);

        //step 2 : lunching FW algorithm to find cheapest ways.O(n**3)
        System.out.println("After lunching FW");
        mat = findCheapestWay(mat, true);
        System.out.println();
        printAll(mat);
        System.out.println();
        printAll(cheapestWays);

        // step 3 : fixing the matrix with the cheapest ways values. O(n**2)
        System.out.println("After fixing");
        for (i = 0; i < mat.length; i++) {
            for (j = 0; j < mat[0].length; j++) {
                if (i != j) {
                    mat[i][j] += arr[i] + arr[j];
                    mat[i][j] /= 2;
                }
            }
        }

        printAll(mat);
        // total complexity : O(n**2) + O(n**3) + O(n**2) = O(n**3)
        return mat;
    }

    private static int numOfConnectingComponents(Boolean[][] mat) {
        String ans = "{"; // for printing each connection component.
        int i, j;
        boolean anotherConnection = false; // to know if there is a new connection component
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
                        anotherConnection = true;
                        ans += j + ",";
                    }
                    arr[j] = i;
                }
            }
            if (anotherConnection) { // means there is a connection component.
                count++;
                anotherConnection = false;

                // this for printing this connection component.
                ans = ans.substring(0, ans.length() - 1);
                ans += "}";
                System.out.println("Connecting component number " + count + " " + ans);
                ans = "{";
            }
            // if all the arr values are different from -1 so we found all the connections components .
            if (notEmpty == size) {
                return count;
            }

        }

        return 0;
    }

    private static String printCourse(Boolean[][] mat, int i, int j, boolean form) {
        String ans = "";
        if (mat[i][j]) { // if there is a way
            // means we want to return a course by the bottle problem
            if (form) {
                return ways[i][j];
            }
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

    private static Boolean[][] findWaysFW(Boolean[][] checkConnections) {
        int p, l, g, q;
        // making a helping mat.
        Boolean[][] neighbourMat = new Boolean[checkConnections.length][checkConnections.length];
        deepCopy(checkConnections, neighbourMat);

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
                    checkConnections[i][j] = checkConnections[i][j] || (checkConnections[i][k] && checkConnections[k][j]);

                    // now saving a way
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

    private static Integer[][] bottlesProblemCheapestWay() {

        Integer[][] mat = new Integer[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mat[i][j] = checkNeighbours(i, j) ? 1 : 10000;
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

    private static int MAX(int i, int n) { // max method.
        return i > n ? i : n;
    }

    private static <T> void printAll(T[][] t) {
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                System.out.print(t[i][j] + ",");
            }
            System.out.println();
        }
    }

    private static <T> void deepCopy(T[][] from, T[][] to) {

        for (int i = 0; i < from.length; i++) {
            for (int j = 0; j < from.length; j++) {
                to[i][j] = from[i][j];
            }

        }
    }

    private static Integer[][] findCheapestWay(Integer mat[][], boolean form) {
        int p, l, g, q;
        Integer newMat[][] = new Integer[mat.length][mat.length];
        deepCopy(mat, newMat);

        // O(n^3)
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    mat[i][j] = MIN(mat[i][j], mat[i][k] + mat[k][j]);
                    // p,i ordered pair of i
                    p = i / (n + 1);
                    l = i % (n + 1);
                    // g,q ordered pair of j
                    g = j / (n + 1);
                    q = j % (n + 1);

                    // now saving the cheapest ways

                    if ((newMat[i][j] != 0 && newMat[i][j] != 10000) && cheapestWays[i][j] == null) { // means we already have a course by 1 edge
                        if (form) { // to know if to save the ways as bottle problem
                            cheapestWays[i][j] = "(" + p + "," + l + ")" + " -> (" + g + "," + q + ")";
                        } else
                            cheapestWays[i][j] = "" + i + " -> " + j + "";
                    } else if (mat[i][j] != 0 && mat[i][j] != 10000) { // if there is  more then one  edge course
                        if (cheapestWays[i][j] == null && (newMat[i][j] == 0 || newMat[i][j] == 10000)) {
                            cheapestWays[i][j] = cheapestWays[i][k] + " -> " + cheapestWays[k][j].substring(9);
                            newMat[i][j] = newMat[i][k] + newMat[k][j];
                        } else if (mat[i][k] + mat[k][j] < newMat[i][j]) {
                            cheapestWays[i][j] = cheapestWays[i][k] + " -> " + cheapestWays[k][j].substring(9);
                            newMat[i][j] = newMat[i][k] + newMat[k][j];
                        }

                    }

                }


            }

        }
        return mat;
    }

    private static Node[] Dijkstra(int v1) throws FileNotFoundException {
        int count = 0, current, min = 10000000;
        // first reading data from file
        int i = 0, j = 0;
        int size = 9;
        // creating nodes neighbour matrix
        Node[][] mat = new Node[size][size];
        File file = new File("Nodes.txt");
        Scanner sc = new Scanner(file);


        while (sc.hasNextLine()) {
            String temp[] = sc.nextLine().split(",");

            mat[i][j] = new Node(Boolean.parseBoolean(temp[0]), Boolean.parseBoolean(temp[1]), Integer.parseInt(temp[2]), Integer.parseInt(temp[3]));
            j++;
            if (j == size) {
                i++;
                j = 0;
            }
            if (i == size) {
                break;
            }
        }


        PriorityQueue<Integer> pQueue = new PriorityQueue();


        // creating arr of ways from v1 to each vertex on graph.
        Node[] courses = new Node[size];
        for (i = 0; i < size; i++) {
            courses[i] = new Node(false, false, 10000, 0);
            // start from this node.
            if (i == v1) {
                courses[i].isVisited = true;
                courses[i].cost = 0;
            }
        }

        current = v1;
        // now starting the algorithm
        while (count != size) {
            // checking on the neighbours of this node.
            for (i = 0; i < mat[current].length; i++) {
                // means its a connected neighbour we havent visited yet.
                if (i != current && mat[current][i].isVisited == false && mat[current][i].isConnected && courses[i].isVisited == false) {
                    if (courses[i].cost == 10000) {
                        courses[i].cost = mat[current][i].edge + courses[current].cost;
                    }
                    // means we reached this node from another one, and now we want to see which way its cheaper
                    else {
                        if (courses[current].cost + mat[current][i].edge < courses[i].cost) {
                            courses[i].cost = courses[current].cost + mat[current][i].edge;
                        }
                    }


                }
            }
            // mark that this node was visited
            count++;
            courses[current].isVisited = true;
            // the next neighbour with the minimum cost
            current = findMin(courses);

        }

        return courses;

    }

    private static int findMin(Node[] courses) {
        // this function returns the minimum cost node to continue with the dijkstra algorithm
        int min = 10000;
        for (int i = 0; i < courses.length; i++) {
            if (!courses[i].isVisited) {
                if (min == 10000) {
                    min = i;
                } else if (courses[i].cost < courses[min].cost) {
                    min = i;
                }

            }
        }
        return min;
    }

}
