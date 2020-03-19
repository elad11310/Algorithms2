/**
 * this class represents bottles problem which converted to a graph
 * description : we have bottle A with 5 litters and a bottle B with 3 litters we want to get the situation :
 * bottle A with 4 litters.
 * we can do the following: empty A or B, fill to the max A or B , or moving from A to B some litters.
 * this problem converted into a graph with situations, for instance if A=1 and B =2 we have
 * (1+1)*(2+1) situations (Vertexes) +1 because of the 0 , so here are the Vertexes: (0.0),(0,1),(0,2),(1,0),(1,1),(1,2)
 * the goal: to check weather if we can reach from a vertex to another by the instructions i depicted above. for each vertex we have 6 options
 * (a,b) - some litters in A and B - (a,0),(0,b),(A,b),(a,B),(a+b-min(a+b,B),min(a+b,B),(min(a+b,A),a+b-min(a+b,A))
 * we will use a matrix to depict all situations for example for A=1,B=2 (0.0),(0,1),(0,2),(1,0),(1,1),(1,2) for rows and (0.0),(0,1),(0,2),(1,0),(1,1),(1,2)
 * for cols. then we will return a boolean matrix where a cell with true value - there is a way from vertex to another.
 *
 */




public class BottlesProblem {

    public static void main(String[] args) {

        System.out.println("Hello World!");
    }
}
