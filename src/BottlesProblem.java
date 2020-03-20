import java.util.Arrays;

import static sun.nio.cs.Surrogate.MIN;

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
 */


public class BottlesProblem {
    static int n = 3, m = 2;
    static int size = (m + 1) * (n + 1);

    public static void main(String[] args) {

        boolean mat [][] = bottlesProblem(m,n);
        // System.out.println(Arrays.deepToString(bottlesProblem(m, n)));
        for (int i = 0; i <size;i++){
            for (int j=0;j<size;j++){
                System.out.print(mat[i][j]+" , ");
            }
            System.out.println();
        }
    }

    private static boolean[][] bottlesProblem(int m, int n) {

        boolean[][] mat = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mat[i][j] = checkWay(i, j);
            }
        }

        return mat;
    }

    private static boolean checkWay(int i, int j) {

        // k ,p are ordered pair of i
        int k = i / (n + 1);
        int p = i % (n + 1);
        // k ,p are ordered pair of j
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
}
