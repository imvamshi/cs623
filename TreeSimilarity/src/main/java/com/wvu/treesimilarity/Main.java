package com.wvu.treesimilarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Stack;
import java.util.Vector;

import static java.lang.Math.min;

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
    public  static int getDepth(int node) {
        return 0;
    }

    public  static int getParent(int node) {
        return 0;
    }

    public static List<String> preOrderTraversal(Node root) {
        List<String> list = new ArrayList<>();
        if(root == null) return list;

        Stack<Node> stack = new Stack<>();
        stack.add(root);

        while(!stack.empty()) {
            root = stack.pop();
            list.add(root.getValue());
            for(int i = root.getNumberOfChildren() - 1; i >= 0; i--) {
                stack.add(root.getChildren().get(i));
            }
        }
        return list;
    }

    public static void main(String[] args) {
        /* Tree S construction */
        Node root = new Node();
        root.setValue("a");

        root.addChild(new Node("b"));
        root.addChild(new Node("d"));
        Node rootsChild = root.getLastChild();
        root.addChild(new Node("c"));

        rootsChild.addChild("c");
        rootsChild.addChild("d");

        System.out.println("Root first child " + root.getFirstChild().getValue());
        System.out.println("Root last child " + root.getLastChild().getValue());
        System.out.println("Root Value " + root.getValue());

        /* Tree T construction */
        Node root2 = new Node();
        root2.setValue("a");

        root2.addChild(new Node("b"));
        root2.addChild(new Node("c"));
        rootsChild = root2.getLastChild();
        root2.addChild(new Node("d"));

        rootsChild.addChild("d");
        rootsChild.addChild("c");

        System.out.println("Root first child " + root2.getFirstChild().getValue());
        System.out.println("Root last child " + root2.getLastChild().getValue());
        System.out.println("Root Value " + root2.getValue());

        /* Pre-order traversals of both trees */
        System.out.println("Pre-Order traversal of tree S");
        System.out.println(Arrays.toString(preOrderTraversal(root).toArray()));

        System.out.println("Pre-Order traversal of tree T");
        System.out.println(Arrays.toString(preOrderTraversal(root2).toArray()));

        // write your code here
        Vector<String> S = new Vector<String>();
        S.add("ab");
        S.add("adc");
        S.add("add");
        S.add("ac");

        /* Code to calculate dtwLcs */
        int m = 4; // Number of nodes in Tree A
        int n = 5; // Number of nodes in Tree B
        double[][] dtwLcs = new double[m][n];
        int[][] lcs = new int[m][n];
        int k = 1, l = 1;
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                /* lcs[i][j] calculation */
//                /* If label(i) equals label(j) */
//                if(true) {
//                    lcs[i][j] = 1 + lcs[getParent(i)][getParent(j)];
//                } else {
//                    lcs[i][j] = Math.max(lcs[getParent(i)][j], lcs[j][getParent(j)]);
//                }
//                /* If i and j are leaves, then calculate DTW */
//                if(true) {
//                    dtwLcs[k][l] = 1 - (lcs[i][j] * 1.0)/(Math.sqrt(getDepth(i) * getDepth(j)));
//                    dtwLcs[k][l] += Math.min(dtwLcs[k - 1][l], Math.min(dtwLcs[k][l - 1], dtwLcs[k - 1][l - 1]));
//                    l++;
//                }
//            }
//            /* If i is leaf */
//            if(true) {
//                k++;
//            }
//        }
        System.out.println("Given trees are distant by " + Double.toString(dtwLcs[k - 1][l - 1]));
    }
}
