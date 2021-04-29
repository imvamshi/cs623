package com.wvu.treesimilarity;

import java.util.*;

import static java.lang.Math.PI;
import static java.lang.Math.min;


class TraverseIndex {
    List<String> list;
    List<Node> nodeList;
    Hashtable<Node, Integer> nodeHash;
}


public class Main {
    
    public static Node Tree1Root;
    public static Node Tree2Root;
    //public static Hashtable<Node, Integer> nodeHashA;
   // public static Hashtable<Node, Integer> nodeHashB;

    public static double LCS(String A, String B) {
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
        return dp[A.length()][B.length()] / Math.sqrt(A.length() * B.length());
    }

    public static double TED(String A, String B) {
        int m = A.length();
        int n = B.length();

        int[][] cost = new int[m + 1][n + 1];
        for(int i = 0; i <= m; i++)
            cost[i][0] = i;
        for(int i = 1; i <= n; i++)
            cost[0][i] = i;

        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(A.charAt(i) == B.charAt(j))
                    cost[i + 1][j + 1] = cost[i][j];
                else {
                    int a = cost[i][j];
                    int b = cost[i][j + 1];
                    int c = cost[i + 1][j];
                    cost[i + 1][j + 1] = a < b ? (a < c ? a : c) : (b < c ? b : c);
                    cost[i + 1][j + 1]++;
                }
            }
        }
        return 1 - (cost[m][n] / Math.sqrt(A.length() * B.length()));
    }

    public static int normalizedLCS(String A, String B) {
        return (int) (LCS(A, B) / Math.sqrt(A.length() * B.length()));
    }
    public static int getDepth(Node node) {
        if(node == null) return 0;
        int depth = 0;
        Node it = node.getParent();
        while(it != null) {
            it = it.getParent();
            depth++;
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

    public static List<String> rootToLeafPaths(Node node, List<String> store, String path) {
        if(node == null) {
            return store;
        }
        path = path + node.getValue();
        if(node.isLeaf()) {
            store.add(path);
        } else {
            for(int i = node.getNumberOfChildren() - 1; i >= 0; i--) {
                store = rootToLeafPaths(node.getChildren().get(i), store, path);
            }
        }
        return store;
    }

    public static void printTable(double table[][], int nRow, int nCol) {
        for(int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                System.out.print(fixedLengthString(Double.toString(table[i][j]), 6) + "  ");
            }
            System.out.println();
        }
    }

    public static String fixedLengthString(String string, int length) {
//        return String.format("%010d", string);
        if(length <= string.length()) {
            return string.substring(0, length);
        } else {
            String printSpace = "";
            for (int i = 0; i < length - string.length(); i++) {
                printSpace += " ";
            }
            return string + printSpace;
        }
    }
    
    public static double NormalAverageTable(double table[][], int nRow, int nCol) {
        double sum = 0;
        double avg = 0;
        int numOfElements = nRow * nCol;
        for( int i=0; i < nCol; i++) {
            for( int j = 0; j < nRow; j++){
                sum = sum + table[i][j];
            }
        }
        avg = sum/numOfElements;
        
        return avg;
    }
    
    public static double[][] addTables(double table1[][], double table2[][], int nRow, int nCol) {
        double tableResult[][] = new double[nRow][nCol];
        for( int i=0; i < nCol; i++) {
            for( int j = 0; j < nRow; j++){
                tableResult[i][j] = table1[i][j] + table2[i][j];
            }
        }
        
        return tableResult;
    }
    
    public static void buildDefaultTrees() {
        /* Tree S construction */
        Tree1Root = new Node("a");

        Tree1Root.addChild(new Node("b"));
        Tree1Root.addChild(new Node("d"));
        Node rootsChild = Tree1Root.getLastChild();
        Tree1Root.addChild(new Node("c"));

        rootsChild.addChild(new Node("c"));
        rootsChild.addChild(new Node("d"));

        System.out.println("Root first child " + Tree1Root.getFirstChild().getValue());
        System.out.println("Root last child " + Tree1Root.getLastChild().getValue());
        System.out.println("Root Value " + Tree1Root.getValue());

        /* Tree T construction */
        Tree2Root = new Node("a");

        Tree2Root.addChild(new Node("b"));
        Tree2Root.addChild(new Node("c"));
        rootsChild = Tree2Root.getLastChild();
        Tree2Root.addChild(new Node("d"));

        rootsChild.addChild(new Node("d"));
        rootsChild.addChild(new Node("c"));

        System.out.println("Root first child " + Tree2Root.getFirstChild().getValue());
        System.out.println("Root last child " + Tree2Root.getLastChild().getValue());
        System.out.println("Root Value " + Tree2Root.getValue());
        
    }
    
    public static void buildLongTree1() {

        
        Tree1Root = new Node("a");
        
        /*
        Build tree from bottom lefthand side to top right
        */
        
        //left side
        Node node3 = new Node("a");
            Node node4 = new Node("d");
            Node node5 = new Node("c");     
            node3.addChild(node4);
            node3.addChild(node5);
            
        Node node6 = new Node("b");
            Node node7 = new Node("a");
            Node node8 = new Node("b");
            node6.addChild(node7);
            node6.addChild(node8);
            
        Node node2 = new Node("a");
            node2.addChild(node3);
            node2.addChild(node6);
            
        Tree1Root.addChild(node2);
        
        //middle
        Node node9 = new Node("d");
            Node node10 = new Node("a");
            Node node11 = new Node("b");
            Node node12 = new Node("d");
            node9.addChild(node10);
            node9.addChild(node11);
            node9.addChild(node12);
            
        Node node13 = new Node("c");
            Node node14 = new Node("c");
            Node node15 = new Node("b");
            node13.addChild(node14);
            node13.addChild(node15);
            
        Tree1Root.addChild(node9);
        Tree1Root.addChild(node13);
        
        //right side
        Node node16 = new Node("a");
            Node node17 = new Node("d");
                Node node18 = new Node("b");
                    //adding children 19 and 20
                    node18.addChildren(new String[]{"c", "c"});
                Node node21 = new Node("b");
                    //adding children 22 and 23
                    node21.addChildren(new String[]{"a", "b"});
                node17.addChild(node18);
                node17.addChild(node21);
            node16.addChild(node17);
            
        Tree1Root.addChild(node16);
        
        System.out.println("Root first child " + Tree1Root.getFirstChild().getValue());
        System.out.println("Root last child " + Tree1Root.getLastChild().getValue());
        System.out.println("Root Value " + Tree1Root.getValue());
        
                      
    }
    
    public static String getRandomLetter(){
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        
        return Character.toString(c);
    }
    
    public static int getRandomNumberBetween(int max, int min) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    

    public static Node buildRandomTree(int depth, int maxLeaves){
        
        Node root = new Node(getRandomLetter());
        depth--;
        System.out.println("Depth " + depth);
        int numOfLeaves = getRandomNumberBetween(maxLeaves, 1);
        for (int i=0; i<numOfLeaves; i++){
                String letter = getRandomLetter();
                System.out.println("Adding child " + letter+ " at depth " + depth);
                root.addChild(letter);
        }
        depth--;
        if (depth > 0) {
            List<Node> nodes = root.getChildren();
            for (int i=0; i<numOfLeaves; i++){
                   Node child = nodes.get(i);
                   Node node = buildRandomTree(depth, maxLeaves, child);
                   root.addChild(node);
            }
        }
        
        return root;
    }
    
    public static Node buildRandomTree(int depth, int maxLeaves, Node node){
        
        while(depth > 0) {
            System.out.println("Depth: " + depth);
            depth--;
            int numOfLeaves = getRandomNumberBetween(maxLeaves, 1);
            System.out.println("Number of leaves: " + numOfLeaves);
            for (int i=0; i<numOfLeaves; i++){
                String letter = getRandomLetter();
                System.out.println("Adding child " + letter+ " at depth " + depth);
                node.addChild(letter);
            }

            List<Node> nodes = node.getChildren();
            for (int i=0; i<node.getNumberOfChildren(); i++){
                   Node child = nodes.get(i);
                   node = buildRandomTree(depth, maxLeaves, child);
                   node.addChild(node);
            }
        }
        
        return node;
    }
    

    public static void main(String[] args) {
        
        buildDefaultTrees();
        
//        Tree1Root = buildRandomTree(2, 2);
//        Tree2Root = buildRandomTree(2, 2);

        /* Pre-order traversals of both trees */
        TraverseIndex traverseIndexA = preOrderTraversal(Tree1Root);
        TraverseIndex traverseIndexB = preOrderTraversal(Tree2Root);

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

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
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
//                System.out.println("LCS = " + lcs[i][j]);

                /* If i and j are leaves, then calculate DTW */
                if((i > 0 && j > 0) && nodeListA.get(i - 1).isLeaf() && nodeListB.get(j - 1).isLeaf()) {
                    System.out.println("value = " + ((lcs[i][j] * 1.0)/(Math.sqrt(getDepth(nodeListA.get(i)) * getDepth(nodeListB.get(j))))));
                    dtwLcs[k][l] = 1 - ((lcs[i][j] * 1.0)/(Math.sqrt(getDepth(nodeListA.get(i)) * getDepth(nodeListB.get(j)))));
                    dtwLcs[k][l] += Math.min(dtwLcs[k - 1][l], Math.min(dtwLcs[k][l - 1], dtwLcs[k - 1][l - 1]));
                    l++;
                }
            }
            /* If i is leaf */
            if((i > 0) && nodeListA.get(i - 1).isLeaf()) {
                k++;
            }
        }

        /* dtwLcs array */
        for(int i = 0; i <= noLeavesA; i++) {
            for(int j = 0; j <= noLeavesA * noLeavesB; j++) {
                System.out.print(Double.toString(dtwLcs[i][j]).length() > 4 ? Double.toString(dtwLcs[i][j]).substring(0, 8) + " " : Double.toString(dtwLcs[i][j]) + " ");
//                System.out.print(Double.toString(dtwLcs[i][j]) + " ");
            }
            System.out.println();
        }
        System.out.println();
//        for(int i = 0; i <= m; i++) {
//            for(int j = 0; j <= n; j++) {
//                System.out.print(lcs[i][j] + " ");
//            }
//            System.out.println();
//        }
        System.out.println("Given trees are distant by " + Double.toString(dtwLcs[k - 1][l - 1]) + "\n\n");

        List<String> treeASeqs = new ArrayList<>();
        List<String> treeBSeqs = new ArrayList<>();
        /* MultiDimensional Sequences of Tree A */
        treeASeqs = rootToLeafPaths(Tree1Root, treeASeqs, "");
        /* MultiDimensional Sequences of Tree B */
        treeBSeqs = rootToLeafPaths(Tree2Root, treeBSeqs, "");

        System.out.println("Sequences of Tree A: " + Arrays.toString(treeASeqs.toArray()));
        System.out.println("Sequences of Tree B: " + Arrays.toString(treeBSeqs.toArray()));

        /* dLCS and dTED construction */
        double[][] dLCS = new double[treeASeqs.size()][treeBSeqs.size()];
        double[][] dTED = new double[treeASeqs.size()][treeBSeqs.size()];
        double[][] avg = new double[treeASeqs.size()][treeBSeqs.size()];

        for(int i = 0; i < treeASeqs.size(); i++) {
            for (int j = 0; j < treeBSeqs.size(); j++) {
                dLCS[i][j] = LCS(treeASeqs.get(i), treeBSeqs.get(j));
                dTED[i][j] = TED(treeASeqs.get(i), treeBSeqs.get(j));
            }
        }

        /* Printing dLCS table */
        System.out.println("\ndLCS of sequences");
        printTable(dLCS, treeASeqs.size(), treeBSeqs.size());

        /* Printing dTED table */
        System.out.println("\ndTED of sequences");
        printTable(dTED, treeASeqs.size(), treeBSeqs.size());
        
        double avgOfdLCS = NormalAverageTable(dLCS, treeASeqs.size(), treeBSeqs.size());
            System.out.println("\nAverage of LCS table: " + avgOfdLCS);
        
        double avgOfdTED = NormalAverageTable(dTED, treeASeqs.size(), treeBSeqs.size());
            System.out.println("\nAverage of TED table: " + avgOfdTED);
        
        System.out.println("\nAverage of both averages : " + (avgOfdLCS + avgOfdTED)/2);
        

    }
}