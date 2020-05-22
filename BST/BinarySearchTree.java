package BST;

import java.util.*;
import java.io.*;

public class BinarySearchTree<T extends Comparable<? super T>> {
    
    private static class BinaryNode<T> {
        
        T element;            // The data in the node
        BinaryNode<T> left;   // Left child
        BinaryNode<T> right;  // Right child
        
        // Constructors
        BinaryNode(T theElement) {
            this(theElement, null, null);
        }

        BinaryNode(T theElement, BinaryNode<T> lt, BinaryNode<T> rt) {
            element  = theElement;
            left     = lt;
            right    = rt;
        }
    }

    private BinaryNode<T> root;
    
    /**
     * Construct the tree.
     */
    public BinarySearchTree() {
        root = null;
    }
    
    public void makeEmpty() {
        root = null;
    }
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public boolean conatins (T x) {
        return contains(x, root);
    }
    
    public T findMin() throws Exception {
        if(isEmpty()) {
            throw new Exception();
        }
        return findMin(root).element;
    }
    
    public T findMax() throws Exception {
        if(isEmpty()) {
            throw new Exception();
        }
        return findMax(root).element;
    }
    
    public void insert(T x) {
        root = insert(x, root);
    }
    
    public void remove(T x) {
        root = remove(x, root);
    }
    
    public void printTree() {
        if( isEmpty()) {
            System.out.println("Empty tree");
        }
        else {
            printTree(root);
        }
    } 
    
    private boolean contains(T x, BinaryNode<T> t) {
        if(t == null) {
            return false;
        }
        
        int compareResult = x.compareTo(t.element);
        
        if(compareResult < 0) {
            return contains(x, t.left);
        }
        else if(compareResult > 0) {
            return contains(x, t.right);
        }
        else {
            return true;    //match
        }
    }
    
    private BinaryNode<T> findMin(BinaryNode<T> t) {
        if(t == null) {
            return null;
        }
        else if(t.left == null) {
            return null;
        }
        else {
            return findMin(t.left);
        }
    }
    
    private BinaryNode<T> findMax(BinaryNode<T> t) {
        if(t != null) {
            while(t.right != null) {
                t = t.right;
            }
        }
        return t;
    }
    
    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that the roots if the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<T> insert(T x, BinaryNode<T> t) {
        if(t == null) {
            return new BinaryNode<>(x, null, null);
        }
        
        int compareResult = x.compareTo(t.element);
        
        if(compareResult < 0) {
            t.left = insert(x, t.left);
        }
        else if(compareResult > 0) {
            t.right = insert(x, t.right);
        }
        else {
            ;   // duplicate; do nothing
        }
        return t;
    }
    
    private BinaryNode<T> remove(T x, BinaryNode<T> t) {
        if(t == null) {
            return t;   // Item not found; do nothing
        }
            
        int compareResult = x.compareTo(t.element);
            
        if(compareResult < 0) {
            t.left = remove(x, t.left);
        }
        else if(compareResult > 0) {
            t.right = remove(x, t.right);
        }
        else if(t.left != null && t.right != null) {// Two children
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        }
        else {
            if(t.left != null) {
                t = t.left;
            }
            else {
                t = t.right;
            }
        }
        return t;
    }
    
    private void printTree(BinaryNode<T> t) {
        if(t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }  

    /**
     * Question 3 - Implement a public method for the above class that 
     * returns the height of the binary tree.
     * @param t
     * @return height of t.left, height of t.right
     */
    public int height(BinaryNode<T> t) {
        if(t == null)
            return -1;
        else
            return 1 + Math.max(height(t.left), height(t.right));    
    }

    /**
     * Question 4 - Write a private method in BinarySearch called passedTest. 
     * This method accepts a BinaryNode and determines if the difference of 
     * height of the right subtree and left subtree is greater than 1. 
     * If it is greater than 1, it should return false and otherwise it should 
     * return true.
     * @return false if >1
     */
    private boolean passedTest(BinaryNode<T> t) {
        if (t == null) {
            return true; //if the tree is empty
        }
        
        /* Need to define variables below if statement otherwise it will 
         * assume that t != null .
         */
        int leftH = height(t.left);
        int rightH = height(t.right);
  
        if (Math.abs(leftH - rightH) <= 1 && passedTest(t.left) && passedTest(t.right)) {
            return true; 
        }

        return false;   // if the above conditions don't pass 
    }
    
    /**
     * Question 5 - Write a private method called allPassed in BinarySearch 
     * that returns true if all the nodes are satisfying the passedTest 
     * condition as defined in problem-3. Note that you may traverse the tree 
     * using any technique that you like.
     * @param t
     */
    private boolean allPassed() {
        if(root != null) {
           return passedTest(root);
        }
        return false;
    }

    /**
     * Question 6 - Implement the function printInOrder.
     */
    public void printInOrder(BinaryNode<T> t){
        if(t !=  null) {
            printInOrder(t.left);
            System.out.printf("%d ",t.element);
            printInOrder(t.right);
        }
    }
    
    /**
     * Question 7 - Implement a method that returns a String that contains 
     * the PostOrder traversal of the binary search tree. 
     * @param t
     */
    public void printPostOrder(BinaryNode<T> t) {
        if (t != null) {
            printPostOrder(t.left); 
            printPostOrder(t.right); 
            System.out.printf("%d ",t.element);
        }
    }

    /**
     * Question 8 - Implement a method that returns a String that contains 
     * the PostOrder traversal of the binary search tree.
     * @param t
     * @return 
     */
    public void printPreOrder(BinaryNode<T> t) {
        if (t != null) {
            System.out.printf("%d ",t.element);
            printPreOrder(t.left); 
            printPreOrder(t.right); 

        }
    }   
    
    public void printInOrder() {     
        printInOrder(root);   
    } 
    
    public void printPostOrder() {     
        printPostOrder(root);  
    } 
    
    public void printPreOrder(){     
        printPreOrder(root); 
    } 
    
    public static void main(String[] args) {
        BinarySearchTree t = new BinarySearchTree();

        t.root = t.insert(100, t.root);
        t.root = t.insert(50, t.root);
        t.root = t.insert(20, t.root);
        t.root = t.insert(75, t.root);
        t.root = t.insert(200, t.root);
        t.root = t.insert(150, t.root);
        t.root = t.insert(170, t.root);
        t.root = t.insert(250, t.root);
        
        System.out.println("Preorder Traversal :");
        
        t.printPreOrder(t.root);
        
        System.out.println();
        
        System.out.println("Postorder Traversal :");
        
        t.printPostOrder(t.root);
        
        
    }
}


