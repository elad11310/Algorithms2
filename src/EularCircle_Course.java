import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class EularCircle_Course {


    /**
     * this class represents 2 algorithms to find an Eular circle in a graph and Eular course in a graph(we assume we do have them)
     * for an Eular circle we know we need two things: first the graph should be connected , second all the vertexes degrees must be even.
     * for an Eular course we need also two things: first graph connected, second only 2 or 0 vertexes should be with odd degree.
     */

    static ArrayList<ArrayList<Integer>> Graph;

    public static void main(String[] args) {

        //creating the graph - Array of Arrays.
        Graph = new ArrayList<>();
        createGrapth();
        findEularCircle(8);


    }

    private static void findEularCircle(int start) {
        // initialize  a stack for this algorithm , when we will finish a sub circle we will know to go back to start printing the course
        // and another for saving the course.
        Stack<ArrayList<Integer>> s = new Stack<ArrayList<Integer>>();
        Stack<Integer> s2 = new Stack<>();

        // for the course
        String ans = "";

        // inserting the first vertex to start the course from.
        s.push(Graph.get(start));
        s2.push(start);

        while (!s.isEmpty()) {

            ArrayList<Integer> u = s.peek();

            // if we know we finished a sub circle
            if (u.size() == 0) {

                s.pop(); // removing it from stack
                ans += s2.pop(); // start moving back until we have more neighbours to visit
                if (!s2.isEmpty())
                    start = s2.peek();

            } else {
                // pushing next neighbour
                int neighbour = Graph.get(start).get(0);
                s.push(Graph.get(u.get(0)));
                s2.push(u.get(0));
                // deleting the edge between those two vertexes.


                for (int i = 0; i < Graph.get(u.get(0)).size(); i++) {
                    if (Graph.get(u.get(0)).get(i) == start) {
                        Graph.get(u.get(0)).remove(i);
                    }
                }

                u.remove(0);

                start = neighbour;
            }

        }
        System.out.println(ans);

    }

    private static void createGrapth() {
        ArrayList<Integer> v0 = new ArrayList<>();
        v0.add(1);
        v0.add(7);
        v0.add(8);
        v0.add(2);
        Graph.add(v0);
        ArrayList<Integer> v1 = new ArrayList<>();
        v1.add(0);
        v1.add(2);
        Graph.add(v1);
        ArrayList<Integer> v2 = new ArrayList<>();
        v2.add(1);
        v2.add(0);
        v2.add(7);
        v2.add(3);
        Graph.add(v2);
        ArrayList<Integer> v3 = new ArrayList<>();
        v3.add(2);
        v3.add(7);
        v3.add(5);
        v3.add(4);
        Graph.add(v3);
        ArrayList<Integer> v4 = new ArrayList<>();
        v4.add(3);
        v4.add(5);
        Graph.add(v4);
        ArrayList<Integer> v5 = new ArrayList<>();
        v5.add(4);
        v5.add(7);
        v5.add(6);
        v5.add(3);
        Graph.add(v5);
        ArrayList<Integer> v6 = new ArrayList<>();
        v6.add(5);
        v6.add(7);
        Graph.add(v6);
        ArrayList<Integer> v7 = new ArrayList<>();
        v7.add(0);
        v7.add(8);
        v7.add(6);
        v7.add(2);
        v7.add(5);
        v7.add(3);
        Graph.add(v7);
        ArrayList<Integer> v8 = new ArrayList<>();
        v8.add(0);
        v8.add(7);
        Graph.add(v8);
    }
}
