import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Huffman_Encoding {


    /**
     * this class represents the huffman encoding.
     * whats the idea? to take each time 2 nodes with the lowest frequency of the chars and to connect them to a father,
     * and build a tree . how to draw the encode for any char? starting from the root and for going left we will concat 1 and right 0.
     * when we reach a leaf , inside the node will be tha encoded char , and the string we concat until it will be printed.
     * for the first algorithm we use min_priority_queue to draw the minimum frequency nodes in the most efficient way.
     * at last in the queue will remain the root - we draw it and start iterating the tree to draw all the encodes.
     * complexity O(n*logn).
     * <p>
     * in the second algorithm we assume the chars with the frequency are sorted so we dont need to find the minimum each time
     * so we can make it using 2 normal queues in O(N)
     */


    static class Node {
        char c;
        int frequency;
        Node left;
        Node right;

        public Node(char c, int frequency) {
            this.c = c;
            this.frequency = frequency;
            left = null;
            right = null;
        }
    }

    static class sortByFreq implements Comparator<Node> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Node a, Node b) {
            return a.frequency - b.frequency;
        }
    }

    public static void main(String[] args) {


        Node[] freq = new Node[5];
        freq[0] = new Node('a', 5);
        freq[1] = new Node('b', 6);
        freq[2] = new Node('c', 11);
        freq[3] = new Node('d', 12);
        freq[4] = new Node('e', 66);


        Huffman(freq);
        Huffman2();


    }

    private static void Huffman2() {

        // creating two queues

        Queue<Node> q1 = new LinkedList<>();
        Queue<Node> q2 = new LinkedList<>();

        // we insert all the nodes with the minimum frequency to the queue ( assume its sorted from min to max).
        // because the queue is sorted the poll is in O(1)
        q1.add(new Node('a', 5));
        q1.add(new Node('b', 6));
        q1.add(new Node('c', 11));
        q1.add(new Node('d', 12));
       q1.add(new Node('e', 66));


        Node father = new Node('0', 0);
        father.left = q1.poll();
        father.right = q1.poll();
        father.frequency = father.left.frequency + father.right.frequency;
        q2.add(father);

        while (!q1.isEmpty() && !q2.isEmpty()) {
            father = new Node('0', 0);
            if (q1.peek().frequency <= q2.peek().frequency) {
                father.left = q1.poll();

                if (!q1.isEmpty() && q1.peek().frequency < q2.peek().frequency) {
                    father.right = q1.poll();
                } else {
                    father.right = q2.poll();
                }
                //  father.frequency = father.left.frequency+father.right.frequency;
            } else {
                father.left = q2.poll();
                if (!q2.isEmpty() && q2.peek().frequency <= q1.peek().frequency) {
                    father.right = q2.poll();
                } else {
                    father.right = q1.poll();
                }

            }
            father.frequency = father.left.frequency + father.right.frequency;

            q2.add(father);

        }

        if (q2.size() == 2) {
            father = new Node('0', 0);
            father.left = q2.poll();
            father.right = q2.poll();
            father.frequency = father.left.frequency + father.right.frequency;

        }
        printEncode(father, "");


    }

    private static void Huffman(Node[] freq) {
        // taking number of chars.
        int n = freq.length;

        // creating a min   Priority queue using my comparator.
        PriorityQueue<Node> q = new PriorityQueue(n, new sortByFreq());

        // Adding items to the pQueue using add()
        for (Node node : freq) {
            q.add(node);
        }

        for (int i = 0; i < n - 1; i++) {
            Node father = new Node('0', 0); // O(1)
            father.left = q.poll(); // log n
            father.right = q.poll(); // log n
            father.frequency = father.left.frequency + father.right.frequency; // O(1)
            q.add(father); // O(1)
        }

        // total n*logn
        printEncode(q.poll(), "");

    }

    private static void printEncode(Node root, String str) {

        if (root == null) {
            return;
        }

        printEncode(root.left, str + "1");
        printEncode(root.right, str + "0");
        if (root.c != '0')
            System.out.println(root.c + " ->  " + str);

    }
}
