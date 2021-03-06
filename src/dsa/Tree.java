/*
 * Copyright (c) 2020.
 * File : Tree.java
 * Author : Ankur
 * Last modified : 15/8/2020
 * Problem Statement at the end of the code
 *
 * All code is for practice purpose only and strictly non-commercial.
 * All rights reserved.
 * Please refer to apache license terms in the project.
 */

package dsa;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Tree {
    public Node root;
    public int size;
    public int height;

    public static class Node{
        public int data;
        public Node left;
        public Node right;

        public Node(int d){
            data = d;
            left = right = null;
        }
    }

    void treeTraversal()
    {
        System.out.print("\nInorder Traversal : ");
        inorderTraversal(root);

        System.out.print("\nPreorder Traversal : ");
        preorderTraversal(root);

        System.out.print("\nPostorder Traversal : ");
        postorderTraversal(root);

        System.out.print("\nLevelorder Traversal : ");
        levelorderTraversal(root);

        System.out.println();
    }

    // (Left, root, right)
    public void inorderTraversal(Node n) {
        if (n==null)
            return;

        inorderTraversal(n.left);
        System.out.print(n.data + " ");
        inorderTraversal(n.right);
    }

    // (Root, left, right)
    public void preorderTraversal(Node n) {
        if (n==null)
            return;

        System.out.print(n.data + " ");
        preorderTraversal(n.left);
        preorderTraversal(n.right);
    }

    // (Left, right, root)
    public void postorderTraversal(Node n) {
        if (n==null)
            return;

        postorderTraversal(n.left);
        postorderTraversal(n.right);
        System.out.print(n.data + " ");
    }

    // Print level by level
    public void levelorderTraversal(Node n){
        java.util.Queue<Node> var = new LinkedList<>();

        var.add(n);
        height = 0;
        while(!var.isEmpty()){
            n = var.poll();
            ++height;
            System.out.print(n.data + " ");

            if(n.left != null)
                var.add(n.left);
            if(n.right != null)
                var.add(n.right);
        }
    }
    void inorderTraversalWithoutRecursion(){
        Stack<Node> st = new Stack<>();
        Node current = root;
        while(current!=null || st.size()>0){
            while(current!=null){
                st.push(current);
                current = current.left;
            }

            current = st.pop();
            System.out.print(current.data + " ");

            current = current.right;
        }
    }

    /* Function to traverse a binary tree without recursion and
       without stack
       https://www.youtube.com/watch?v=wGXB9OWhPTg
    */
    void doMorrisTraversal(){
        Node current = root;
        while(current!=null){
            if(current.left==null){
                // If left child is null, print current node and go to right child
                System.out.print(current.data + " ");
                current = current.right;
            } else {
                Node predecessor = current.left;
                // Find predecessor of current and let it point to current
                while(predecessor.right!=null && predecessor.right != current){
                    predecessor = predecessor.right;
                }

                if(predecessor.right != current){
                    // Move on to left child of current.
                    predecessor.right = current;
                    current = current.left;
                } else{
                    // If we have already visited the predecessor then that will lead to a loop
                    // hence remove right child link to current and print current node
                    predecessor.right = null;
                    System.out.print(current.data + " ");
                    current = current.right;
                }
            }
        }
    }

    void substituteNode(int d){
        if(size==1) {
            if(root.data == d) {
                root = null;
                --size;
            }
            return;
        }

        java.util.Queue<Node> q = new LinkedList<>();
        q.add(root);

        Node keyNode = null;
        Node n = null;
        while(!q.isEmpty()) {
            n = q.poll();

            if(n.data == d)
                keyNode = n;

            if(n.left!=null)
                q.add(n.left);

            if(n.right!=null)
                q.add(n.right);
        }

        if(keyNode!=null) {
            deleteNode(root, n);
            keyNode.data = n.data;
        }
    }

    // Here we do Depth First Traversal
    void deleteNode(Node parent, Node n){
        if(parent.left != null){
            if(parent.left == n) {
                parent.left = null;
                --size;
                return;
            }
        }

        if(parent.right != null){
            if(parent.right == n){
                parent.right = null;
                --size;
                return;
            }
        }

       if(parent.left != null)
           deleteNode(parent.left, n);

       if(parent.right != null)
           deleteNode(parent.right , n);
    }

    // Here nodes are added as and when they arrive. Unorderded tree.
    public void addNode(int d){
        System.out.print(d + " ");
        if(root == null) {
            root = new Node(d);
            ++size;
            return;
        }

        Node n = root;
        java.util.Queue<Node> q = new LinkedList<>();
        q.add(n);

        while(!q.isEmpty()){
            n = q.poll();

            if(n.left==null) {
                n.left = new Node(d);
                ++size;
                return;
            } else q.add(n.left);

            if(n.right==null) {
                n.right = new Node(d);
                ++size;
                return;
            } else q.add(n.right);
        }
    }

    public static void main(String[] args) {
        Tree treeNode = new Tree();
        System.out.print("Input : ");

        for(int i = 0; i<10; ++i)
            treeNode.addNode(ThreadLocalRandom.current().nextInt(100));

        System.out.println();
        treeNode.treeTraversal();

        System.out.println("---------------------------");
        treeNode.addNode(200);
        treeNode.treeTraversal();

        System.out.println("---------------------------");
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        System.out.println("Size : " + treeNode.size + " Height : " + treeNode.height);
        System.out.println("---------------------------");
        treeNode.substituteNode(k);
        treeNode.treeTraversal();
        System.out.println("Size : " + treeNode.size + " Height : " + treeNode.height);
        System.out.println("---------------------------");

        System.out.print("Inorder Traversal : ");
        treeNode.inorderTraversal(treeNode.root);
        System.out.print("\nInorder Traversal without Recursion : ");
        treeNode.inorderTraversalWithoutRecursion();
        System.out.print("\nDo Morris Traversal : ");
        treeNode.doMorrisTraversal();
    }
}
