package Searching;

/**
 * @author jiaao@yscredit.com
 * @date 2021/2/4 19:20
 */
public class AVLTree<K extends Comparable,V> {

    private class Node {
        public K key;
        public V value;
        public Node parent;
        public Node left;
        public Node right;
        public long height;
    }

    private Node root;

    private void leftRotation(Node node) {
        if (nodeIsEmpty(node.parent))
            return;
        Node parentNode = node.parent;
        node.parent = parentNode.parent;
        parentNode.right = node.left;
        node.left = parentNode;

        node.height = height(node) + 1;
        parentNode.height = height(parentNode) + 1;
    }

    private void rightRotation(Node node) {
        if (nodeIsEmpty(node.parent))
            return;
        Node parentNode = node.parent;
        node.parent = parentNode.parent;
        parentNode.left = node.right;
        node.right = parentNode;

        node.height = height(node) + 1;
        parentNode.height = height(parentNode) + 1;
    }

    private void leftRightRotation(Node node) {
        if (nodeIsEmpty(node.parent) || nodeIsEmpty(node.parent.parent))
            return;
        leftRotation(node);
        rightRotation(node);
    }
    
    private void rightLeftRotation(Node node) {
        if (nodeIsEmpty(node.parent) || nodeIsEmpty(node.parent.parent))
            return;
        rightRotation(node);
        leftRotation(node);
    }
    
    public void put(K key,V value) {
        root = put(key, value, root);
    }
    
    private Node put(K key,V value,Node node) {
        if (nodeIsEmpty(node)) {
            return createNode(key,value,node);
        }
        int compareValue = compareTo(node.key, key);
        if (compareValue < 0) {
            node.right = put(key, value, node.right);
            if (height(node.right) - height(node.left) > 1) {
                if (compareTo(node.right.key,key) < 0) {
                    leftRotation(node);
                }else {
                    rightLeftRotation(node);
                }
            }
        }else if (compareValue > 0) {
            node.left = put(key, value, node.left);
            if (height(node.left) - height(node.right) > 1) {
                if (compareTo(node.left.key,key) > 0) {
                    rightRotation(node);
                }else{
                    leftRightRotation(node);
                }
            }
        }else {
            node.value = value;
        }

        node.height = height(node) + 1;
        return node;
    }

    public void remove(K key) {
        root = remove(key,root);
    }

    private Node remove (K key,Node node) {
        if (nodeIsEmpty(node)) {
            return null;
        }
        int compareValue = compareTo(node.key, key);
        if (compareValue < 0) {
            node.right = remove(key, node.right);
            if (height(node.left) - height(node.right) > 1) {
                Node leftTree = node.left;
                if (height(leftTree.left) > height(leftTree.right)) {
                    rightRotation(node);
                }else {
                    leftRightRotation(node);
                }
            }
        }else if (compareValue > 0) {
            node.left = remove(key, node.left);
            if (height(node.right) - height(node.left) > 1) {
                Node rightTree = node.right;
                if (height(rightTree.right) > height(rightTree.left)) {
                    leftRotation(node);
                }else{
                    rightLeftRotation(node);
                }
            }
        }else {
            if (!nodeIsEmpty(node.left) && !nodeIsEmpty(node.right)) {
                if (height(node.left) > height(node.right)) {
                    Node maxNode = maxNode(node.left);
                    node.value = maxNode.value;
                    remove(maxNode.key,node.left);
                }else {
                    Node minNode = minNode(node.right);
                    node.value = minNode.value;
                    remove(minNode.key,node.right);
                }
            }else {
                node = nodeIsEmpty(node.left)?node.right:node.left;
            }
        }

        node.height = height(node) + 1;
        return node;
    }

    private Node maxNode(Node node) {
        if (nodeIsEmpty(node)) {
            return null;
        }
        Node rightNode = maxNode(node.right);
        return rightNode == null?node:rightNode;
    }

    private Node minNode(Node node) {
        if (nodeIsEmpty(node)) {
            return null;
        }
        Node leftNode = minNode(node.left);
        return leftNode == null?node:leftNode;
    }

    private int compareTo(K source,K target) {
        return source.compareTo(target);
    }

    private long height(Node node) {
        if (nodeIsEmpty(node))
            return 0L;
        return max(height(node.left),height(node.right)) + 1;
    }

    private Node createNode(K key,V value,Node parentNode) {
        Node node = new Node();
        node.key = key;
        node.value = value;
        node.parent = parentNode;
        node.height = 0L;
        return node;
    }

    private boolean nodeIsEmpty(Node node) {
        if (node == null)
            return true;
        return false;
    }

    private long max(long height1,long height2) {
        return height1 > height2?height1:height2;
    }

    public void preOrder() {
        preOrder(root);
        System.out.println("\n");
    }

    private void preOrder(Node node) {
        if (nodeIsEmpty(node)) {
            return;
        }
        preOrder(node.left);
        System.out.print(node.value + " ");
        preOrder(node.right);
    }
}
