package dsa_assignment_08;

import java.util.Random;
import java.util.Stack;

/**
 *
 * @author 1999k
 */
class SkipNode<T> {

    int key;
    T value;
    SkipNode right, down;// The pointer in the lower right direction 

    public SkipNode(int key, T value) {
        this.key = key;
        this.value = value;
    }

    SkipNode(int MIN_VALUE) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

public class SkipList<T> {

    SkipNode headNode;// Head node , entrance 
    int highLevel;// The layer number 
    Random random;// For tossing coins 
    final int MAX_LEVEL = 32;// The biggest layer 

    SkipList() {
        random = new Random();
        headNode = new SkipNode(Integer.MIN_VALUE, null);
        highLevel = 0;
    }

    int steps = -1;

    public SkipNode search(int key) {
        SkipNode team = headNode;
        while (team != null) {
            steps = steps + 1;
            if (team.key == key) {
                return team;
            } else if (team.right == null)// The right side is gone , It can only go down 
            {
                team = team.down;
            } else if (team.right.key > key)// Need to go down to find 
            {
                team = team.down;
            } else // The right side is smaller, the right side 
            {
                team = team.right;
            }
        }
        return null;
    }

    public void add(SkipNode node) {
        int key = node.key;

        SkipNode findNode = search(key);

        if (findNode != null)// If there is this key The node of 
        {

            findNode.value = node.value;

            return;

        }
        Stack<SkipNode> stack = new Stack<SkipNode>();// Storage down nodes , These nodes may be inserted on the right side 

        SkipNode team = headNode;// Find the node to be inserted Find the bottom node .

        while (team != null) {// Search operation 

            if (team.right == null)// The right side is gone , It can only go down 
            {

                stack.add(team);// Record the nodes that once went down 

                team = team.down;

            } else if (team.right.key > key)// Need to go down to find 
            {

                stack.add(team);// Record the nodes that once went down 

                team = team.down;

            } else // towards the right 
            {

                team = team.right;

            }

        }
        int level = 1;// The current number of floors , Add... From the first tier ( The first layer has to add , Add before you judge )

        SkipNode downNode = null;// Keep the precursor node ( namely down The direction of , For the initial null)

        while (!stack.isEmpty()) {

// Insert... In this layer node
            team = stack.pop();// Throw the left node to be inserted 

            SkipNode nodeTeam = new SkipNode(node.key, node.value);// Nodes need to be recreated 

            nodeTeam.down = downNode;// Deal with the vertical direction 

            downNode = nodeTeam;// Mark the new node for next use 

            if (team.right == null) {// The right side is null The description is inserted at the end 

                team.right = nodeTeam;

            } // Horizontal processing 
            else {// There are nodes on the right , Insert between the two 

                nodeTeam.right = team.right;

                team.right = nodeTeam;

            }

// Consider whether you need to go up 
            if (level > MAX_LEVEL)// We have reached the most advanced node 
            {
                break;
            }

            double num = random.nextDouble();//[0-1] random number 

            if (num > 0.5)// Bad luck. It's over 
            {
                break;
            }

            level++;

            if (level > highLevel)// Higher than the current maximum height, but still within the allowable range Need to change head node 
            {

                highLevel = level;

// You need to create a new node 
                SkipNode highHeadNode = new SkipNode(Integer.MIN_VALUE, null);

                highHeadNode.down = headNode;

                headNode = highHeadNode;// change head

                stack.add(headNode);// Next time head

            }

        }
    }

    public void printList() {

        SkipNode teamNode = headNode;

        int index = 1;

        SkipNode last = teamNode;

        while (last.down != null) {

            last = last.down;

        }

        while (teamNode != null) {

            SkipNode enumNode = teamNode.right;

            SkipNode enumLast = last.right;

            System.out.printf("%-8s", "head->");

            while (enumLast != null && enumNode != null) {

                if (enumLast.key == enumNode.key) {

                    System.out.printf("%-5s", enumLast.key + "->");

                    enumLast = enumLast.right;

                    enumNode = enumNode.right;

                } else {

                    enumLast = enumLast.right;

                    System.out.printf("%-5s", "");

                }
            }

            teamNode = teamNode.down;

            index++;

            System.out.println();

        }

    }

    public static void main(String[] args) {

        SkipList<Integer> list = new SkipList<Integer>();

        for (int i = 1; i < 8; i++) {

            list.add(new SkipNode(i, 666));

        }

        System.out.println("");
        list.printList();
        System.out.println("");

        list.steps = 0;
        SkipNode nd1 = list.search(1);
        System.out.println("");
        if (nd1 != null) {
            System.out.println("Searching for : 1 ; Found in " + list.steps + " steps.");
        } else {
            System.out.println("Searching for : 1 ; Not found in " + list.steps + " steps.");
        }

        list.steps = 0;
        SkipNode nd4 = list.search(4);
        System.out.println("");
        if (nd4 != null) {
            System.out.println("Searching for : 4 ; Found in " + list.steps + " steps.");
        } else {
            System.out.println("Searching for : 4 ; Not found in " + list.steps + " steps.");
        }

        list.steps = 0;
        SkipNode nd10 = list.search(10);
        System.out.println("");
        if (nd10 != null) {
            System.out.println("Searching for : 10 ; Found in " + list.steps + " steps.");
        } else {
            System.out.println("Searching for : 10 ; Not found in " + list.steps + " steps.");
        }

        System.out.println("");

    }

}
