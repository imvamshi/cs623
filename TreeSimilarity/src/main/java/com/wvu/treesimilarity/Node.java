/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wvu.treesimilarity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Towny
 */
public class Node {
    private String Value;
    private int Number = 0;
    private List<Node> Children = new ArrayList<Node>();
    private Node Parent;

    public Node() {

    }

    Node (String value) {
        Value = value;
        Parent = null;
    }

    Node (int value) {
        Value = String.valueOf(value);
        Parent = null;
    }    
    
    Node (String value, Node parent) {
        Value = value;
        Parent = parent;
    }
    
    Node (int value, Node parent) {
        Value = String.valueOf(value);
        Parent = parent;
    }
    
    Node (String value, int nodeNum) {
        Value = value;
        Parent = null;
        Number = nodeNum;
    }
    
    Node (int value, int nodeNum) {
        Value = String.valueOf(value);
        Parent = null;
        Number = nodeNum;
    }    
    
    Node (String value, Node parent, int nodeNum) {
        Value = value;
        Parent = parent;
        Number = nodeNum;
    }
    
    Node (int value, Node parent, int nodeNum) {
        Value = String.valueOf(value);
        Parent = parent;
        Number = nodeNum;
    }
   /* 
    Node (String value, Node parent, List<Node> children) {
        Value = value;
        addChildren(children);
        Parent = parent;
    }
    
    Node (int value, Node parent, List<Node> children) {
        Value = String.valueOf(value);
        Parent = parent;
        addChildren(children);
    }
    
    Node (String value, Node parent, String[] values) {
        Value = value;
        addChildren(values);
        Parent = parent;
    }
    
    Node (int value, Node parent, String[] values) {
        Value = String.valueOf(value);
        Parent = parent;
        addChildren(values);
    }
    
    
    Node (String value, Node parent, Node child) {
        Value = value;
        addChild(child);
        Parent = parent;
    }
    
    Node (int value, Node parent, Node child) {
        Value = String.valueOf(value);
        Parent = parent;
        addChild(child);
    }
    
    Node (String value, Node parent, String child) {
        Value = value;
        addChild(child);
        Parent = parent;
    }
    
    Node (int value, Node parent, String child) {
        Value = String.valueOf(value);
        Parent = parent;
        addChild(child);
    }
    */
    
    public boolean isLeaf(){
        if(Children.isEmpty()){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isRoot() {
        if(Parent == null) {
            return true;
        } else {
            return false;
        }
    }
    
    public void setParent(Node node){
       Parent = node;
    }
    
    public void setValue(String string){
        Value = string;
    }
    
    public void setNumber(int number) {
        Number = number;
    }
    
    public List<Node> getChildren() {
        return Children;
    }
    
    public Node[] getChildrenAsArray() {
        return (Node[]) Children.toArray();
    }
    
    public Node getLastChild() {
        if (Children.isEmpty()){
            return null;
        }
        else {
            return Children.get(Children.size()-1);
        }
    }
    
    public Node getFirstChild() {
        if (Children.isEmpty()){
            return null;
        }
        else {
            return Children.get(0);
        }
    }
    
    public String getValue() {
        return Value;
    }
    
    public Node getParent() {
        return Parent;
    }
    
    public int getNumberOfChildren() {
        return Children.size();
    }
    
    public boolean hasChildren() {
        return !Children.isEmpty();
    }
        
    
    public void addChild(Node node) {
        if(node != this) {
            node.setParent(this);
            Children.add(node);
        } else {
            System.out.println("Eror! Cannot make child the node itself");
        }
    }
    
    public void addChild(String data) {
        Node child = new Node(data, this);
        Children.add(child);
    }
    
    public void addChildren(List<Node> children) {
       for(Node child : children){
           if(child != this){
               addChild(child);
           }
       }
    }
    
    public void addChildren(String[] values) {
        for (String value : values) {
           addChild(value);
        }
    }
    
}
