package com.wvu.treesimilarity;

import java.util.*;

import static java.lang.Math.min;
class TraverseIndex {
    List<String> list;
    List<Node> nodeList;
    Hashtable<Node, Integer> nodeHash;
}
public class Main {

    public static int LCS(String A, String B) {
        /* matrix for storing results of the sub-problems, size is
         * one more than the string length to store the base case
         * results*/
        int[][] dp = new int[A.length() + 1][B.length() + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                /*checking characters at index - 1 as 0th character
                 *  of given String is at 1st index in DP matrix */
                if (A.charAt(i-1) == B.charAt(j-1)) {
                    /*optimal substructure as explained in the article*/
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[A.length()][B.length()];
    }
    public static int normalizedLCS(String A, String B) {
        return (int) (LCS(A, B) / Math.sqrt(A.length() * B.length()));
    }
    public static int getDepth(Node node) {
//        return 1; // only for debugging purposes
        if(node == null) return 0;
        int depth = 0;
        Node it = node.getParent();
        while(it != null) {
//            System.out.print("Parent of " + node.getValue() + " is ");
            it = it.getParent();
            depth++;
//            System.out.println(node.getParent().getValue());
        }
        return depth;
    }

    public  static int getParent(int node) {
        return 0;
    }

    public static TraverseIndex preOrderTraversal(Node root) {
        TraverseIndex traverseIndex = new TraverseIndex();
        List<String> list = new ArrayList<>();
        Hashtable<Node, Integer> nodeHash = new Hashtable<Node, Integer>();
        List<Node> nodeList = new ArrayList<>();
//        nodeHash.put(null, 0); // Index of root's parent is 0

        int nodeCount = 1;
        if(root == null) return traverseIndex;

        Stack<Node> stack = new Stack<>();
        stack.add(root);

        while(!stack.empty()) {
            root = stack.pop();
            list.add(root.getValue());
            nodeHash.put(root, nodeCount++);
            nodeList.add(root);
            System.out.println("Added " + root.getValue() + " id is " + nodeCount);

            for(int i = root.getNumberOfChildren() - 1; i >= 0; i--) {
                stack.add(root.getChildren().get(i));
            }
        }
        traverseIndex.list = list;
        traverseIndex.nodeHash = nodeHash;
        traverseIndex.nodeList = nodeList;
        return traverseIndex;
    }

    public static int getParentIndex(Node node, Hashtable<Node, Integer> nodeHash) {
        if (node.getParent() == null) {
            return 0;
        } else {
            return nodeHash.get(node.getParent());
        }
    }

    public static void main(String[] args) {

        /* Tree S construction */
        Node root = new Node("a");

        root.addChild(new Node("b"));
        root.addChild(new Node("d"));
        Node rootsChild = root.getLastChild();
        root.addChild(new Node("c"));

        rootsChild.addChild(new Node("c"));
        rootsChild.addChild(new Node("d"));

        System.out.println("Root first child " + root.getFirstChild().getValue());
        System.out.println("Root last child " + root.getLastChild().getValue());
        System.out.println("Root Value " + root.getValue());

        /* Tree T construction */
        Node root2 = new Node("a");

        root2.addChild(new Node("b"));
        root2.addChild(new Node("c"));
        rootsChild = root2.getLastChild();
        root2.addChild(new Node("d"));

        rootsChild.addChild(new Node("d"));
        rootsChild.addChild(new Node("c"));

        System.out.println("Root first child " + root2.getFirstChild().getValue());
        System.out.println("Root last child " + root2.getLastChild().getValue());
        System.out.println("Root Value " + root2.getValue());

        /* Pre-order traversals of both trees */
        TraverseIndex traverseIndexA = preOrderTraversal(root);
        TraverseIndex traverseIndexB = preOrderTraversal(root2);

        System.out.println("Pre-Order traversal of tree S");
        System.out.println(Arrays.toString(traverseIndexA.list.toArray()));

        List<Node> nodeListA = traverseIndexA.nodeList;
        Hashtable<Node, Integer> nodeHashA = traverseIndexA.nodeHash;
        /* Below prints the index mentioned in the example 4 */
        for(Node node: nodeListA) {
            System.out.print(node.getValue() + " - (" + nodeHashA.get(node) + ", " + getParentIndex(node, nodeHashA) + ")\n");
        }
        /* Below code to test isLeaf() functionality */
//        for(Node node: nodeListA) {
//            System.out.print("Node " + node.getValue() + " is ");
//            System.out.println((node.isLeaf() == true) ? "Leaf" : "Not Leaf");
//        }

        System.out.println("Pre-Order traversal of tree T");
        System.out.println(Arrays.toString(traverseIndexB.list.toArray()));

        List<Node> nodeListB = traverseIndexB.nodeList;
        Hashtable<Node, Integer> nodeHashB = traverseIndexB.nodeHash;

        /* Below code to test getParent() functionality */
//        for(Node node: nodeListB) {
//            System.out.print("Node " + node.getValue() + "'s parent is ");
//            System.out.println(node.getParent() != null ? node.getParent().getValue() : "NULL");
//        }

        /* Below prints the index mentioned in the example 4 */
        for(Node node: nodeListB) {
            System.out.print(node.getValue() + " - (" + nodeHashB.get(node) + ", " + getParentIndex(node, nodeHashB) + ")\n");
        }

        /* Number of leaves calculation */
        int noLeavesA = 0, noLeavesB = 0;
        for(Node node: nodeListA) if(node.isLeaf()) noLeavesA++;
        for(Node node: nodeListB) if(node.isLeaf()) noLeavesB++;

        /* Code to calculate dtwLcs */
        int m = traverseIndexA.list.size(); // Number of nodes in Tree A
        int n = traverseIndexB.list.size(); // Number of nodes in Tree B
        System.out.println("m = " + m + " n = " +  n);

//        double[][] dtwLcs = new double[m + 1][n + 1];
        double[][] dtwLcs = new double[noLeavesA + 1][noLeavesA * noLeavesB + 1];
        int[][] lcs = new int[m + 1][n + 1];

        lcs[0][0] = 0;
        dtwLcs[0][0] = 0.0;

        for(int i = 0; i <= m ; i++) {
            lcs[i][0] = 0;
        }
        for(int j = 0; j <= n ; j++) {
            lcs[0][j] = 0;
        }

        for(int i = 0; i <= noLeavesA ; i++) {
            dtwLcs[i][0] = 0.0;
        }
        for(int j = 0; j <= noLeavesA * noLeavesB ; j++) {
            dtwLcs[0][j] = 0.0;
        }

        int k = 1, l = 1;
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                /* lcs[i][j] calculation */
                if(i == 0 || j == 0) {
                    lcs[i][j] = 0;
                } else {
                    /* If label(i) equals label(j) */
                    if(nodeListA.get(i - 1).getValue().equals(nodeListB.get(j - 1).getValue())) {
                        lcs[i][j] = 1 + lcs[getParentIndex(nodeListA.get(i - 1), nodeHashA)][getParentIndex(nodeListB.get(j - 1), nodeHashB)];
                    } else {
                        lcs[i][j] = Math.max(lcs[getParentIndex(nodeListA.get(i - 1), nodeHashA)][j], lcs[i][getParentIndex(nodeListB.get(j - 1), nodeHashB)]);
                    }
                }

                /* If i and j are leaves, then calculate DTW */
                if(nodeListA.get(i - 1).isLeaf() && nodeListB.get(j - 1).isLeaf()) {
                    System.out.println("= 1 - " + ((lcs[i][j] * 1.0)/(Math.sqrt(getDepth(nodeListA.get(i)) * getDepth(nodeListB.get(j))))));
                    dtwLcs[k][l] = 1 - ((lcs[i][j] * 1.0)/(Math.sqrt(getDepth(nodeListA.get(i)) * getDepth(nodeListB.get(j)))));
//                    System.out.println(1 - (lcs[i][j] * 1.0)/(Math.sqrt(getDepth(nodeListA.get(i)) * getDepth(nodeListB.get(j)))));
                    dtwLcs[k][l] += Math.min(dtwLcs[k - 1][l], Math.min(dtwLcs[k][l - 1], dtwLcs[k - 1][l - 1]));
                    l++;
                }
            }
            /* If i is leaf */
            if(nodeListA.get(i - 1).isLeaf()) {
                k++;
            }
        }

        for(int i = 0; i <= noLeavesA; i++) {
            for(int j = 0; j <= noLeavesA * noLeavesB; j++) {
                System.out.print(dtwLcs[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Given trees are distant by " + Double.toString(dtwLcs[k - 1][l - 1]));
    }
}
