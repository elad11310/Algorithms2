import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * this class represents Burning Algorithm in trees only.
 * as the name as the algorithm - we burn every iteration the leaves - which its ok because when we take out a leave
 * the vertex is taken , and the edge is taken also so we maintain on a graph with n vertex and n-1 edges and no cycles so its a tree.
 * this algorithm finds how many centers ( 1,2 only possible in trees), the diameter , and radius.
 * we need to separate 2 occasions:
 * 1) if we have 1 center after the burning its means the diameter has to be even and therefore the radius will be diameter/2.
 * in this case the diameter is equal to the number of burns (number of burning iterations).
 * <p>
 * 2)if we have 2 centers after burning - the diameter is odd which means radius equals to number os burns +1 , and diameter equals to
 * 2*radius -1.
 * <p>
 * data structure for the algorithm : Array list of Array lists , and an arr for saving the degrees.
 * basically this algorithm finds : the center (1 or 2 ) , radius , and diameter.
 */


public class BurningAlgorithm {
    static ArrayList<ArrayList<Integer>> Graph; // the graph.
    static int[] degArray; // for storing the degrees.


    public static void main(String[] args) {

        //creating the graph - Array of Arrays.
        Graph = new ArrayList<>();
        createGrapth();
        //creating arr in size of the Graph
        degArray = new int[Graph.size()];
        // init the arr with values of the degrees of each vertex.
        initArray();

        burningAlgo(); // continue from here!!!!!!!


    }

    private static void burningAlgo() {
        // saving the number of vertexes , when we burn we do counter--.
        int degCounter = degArray.length;
        // saving the current number of leaves in the queue so we know to do number of burns++ when complete a burn.
        int leavesCounter = 0;
        int center = 0, numOfBurns = 0;

        // now creating our Queue
        Queue<Integer> q = new LinkedList<>();
        // first inserting all the leaves to the queue in o(v)
        for (int i = 0; i < degArray.length; i++) {
            if (degArray[i] == 1) {
                q.add(i);
            }
        }
        // saving number of current leaves.
        leavesCounter = q.size();
        while (degCounter >2) {


            int v = q.poll();
            leavesCounter--;
            degCounter--;
            degArray[v] = 0;
            int ind = checkNeighbours(v);
            if (ind != -1) {
                degArray[ind]--;

            }
            if (degArray[ind] == 1) {
                q.add(ind);


            }
            if (leavesCounter == 0) {
                numOfBurns++;
                leavesCounter = q.size();
            }
        }


        if (leavesCounter==1) { // 1 center
             numOfBurns++;
            System.out.println("Diameter is " + numOfBurns * 2);
            System.out.println("Radius is " + numOfBurns);
            // we have left with 2 nodes we know for sure the last in the queue is the center.
            int stam=q.poll();
            center = q.poll();
            System.out.println("Center is " + center);

        } else if (leavesCounter==2) { // 2 centers.
            System.out.println("Diameter is " + (2 * numOfBurns + 1));
            System.out.println("Radius is " + (numOfBurns + 1));
            System.out.print("Centers are : ");
            for (int k = 0; k < degArray.length; k++) {
                if (degArray[k] == 1) {
                    System.out.print(k + " ");
                }
            }

        }


    }

    private static int checkNeighbours(int vertex) {

        for (int i = 0; i < Graph.size(); i++) {
            if (Graph.get(i).contains(vertex) && degArray[i] != 0) {
                return i;
            }
        }
        return -1;
    }

    private static void initArray() {
        for (int i = 0; i < Graph.size(); i++) {
            degArray[i] = Graph.get(i).size();
        }

    }

    private static void createGrapth() {
//        ArrayList<Integer> v0 = new ArrayList<>();
//        v0.add(1);
//        Graph.add(v0);
//        ArrayList<Integer> v1 = new ArrayList<>();
//        v1.add(0);
//        v1.add(2);
//        v1.add(3);
//        Graph.add(v1);
//        ArrayList<Integer> v2 = new ArrayList<>();
//        v2.add(1);
//        Graph.add(v2);
//        ArrayList<Integer> v3 = new ArrayList<>();
//        v3.add(1);
//        v3.add(4);
//        v3.add(5);
//        v3.add(6);
//        Graph.add(v3);
//        ArrayList<Integer> v4 = new ArrayList<>();
//        v4.add(3);
//        Graph.add(v4);
//        ArrayList<Integer> v5 = new ArrayList<>();
//        v5.add(3);
//        Graph.add(v5);
//        ArrayList<Integer> v6 = new ArrayList<>();
//        v6.add(3);
//        v6.add(7);
//        Graph.add(v6);
//        ArrayList<Integer> v7 = new ArrayList<>();
//        v7.add(6);
//        Graph.add(v7);
//
//        ArrayList<Integer> v0 = new ArrayList<>();
//        v0.add(1);
//        Graph.add(v0);
//        ArrayList<Integer> v1 = new ArrayList<>();
//        v1.add(0);
//        v1.add(2);
//        Graph.add(v1);
//        ArrayList<Integer> v2 = new ArrayList<>();
//        v2.add(1);
//        v2.add(3);
//        Graph.add(v2);
//        ArrayList<Integer> v3 = new ArrayList<>();
//        v3.add(2);
//        v3.add(4);
//        v3.add(5);
//        v3.add(7);
//        Graph.add(v3);
//        ArrayList<Integer> v4 = new ArrayList<>();
//        v4.add(3);
//        v4.add(6);
//        Graph.add(v4);
//        ArrayList<Integer> v5 = new ArrayList<>();
//        v5.add(3);
//        Graph.add(v5);
//        ArrayList<Integer> v6 = new ArrayList<>();
//        v6.add(4);
//        Graph.add(v6);
//        ArrayList<Integer> v7 = new ArrayList<>();
//        v7.add(3);
//        v7.add(8);
//        v7.add(9);
//        v7.add(10);
//        v7.add(13);
//        Graph.add(v7);
//        ArrayList<Integer> v8 = new ArrayList<>();
//        v8.add(7);
//        Graph.add(v8);
//        ArrayList<Integer> v9 = new ArrayList<>();
//        v9.add(7);
//        Graph.add(v9);
//        ArrayList<Integer> v10 = new ArrayList<>();
//        v10.add(7);
//        v10.add(11);
//        Graph.add(v10);
//        ArrayList<Integer> v11 = new ArrayList<>();
//        v11.add(10);
//        v11.add(12);
//        Graph.add(v11);
//        ArrayList<Integer> v12 = new ArrayList<>();
//        v12.add(11);
//        Graph.add(v12);
//        ArrayList<Integer> v13 = new ArrayList<>();
//        v13.add(14);
//        v13.add(15);
//        v13.add(7);
//        Graph.add(v13);
//        ArrayList<Integer> v14 = new ArrayList<>();
//        v14.add(13);
//        Graph.add(v14);
//        ArrayList<Integer> v15 = new ArrayList<>();
//        v15.add(13);
//        v15.add(16);
//        Graph.add(v15);
//        ArrayList<Integer> v16 = new ArrayList<>();
//        v16.add(15);
//        Graph.add(v16);


//        ArrayList<Integer> v0 = new ArrayList<>();
//        v0.add(3);
//        Graph.add(v0);
//        ArrayList<Integer> v1 = new ArrayList<>();
//        v1.add(2);
//        Graph.add(v1);
//        ArrayList<Integer> v2 = new ArrayList<>();
//        v2.add(1);
//        v2.add(3);
//        Graph.add(v2);
//        ArrayList<Integer> v3 = new ArrayList<>();
//        v3.add(2);
//        v3.add(4);
//        v3.add(0);
//        Graph.add(v3);
//        ArrayList<Integer> v4 = new ArrayList<>();
//        v4.add(3);
//        v4.add(5);
//        v4.add(6);
//        Graph.add(v4);
//        ArrayList<Integer> v5 = new ArrayList<>();
//        v5.add(4);
//        Graph.add(v5);
//        ArrayList<Integer> v6 = new ArrayList<>();
//        v6.add(4);
//        v6.add(7);
//        Graph.add(v6);
//        ArrayList<Integer> v7 = new ArrayList<>();
//        v7.add(6);
//        v7.add(8);
//        v7.add(9);
//        v7.add(10);
//        Graph.add(v7);
//        ArrayList<Integer> v8 = new ArrayList<>();
//        v8.add(7);
//        Graph.add(v8);
//        ArrayList<Integer> v9 = new ArrayList<>();
//        v9.add(7);
//        Graph.add(v9);
//        ArrayList<Integer> v10 = new ArrayList<>();
//        v10.add(7);
//        Graph.add(v10);

        ArrayList<Integer> v0 = new ArrayList<>();
        v0.add(3);
        v0.add(2);
        v0.add(4);
        v0.add(1);
        Graph.add(v0);
        ArrayList<Integer> v1 = new ArrayList<>();
        v1.add(0);
        Graph.add(v1);
        ArrayList<Integer> v2 = new ArrayList<>();
        v2.add(0);
        Graph.add(v2);
        ArrayList<Integer> v3 = new ArrayList<>();
        v3.add(0);
        Graph.add(v3);
        ArrayList<Integer> v4 = new ArrayList<>();
        v4.add(0);
        Graph.add(v4);
        ArrayList<Integer> v5 = new ArrayList<>();
        v5.add(19);
        Graph.add(v5);
        ArrayList<Integer> v6 = new ArrayList<>();
        v6.add(19);
        Graph.add(v6);
        ArrayList<Integer> v7 = new ArrayList<>();
        v7.add(19);
        Graph.add(v7);
        ArrayList<Integer> v8 = new ArrayList<>();
        v8.add(19);
        Graph.add(v8);
        ArrayList<Integer> v9 = new ArrayList<>();
        v9.add(10);
        v9.add(19);
        Graph.add(v9);
        ArrayList<Integer> v10 = new ArrayList<>();
        v10.add(9);
        Graph.add(v10);
        ArrayList<Integer> v11 = new ArrayList<>();
        v11.add(0);
        v11.add(12);
        v11.add(13);
        Graph.add(v11);
        ArrayList<Integer> v12 = new ArrayList<>();
        v12.add(11);
        Graph.add(v12);
        ArrayList<Integer> v13 = new ArrayList<>();
        v13.add(11);
        v13.add(9);
        v13.add(14);
        Graph.add(v13);
        ArrayList<Integer> v14 = new ArrayList<>();
        v14.add(13);
        v14.add(15);
        Graph.add(v14);
        ArrayList<Integer> v15 = new ArrayList<>();
        v15.add(14);
        v15.add(16);
        Graph.add(v15);
        ArrayList<Integer> v16 = new ArrayList<>();
        v16.add(15);
        v16.add(17);
        Graph.add(v16);
        ArrayList<Integer> v17 = new ArrayList<>();
        v17.add(16);
        v17.add(18);
        Graph.add(v17);
        ArrayList<Integer> v18= new ArrayList<>();
        v18.add(17);
        Graph.add(v18);
        ArrayList<Integer> v19= new ArrayList<>();
        v19.add(5);
        v19.add(6);
        v19.add(7);
        v19.add(8);
        Graph.add(v19);

    }
}
