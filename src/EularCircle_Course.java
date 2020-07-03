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

        if (eularCourse()) {

            findEularCourse();

        }
        else{
            String ans = findEularCircle(8);
            System.out.println(ans);
        }




    }

    private static void findEularCourse() {

        int ezugiFirst = 0, ezugiSecond = 0;


        for (int i = 0; i < Graph.size(); i++) {
            // if the deg of this vertex is odd
            if (Graph.get(i).size() % 2 == 1 && i != ezugiFirst) {
                ezugiSecond = i;
            } else if (Graph.get(i).size() % 2 == 1) {
                ezugiFirst = i;
            }
        }

        // now we found the two vertexes with the odd deg.
        // now we check if there is an edge between them, if not we will ad one and send it to the eulcarcircle algo
        // if there is already an edge between them so make a new vertex connect it to both of them and send it to the eulcarcircle algo.
        // afterwards delete the nodes/edges we added from the course.


        if (Graph.get(ezugiFirst).contains(ezugiSecond) && Graph.get(ezugiSecond).contains(ezugiFirst)) { // means there is an edge between them
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(ezugiFirst);
            temp.add(ezugiSecond);
            Graph.add(temp);
            Graph.get(ezugiFirst).add(Graph.size() - 1);
            Graph.get(ezugiSecond).add(Graph.size() - 1);
            String ans = findEularCircle(Graph.size() - 1);
            ans = ans.substring(1, ans.length() - 1);
            System.out.println(ans);


        } else { /// means we dont have an edge between those 2 odd vertexes degrees , so just add an edge.

            Graph.get(ezugiFirst).add(ezugiSecond);
            Graph.get(ezugiSecond).add(ezugiFirst);
            String ans = findEularCircle(ezugiFirst);
            System.out.println(ans);
            ans = ans.substring(1);
            System.out.println(ans);


        }
    }

    private static boolean eularCourse() {

        int eZugi = 0;

        for (ArrayList<Integer> i : Graph) {
            if (i.size() % 2 == 1) {
                eZugi++;
            }
        }
        return eZugi == 2;

    }

    private static String findEularCircle(int start) {
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
        // System.out.println(ans);
        return ans;

    }

    private static void createGrapth() {
//        ArrayList<Integer> v0 = new ArrayList<>();
//        v0.add(1);
//        v0.add(7);
//        v0.add(8);
//        v0.add(2);
//        Graph.add(v0);
//        ArrayList<Integer> v1 = new ArrayList<>();
//        v1.add(0);
//        v1.add(2);
//        Graph.add(v1);
//        ArrayList<Integer> v2 = new ArrayList<>();
//        v2.add(1);
//        v2.add(0);
//        v2.add(7);
//        v2.add(3);
//        Graph.add(v2);
//        ArrayList<Integer> v3 = new ArrayList<>();
//        v3.add(2);
//        v3.add(7);
//        v3.add(5);
//        v3.add(4);
//        Graph.add(v3);
//        ArrayList<Integer> v4 = new ArrayList<>();
//        v4.add(3);
//        v4.add(5);
//        Graph.add(v4);
//        ArrayList<Integer> v5 = new ArrayList<>();
//        v5.add(4);
//        v5.add(7);
//        v5.add(6);
//        v5.add(3);
//        Graph.add(v5);
//        ArrayList<Integer> v6 = new ArrayList<>();
//        v6.add(5);
//        v6.add(7);
//        Graph.add(v6);
//        ArrayList<Integer> v7 = new ArrayList<>();
//        v7.add(0);
//        v7.add(8);
//        v7.add(6);
//        v7.add(2);
//        v7.add(5);
//        v7.add(3);
//        Graph.add(v7);
//        ArrayList<Integer> v8 = new ArrayList<>();
//        v8.add(0);
//        v8.add(7);
//        Graph.add(v8);


        // for eulcar course but we add a vertex and 2 edges to the two odd vertexes
//        ArrayList<Integer> v0 = new ArrayList<>();
////        v0.add(1);
////        v0.add(3);
////        v0.add(4);
////        Graph.add(v0);
////        ArrayList<Integer> v1 = new ArrayList<>();
////        v1.add(0);
////        v1.add(2);
////        v1.add(3);
////        v1.add(4);
////        Graph.add(v1);
////        ArrayList<Integer> v2 = new ArrayList<>();
////        v2.add(1);
////        v2.add(3);
////        Graph.add(v2);
////        ArrayList<Integer> v3 = new ArrayList<>();
////        v3.add(2);
////        v3.add(1);
////        v3.add(0);
////        v3.add(4);
////        Graph.add(v3);
////        ArrayList<Integer> v4 = new ArrayList<>();
////        v4.add(0);
////        v4.add(1);
////        v4.add(3);
////        Graph.add(v4);

        // for eulcar course but we add an edge between those 2 odd vertexes.
        ArrayList<Integer> v0 = new ArrayList<>();
        v0.add(2);
        v0.add(3);
        v0.add(4);
        Graph.add(v0);
        ArrayList<Integer> v1 = new ArrayList<>();
        v1.add(2);
        v1.add(3);
        v1.add(4);
        Graph.add(v1);
        ArrayList<Integer> v2 = new ArrayList<>();
        v2.add(1);
        v2.add(0);
        Graph.add(v2);
        ArrayList<Integer> v3 = new ArrayList<>();
        v3.add(1);
        v3.add(0);
        Graph.add(v3);
        ArrayList<Integer> v4 = new ArrayList<>();
        v4.add(0);
        v4.add(1);
        Graph.add(v4);

    }
}
