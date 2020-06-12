import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * this class represents algorithms 2 course in ariel university:
 * bottles problem which converted to a graph :
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
 *  another algorithm ( to duplicate the arr and to use findDiameterGreedy(O(n)) on "different arr " each time but with a restriction to search for parts that are no longer then
 *  the size of the original arr. duplicateArrFindDiameter total: O(n**2)
 *
 *
 *  another algorithm is based on symmetric that as follow : Diameter = Max(best(A) , sum(A) -- best(-A)) -- will be +. O(n)
 *  findDiameterSymmetric
 *
 *
 *  another algorithm is dynamic programming  -  open matrix - putting in the diagonal the arr values based on the dynamic programming we did before
 *  when we didnt search in circled arr , but in normal . now for circled arr we need to fill the left down diagonal , we will sum all the sequence and do the following:
 *  sum(arr) -[2,2] = [3,1] , sum(arr) - [2,3] = [4,1] for an instance. k=j+1 , p=(i+j)-k
 *
 * new subject - gas station problem. we have a circle with points. each point is a gas station where we can fill gas.
 * now we want to check if we can start from a certain point and close a circle. between point to point we have a cost.
 * so we basically have 2  arr's , 1 for the gas station (each variable represents num of liters we can fill),second arr is for the costs between
 * point to point. is there a way to close a circle in O(n)? the idea is (if a means gas stations and b means costs) to check if
 * a1>b1 and then a1+a2>b1+b2 and .... why not to make sum of first array(Gas statiosn) and sum of second array(costs) and then to see if sum(a) > sum(b)?
 * because the total sum might be greater but in the way we might have a course where the fuel we have at the moment is not enough to get from ai to ai+1.
 * gasStationProblem
 *
 * now we want to know if any point exists which i can finish a circle when i start driving from it.
 * we will convert the problem to find longest sub sequence in circle arr , how? we take arr A which represents the gas stations , we will take arr B
 * which represents the cost from gas to another and we will subtract b from a into new arr C.
 * now we will execute on c the  symmetric algorithm that finds the biggest sequence in circle in O(n), we will save the starting index of the biggest sub sequence
 * (saving index from best(A) , and saving from sum(A) - -best(-A)
 * and that's will be the index of the gas station that we can make a circle from it.
 * gasStationProblemConverted
 *
 *
 * now we gonna find biggest sub sequence in a matrix. we will see 4 methods:
 * 1) full search O(n**6)
 * 2) algorithm with an helper arr in O(n**4) - by taking all possible lines , adding them to 1 arr and sent it to Best
 * 3) algorithm with an helper matrix (dynamic programming) O(n**4)
 * 4) superBest - we take algo's number 2 and 3 and improve them to O(n**3)
 * first we check which one is larger n or m. if m so it will be O(m**2*n) and if me is smaller so O(n**2*m).
 * we take every iteration 1 col or 1 row (depends on m or n) and then we send it to best - saving index start of best and end and i,j(rectangle)
 * next iteration we are not zeroing the arr yet we add the next row/col on it and then again send to best.
 * when i grow by one we will zero the arr and then it all begins again, at the end we will have the rectangle with the largest sum in the matrix in O(n**3) if n==m.
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


        int mat [][] = {{2,1,-3,-4,5},{7,-4,7,2,4},{0,6,3,4,1},{-3,3,1,0,3}};
        int mat2 [][] = {{9,5,-2,5,-8},{7,-4,7,2,4},{-1,7,2,1,-6},{-3,3,-1,0,1}};
        int mat3 [][] = {{-2,5,1,-8,-3},{4,-100,10,-3,1},{-10,7,7,5,-2},{1,5,-20,3,-5}};
        int mat4 [][] = {{-2,-5,-1,-8,-3},{4,100,-10,-3,-1},{10,7,-7,-5,-2},{-1,-5,-20,-3,-5}};
        int mat5 [][] = {{-2,-5,-1,-8,-3},{4,100,10,3,1},{10,7,7,5,2},{1,5,20,3,5}};
        System.out.println(findSubSeqMatrixFullSearch(mat5));
        System.out.println(findSubSeqMatrixCreateLines(mat5));
        System.out.println(findSubSeqMatrixDynamic(mat5));
        System.out.println(findSubSeqMatrixSuperBest(mat5));



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
    private static int  findSubSeqMatrixSuperBest(int [][] matrix){
        int endY=0,endX=0,startI=0,startJ=0,i,j,k,sum=0,max=0;
        // checking who is larger m/n
        int n = matrix.length,m = matrix[0].length;

        //O(m**2*n)
        if(m>n){
            int [] helpArr = new int[matrix.length];
            for(i=0;i<matrix[0].length;i++){
                // zeroing the arr
                zeroingArr(helpArr);
                for(j=i;j<matrix[0].length;j++){
                    for(k=0;k<matrix.length;k++){
                        helpArr[k]+=matrix[k][j];
                    }
                    int [] ans =findDiameterGreedy(helpArr);
                    sum=ans[2];

                    if(sum>max){
                        max=sum;
                        startI=ans[0];
                        endY =j;
                        endX = ans[1]-1+startI;
                        startJ=i;
                    }
                }
            }



        }
        // O(n**2*m)
        else{
            int [] helpArr = new int[matrix.length];
            for(i=0;i<matrix.length;i++) {
                // zeroing the arr
                zeroingArr(helpArr);
                for (j = i; j < matrix.length; j++) {
                    for (k = 0; k < matrix[0].length; k++) {
                        helpArr[k] += matrix[j][k];
                    }
                    int [] ans =findDiameterGreedy(helpArr);
                    sum=ans[2];

                    if(sum>max){
                        max=sum;
                        startI=ans[0];
                        endY =j;
                        endX = ans[1]-1+startI;
                        startJ=i;
                    }
                }
            }

        }
        System.out.println("Starting indices : " + startI +" "+  startJ + " Ending indices: " + endX + " " + endY);
        return max;
    }
    private static int  findSubSeqMatrixCreateLines(int [][] matrix){
        int sum=0,max=0,ind=0,length=0;
        int startX=0,startY=0,endX=0,endY=0;
        // creating a helper arr
        int arr [] = new int[matrix[0].length];
        // n**2 for creating all possible linge
        for(int i=0;i<matrix.length;i++){
            for(int j=i;j<matrix.length;j++){
                // another n**2 for summing the lines
                for(int x=i;x<=j;x++){
                    for(int y=0;y<matrix[0].length;y++){
                        arr[y]+=matrix[x][y];
                    }
                }
                // o**m for best algo
                int ans [] = findDiameterGreedy(arr);
                sum = ans[2]; // the value best returns
                ind =ans[0]; // the first index of the longest sub sequence
                length = ans[1]; // the length of the sequence
                if(sum>max){
                    max=sum;
                    startX=i;
                    startY=ind;
                    endY = j;
                    endX= length-1+ind;

                }
                zeroingArr(arr);
            }
        }
        // if n==m so total is n**4 + O(n) for best and O(n) for zeroing arr. sp O(n**4)
        System.out.println("Starting indices : " + startX +" "+  startY + " Ending indices: " + endY + " " + endX);
        return max;
    }
    private static int findSubSeqMatrixDynamic(int [][] matrix) {
        int i, j, k, l, sum = 0, max = 0, startX = 0, startY = 0, endX = 0, endY = 0;
        // first we create a helper matrix.
        int[][] helpMat = new int[matrix.length][matrix[0].length];


        // putting the first variable so we have a variable to relay on when we sum first row and first col
        helpMat[0][0] = matrix[0][0];
        // first, we fill the first row and the first col with the sum of the current place + the previous sum till this point.(just summing row and col) O(n)

        for (i = 1; i < helpMat[0].length; i++) {
            helpMat[0][i] = helpMat[0][i- 1]  + matrix[0][i];
        }
        for (i = 1; i < helpMat.length; i++) {
            helpMat[i][0] = helpMat[i - 1][0] + matrix[i][0];
        }


        // now go through the helper mat and from each point we calculate the sum from 0,0 to this point, based on previous calculations. O(n**2)

        for (i = 1; i < helpMat.length; i++) {
            for (j = 1; j < helpMat[0].length; j++) {
                helpMat[i][j] = matrix[i][j] + helpMat[i][j - 1] + helpMat[i - 1][j] - helpMat[i - 1][j - 1];
            }
        }


        // now we we have in helpMatrix in each point the value of the rectangle from 0,0 to this point , we can calculate now sub rectangles O(N**4)(if n==m)

        for (i = 1; i < helpMat.length; i++) {
            for (j = 1; j < helpMat[0].length; j++) {
                for (k = i; k < helpMat.length; k++) {
                    for (l = j; l < helpMat[0].length; l++) {
                        sum = helpMat[k][l] - helpMat[k][j - 1] - helpMat[i - 1][l] + helpMat[i - 1][j - 1];
                        if (sum > max) {
                            max = sum;
                            startX = i;
                            startY = j;
                            endX = k;
                            endY = l;
                        }
                    }
                }
            }

        }

        // we have some special cases we need to take care of(because we start from 1,1, some recatngles are missing)
        // so we will do for case (i=-,j=0), (i=0),(j=0)

        // i=0

        for(i=0;i<helpMat.length;i++){
            for(j=1;j<helpMat[0].length;j++){
                for(k=0;k<j;k++){
                    sum = helpMat[i][j]-helpMat[i][k];
                    if (sum > max) {
                        max = sum;
                        startX = 0;
                        startY = i-j;
                        endX = i;
                        endY = j;
                    }
                }
            }
        }

        //j=0


        for(i=1;i<helpMat.length;i++){
            for(j=0;j<helpMat[0].length;j++){
                sum=helpMat[i][j] - helpMat[i-i][j];
                if(sum>max){
                    max=sum;
                        startX = 1;
                        startY = 0;
                        endX = i;
                        endY = j;
                }
            }
        }

        // i,j

        for(i=0;i<helpMat.length;i++){
            for(j=0;j<helpMat[0].length;j++){
                if(helpMat[i][j]>max){
                    max = helpMat[i][j];
                     startX = 0;
                     startY = 0;
                     endX = i;
                     endY = j;

                }
            }
        }

        System.out.println("Starting indices : " + startX +" , " + startY +" Ending indices: " + endX + " , " + endY);
        return max;
    }
    private static void zeroingArr(int[] arr) {
        for(int i=0;i<arr.length;i++){
            arr[i]=0;
        }
    }
    private static int findSubSeqMatrixFullSearch(int [][] matrix){
        // this algorithm creates all possible options for sub sequence in matrix , checks which is the largest, returns its number and start,end indices.
        int startX=0,startY=0,endX=0,endY=0;
        int sum=0,max=0;

        // calculating indices for up left corner of the rectangle.
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                // calculating indices for down right corner of the rectangle.
                for(int k=i;k<matrix.length;k++){
                    for(int l=j;l<matrix[k].length;l++){
                        // summing the current rectangle
                        for(int x=i;x<=k;x++){
                            for (int y=j;y<=l;y++){
                                sum+=matrix[x][y];
                            }

                        }
                        if(sum>max){
                            max=sum;
                            startX=i;
                            startY=j;
                            endX=k;
                            endY=l;
                        }
                        sum=0;
                    }
                }
            }
        }
        System.out.println("Starting indices : " + startX +" , " + startY +" Ending indices: " + endX + " , " + endY);
        return  max;

    }
    private static int duplicateArrFindDiameter(int [] weights){
        int max=0;
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
        for(int i=0;i<weights.length;i++) {
            int ans = findDiameterGreedy(newArr,i,i+weights.length-1);
            if(max<ans){
                max=ans;
            }
        }
        return  max;

    }
    private static boolean gasStationProblemConverted(int [] gasStations, int [] costs){
        int convertedArr [] = new int[gasStations.length]; // creating new arr in size of both of the arr's
        for(int i=0;i<convertedArr.length;i++){ // filling it by subtracting from gasStations arr the costs arr.
            convertedArr[i]= gasStations[i]-costs[i];
        }
        // now we gonna use findDiameterSymmetric if the result is 0 or beneth there is no any point on the circle we can complete a full circle from it.
        // if now we gonna the the max from (Best(convertedArr), sum(convertedArr) - - Best(-convertedArr). and the starting index is from the algorithm which returns the max value.
        // didnt understand how to calculate the starting index of the  sum(convertedArr) - - Best(-convertedArr).

        return true;
    }
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
        return MAX(findDiameterGreedy(weights,true),Sum(weights,0,weights.length-1)-findDiameterGreedy(weights,false));
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
    private static int findDiameterGreedy(int[] weights,int i,int j) {
        int current = 0,  max = weights[0] , firstIndex=0,lastIndex=0,potentialIndex=0;
        for (int k = i; k <= j; k++) {

            current += weights[k];


            if (current > max) {
                max = current;
                lastIndex=i;
                firstIndex=potentialIndex;

            }
            if (current < 0) {// if we want longest number of edges we do current<0 , if we want shortest num of edges we do current<=0
                current = 0;
                potentialIndex=i+1;
                continue;
            }


        }
        lastIndex = lastIndex- firstIndex+1;  // setting the length of the edges that connected for the diameter
        //System.out.println("The diameter is " + max + " and in num of edges :" + lastIndex);
        return max;
    }
    private static int findDiameterGreedy(int[] weights,boolean isPositiveSum) {
        int current = 0, max = weights[0] , firstIndex=0,lastIndex=0,potentialIndex=0;

        // if we want the smallest sum instead of highest
        if(!isPositiveSum){
            changeSign(weights);
        }

        for (int i = 0; i < weights.length; i++) {


            current += weights[i];

            if (current > max) {
                max = current;
                lastIndex=i;
                firstIndex=potentialIndex;
            }

            if (current <= 0) { // if we want longest number of edges we do current<0 , if we want shortest num of edges we do current<=0
                current = 0;
                potentialIndex=i+1;
                continue;
            }





        }
        if(!isPositiveSum){
            max*=-1;
        }
        lastIndex = lastIndex- firstIndex+1;  // setting the length of the edges that connected for the diameter
        System.out.println("The diameter is " + max + " and in num of edges :" + lastIndex);

        return max;
    }
    private static int [] findDiameterGreedy(int[] weights){
        int [] arr = new int[3];
        int current = 0, max = weights[0] , firstIndex=0,lastIndex=0,potentialIndex=0;


        for (int i = 0; i < weights.length; i++) {


            current += weights[i];

            if (current > max) {
                max = current;
                lastIndex=i;
                firstIndex=potentialIndex;
            }

            if (current <= 0) { // if we want longest number of edges we do current<0 , if we want shortest num of edges we do current<=0
                current = 0;
                potentialIndex=i+1;
            }





        }

        lastIndex = lastIndex- firstIndex+1;  // setting the length of the edges that connected for the diameter
        arr[0]= firstIndex;
        arr[1] = lastIndex;
        arr[2] = max;
        return arr;
    }
    private static void changeSign(int[] weights) {
       for(int i=0;i<weights.length;i++){
           weights[i]*=-1;
       }
    }
    private static int findDiameterDynamicCircled(int [] weights){
        // first , we use the dynamic programming which handles in a normal graph diameter , which means filling the up right triangle.
        int i, j, max = 0, numOfEdges = 0;
        int sum = Sum(weights,0,weights.length-1);
        int mat [][] = new int[weights.length][weights.length];
        // now putting the values from the weights arr into the diagonal O(n)
        for (i = 0; i < weights.length; i++) {
            mat[i][i] = weights[i];
            // starting looking for max from now
            if (weights[i] > max) {
                max = weights[i];
                numOfEdges = 1;
            }
        }
        // now first algorithm  mat[i,j] = mat[i,j-1] +mat [j,j] O(n**2)
        for (i = 0; i < mat.length - 1; i++) {
            for (j = i + 1; j < mat[i].length; j++) {
                mat[i][j] = mat[i][j - 1] + mat[j][j];
                if (mat[i][j] > max) {
                    max = mat[i][j];
                    numOfEdges = Math.abs(i - j) + 1;
                }
            }
        }


        // now by using the filled up right  triangle of the matrix, we gonna fill the left down triangle which the max there - its make sequence in the circled arr

        for(i=1;i<mat.length;i++){
            for(j=0;j<i;j++){
                int k = j+1;
                int p = (i+j)-k;
                mat[i][j]= sum - mat[k][p];
                if(mat[i][j]>max){
                    max=mat[i][j];
                    numOfEdges = weights.length-i+j+1;
                }
            }
        }
        System.out.println("The diameter is " + max + " and in num of edges :" + numOfEdges);
        return max;
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
