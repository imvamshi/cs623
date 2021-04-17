/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wvu.treesimilarity;

/**
 *
 * @author Towny
 */

/*
TODO: Set way to control and manipulate node numbers?
*/
public class Tree {
    private Node Root;
    int Index;
    
    Tree(Node root) {
        Root = root;
    }

    public Tree() {
        Root = null;
    }

    //get Last Node recursively
    public Node getLastNode() {
        Node node = Root;
        while(node.getLastChild() != null){
            node = node.getLastChild();
        }
        return node;
    }
    
    public Node getRoot() {
        return Root;
    }
    
    
    //recursive functions to get size of tree
    //requires testing
    public int getSize() {
        //num is size 1 for root
        int num = 1;
        //call size on root
        return size(Root, num);
    }
    
    private int size(Node node, int num) {
        //get number of children
        num = num + node.getNumberOfChildren();
        //for each child, recursively call self
        for (Node child : node.getChildren()) {
            size(child, num);
        }
        return num;
    }
    
    public void addChildToLastNode(Node child) {
        Node node = getLastNode();
        node.addChild(child);
    }
    
    public void addChildToRoot(Node child) {
        Root.addChild(child);
    }
    
    //TODO: Finish 
    /*public List<Node> getListOfLeafNodes(){
        List<Node> leafNodes = new ArrayList<Node>();
        Node node = Root;
        
        while(node.hasChildren()) {
            
        }
        
        return leafNodes;
    }
    */
    
    /*
    TODO: List of parent nodes??
    TODO: Get Node by Number
    */
    public static void main(String[] args) {
        Tree tree = new Tree(new Node("Hello there"));
    }
}
