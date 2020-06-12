import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Isomorphic_Trees {


    /**
     * this class examines if two trees are isomorphic, which means the trees are the same but in different form.
     * we will calculate each tree String by the following : if we go down put 0 to the String and if we go up we put 1 to the string.
     * so the algorithm is to recursive run on the tree , when we reach a leaf we add 01 and for is father we start in 0 , ends in 1 ,
     * and in the middle will be all his sons String sorted by lexicographic sort. finally if the two Tree Strings are equal we can say
     * those trees are isomorphic.
     */

    static ArrayList<ArrayList<Integer>> TreeOne;
    static ArrayList<ArrayList<Integer>> TreeTwo;
    static int[] fathers;
    static ArrayList<String> treeString;
    static String s1, s2;

    public static void main(String[] args) {

        TreeOne = new ArrayList<>();
        TreeTwo = new ArrayList<>();
        treeString = new ArrayList<>();
        treeString.add("0");
        createGraph();
        isIsomorphic();



    }

    private static void isIsomorphic() {


        s1 = getString(0, Integer.MAX_VALUE, TreeOne, "");
        System.out.println("Before sorting");
        System.out.println(s1);
        // sort first string
        s1 = sortByLexicographic(treeString);
        treeString.clear();
        treeString.add("0");
        s2 = getString(0, Integer.MAX_VALUE, TreeTwo, "");
        System.out.println(s2);
        // sorting second string
        s2 = sortByLexicographic(treeString);

        System.out.println("After sorting");
        if(s1.equals(s2)){

            System.out.println("Isomorphic");
        }


        else{
            System.out.println("Not Isomorphic");
        }

        System.out.println(s1);
        System.out.println(s2);

    }

    private static String sortByLexicographic(ArrayList<String> treeString) {
        // we gonna make a Lexicographic sort and for that we will use java"s compareTo method, it will return -1 if a string should be before another,
        // 1 if not , and 0 if they equal.

        String ans = "";
        // sorting
        Collections.sort(treeString);

        for (String s : treeString) {
            ans += s;
        }

        return ans;
    }


    private static String getString(int vertex, int father, ArrayList<ArrayList<Integer>> Tree, String str) {


        for (int i = 0; i < Tree.get(vertex).size(); i++) {
            // if we on a leaf
            if (Tree.get(vertex).size() == 1 && Tree.get(vertex).get(0) == father) {
                str += "1";
                return str;
            }
            // going into sons.
            str += "0";
            if (Tree.get(vertex).get(i) != father) {
                str = getString(Tree.get(vertex).get(i), vertex, Tree, str);
            } else {
                str = getString(Tree.get(vertex).get(++i), vertex, Tree, str);
                if (father != Integer.MAX_VALUE && i == Tree.get(father).size() - 1) {
                    return str;
                }
            }
            // means we have more sons to concat their string's - go deeply into the tree
            if (father != Integer.MAX_VALUE && i < Tree.get(father).size()) {
                continue;


            } else {
                // we finished a son , concat it .
                treeString.add(str);
                str = "";
            }
        }

        // if we finished the concating of the tree
        if (father == Integer.MAX_VALUE) {
            for (String s : treeString) {
                str += s;

            }
            str += "1";
            return str;
        }

        str += "1";
        return str;

    }

    private static void createGraph() {
/// tree one
        ArrayList<Integer> v0 = new ArrayList<>();
        v0.add(1);
        v0.add(2);
        v0.add(3);
        TreeOne.add(v0);
        ArrayList<Integer> v1 = new ArrayList<>();
        v1.add(0);
        TreeOne.add(v1);
        ArrayList<Integer> v2 = new ArrayList<>();
        v2.add(0);
        v2.add(4);
        v2.add(5);
        TreeOne.add(v2);
        ArrayList<Integer> v3 = new ArrayList<>();
        v3.add(0);
        TreeOne.add(v3);
        ArrayList<Integer> v4 = new ArrayList<>();
        v4.add(2);
        TreeOne.add(v4);
        ArrayList<Integer> v5 = new ArrayList<>();
        v5.add(2);
        v5.add(6);
        TreeOne.add(v5);
        ArrayList<Integer> v6 = new ArrayList<>();
        v6.add(5);
        TreeOne.add(v6);

//        // tree two
//        ArrayList<Integer> v11 = new ArrayList<>();
//        v11.add(1);
//        v11.add(2);
//        v11.add(3);
//        TreeTwo.add(v11);
//        ArrayList<Integer> v12 = new ArrayList<>();
//        v12.add(0);
//        v12.add(4);
//        v12.add(5);
//        TreeTwo.add(v12);
//        ArrayList<Integer> v13 = new ArrayList<>();
//        v13.add(0);
//        TreeTwo.add(v13);
//        ArrayList<Integer> v14 = new ArrayList<>();
//        v14.add(0);
//        TreeTwo.add(v14);
//        ArrayList<Integer> v15 = new ArrayList<>();
//        v15.add(1);
//        TreeTwo.add(v15);
//        ArrayList<Integer> v16 = new ArrayList<>();
//        v16.add(1);
//        v16.add(6);
//        TreeTwo.add(v16);
//        ArrayList<Integer> v17 = new ArrayList<>();
//        v17.add(5);
//        TreeTwo.add(v17);

        ArrayList<Integer> v11 = new ArrayList<>();
        v11.add(1);
        v11.add(2);
        v11.add(3);
        TreeTwo.add(v11);
        ArrayList<Integer> v12 = new ArrayList<>();
        v12.add(0);
        TreeTwo.add(v12);
        ArrayList<Integer> v13 = new ArrayList<>();
        v13.add(0);
        v13.add(4);
        TreeTwo.add(v13);
        ArrayList<Integer> v14 = new ArrayList<>();
        v14.add(0);
        v14.add(5);
        v14.add(6);
        TreeTwo.add(v14);
        ArrayList<Integer> v15 = new ArrayList<>();
        v15.add(2);
        TreeTwo.add(v15);
        ArrayList<Integer> v16 = new ArrayList<>();
        v16.add(3);
        TreeTwo.add(v16);
        ArrayList<Integer> v17 = new ArrayList<>();
        v17.add(3);
        TreeTwo.add(v17);


    }
}