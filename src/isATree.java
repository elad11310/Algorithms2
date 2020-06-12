import java.util.Arrays;

public class isATree {


    /**
     *
     *  in this class im gonna check weather an array of graph degrees is a tree or not.
     *  first we will check if the sum of the degrees equals to the size of the  2 times (vertexes -1) as expected in a tree
     *  and if so we will start the algorithm for finding the tree.
     *  there is a sentence that says in a tree we have at least 2 leaves, so we gonna "connect" each leaf with a vertex with deg greater then 1,
     *  because we dont want to connect two leaves , a thing that will lead to another connecting component and then its not a tree.
     *  the input is: an array with the degree of each vertex.
     *  output : an array which its indices will represent the children and its value will represent the father, and then we can reconstruct the tree.
     *
     */

    static int [] tree;
    public static void main(String [] args)
    {


        int [] degrees = {1,2,3,2,3,1,1,1};
        getTree(degrees);
        for(int i: tree){
            System.out.print(i + " ");
        }
    }

    private static void getTree(int[] degrees) {
        int sum=0,j=0,i=0;
        // first we gonna sum the degrees and check if could be a tree.

       for ( int k : degrees){
           sum+=k;
       }

       // if its a tree
       if (sum/2 == degrees.length-1){
           tree = new int[degrees.length];
           // sorting the deg arr
           Arrays.sort(degrees);

           // putting pointer at the first index that greater then 1
           while(degrees[j]<=1){
               j++;
           }

           for(i=0;i<degrees.length-2;i++){
               tree[i]=j; // putting this leaf its father
               degrees[j]-=1; // decreasing 1 son from this father
               // if we finished with all this father's sons , move to the next father
               if(degrees[j]==1){
                   j++;
               }

           }
           // the last two ones we will connect separately
           tree[i]=j-1;


       }

    }


}
