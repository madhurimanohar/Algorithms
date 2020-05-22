package AVLTree;

class Node {
    
    int key;
    int height;
    Node left;
    Node right;

    Node(int k) {
        key = k;
        height = 1;
    }
}

public class AVLTree {
    
    Node root;

    int height(Node N) {
        if (N == null) {
            return 0;
        }
        else {
            return N.height;
        }
    }

    int max(int a, int b) {
        if(a > b) {
            return a;
        }
        else {
            return b;
        }
    }

    public Node right(Node a) {
        Node b = a.left;
        Node n = b.right;

        b.right = a;
        a.left = n;

        a.height = 1 + Math.max(height(a.left), height(a.right));
        b.height = 1 + Math.max(height(b.left), height(b.right));

        return b; // new root
    }

    public Node left(Node b) {
        Node a = b.right;
        Node n = a.left;

        a.left = b;
        b.right = n;

        b.height = 1 + Math.max(height(b.left), height(b.right));
        a.height = 1 + Math.max(height(a.left), height(a.right));

        return a;
    }

    int getBalance(Node N) {
        if (N == null) {
            return 0;
        }
        else {
            return height(N.left) - height(N.right);
        }
    }

    public Node insert(Node n, int key) {
        
        
        if (n == null || n.key == key) {
            return (new Node(key));
        }

        if (key < n.key) {
            n.left = insert(n.left, key);
        }
        else if (key > n.key) {
            n.right = insert(n.right, key);
        }
        else {
            return n;
        }

        n.height = 1 + Math.max(height(n.left), height(n.right));
        
        int b = getBalance(n);

        if (b > 1 && key < n.left.key) {
            return right(n);
        }

        if (b > 1 && key > n.left.key) {
            n.left = left(n.left);
            return right(n);
        }
        
        if (b < -1 && key > n.right.key) {
             return left(n);
        }

        if (b < -1 && key < n.right.key) {
            n.right = right(n.right);
            return left(n);
        }

        return n;
    }

    // pre-order traversal
    void preOrder(Node n) {
        if (n != null) {
            System.out.println(n.key);
            preOrder(n.left);
            preOrder(n.right);
        }
    }

    public static void main(String[] args) {
        AVLTree t = new AVLTree();

        // 100, 200, 150, 170, 165, 180, 220, 163, 164

        t.root = t.insert(t.root, 100);
        t.root = t.insert(t.root, 200);
        t.root = t.insert(t.root, 150);
        t.root = t.insert(t.root, 170);
        t.root = t.insert(t.root, 165);
        t.root = t.insert(t.root, 180);
        t.root = t.insert(t.root, 220);
        t.root = t.insert(t.root, 163);
        t.root = t.insert(t.root, 164);
        
        System.out.println("Preorder Traversal :");
        
        t.preOrder(t.root);
    }
}