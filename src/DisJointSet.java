public class DisJointSet {

    int [] parent;
    int [] rank;
    int size;

    public DisJointSet(int size){
        this.size=size;
        parent = new int [size];
        rank = new int [size];
        makeSet();
    }


    // making each vertex's father to be itself
     void makeSet() {
        for (int i=0;i<size;i++){
            parent[i]=i;
        }
    }

    int find(int x){

        // if its not the representative of the set
        if(parent[x]!=x){

            // call it recursively up the tree until we reach the root(e representative of the set )
            // and updating each vertex's father.
            parent[x] = find(parent[x]);

        }
        return parent[x];
    }

    boolean union(int x,int y){
        // finding x,y fathers
        int rootX = find(x);
        int rootY = find(y);

        // means we cant connect because we will close a circle because these two vertexes under the same father.
        if (rootX == rootY){
            System.out.println("cannot connect there vertexes , we will close circle " + x + " " + y);
            return false;
        }

        if(rank[rootX]<rank[rootY]){
            parent[rootX] = rootY;
        }
        else if(rank[rootY]<rank[rootX]){
            parent[rootY] = rootX;
        }

        // if they are on same rank

        else{
            parent[rootY] = rootX;
            rank[rootX] = rank[rootX]+1;
        }

        return  true;
    }

}
